package com.speedstudio.pptist.controller;

import com.speedstudio.pptist.bean.AipptOutlineRequest;
import com.speedstudio.pptist.bean.AIWritingRequest;
import jakarta.annotation.Nonnull;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/tools")
@CrossOrigin(origins = "*")
class PPTistController {
	private final OpenAiChatModel chatModel;

	@Value("classpath:prompts/aippt-prompt.txt")
	private Resource aipptPromptResource;

	@Value("classpath:prompts/aippt-outline-prompt.txt")
	private Resource aipptOutlinePromptResource;

	@Value("classpath:prompts/ai-writing-prompt.txt")
	private Resource aiWritingPromptResource;

	@Autowired
	public PPTistController(OpenAiChatModel chatModel) {
		this.chatModel = chatModel;
	}

	private String readResourceAsString(Resource resource) {
		try {
			return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read prompt resource", e);
		}
	}

	@GetMapping(value = "/generateStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ChatResponse> generateStream(
			@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return chatModel.stream(new Prompt(new UserMessage(message)));
	}

	@PostMapping(value = "/aippt")
	public Flux<String> generatePPT(@RequestBody AipptOutlineRequest request){
		System.out.println("=========================invoke aippt=========================");

		String systemText = readResourceAsString(aipptPromptResource);
		String userText = request.getContent();

		Message systemMessage = new SystemMessage(systemText);
		Message userMessage = new UserMessage(userText);

		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

		final JsonStreamProcessor processor = new JsonStreamProcessor();

		return chatModel.stream(prompt)
			.map(response -> response.getResult().getOutput().getContent().replace("```json", "").replace("```",""))
			.flatMap(processor::process);
			//.doOnNext(slide -> System.out.println("Emitting slide: " + slide));
    }

	private static class JsonStreamProcessor {
		private final StringBuilder buffer = new StringBuilder();
		private final List<String> completeObjects = new ArrayList<>();
		private int braceDepth = 0;
		private boolean insideObject = false;

		public Flux<String> process(String chunk) {
			for (int i = 0; i < chunk.length(); i++) {
				char c = chunk.charAt(i);

				if (c == '{') {
					if (!insideObject) {
						insideObject = true;
					}
					braceDepth++;
				}

				if (insideObject) {
					buffer.append(c);
				}

				if (c == '}') {
					braceDepth--;
					if (braceDepth == 0 && insideObject) {
						insideObject = false;
						String completeObject = buffer.toString();
						buffer.setLength(0);
						completeObjects.add(completeObject);
					}
				}
			}

			if (!completeObjects.isEmpty()) {
				String[] objects = completeObjects.toArray(new String[0]);
				completeObjects.clear();
				return Flux.fromArray(objects);
			}
			return Flux.empty();
		}
	}

	@PostMapping(value = "/aippt_outline")
	public Flux<String> generateOutline(@RequestBody AipptOutlineRequest request) {
		System.out.println("=========================invoke aippt_outline=========================");

		String systemText = readResourceAsString(aipptOutlinePromptResource);
		String userText = "请用中文，以" + request.getContent() + "为主题，写一个PPT大纲。请用Markdown格式输出。";

		Message systemMessage = new SystemMessage(systemText);
		Message userMessage = new UserMessage(userText);

		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

		return chatModel.stream(prompt).map(response -> {
			String output = response.getResult().getOutput().getContent();
			return skipThinkContent(output);
		});
	}

	@PostMapping(value = "/ai_writing")
	public Flux<String> aiWriting(@RequestBody AIWritingRequest request) {
		System.out.println("=========================invoke ai_writing=========================");

		String systemText = readResourceAsString(aiWritingPromptResource);
		String command = request.getCommand();
		String content = request.getContent();

		Prompt prompt = getPrompt(command, content, systemText);

		return chatModel.stream(prompt).map(response -> {
			String output = response.getResult().getOutput().getContent();
			return skipThinkContent(output);
		});
	}

	@Nonnull
	private static Prompt getPrompt(String command, String content, String systemText) {
		String userText ;
		if ("美化改写".equals(command)) {
			userText = "请对以下文本进行美化改写处理，优化表达方式，保持原意不变：\n" + content;
		} else if ("扩写丰富".equals(command)) {
			userText = "请对以下文本进行扩写丰富处理，增加细节和深度，保持原意：\n" + content;
		} else if ("精简提炼".equals(command)) {
			userText = "请对以下文本进行精简提炼处理，压缩篇幅，保留核心要点：\n" + content;
		} else {
			userText = "请对以下文本进行处理：\n" + content;
		}

		Message systemMessage = new SystemMessage(systemText);
		Message userMessage = new UserMessage(userText);

		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
		return prompt;
	}

	private static final Pattern THINK_TAG_PATTERN = Pattern.compile("```.*?```", Pattern.DOTALL);

	private String skipThinkContent(String input) {
		return THINK_TAG_PATTERN.matcher(input).replaceAll("");
	}
}
