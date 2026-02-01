package com.speedstudio.pptist.bean;


import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Aippt 0utline API request")
public class AipptOutlineRequest {

    @JsonProperty(required = true, value = "content")
    private String content;

    @JsonProperty(required = true, value = "language")
    private String language;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
