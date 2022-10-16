package com.seaflower.env.env_server;

import com.seaflower.backup.BackupList;
import com.seaflower.env.bean.Environment;
import com.seaflower.logs.impl.LogImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/12 21:34]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/12 21:34]
 * @updateRemark : [说明本次修改内容]
 */

public class SqlHandle {
    // load sql settings
    private Properties properties;
    private InputStream inputStream;
    private Connection connection;
    private LogImpl log;

    public void insertSql(List<Environment> coll) {
        properties = new Properties();
        try {
            inputStream = new FileInputStream("src/main/resources/db.properties");
            properties.load(inputStream);
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String passwd = properties.getProperty("passwd");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, passwd);
            PreparedStatement ps = null;
            Map<Integer, List<Environment>> map = grouping(coll);

            connection.setAutoCommit(false);
            for (Map.Entry<Integer, List<Environment>> list : map.entrySet()) {
                String day = String.valueOf(list.getKey());
                String sql = "insert into information_" + day +
                        " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

                int index = 0;
                for (Environment e : list.getValue()) {
                    ps = connection.prepareStatement(sql);
                    ps.setString(1 ,e.getName());
                    ps.setString(2 ,e.getSrcID());
                    ps.setString(3 ,e.getDstID());
                    ps.setString(4 ,e.getDevID());
                    ps.setString(5 ,e.getSensorAddress());
                    ps.setInt(6, e.getCount());
                    ps.setString(7, e.getCmd());
                    ps.setInt(8, e.getData());
                    ps.setInt(9, e.getStatus());
                    ps.setString(10, day);

                    ps.addBatch();
                    index++;

                    if (index % 5 == 0) {
                        ps.executeBatch();
                        connection.commit();
                    }
                }

                ps.executeBatch();
                ps.clearBatch();
                connection.commit();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            try {
                // error, backup coll(list)
                BackupList.backupList(coll, "src/main/resources/sqlBackupFile.txt");
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }


    }

    // grouping
    public static Map<Integer, List<Environment>> grouping(List<Environment> coll) {
        Map<Integer, List<Environment>> map = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        List<Environment> backupData = BackupList.getBackupFile("src/main/resources/sqlBackupFile.txt");
        coll.addAll(backupData);

            // grouping
            if (coll.size() != 0) {
                for (Environment e : coll) {
                    String date = simpleDateFormat.format(e.getGatherTime());
                    int day = Integer.parseInt(date.substring(6, 8));
                    if (map.containsKey(day)) map.get(day).add(e);
                    else {
                        List<Environment> envs = new ArrayList<>();
                        envs.add(e);
                        map.put(day, envs);
                    }
                }
            }
            return map;
    }


}



















