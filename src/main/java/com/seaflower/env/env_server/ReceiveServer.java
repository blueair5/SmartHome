package com.seaflower.env.env_server;

import com.seaflower.env.bean.Environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/12 19:56]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/12 19:56]
 * @updateRemark : [说明本次修改内容]
 */

public class ReceiveServer {
    private ServerSocket serverSocket;
    private int port = 7100;
    private Socket socket;

    public void serverRun() {
            try {
                serverSocket = new ServerSocket(port);
                while (true) {
                    new SingleSendClient(serverSocket.accept()).start();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public static void main(String[] args) {
        ReceiveServer receiveServer = new ReceiveServer();
        receiveServer.serverRun();
    }

}

class SingleSendClient extends Thread {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private Map<Integer, List<Environment>> map;
    private Collection<Environment> coll;
    // according to different day, put corresponding List into map
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
    private SqlHandle sqlHandle = new SqlHandle();

    public SingleSendClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            coll = (Collection)objectInputStream.readObject();

            // transfer coll to sql program to handle
            if (coll != null) {
                sqlHandle.insertSql((List<Environment>)coll);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
    }


}
