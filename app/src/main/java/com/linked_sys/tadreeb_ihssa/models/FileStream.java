package com.linked_sys.tadreeb_ihssa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileStream {
    @SerializedName("_buffer")
    @Expose
    private String buffer;
    @SerializedName("_origin")
    @Expose
    private Integer origin;
    @SerializedName("_position")
    @Expose
    private Integer position;
    @SerializedName("_length")
    @Expose
    private Integer length;
    @SerializedName("_capacity")
    @Expose
    private Integer capacity;
    @SerializedName("_expandable")
    @Expose
    private Boolean expandable;
    @SerializedName("_writable")
    @Expose
    private Boolean writable;
    @SerializedName("_exposable")
    @Expose
    private Boolean exposable;
    @SerializedName("_isOpen")
    @Expose
    private Boolean isOpen;
    @SerializedName("__identity")
    @Expose
    private Object identity;

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public Boolean getWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    public Boolean getExposable() {
        return exposable;
    }

    public void setExposable(Boolean exposable) {
        this.exposable = exposable;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Object getIdentity() {
        return identity;
    }

    public void setIdentity(Object identity) {
        this.identity = identity;
    }
}
