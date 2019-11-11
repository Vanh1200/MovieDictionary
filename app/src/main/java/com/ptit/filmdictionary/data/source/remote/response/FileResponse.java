package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 10/11/2019
 */
public class FileResponse {
    @SerializedName("fieldname")
    private String fieldName;

    @SerializedName("originalname")
    private String originalName;

    @SerializedName("encoding")
    private String encoding;

    @SerializedName("mimetype")
    private String mimeType;

    @SerializedName("name")
    private String name;

    @SerializedName("path_lower")
    private String pathLower;

    @SerializedName("path_display")
    private String pathDisplay;

    @SerializedName("id")
    private String id;

    @SerializedName("client_modified")
    private String clientModified;

    @SerializedName("server_modified")
    private String serverModified;

    @SerializedName("rev")
    private String rev;

    @SerializedName("size")
    private long size;

    @SerializedName("is_downloadable")
    private boolean isDownloadable;

    @SerializedName("content_hash")
    private String contentHash;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathLower() {
        return pathLower;
    }

    public void setPathLower(String pathLower) {
        this.pathLower = pathLower;
    }

    public String getPathDisplay() {
        return pathDisplay;
    }

    public void setPathDisplay(String pathDisplay) {
        this.pathDisplay = pathDisplay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientModified() {
        return clientModified;
    }

    public void setClientModified(String clientModified) {
        this.clientModified = clientModified;
    }

    public String getServerModified() {
        return serverModified;
    }

    public void setServerModified(String serverModified) {
        this.serverModified = serverModified;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isDownloadable() {
        return isDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }

    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }
}
