/*
 * This file is NOT a part of Moodle - http://moodle.org/
 *
 * This client for Moodle 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package com.tmp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * XML-RPC MOODLE Client
 * You need to download the Apache XML-RPC library http://apache.mirror.aussiehq.net.au//ws/xmlrpc/
 * and add the jar files to your project.
 *
 * @author Jerome Mouneyrac jerome@moodle.com
 */
public class XmlrpcTest {

    /**
     * Do a XML-RPC call to Moodle. Result are displayed in the console log.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {

        /// NEED TO BE CHANGED
        String token = "2798d23895a18ebdacf0a3e0584854c4";
        String domainName = "http://127.0.0.1";

        /// PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
//        String functionName = "core_user_create_users";
//        String functionName = "usersMgr";
        String functionName = "usersMgr";
        
//        Hashtable user1 = new Hashtable();
//        user1.put("username", "testusername1");
//        user1.put("password", "testpassword1");
//        user1.put("firstname", "testfirstname1");
//        user1.put("lastname", "testlastname1");
//        user1.put("email", "testemail1@moodle.com");
//        user1.put("auth", "manual");
//        user1.put("idnumber", "testidnumber1");
//        user1.put("lang", "en");
//        user1.put("theme", "standard");
//        user1.put("timezone", "-12.5");
//        user1.put("mailformat", "0");
//        user1.put("description", "Hello World!");
//        user1.put("city", "testcity1");
//        user1.put("country", "au");
//        
//        Hashtable preference1 = new Hashtable();
//        preference1.put("type", "preference1");
//        preference1.put("value", new Date());
//        
//        Hashtable preference2 = new Hashtable();
//        preference2.put("type", "preference2");
//        preference2.put("value", "preferencevalue2");
//        
//        Object[] preferences = new Object[]{preference1, preference2};
//        
//        user1.put("preferences", preferences);
//        
//        Hashtable user2 = new Hashtable();
//        
//        user2.put("username", "testusername2");
//        user2.put("password", "testpassword2");
//        user2.put("firstname", "testfirstname2");
//        user2.put("lastname", "testlastname2");
//        user2.put("email", "testemail2@moodle.com");
//        user2.put("timezone", "Pacific/Port_Moresby");
//        
        
        Hashtable usera = new Hashtable();
        usera.put("userName", "liqiang");
        usera.put("fullName", "ligang2");
        usera.put("email", "ligang2");
        
        Hashtable userb = new Hashtable();
        userb.put("userName", "wangming");
        userb.put("fullName", "wangming2");
        userb.put("email", "wangming2");
        
        
//        Object[] users = new Object[]{user1, user2};
        Object[] users = new Object[]{usera, userb};
        
//        Integer[] ids = new Integer[]{1, 2};

        /// XML-RPC CALL
        String serverurl = domainName + "/XmlRpcService.action";// + "?wstoken=" + token;// + "&wsfunction=" + functionName;
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(serverurl));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        
//        List<UserVo> users = new ArrayList<UserVo>();
//        UserVo user = new UserVo();
//        user.setUserName("liqiang");
//        user.setAge(11);
//        user.setBirthday(new Date());
//        users.add(user);
//        
//        user = new UserVo();
//        user.setUserName("wangming");
//        user.setAge(12);
//        user.setBirthday(new Date());
//        users.add(user);
        
        Object[] params = new Object[]{1, users};
//        Object[] params = new Object[]{ids};
        
        
        Object[] params2 = new Object[]{"123"};
        Object[] result = (Object[]) client.execute(functionName, params);

        //Display the result in the console log
        //This piece of code NEED TO BE CHANGED if you call another function        
        System.out.println("An array has been returned. Length is " + result.length);
        
        System.out.println("xxx");
//        for (int i = 0; i < result.length; i++) {
//                HashMap createduser = (HashMap) result[i];
//                Integer id = (Integer) createduser.get("id");
//                System.out.println("id = " + id);
//                String username = (String) createduser.get("username");
//                System.out.println("username = " + username);

//        }
    }
}
