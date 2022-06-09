package cn.stylefeng.guns.modular;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    public Boolean createWord(Map dataMap, String templateName, String filePath, String fileName) throws Exception {

            //创建配置实例
            Configuration configuration = new Configuration();
            //设置编码
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassicCompatible(true);
            //模板存放路径
            configuration.setClassForTemplateLoading(this.getClass(), "/freemarker");
            configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/freemarker"));
            //获取模板文件
            Template template = configuration.getTemplate(templateName,"UTF-8");
            //输出文件
            File outFile = new File(filePath+File.separator+fileName);
            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            //将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
        try {
            //生成文件
            template.process(dataMap, out);
            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
