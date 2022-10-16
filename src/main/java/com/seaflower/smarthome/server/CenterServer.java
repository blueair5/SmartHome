package com.seaflower.smarthome.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/10 17:55]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/10 17:55]
 * @updateRemark : [说明本次修改内容]
 */

public class CenterServer {
    private ServerSocket serverSocket;
    int port = 10010;

    public void serverRun() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new ServerThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}

class ServerThread extends Thread {
    private Socket socket;
    private String reData = "";
    // io
    private BufferedReader reader;
    private BufferedWriter writer;
    private String comment;

    // the data return to client
    private String varData;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        GetVarData getVarData = new GetVarData();
        String comment;

            try {
                reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                while ((comment = reader.readLine()) != null) {
                    reData += comment;
                    if ("</Message>".equals(comment)) break;
                }

                Document document = DocumentHelper.parseText(reData);
                Element root = document.getRootElement();
                List<Element> list = root.elements();

                for (Element e : list) {
                    if ("SensorAddress".equals(e.getName())) {
                        varData = getVarData.getVarData((String)e.getData());
                    }
                }

                // return varData
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                writer.write(varData);
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (DocumentException e) {
                e.printStackTrace();

            }

    }

    public static void main(String[] args) {
        CenterServer centerServer = new CenterServer();
        centerServer.serverRun();
    }
}
