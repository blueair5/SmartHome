package com.seaflower.smarthome;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/10 14:38]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/10 14:38]
 * @updateRemark : [说明本次修改内容]
 */

public class XMLTest {
    public static void main(String[] args) {
        SAXReader saxReader = new SAXReader();
        Document doc = null;

        try {
            doc = saxReader.read(new File("src/main/resources/Stu.xml"));

            Element root = doc.getRootElement();

            List<Element> list = root.elements();
            for (Element e : list) {
                System.out.println(e.getName());
                System.out.println("---------------");
                List<Element> list1 = e.elements();
                for (Element e1 : list1) {
                    System.out.println(e1.getData());
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();

        }
    }
}
