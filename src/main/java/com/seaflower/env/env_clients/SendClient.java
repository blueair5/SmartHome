package com.seaflower.env.env_clients;


import com.seaflower.backup.BackupList;
import com.seaflower.env.bean.Environment;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/12 18:33]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/12 18:33]
 * @updateRemark : [说明本次修改内容]
 */

public class SendClient {
    private List<Environment> sendData;
    private List<Environment> backupdata;
    private int port = 7100;
    private String host = "127.0.0.1";
    Socket socket = null;
    private ObjectOutputStream outputStream = null;
    Timer timer = null;

    public void sendClient() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // splicing backup data
                backupdata = BackupList.getBackupFile("src/main/resources/bakcupfile.txt");
                sendData = new CreateEnv().createEnvs();
                if (backupdata != null ) sendData.addAll(backupdata);
                try {
                    socket = new Socket(host, port);
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(sendData);
                    outputStream.flush();
                } catch (IOException e) {
                    // backup data
                    BackupList.backupList(sendData, "src/main/resources/bakcupfile.txt");
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, 1000, 3600000);


    }

    public static void main(String[] args) {
        SendClient sendClient = new SendClient();
        sendClient.sendClient();
    }
}




















