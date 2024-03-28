package com.stonedt.spider.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class DownFileUtil {
    public static void main(String[] args) {
        doZip(new File("E:/_temporary/zip/file/"),"E:/_temporary\\zip\\zip/data.zip");
    }
    public static boolean doZip(File dir, String zip){
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zip))) {
            // 创建 File 对象
            File folder = dir;
            // 获取文件夹下所有文件
            File[] files = folder.listFiles();
            // 遍历文件数组
            for (File file : files) {

                // 创建 ZipEntry 对象
                ZipEntry zipEntry = new ZipEntry("data/" + file.getName());
                // 添加到压缩包中
                zipOutputStream.putNextEntry(zipEntry);
                // 创建 FileInputStream 对象
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    // 读取文件内容并写入压缩包
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);
                    }
                }
            }
            System.out.println("压缩完成！");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void downFile(File file, HttpServletResponse response){
        try {
            // 读到流中
            InputStream inputStream = new FileInputStream(file);
            response.reset();
            response.setContentType("application/octet-stream");
            String filename = file.getName();
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
