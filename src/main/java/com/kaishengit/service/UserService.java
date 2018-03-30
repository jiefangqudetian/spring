package com.kaishengit.service;

import com.kaishengit.dao.UserDao;

import java.util.*;

public class UserService {
    private UserDao userDao;
    private int id;
    private String name;
    private List<String> list;
    private Set<String> set;
    private Map<String,String> map;
    private Properties properties;
    private UserDao userDao1;

    public UserService(UserDao userDao){
        this.userDao1 = userDao;
    }

    public void print(){
        for (Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey()+"-->"+entry.getValue());
        }
        Enumeration<Object> enumeration = properties.keys();
        while (enumeration.hasMoreElements()){
            Object key = enumeration.nextElement();
            Object value = properties.get(key);
            System.out.println(key+"-->"+value);
        }
        System.out.println(id);
        System.out.println(name);
        for(String str:list){
            System.out.println(str);
        }
        for (String str:set){
            System.out.println(str);
        }

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    public void save(){
        userDao.save();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserDao getUserDao1() {
        return userDao1;
    }
}
