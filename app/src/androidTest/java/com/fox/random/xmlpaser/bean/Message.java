package com.fox.random.xmlpaser.bean;

import com.fox.random.xmlpaser.core.ParserPro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 渠 on 2014/9/4.
 */
public class Message implements ParserPro{
    private ArrayList<Content> contents;
    private String dateTime;
    private String type;
    private String width;
    private String height;
    private String imgThumbUri;
    private String msgDataBytes;
    public Message(){
        contents = new ArrayList<Content>();
        profile.put("dateTime","setDateTime");
        profile.put("type","setType");
        profile.put("width","setWidth");
        profile.put("height","setHeight");
        profile.put("imgThumbUri","setImgThumbUri");
        profile.put("msgDataBytes","setMsgDataBytes");
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImgThumbUri() {
        return imgThumbUri;
    }

    public void setImgThumbUri(String imgThumbUri) {
        this.imgThumbUri = imgThumbUri;
    }

    public String getMsgDataBytes() {
        return msgDataBytes;
    }

    public void setMsgDataBytes(String msgDataBytes) {
        this.msgDataBytes = msgDataBytes;
    }

    @Override
    public String toString() {
        return "Message{" +
                "contents=" + contents +
                ", dateTime='" + dateTime + '\'' +
                ", type='" + type + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", imgThumbUri='" + imgThumbUri + '\'' +
                ", msgDataBytes='" + msgDataBytes + '\'' +
                '}';
    }

    @Override
    public String getTagName() {
        return "msg";
    }

    @Override
    public List<Class> getChildren() {
        ArrayList<Class> children = new ArrayList<Class>();
        children.add(Content.class);
        return children;
    }

    @Override
    public void addChild(ParserPro obj) {
        if (obj instanceof Content){
            contents.add((Content) obj);
        }
    }
}
