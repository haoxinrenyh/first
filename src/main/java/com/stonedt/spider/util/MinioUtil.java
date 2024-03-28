package com.stonedt.spider.util;

import com.stonedt.spider.config.minio.MinioConfiguration;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

;

/**
 * @ClassName: minioConfig
 * @Author: jdh
 * @CreateTime: 2022-03-12
 * @Description:
 */
@Slf4j
@Data
@Configuration
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfiguration configuration;


    @PostConstruct
    public void initMinio(){
        configuration = this.configuration;
        minioClient = configuration.minioClient();
    }

    /**
     * 获取上传文件临时签名，作用是：前端获取到签名信息后可以直接将文件上传到minio
     * @param fileName
     * @param time
     * @return
     */
    public Map getPolicyUrl(String fileName, ZonedDateTime time) {
        PostPolicy postPolicy = new PostPolicy(configuration.getBucketName(), time);
        postPolicy.addEqualsCondition("key", fileName);
        try {
            Map<String, String> map = minioClient.getPresignedPostFormData(postPolicy);
            HashMap<String, String> policyMap = new HashMap<>();
            map.forEach((k,v)->{
                policyMap.put(k.replaceAll("-",""),v);
            });
            policyMap.put("host",configuration.getUrl()+"/"+configuration.getBucketName());
            return policyMap;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param objectName
     * @param time
     * @param timeUnit
     * @return
     */
    public String getFileUrl(String objectName,String bucketName,int time, TimeUnit timeUnit) {
        try {
            String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(time, timeUnit).build());
            presignedObjectUrl = presignedObjectUrl.replace(configuration.getUrl(),configuration.getOutUrl());
            return presignedObjectUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFileLocalUrl(String objectName,String bucketName,int time, TimeUnit timeUnit) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(time, timeUnit).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 针对前端直接上传的是文件的格式
     * @param file 文件
     */
    public void uploadToFile(MultipartFile file) {
        // 使用putObject上传一个文件到存储桶中。
        try {
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：{}",fileName);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(configuration.getBucketName())
                    .object(fileName.substring(0,fileName.indexOf(".")))
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据文件名访问指定有效期的文件访问链接
     * @param objectName
     * @param time
     * @param timeUnit
     * @return
     */
    public String getUrl(String objectName, int time, TimeUnit timeUnit) {
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(configuration.getBucketName())
                    .object(objectName)
                    .expiry(time, timeUnit).build());
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
    /**
     * 创建桶
     *
     * @param bucketName 桶名称
     */
    public Boolean createBucket(String bucketName) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("创建桶的时候，桶名不能为空！");
        }
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            return true;
        } catch (Exception e) {
            log.info("创建桶失败！", e);
            return false;
        }

    }

    /**
     * 检查桶是否存在
     *
     * @param bucketName 桶名称
     * @return boolean true-存在 false-不存在
     */
    public boolean checkBucketExist(String bucketName) throws Exception {
        if (!StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("检测桶的时候，桶名不能为空！");
        }

        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 检测某个桶内是否存在某个文件
     *
     * @param objectName 文件名称
     * @param bucketName 桶名称
     */
    public boolean getBucketFileExist(String objectName, String bucketName) throws Exception {
        if (!StringUtils.hasLength(objectName) || !StringUtils.hasLength(bucketName)) {
            throw new RuntimeException("检测文件的时候，文件名和桶名不能为空！");
        }

        try {
            // 判断文件是否存在
            return (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()) &&
                    minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build()) != null);

        } catch (ErrorResponseException e) {
            log.info("文件不存在 ! Object does not exist");
            return false;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 删除文件夹
     *
     * @param bucketName 桶名
     * @param objectName 文件夹名
     * @param isDeep     是否递归删除
     * @return
     */
    public Boolean deleteBucketFolder(String bucketName, String objectName, Boolean isDeep) {
        if (!StringUtils.hasLength(bucketName) || !StringUtils.hasLength(objectName)) {
            throw new RuntimeException("删除文件夹的时候，桶名或文件名不能为空！");
        }
        try {
            ListObjectsArgs args = ListObjectsArgs.builder().bucket(bucketName).prefix(objectName + "/").recursive(isDeep).build();
            Iterable<Result<Item>> listObjects = minioClient.listObjects(args);
            listObjects.forEach(objectResult -> {
                try {
                    Item item = objectResult.get();
                    System.out.println(item.objectName());
                    minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                } catch (Exception e) {
                    log.info("删除文件夹中的文件异常", e);
                }
            });
            return true;
        } catch (Exception e) {
            log.info("删除文件夹失败");
            return false;
        }
    }

    /**
     * 删除文件
     */
    public Boolean delete(String bucketName,String fileName) {
        /**
         * String bucketName = "test2";
         * String fileName = "/2023-04-07/16808560218465670_img.png";
         * address+bucketName+fileName 就是访问路径，删除需要后两个参数。
         */
        try {
            this.getMinioClient().removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            System.out.println("删除失败");
            return false;
        }
        return true;
    }
    /**
     * 文件上传文件
     *
     * @param file       文件
     * @param bucketName 桶名
     * @param objectName 文件名,如果有文件夹则格式为 "文件夹名/文件名"
     * @return
     */
    public Boolean uploadFile(MultipartFile file, String bucketName, String objectName) {

        if (Objects.isNull(file) || Objects.isNull(bucketName)) {
            throw new RuntimeException("文件或者桶名参数不全！");
        }

        try {
            //资源的媒体类型
            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//默认未知二进制流
            InputStream inputStream = file.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            ObjectWriteResponse response = minioClient.putObject(args);
            inputStream.close();
            return response.etag() != null;
        } catch (Exception e) {
            log.info("单文件上传失败！", e);
            return false;
        }
    }
    public Boolean uploadFile(File file, String bucketName, String objectName) {

        if (Objects.isNull(file) || Objects.isNull(bucketName)) {
            throw new RuntimeException("文件或者桶名参数不全！");
        }

        try {
            InputStream inputStream = new FileInputStream(file);
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName)
                    .stream(inputStream, file.length(), -1)
                    .build();
            ObjectWriteResponse response = minioClient.putObject(args);
            inputStream.close();
            return response.etag() != null;
        } catch (Exception e) {
            log.info("单文件上传失败！", e);
            return false;
        }
    }

//    /**
//     * 分片合并
//     *
//     * @param bucketName
//     * @param folderName
//     * @param objectName
//     * @param partNum
//     * @return
//     */
//    public Boolean uploadFileComplete(String bucketName, String folderName, String objectName, Integer partNum) {
//
//        try {
//            //获取临时文件下的所有文件信息
//            Iterable<Result<Item>> listObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(folderName + "/").build());
//
//            //计算minio中分片个数
//            Integer num = 0;
//            for (Result<Item> result : listObjects) {
//                num++;
//            }
//            //在依次校验实际分片数和预计分片数是否一致
//            if (!num.equals(partNum)) {
//                log.info("文件 {} 分片合并的时候，检测到实际分片数 {} 和预计分片数 {} 不一致", folderName, num, partNum);
//                return false;
//            }
//
//            InputStream inputStream = null;
//            log.info("开始合并文件 {} 分片合并，实际分片数 {} 和预计分片数 {}", folderName, num, partNum);
//            for (int i = 0; i < num; i++) {
//                String tempName = folderName + "/" + objectName.substring(0, objectName.lastIndexOf(".")) + "_" + i + ".temp";
//                try {
//                    //获取分片文件流
//                    InputStream response = minioClient.getObject(
//                            GetObjectArgs.builder().bucket(bucketName).object(tempName).build());
//                    //流合并
//                    if (inputStream == null) {
//                        inputStream = response;
//                    } else {
//                        inputStream = new SequenceInputStream(inputStream, response);
//                    }
//                } catch (Exception e) {
//                    log.info("读取分片文件失败！", e);
//                }
//            }
//            if (inputStream == null) {
//                log.info("合并流数据为空！");
//                return false;
//            }
//            //转换为文件格式
//            MockMultipartFile file = new MockMultipartFile(objectName, inputStream);
//
//            //将合并的文件流写入到minio中
//            PutObjectArgs args = PutObjectArgs.builder()
//                    .bucket(bucketName).object(objectName)
//                    .stream(file.getInputStream(), file.getSize(), -1)
////                    .contentType(file.getContentType())//这里可以不知道类型
//                    .build();
//            String etag = minioClient.putObject(args).etag();
//
//            // 删除临时文件
//            if (etag != null) {
//                listObjects.forEach(objectResult -> {
//                    try {
//                        Item item = objectResult.get();
//                        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
//                    } catch (Exception e) {
//                        log.info("删除文件夹中的文件异常", e);
//                    }
//                });
//                log.info("{}:临时文件夹文件已删除！", folderName);
//            }
//
//            inputStream.close();
//            return etag != null;
//        } catch (Exception e) {
//            log.info("合并 {} - {} 文件失败！", folderName, objectName, e);
//            return false;
//        }
//    }

    public static String generateUniqueId(int length) {
        UUID uuid = UUID.randomUUID();
        String hash = uuid.toString().replaceAll("-", "");
        return hash.substring(0, Math.min(length, hash.length()));
    }






    public String upload(File file , String fileName){
        try {
            long fileSize = file.length();

            String fileType = URLConnection.guessContentTypeFromName(file.getName());
            InputStream fileIn = new FileInputStream(file);

            String bucketName = configuration.getBucketName();
            MinioClient minioClient = configuration.minioClient();

            boolean flag = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(flag){
                PutObjectArgs args = PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(fileIn,fileSize,-1)
                        .contentType(fileType)
                        .build();
                minioClient.putObject(args);
                return configuration.getUrl()+"/"+configuration.getBucketName()+"/"+fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int  getBucketNameObjList(){
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
                System.out.println(count);
            } else {
                System.out.println("bucket does not exist");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
        return 0;
    }





}