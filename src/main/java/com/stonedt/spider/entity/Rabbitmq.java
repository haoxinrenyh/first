package com.stonedt.spider.entity;

import lombok.Data;

@Data
public class Rabbitmq {

    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String queue_priority;
}
