package com.seaflower.smarthome.clients;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/10 17:05]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/10 17:05]
 * @updateRemark : [说明本次修改内容]
 */

public class TempClient {
    private Timer timer = null;

    // socket
    private int port = 10010;
    private String host = "127.0.0.1";
    private Socket socket = null;
    private BufferedWriter writer;
    private BufferedReader reader;
    private BufferedWriter writerToFile;

    // data
    private String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n" +
            "<Message> \r\n" +
            "    <SrcID>100</SrcID> \r\n" +
            "    <DstID>101</DstID> \r\n" +
            "    <DevID>2</DevID> \r\n" +
            "    <SensorAddress>16</SensorAddress> \r\n" +
            "    <Counter>1</Counter> \r\n" +
            "    <Cmd>3</Cmd> \r\n" +
            "    <Status>1</Status>\r\n" +
            "</Message>\r\n";

    public void execute() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    // transfer data to server
                    socket = new Socket(host, port);
                    writer = new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(data);
                    writer.flush();

                    // receive data from server
                    reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    String reData = "";
                    String comment;
                    String res = "";

                    while ((comment = reader.readLine()) != null) {
                        reData += comment;
                        if ("</Message>".equals(comment)) break;
                    }

                    Document document = DocumentHelper.parseText(reData);
                    Element root = document.getRootElement();
                    List<Element> list = root.elements();
                    for (Element e : list) {
                        if ("SensorAddress".equals(e.getName())) e.setText("16");
                        if ("Counter".equals(e.getName())) e.setText("1");
                        res += e.getData() + "|";
                    }
                    res += System.currentTimeMillis();

                    // write to file
                    writerToFile = new BufferedWriter(
                            new FileWriter("data/alldata.txt",true));
                    writerToFile.write(res + "\n");
                    writerToFile.flush();

                } catch (IOException e) {
                    e.printStackTrace();

                } catch (DocumentException e) {
                    e.printStackTrace();

                } finally {
                    if (writer != null) {
                        try {
                            writer.close();

                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                }

            }
        },1000,1000);
    }

    public static void main(String[] args) {
        TempClient tempClient = new TempClient();
        tempClient.execute();
    }

}
