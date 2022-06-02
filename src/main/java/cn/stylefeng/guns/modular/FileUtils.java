package cn.stylefeng.guns.modular;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void writerFile(String recordJson, String filePath) {
        FileWriter fw = null;
        try {
            String filename = filePath + "/" + simpleFormat.format(new Date()) + ".json";
            fw = new FileWriter(filename);
            fw.write(recordJson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
