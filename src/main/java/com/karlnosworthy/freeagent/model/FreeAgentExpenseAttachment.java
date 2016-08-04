package com.karlnosworthy.freeagent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeAgentExpenseAttachment {
    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("file_name")
    @Expose
    private String fileName;

    @SerializedName("description")
    @Expose
    private String description;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("content_type")
    @Expose
    private String contentType;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
