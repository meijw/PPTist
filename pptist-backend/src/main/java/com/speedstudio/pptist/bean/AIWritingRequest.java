package com.speedstudio.pptist.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AIWritingRequest {

    @JsonProperty(required = true, value = "content")
    private String content;

    @JsonProperty(required = true, value = "command")
    private String command;

    @JsonProperty(required = true, value = "model")
    private String model;

    @JsonProperty(required = true, value = "stream")
    private Boolean stream;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }
}
