package xyz.qweru.api.logging;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Calendar;

public class GPrintStream extends PrintStream {

    public GPrintStream(OutputStream out) {
        super(out);
    }

    public GPrintStream(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public GPrintStream(OutputStream out, boolean autoFlush, String encoding) throws UnsupportedEncodingException {
        super(out, autoFlush, encoding);
    }

    public GPrintStream(OutputStream out, boolean autoFlush, Charset charset) {
        super(out, autoFlush, charset);
    }

    public GPrintStream(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public GPrintStream(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public GPrintStream(String fileName, Charset charset) throws IOException {
        super(fileName, charset);
    }

    public GPrintStream(File file) throws FileNotFoundException {
        super(file);
    }

    public GPrintStream(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    public GPrintStream(File file, Charset charset) throws IOException {
        super(file, charset);
    }

    @Override
    public void println(int x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(char x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(long x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(float x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(char[] x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(double x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(Object x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(String x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println(boolean x) {
        print(getPrefix() + x);
        newLine();
    }

    @Override
    public void println() {
        newLine();
    }

    public void newLine() {
        print('\n');
    }

    private String getPrefix() {
        Calendar calendar = Calendar.getInstance();
        return ANSI.PURPLE +"[" + Thread.currentThread().getName() + "-" + format(calendar.get(Calendar.HOUR)) + ":" + format(calendar.get(Calendar.MINUTE)) + ":" + format(calendar.get(Calendar.SECOND)) + "] " + ANSI.RESET;
    }

    private String format(int num) {
        return (num < 10 ? "0" : "") + num;
    }
}
