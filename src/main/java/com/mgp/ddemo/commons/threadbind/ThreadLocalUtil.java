package com.mgp.ddemo.commons.threadbind;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 多线程共享
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal() {
        protected Map<String, Object> initialValue() {
            return new HashMap(4);
        }
    };

    private static final ThreadLocal<ThreadUserInfo> threadLocalUserInfo = new ThreadLocal<ThreadUserInfo>();

    public static void setThreadLocalUserInfo(ThreadUserInfo threadUserInfo){
        threadLocalUserInfo.set(threadUserInfo);
    }

    public static ThreadUserInfo getThreadLocalUserInfo(){
        return threadLocalUserInfo.get();
    }

    public static Map<String, Object> getThreadLocal(){
        return threadLocal.get();
    }
    public static <T> T get(String key) {
        Map map = (Map)threadLocal.get();
        return (T)map.get(key);
    }

    public static <T> T get(String key,T defaultValue) {
        Map map = (Map)threadLocal.get();
        return (T)map.get(key) == null ? defaultValue : (T)map.get(key);
    }

    public static void set(String key, Object value) {
        Map map = (Map)threadLocal.get();
        map.put(key, value);
    }

    public static void set(Map<String, Object> keyValueMap) {
        Map map = (Map)threadLocal.get();
        map.putAll(keyValueMap);
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static <T> Map<String,T> fetchVarsByPrefix(String prefix) {
        Map<String,T> vars = new HashMap<>();
        if( prefix == null ){
            return vars;
        }
        Map map = (Map)threadLocal.get();
        Set<Map.Entry> set = map.entrySet();

        for( Map.Entry entry : set ){
            Object key = entry.getKey();
            if( key instanceof String ){
                if( ((String) key).startsWith(prefix) ){
                    vars.put((String)key,(T)entry.getValue());
                }
            }
        }
        return vars;
    }

    public static <T> T remove(String key) {
        Map map = (Map)threadLocal.get();
        return (T)map.remove(key);
    }

    /**
     * 移除以什么开头的数据
     * @param prefix
     */
    public static void clear(String prefix) {
        if( prefix == null ){
            return;
        }
        Map map = (Map)threadLocal.get();
        Set<Map.Entry> set = map.entrySet();
        List<String> removeKeys = new ArrayList<>();

        for( Map.Entry entry : set ){
            Object key = entry.getKey();
            if( key instanceof String ){
                if( ((String) key).startsWith(prefix) ){
                    removeKeys.add((String)key);
                }
            }
        }
        for( String key : removeKeys ){
            map.remove(key);
        }
    }
}
