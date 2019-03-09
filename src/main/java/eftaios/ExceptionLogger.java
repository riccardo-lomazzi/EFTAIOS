package eftaios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class ExceptionLogger extends Logger {

    private static PrintWriter writer;

    protected ExceptionLogger() {
        super("eftaios.logger", null);
    }

    public static void info(Exception e) {
        logExceptionMessage(e.getMessage());
    }

    private synchronized static void logExceptionMessage(String message) {
        writer = getFileWriter();
        if(writer!=null) {
            writer.println(message);
            writer.close();
        }
    }

    private synchronized static PrintWriter getFileWriter() {
        BufferedWriter temp = null;
        String fileName = ".\\GameExceptionLogs\\exceptionLog.txt";
        try {
            checkFile(fileName);
            temp = new BufferedWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PrintWriter(temp);
    }

    private synchronized static void checkFile(String fileName) throws IOException {
        File dir = new File(".\\GameExceptionLogs\\");
        if(!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
    }

}
