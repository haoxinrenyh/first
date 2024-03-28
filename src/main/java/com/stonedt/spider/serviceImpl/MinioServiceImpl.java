package com.stonedt.spider.serviceImpl;

import com.stonedt.spider.config.minio.MinioConfiguration;
import com.stonedt.spider.dao.ResultDao;
import com.stonedt.spider.entity.MinioFileData;
import com.stonedt.spider.service.MinioService;
import com.stonedt.spider.util.DataUtil;
import com.stonedt.spider.util.DateUtil;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioConfiguration configuration;

    @Value("${attachment.temporary}")
    private String temporaryPath;

    @Autowired
    private ResultDao resultDao;
    @Override
    public int minioFileCount() {
        try {
            String bucketName = configuration.getBucketName();
            MinioClient minioClient = configuration.minioClient();
            boolean flag = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (flag) {
                Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
                int count = 0;
                for (Result<Item> result : myObjects) {
                    count ++;
                }
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int pathFileCount() {
        try {
            File filePath = new File(temporaryPath);
            File[] fileArr = filePath.listFiles();
            int fileCount = 0;
            for (File file : fileArr){
                if (file.isFile()){
                    fileCount++;
                }
            }
            return fileCount;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double pathFileSize() {
        try {
            File file = new File(temporaryPath);
            double size = FileUtils.sizeOfDirectory(file);
            if(size>0){
                size = DataUtil.divided(size,1024);
                return size;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void minioFileRecord() {
        try {
            File pathFile = new File(temporaryPath);
            File[] files = pathFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if(file.exists()){
                    String file_name = file.getName();
                    double size = file.length();
                    Date file_time = new Date(file.lastModified());
                    if(file_name!=null && size>0){
                        size = DataUtil.divided(size,1024);

                        MinioFileData data = new MinioFileData();
                        data.setFile_name(file_name);
                        data.setSize(size);
                        data.setFile_time(file_time);
                        int saveType = resultDao.saveMinioFile(data);
//                        if(saveType>0){
//                            System.out.println("文件信息已记录!");
//                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void minoDel() {
        try {
            long endTime = DateUtil.getTime_zero();
            File pathFile = new File(temporaryPath);
            File[] files = pathFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if(file.exists()){
                    long file_time = file.lastModified();
                    if(endTime > file_time){
                        file.delete();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
