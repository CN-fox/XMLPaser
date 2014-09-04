package com.fox.random.xmlpaser;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.fox.random.xmlpaser.bean.Message;
import com.fox.random.xmlpaser.core.ParserPro;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void test1(){
        String xmlString = "<msg dateTime=\"2014-08-29 06:36\"  " +
                "type=\"text\" ><content>msg date Time=\"2014-08-29 06:40\" " +
                "type=\"viod\"</content></msg>";

        try {
            List<ParserPro> list = BaseParser.parserToList(xmlString, Message.class);
            assertNotNull(list);
            assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test2(){
        String xmlString ="<msg dateTime=\"2014-08-29 06:36\"  type=\"text\" ><content><msg width=\"100\" height=\"100\" imgThumbUri=\"group1/M00/00/0F/Kj4Y5VPrZYiAdTSYAAAsRTcGRLg923_t.jpg\" msgDataBytes=\"11333\" /></content></msg>";
        try {
            List<ParserPro> list = BaseParser.parserToList(xmlString, Message.class);
            assertNotNull(list);
            assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test3(){
        String xmlString ="<?xml version=\"1.0\"?>\n" +
                "<msg dateTime=\"2014-08-29 06:36\" type=\"text\">\n" +
                "    <content>\n" +
                "        <msg width=\"100\" height=\"100\" imgThumbUri=\"group1/M00/00/0F/Kj4Y5VPrZYiAdTSYAAAsRTcGRLg923_t.jpg\" msgDataBytes=\"11333\" />\n" +
                "    </content>\n" +
                "</msg>";
        try {
            List<ParserPro> list = BaseParser.parserToList(xmlString, Message.class);
            assertNotNull(list);
            assertTrue(list.size()>0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}