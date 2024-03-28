package com.stonedt.spider.util;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Component
public class HuToolUtil {

    public static void downExcel(String jsonArrStr, HttpServletResponse response){
        try {
            HSSFWorkbook sheets = ExcelUtil.jsonToExcel(toArray(jsonArrStr));
            // 配置文件下载
            response.setHeader("content-type", "text/html");
            response.setContentType("text/html");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=data.xlsx");
            OutputStream os = response.getOutputStream();
            sheets.write(os);
            sheets.close();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JSONArray toArray(String jsonArrStr){
        try {
            JSONArray array = JSONUtil.parseArray(jsonArrStr);
            return array;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JSONArray();
    }

}
