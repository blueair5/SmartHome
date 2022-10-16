package com.seaflower.logs.impl;

import com.seaflower.logs.LogInterface;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author : [seaflower]
 * @version : [v1.0]
 * @description : [一句话描述该类的功能]
 * @createTime : [2022/10/13 14:26]
 * @updateUser : [seaflower]
 * @updateTime : [2022/10/13 14:26]
 * @updateRemark : [说明本次修改内容]
 */

public class LogImpl implements LogInterface {
    Logger logger = Logger.getRootLogger();
    @Override
    public void debug(String msg) {
        // read log4j settings file
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.debug(msg);
    }

    @Override
    public void debug(String msg, Throwable e) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.info(msg);

    }

    @Override
    public void info(String msg) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.info(msg);

    }

    @Override
    public void info(String msg, Throwable e) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

    }

    @Override
    public void warn(String msg) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.warn(msg);

    }

    @Override
    public void warn(String msg, Throwable e) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

    }

    @Override
    public void error(String msg) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.error(msg);

    }

    @Override
    public void error(String msg, Throwable e) {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

    }
}
