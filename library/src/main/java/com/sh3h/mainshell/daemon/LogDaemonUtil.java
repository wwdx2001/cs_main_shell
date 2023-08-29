/**
 * @author qiweiwei
 */
package com.sh3h.mainshell_chaobiaoji.daemon;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogDaemonUtil {

    private static Logger _logger = null;

    public static void initLogger(String folder) {
        if (folder == null) {
            folder = "sh3h/citymanager/log";
        }

        LogManager lm = LogManager.getLogManager();
        File sdDir = Environment.getExternalStorageDirectory();
        File logDir = new File(sdDir, folder);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        String name = String.format("log-daemon-%d", System.currentTimeMillis());
        File logFile = new File(logDir, name);

        initLogWithFluentAPI(lm, logFile.getPath());
    }

    private static void initLogWithFluentAPI(LogManager lm, String logFilePath) {
        _logger = lm.getLogger(TextDaemonUtil.EMPTY);

        try {
            _logger.addHandler(new FileHandler(logFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        _logger.setLevel(Level.ALL);
    }

    private synchronized static void append(Level level, String tag, Throwable tr, String format, Object[] args) {
        append(level, tag, String.format(format, args), tr);
    }

    private synchronized static void append(Level level, String tag, String msg, Throwable tr) {
        if (_logger != null) {
            _logger.log(level, String.format("TAG:%s, %s", tag, msg), tr);
        }
    }

    private synchronized static void append(Level level, String tag, String format, Object[] args) {
        append(level, tag, String.format(format, args));
    }

    private synchronized static void append(Level level, String tag, String msg) {
        if (_logger == null) {
            LogDaemonUtil.initLogger(null);
        }

        _logger.log(level, String.format("TAG:%s, %s", tag, msg));
    }

    public synchronized static void d(String tag, String msg) {
        append(Level.CONFIG, tag, msg);
    }

    public synchronized static void d(String tag, String format, Object... args) {
        append(Level.CONFIG, tag, format, args);
    }

    public synchronized static void i(String tag, String msg) {
        append(Level.INFO, tag, msg);
    }

    public synchronized static void i(String tag, String format, Object... args) {
        append(Level.INFO, tag, format, args);
    }

    public synchronized static void w(String tag, String msg) {
        append(Level.WARNING, tag, msg);
    }

    public synchronized static void w(String tag, String format, Object... args) {
        append(Level.WARNING, tag, format, args);
    }

    public synchronized static void w(String tag, String msg, Throwable tr) {
        append(Level.WARNING, tag, msg);
    }

    public synchronized static void w(String tag, Throwable tr, String format, Object... args) {
        append(Level.WARNING, tag, tr, format, args);
    }

    public synchronized static void e(String tag, String msg) {
        append(Level.SEVERE, tag, msg);
    }

    public synchronized static void e(String tag, String format, Object... args) {
        append(Level.SEVERE, tag, format, args);
    }

    public synchronized static void e(String tag, String msg, Throwable tr) {
        append(Level.SEVERE, tag, msg, tr);
    }

    public synchronized static void e(String tag, Throwable tr, String format, Object... args) {
        append(Level.SEVERE, tag, tr, format, args);
    }
}
