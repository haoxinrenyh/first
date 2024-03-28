package com.stonedt.spider.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Component
public class FileUtil {

    public static void main(String[] args) {
        getFiles();
    }

    public static void getFiles(){
        File file = new File("C:/Users/hk/Desktop/temporary/");
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File f1, File f2) {
                return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
            }
        });
        for(File f : fileList) {
            System.out.print(f.getName()+",");
            System.out.print(f.length()+", ");
            System.out.println( new Date(f.lastModified()) );
        }
    }


    public static void downFile(String loadUrl,String dir){
        try {
            String fileName = loadUrl.substring( loadUrl.lastIndexOf("/"));
            if(fileName!=null && !fileName.equals("")){
                URL url = new URL(loadUrl);
                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/4.76");
                InputStream in = conn.getInputStream();
                OutputStream out = new FileOutputStream(dir+fileName);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean checkFilePath(String path){
        try {
            File file =new File(path);
            if(!file.exists() && !file.isDirectory()){
                file.mkdirs();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
