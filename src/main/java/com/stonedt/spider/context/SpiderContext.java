package com.stonedt.spider.context;

import com.stonedt.spider.concurrent.SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor;
import com.stonedt.spider.model.SpiderNode;
import com.stonedt.spider.model.SpiderOutput;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 爬虫上下文
 *
 * @author jmxd
 */
public class SpiderContext extends HashMap<String, Object> {

    private String id = UUID.randomUUID().toString().replace("-", "");

    /**
     * 流程ID
     */
    private String flowId;

    private static final long serialVersionUID = 8379177178417619790L;

    /**
     * 流程执行线程
     */
    private SubThreadPoolExecutor threadPool;

    /**
     * 根节点
     */
    private SpiderNode rootNode;

    /**
     * 爬虫是否运行中
     */
    private volatile boolean running = true;

    /**
     * Future队列
     */
    private LinkedBlockingQueue<Future<?>> futureQueue = new LinkedBlockingQueue<>();

    /**
     * Cookie上下文
     */
    //private CookieContext cookieContext = new CookieContext();

    //存储异常信息
    private List<Map<String, String>> list = new ArrayList<>();

    private String[] keys = {"error","errorMsg","nodeShape","jsonValue","variableName","variableValue"};

    public List<Map<String, String>> getList() {
        return this.list;
    }

    public void addList(Map<String, String> map) {
        this.list.add(map);
    }

    public void addList(String ...msgs) {
        if(msgs.length <= keys.length){
            Map<String, String> map = new HashMap<>();
            for (int i =0; i < msgs.length; i ++){
                map.put(keys[i],msgs[i]);
            }
            for (int i =0; i < keys.length - msgs.length; i ++){
                map.put(keys[msgs.length + i],"");
            }
            this.list.add(map);
        }

    }


    public List<SpiderOutput> getOutputs() {
        return Collections.emptyList();
    }

    public <T> T get(String key) {
        return (T) super.get(key);
    }

    public <T> T get(String key, T defaultValue) {
        T value = this.get(key);
        return value == null ? defaultValue : value;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public LinkedBlockingQueue<Future<?>> getFutureQueue() {
        return futureQueue;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void addOutput(SpiderOutput output) {

    }

    public SubThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(SubThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public SpiderNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(SpiderNode rootNode) {
        this.rootNode = rootNode;
    }

    public String getId() {
        return id;
    }

//    public CookieContext getCookieContext() {
//        return cookieContext;
//    }

    public void pause(String nodeId, String event, String key, Object value) {
    }

    public void resume() {
    }

    public void stop() {
    }

}
