package com.stonedt.spider.controller.spider;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.spider.aop.Token;
import com.stonedt.spider.controller.BaseController;
import com.stonedt.spider.entity.ResultNote;
import com.stonedt.spider.service.ResultService;
import com.stonedt.spider.util.DownFileUtil;
import com.stonedt.spider.util.FileUtil;
import com.stonedt.spider.util.HuToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/result")
public class ResultController extends BaseController {

    @Autowired
    private ResultService resultService;

    @Value("${zip.path}")
    private String zipPath;


    @Token
    @GetMapping("/statistic")
    public Object statistic(){
        try {
            return success_int(200,"查询成功",resultService.getStatistic());
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败","");
    }


    @Token
    @GetMapping("/page")
    public Object page(HttpServletRequest request,
                       @RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                       @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                       @RequestParam(value = "category_id",required = false,defaultValue = "0")int category_id,
                       @RequestParam(value = "esindex",required = false)String esindex,
                       @RequestParam(value = "keyword",required = false) String keyword ){
        try {
            int beginIndex = (pageNum-1)*pageSize;

            return success_int(200,"查询成功！", resultService.pageList(beginIndex,pageSize,category_id,esindex,keyword));
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败,请检查参数是否有误!","");
    }

    @Token
    @GetMapping("/info")
    public Object info(HttpServletRequest request,
                       @RequestParam(value = "id",required = false)String id,
                       @RequestParam(value = "esindex",required = false)String esindex){
        try {
            if(id!=null && !"".equals(id)){
                return success_int(200,"查询成功！", resultService.info(id,esindex));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败,请检查参数是否有误!","");
    }

    @GetMapping("/note/page")
    public Object note_page(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                            @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                            @RequestParam(value = "keyword",required = false) String keyword){
        try {
            int beginIndex = (pageNum-1)*pageSize;
            String limit = "limit "+beginIndex+","+pageSize;

            List<ResultNote> data = resultService.pageResultNote(limit,keyword);
            int count = resultService.noteCount(keyword);

            Map<String,Object> result = new HashMap<>();
            result.put("list", data);
            result.put("total", count);
            return success_int(200,"查询成功！", result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"查询失败,请检查参数是否有误!","");
    }

    @PostMapping("/note/save")
    public Object note_save(@RequestBody ResultNote resultNote){
        try {
            if(resultNote!=null && resultNote.getId()==0 && !"".equals(resultNote.getEnglish_key()) && resultNote.getEnglish_key()!=null && !"".equals(resultNote.getChina_key()) && resultNote.getChina_key()!=null ){
                ResultNote checkInfo = resultService.infoNote(resultNote.getEnglish_key());
                if(checkInfo!=null){
                    return error_int(502,"插入失败,关键词重复!","");
                }
                int insertType = resultService.saveNote(resultNote);
                return success_int(200,"插入成功！", insertType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"插入失败,请检查参数是否有误!","");
    }

    @PostMapping("/note/update")
    public Object note_update(@RequestBody ResultNote resultNote){
        try {
            if(resultNote!=null && resultNote.getId()>0 && !"".equals(resultNote.getEnglish_key()) && resultNote.getEnglish_key()!=null && !"".equals(resultNote.getChina_key()) && resultNote.getChina_key()!=null ){
                ResultNote checkInfo = resultService.infoNote(resultNote.getEnglish_key());
                if(checkInfo!=null && checkInfo.getId()!=resultNote.getId() ){
                    return error_int(502,"更新失败,关键词重复!","");
                }
                int insertType = resultService.updateNote(resultNote);
                return success_int(200,"更新成功！", insertType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"更新失败,请检查参数是否有误!","");
    }



    @GetMapping("/note/remove")
    public Object note_remove(@RequestParam(value = "id",required = false,defaultValue = "1")int id){
        try {
            if(id>0){
                int removeType = resultService.removeNote(id);
                return success_int(200,"删除成功！", removeType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return error_int(501,"删除失败,请检查参数是否有误!","");
    }


    @GetMapping("/downloadFile")
    public void textFile(HttpServletResponse response,
                         @RequestParam(value = "category_id",required = false,defaultValue = "0")int category_id,
                         @RequestParam(value = "esindex",required = false)String esindex,
                         @RequestParam(value = "ids",required = false)String ids ){
        try {

            /*JSONObject result_json = resultService.info(id,esindex);
            JSONArray paramArr = new JSONArray();
            paramArr.add(result_json);*/

            String body = resultService.list(category_id, esindex, ids);
            HuToolUtil.downExcel(body,response);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/downloadZip")
    public Object downloadZip(HttpServletResponse response,
                            @RequestParam(value = "id",required = false)String id ,
                            @RequestParam(value = "esindex",required = false)String esindex ){
        try {

            JSONObject result_json = resultService.info(id,esindex);

            List<String> downUrlList = new ArrayList<>();
            if(result_json!=null && result_json.getJSONObject("attachmentData")!=null){
                JSONObject attachmentData_json = result_json.getJSONObject("attachmentData");
                String[] keys = new String[]{"imgList","videoList","wordList","pdfList","excelList","pptList","otherList"};

                for (int i = 0; i < keys.length; i++) {
                    JSONArray array = attachmentData_json.getJSONArray(keys[i]);
                    if(array!=null && array.size()>0){
                        for (int j = 0; j < array.size(); j++) {
                            String url = array.getString(j);
                            if(url!=null && "http".equals(url.substring(0,4))){
                                downUrlList.add(url);
                            }
                        }
                    }
                }
            }

            if(downUrlList.size()>0){
                String time = System.currentTimeMillis()+"";
                String downFilePath = zipPath+"file/"+time+"/";
                String downZipPath = zipPath+"zip/"+time+"/";
                if(FileUtil.checkFilePath(downFilePath) && FileUtil.checkFilePath(downZipPath)){
                    for (String downUrl : downUrlList) {
                        try {
                            FileUtil.downFile(downUrl,downFilePath);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    boolean flag = DownFileUtil.doZip(new File(downFilePath),downZipPath+"data.zip");
                    if(flag){
                        DownFileUtil.downFile(new File(downZipPath+"data.zip"),response);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return success_int(501,"下载失败, 无相关数据!","");
    }

}
