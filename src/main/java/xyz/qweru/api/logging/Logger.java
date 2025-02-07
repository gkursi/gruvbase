package xyz.qweru.api.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for System.out / System.err
 */
@SuppressWarnings("unused")
public class Logger {

    private final String name;
    private final boolean logToStdErr;

    /**
     * Better logger fr
     * @param name the name of the logger
     * @param logToStdErr should errors be logged to StdErr
     */
    public Logger(String name, boolean logToStdErr) {
        this.name = ANSI.CYAN + "(" + name + ") " + ANSI.RESET;
        this.logToStdErr = logToStdErr;
    }

    /**
     * Better logger fr
     * @param name the name of the logger
     */
    public Logger(String name) {
        this(name, false);
    }

    public void log(String message, Level level, Object[] args) {
        String text = name + level.prefix + format(message, args) + ANSI.RESET;
        if(logToStdErr && level == Level.ERR) System.err.println(text);
        else System.out.println(text);
    }

    public void info(String message, Object... args) {
        log(message, Level.INFO, args);
    }

    public void warn(String message, Object... args) {
        log(message, Level.WARN, args);
    }

    public void error(String message, Object... args) {
        log(message, Level.ERR, args);
    }

    public enum Level {
        INFO(ANSI.BLUE + "[INFO] "),
        WARN(ANSI.YELLOW + "[WARN] "),
        ERR(ANSI.RED + "[ERR] ");

        final String prefix;
        Level(String prefix) {
            this.prefix = prefix;
        }
    }

    /**
     * Slightly faster than String#format
     * @param str target string
     * @param arg an array of args
     * @return the target string with all <code>{}</code> replaced with args
     */
    private static String format(String str, Object... arg) {
        StringBuilder finalStr = new StringBuilder();
        boolean fp = false;
        int i = 0;
        ArrayList<Object> args = new ArrayList<>(List.of(arg));

        for (char aChar : str.toCharArray()) {
            if (args.isEmpty()) {
                finalStr.append(str.substring(i));
                break;
            }
            if (aChar == '}' && fp) {
                finalStr.append(args.remove(0)); // List.removeFirst is just List.remove but with extra checks (slower) why would I want that????
                fp = false;
            } else if (aChar == '{') {
                fp = true;
            } else {
                if (fp) {
                    finalStr.append('{');
                    fp = false;
                }
                finalStr.append(aChar);
            }
            i++;
        }

        if (fp) {
            finalStr.append('{');
        }

        return finalStr.toString();
    }
}
