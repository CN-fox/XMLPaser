package com.fox.random.xmlpaser.bean;

import com.fox.random.xmlpaser.core.ParserPro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 渠 on 2014/9/4.
 */
public class Content implements ParserPro {
    private ArrayList<Message> messagesList;
    private String msg;

    public Content(){
        profile.put(TEXT,"setMsg");
        messagesList = new ArrayList<Message>();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Content{" +
                "messagesList=" + messagesList +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public String getTagName() {
        return "content";
    }

    @Override
    public List<Class> getChildren() {
        ArrayList<Class> children = new ArrayList<Class>();
        children.add(Message.class);
        return children;
    }

    @Override
    public void addChild(ParserPro obj) {
        if (obj instanceof Message){
            messagesList.add((Message) obj);
        }
    }
}
