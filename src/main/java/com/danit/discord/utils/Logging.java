package com.danit.discord.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Logging {

    private final Logger logger;

    private enum LogType {
        REQUEST, RESPONSE,
    }

    public class Log {
        private final LogType type;

        public Log(LogType type) {
            this.type = type;
        }

        private <Data extends String> String createLogString(String path, Data data) {
            return String.format("%s | %s | %s", type, path, data);
        }

        private <D extends Object> String log(HttpServletRequest req, D data) {
            return createLogString(req.getServletPath(), data.toString());
        }

        public <D extends Object> void info(HttpServletRequest req, D data) {
            logger.info(log(req, data));
        }

        public <D extends Object> void error(HttpServletRequest req, D data) {
            logger.error(log(req, data));
        }
    }

    public final Log request = new Log(LogType.REQUEST);
    public final Log response = new Log(LogType.RESPONSE);

    public Logging(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public static Logging of(Class<?> clazz) {
        return new Logging(clazz);
    }
}
