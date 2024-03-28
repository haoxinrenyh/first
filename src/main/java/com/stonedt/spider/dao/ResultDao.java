package com.stonedt.spider.dao;

import com.stonedt.spider.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResultDao {

    public Statistic findStatistic();

    public List<String> findEsIndexAll();

    int saveStatisticCount(StatisticCount statisticCount);

    StatisticCount findStatisticCount(@Param("create_time")String create_time);

    int saveMinioFile(MinioFileData minioFileData);

    MinioFileCount findMinioFileCount();

    int findEsRecordCount(@Param("beginTime")String beginTime,@Param("endTime")String endTime);

    List<ResultNote> pageResultNote(@Param("limit")String limit , @Param("keyword")String keyword);

    ResultNote infoNote(@Param("english")String english);

    int saveNote(@Param("note") ResultNote note);

    int updateNote(@Param("note") ResultNote note);

    int removeNote(@Param("id") int id);

    int noteCount(@Param("keyword")String keyword);

    List<ResultNote> noteList();

}
