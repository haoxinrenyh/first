package com.stonedt.spider.util;
 
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
 
import java.io.File;
 
public class OCRUtil {
    public static void main(String[] args) {
        //验证码图片存储地址
        File imageFile = new File("/Users/wangyi/Downloads/verify.png");
        Tesseract tessreact = new Tesseract();
        tessreact.setDatapath("/Users/wangyi/Downloads/tesseract-main/tessdata");
        String result;
        try {
            result = "识别结果：" + tessreact.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}