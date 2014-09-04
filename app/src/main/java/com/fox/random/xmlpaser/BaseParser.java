package com.fox.random.xmlpaser;

import android.text.TextUtils;
import android.util.Log;

import com.fox.random.xmlpaser.core.ParserPro;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Hanshuang 2014年8月22日16:37:2
 */
public class BaseParser {
    private static final String TAG = "BaseParser";
    private static HashMap<String, Class> mProfile = new HashMap<String, Class>();

    private BaseParser(){}

    public static List<ParserPro> parserToList(String xml, Class cls)
            throws Exception {
        init(xml, cls);

        XmlPullParser parser = getXPP(xml);
        int eventType = parser.getEventType();
        ArrayList<ParserPro> result = new ArrayList<ParserPro>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            ParserPro newInstance = parser(parser, cls);
            if (newInstance != null) {
                result.add(newInstance);
                do {
                    ;
                } while (parserChild(parser, newInstance));
            } else {
                Log.d(TAG, "get null");
            }
            eventType = parser.getEventType();
        }

        Log.d(TAG, result.toString());
        return result;
    }


    /**
     * 解析出一个对象
     * @param xml
     * @param cls
     * @return
     * @throws Exception
     */
    public static ParserPro parser(String xml,Class cls) throws Exception{
        init(xml, cls);

        XmlPullParser parser = getXPP(xml);
        int eventType = parser.getEventType();

        ParserPro newInstance = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            newInstance = parser(parser, cls);
            if (newInstance != null) {
                do {
                    ;
                } while (parserChild(parser, newInstance));
                break;
            } else {
                Log.d(TAG, "get null");
            }
            eventType = parser.getEventType();
        }

        return newInstance;
    }

    private static void init(String xml, Class cls) throws Exception {
        if (TextUtils.isEmpty(xml)) {
            throw new Exception("source is empty");
        } else {
            mProfile.clear();
        }

        Object instance = cls.newInstance();
        if (!(instance instanceof ParserPro)) {
            throw new Exception("unSupport class " + cls.getSimpleName());
        } else {
            initProfile((ParserPro) instance, cls);
        }
    }

    private static XmlPullParser getXPP(String xml) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));
        return parser;
    }

    /**
     * 初始化所有的可解析对象
     *
     * @param pro
     * @param cls
     * @throws Exception
     */
    private static void initProfile(ParserPro pro, Class cls)
            throws Exception {
        String tagName = pro.getTagName();
        if (mProfile.containsKey(tagName)){
            return;
        }
        mProfile.put(tagName, cls);
        List<Class> children = pro.getChildren();
        if (children != null) {
            for (Class child : children) {
                Object o = child.newInstance();
                if (!(o instanceof ParserPro))
                    throw new Exception("unSupport class "
                            + o.getClass().getSimpleName());
                initProfile((ParserPro) o, child);
            }
        }
    }

    private static ParserPro parser(XmlPullParser xmlPullParser, Class tClass)
            throws Exception {

        ParserPro newInstance = (ParserPro) tClass.newInstance();
        HashMap<String, String> profile = newInstance.profile;
        int eventType = xmlPullParser.getEventType();
        if (eventType == XmlPullParser.START_TAG) {
            if (profile.isEmpty()) {
                return newInstance;
            } else {
                if (xmlPullParser.getName().equals(newInstance.getTagName())) {
                    int attrCount = xmlPullParser.getAttributeCount();
                    for (int i = 0; i < attrCount; i++) {
                        String attrName = xmlPullParser.getAttributeName(i);
                        String methodName = profile.get(attrName);
                        if (!TextUtils.isEmpty(methodName)) { // 这样写，里面只能是String类型的参数
                            Method method = tClass.getMethod(methodName,
                                    new Class[]{String.class});
                            method.invoke(newInstance,
                                    xmlPullParser.getAttributeValue(i));
                        }
                    }
                } else {
                    newInstance = null; // 当前标签和Class的标签不匹配
                }
            }
        } else {
            newInstance = null; // 当前不是标签的状态不对，非 XmlPullParser.START_TAG
        }

        xmlPullParser.next();
        return newInstance;
    }

    private static boolean parserChild(final XmlPullParser xmlPullParser,
                                      final ParserPro parent) throws Exception {
        boolean haveChild = true;
        int eventType = xmlPullParser.getEventType();
        String tagName = xmlPullParser.getName();
        switch (eventType) {
            case XmlPullParser.START_TAG:
                Class cls = getClassByTag(tagName);
                if (cls != null && parent.getChildren().contains(cls)) { // 判断是否存在解析方案
                    ParserPro child = parser(xmlPullParser, cls);
                    if (child != null) {
                        parent.addChild(child); // 成功赋值一次
                        do {
                            ;
                        } while (parserChild(xmlPullParser, child));
                    }
                    break;
                }
                xmlPullParser.next(); // 未找到解析对象的标签
                break;
            case XmlPullParser.END_TAG:
                if (tagName.equals(parent.getTagName())) {// 当前父节点结束
                    haveChild = false;
                    xmlPullParser.next();
                }
                break;
            case XmlPullParser.TEXT:
                String text = xmlPullParser.getText();
                String methodName = parent.profile.get(ParserPro.TEXT);
                if(!TextUtils.isEmpty(methodName) && !needIgnore(text)){
                    Class clz = parent.getClass();
                    Method method = null;
                    try {
                        method = clz.getMethod(methodName,new Class[]{String.class});
                    }catch (NoSuchMethodException e){
                        e.printStackTrace();
                    }
                    if (method !=null){
                        method.invoke(parent,text);
                    }
                }
                xmlPullParser.next();
                break;
            default:
                xmlPullParser.next();
                break;

        }
        return haveChild;
    }

    private static Class getClassByTag(String tag) {
        return mProfile.get(tag);
    }

    private static boolean needIgnore(String text){
        boolean ignore = false;
        if (TextUtils.isEmpty(text)){
            ignore = true;
        }else if (text.trim().length() == 0){
            ignore = true;
        }
        return ignore;
    }
}
