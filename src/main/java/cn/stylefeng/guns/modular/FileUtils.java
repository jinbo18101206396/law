package cn.stylefeng.guns.modular;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FileUtils {

    private static final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

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

//    public Boolean createWord(Map dataMap, String templateName, String filePath, String fileName) throws Exception {
//
//            //创建配置实例
//            Configuration configuration = new Configuration();
//            //设置编码
//            configuration.setDefaultEncoding("UTF-8");
//            configuration.setClassicCompatible(true);
//            //模板存放路径
//            configuration.setClassForTemplateLoading(this.getClass(), "/freemarker");
//            configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/freemarker"));
//            //获取模板文件
//            Template template = configuration.getTemplate(templateName,"UTF-8");
//            //输出文件
//            File outFile = new File(filePath+File.separator+fileName);
//            //如果输出目标文件夹不存在，则创建
//            if (!outFile.getParentFile().exists()){
//                outFile.getParentFile().mkdirs();
//            }
//            //将模板和数据模型合并生成文件
//            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
//        try {
//            //生成文件
//            template.process(dataMap, out);
//            //关闭流
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    public void downloadRecord(HttpServletResponse response,String fileName) throws IOException {
        File file = new File(fileName);
        String filename = file.getName();
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStream fis = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
        //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
        // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        // 告知浏览器文件的大小
        response.addHeader("Content-Length", "" + file.length());
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        outputStream.write(buffer);
        outputStream.flush();
    }
}
