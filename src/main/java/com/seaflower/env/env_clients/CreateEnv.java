package com.seaflower.env.env_clients;

import com.seaflower.env.bean.Environment;
import com.sun.media.jfxmediaimpl.HostUtils;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/11 20:13]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/11 20:13]
 * @updateRemark : [说明本次修改内容]
 */

public class CreateEnv {
    public List<Environment> createEnvs() {
        List<Environment> envs = new ArrayList<>();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(
                    new File("data/alldata.txt"), "r");

            String content = null;
            // get RandomAccessFile point from a file
            long point = getPoint();
            randomAccessFile.seek(point);
            while ((content = randomAccessFile.readLine()) != null) {
                String[] arr = content.split("[|]");
                Environment env = new Environment();
                // handle different data type
                switch (arr[3]) {
                    case "16" : env = createEvnObj(arr, "Temperature"); break;
                    case "256" : env = createEvnObj(arr, "light"); break;
                    case "1280" : env = createEvnObj(arr, "carbon");
                }
                envs.add(env);
            }

            // save RandomAccessFile point to a file
            point = randomAccessFile.getFilePointer();
            savePoint(point);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return envs;
    }

    // get RandomAccessFile point
    public static long getPoint() {
        long point = 0;
        DataInputStream dataInputStream = null;
        File file = new File("src/main/resources/RandomAccessFilePoint.txt");
        if (file.exists()) {
            try {
                dataInputStream = new DataInputStream(
                        new FileInputStream(file));
                point = dataInputStream.readLong();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException  e) {
                e.printStackTrace();
            } finally {
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return point;
    }

    // save point
    public static void savePoint(Long point) {
        DataOutputStream outData = null;
        try {
            outData = new DataOutputStream(
                    new FileOutputStream("src/main/resources/RandomAccessFilePoint.txt"));
            outData.writeLong(point);
            outData.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outData != null) {
                try {
                    outData.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // create Environment Object
    public static Environment createEvnObj(String[] arr, String name) {
        int four = 0;
        int six = 0;
        int seven = 0;
        four = Integer.parseInt(arr[4]);
        seven = Integer.parseInt(arr[7]);

        // handle data
        Long data = Long.valueOf(arr[6].substring(0, 4), 16);
        six = data.intValue();
        if ("Temperature".equals(name)) six = (int)(six * 0.00268127 - 46.85);

        // get Date time
        long eight = Long.parseLong(arr[8]);
        Timestamp timestamp = new Timestamp(eight);
        Environment env = new Environment(name, arr[0], arr[1], arr[2], arr[3], four, arr[5], six, seven, timestamp);
        return env;
    }

    public static void main(String[] args) {
    }

}
