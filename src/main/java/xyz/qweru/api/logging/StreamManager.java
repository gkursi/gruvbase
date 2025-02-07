package xyz.qweru.api.logging;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class StreamManager {
    public StreamManager() {}

    PrintStream outputStream = null;
    PrintStream inputStream = new GPrintStream(new OutputStream() {
        @Override
        public void write(int i) {
            outputStream.write(i);
        }

        @Override
        public void flush() {
            outputStream.flush();
        }

        @Override
        public void close() {
            outputStream.close();
        }
    }, true, StandardCharsets.UTF_8);
    Stream current = null;
    public void capture(Stream stream) {
        if(current == null) {
            current = stream;
            switch (stream) {
                case STDOUT -> {
                    outputStream = System.out;
                    System.setOut(inputStream);
                }
                case STDERR -> {
                    outputStream = System.err;
                    System.setErr(inputStream);
                }
            }
        } else if(current != stream){
            switch (stream) {
                case STDOUT -> {
                    System.setErr(outputStream);
                    outputStream = System.out;
                    System.setOut(inputStream);
                    current = Stream.STDOUT;
                }
                case STDERR -> {
                    System.setOut(outputStream);
                    outputStream = System.err;
                    System.setErr(inputStream);
                    current = Stream.STDERR;
                }
            }
        }
    }

    public Stream getCurrent() {
        return current;
    }

    public void write(String data) {
        inputStream.print(data);
    }

    public void flush() {
        inputStream.flush();
    }

    public enum Stream {
        STDOUT,
        STDERR
    }
}
