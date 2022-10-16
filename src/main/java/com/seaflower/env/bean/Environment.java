package com.seaflower.env.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/11 19:58]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/11 19:58]
 * @updateRemark : [说明本次修改内容]
 */

public class Environment implements Serializable {
    private String name;
    private String srcID;
    private String dstID;
    private String devID;
    private String sensorAddress;
    private int count;
    private String cmd;
    private int data;
    private int status;
    private Timestamp gatherTime;

    public Environment() {}

    public Environment(String name, String srcID, String dstID,
                       String devID, String sensorAddress, int count,
                       String cmd, int data, int status, Timestamp gatherTime) {
        this.name = name;
        this.srcID = srcID;
        this.dstID = dstID;
        this.devID = devID;
        this.sensorAddress = sensorAddress;
        this.count = count;
        this.cmd = cmd;
        this.data = data;
        this.status = status;
        this.gatherTime = gatherTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcID() {
        return srcID;
    }

    public void setSrcID(String srcID) {
        this.srcID = srcID;
    }

    public String getDstID() {
        return dstID;
    }

    public void setDstID(String dstID) {
        this.dstID = dstID;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getSensorAddress() {
        return sensorAddress;
    }

    public void setSensorAddress(String sensorAddress) {
        this.sensorAddress = sensorAddress;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(Timestamp gatherTime) {
        this.gatherTime = gatherTime;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "name='" + name + '\'' +
                ", srcID='" + srcID + '\'' +
                ", dstID='" + dstID + '\'' +
                ", devID='" + devID + '\'' +
                ", sensorAddress='" + sensorAddress + '\'' +
                ", count=" + count +
                ", cmd='" + cmd + '\'' +
                ", data=" + data +
                ", status=" + status +
                ", gatherTime=" + gatherTime +
                '}';
    }
}
