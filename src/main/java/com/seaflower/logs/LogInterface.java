package com.seaflower.logs;

public interface LogInterface {
    // debug stage
    void debug(String msg);
    void debug(String mag, Throwable e);

    // common output
    void info(String msg);
    void info(String msg, Throwable e);

    // warning
    void warn(String msg);
    void warn(String msg, Throwable e);

    // Error
    void error(String msg);
    void error(String msg, Throwable e);
}
