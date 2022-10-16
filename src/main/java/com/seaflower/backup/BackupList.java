package com.seaflower.backup;

import com.seaflower.env.bean.Environment;

import java.io.*;
import java.util.List;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/16 19:21]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/16 19:21]
 * @updateRemark : [说明本次修改内容]
 */

public class BackupList {
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;

    // Put the data to be backed up into the file
    public static void backupList(List<Environment> coll, String backupPath) {
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(backupPath));
            objectOutputStream.writeObject(coll);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // get the data from backup file
    public static List<Environment> getBackupFile(String backupPath) {
        List<Environment> coll = null;
        File file = new File(backupPath);

        if (file.exists()) {
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(backupPath));
                coll = (List<Environment>) objectInputStream.readObject();
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return coll;
    }

}



















