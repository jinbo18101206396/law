package cn.stylefeng.guns.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @ClassName WordUtil
 * @Description 使用Freemarker生成Word文档工具类
 * @Author jinbo
 * @Date 2022/6/8
 **/
public class WordUtil {
    /**
     * 使用Freemarker自动生成Word文档
     *
     * @param dataMap      保存Word文档中所需要的数据
     * @param templatePath 模板文件的绝对路径
     * @param templateFile 模板文件的名称
     * @param generateFile 生成文件的路径+名称
     * @throws Exception
     */
    public static void generateWord(Map<String, Object> dataMap, String templatePath, String templateFile, String generateFile) {
        // 设置FreeMarker的版本
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        // 设置Freemarker的编码格式
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        Writer out = null;
        try {
            // 设置FreeMarker生成Word文档所需要的模板的路径
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            // 设置FreeMarker生成Word文档所需要的模板名称
            Template template = configuration.getTemplate(templateFile, "UTF-8");
            // 创建一个Word文档的输出流
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(generateFile)), StandardCharsets.UTF_8));
            //FreeMarker使用Word模板和数据生成Word文档
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}