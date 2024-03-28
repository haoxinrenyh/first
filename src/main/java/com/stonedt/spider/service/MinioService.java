package com.stonedt.spider.service;

public interface MinioService {

    int minioFileCount();

    int pathFileCount();

    double pathFileSize();

    void minioFileRecord();

    void minoDel();

}
