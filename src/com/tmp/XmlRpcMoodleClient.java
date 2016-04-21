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
public class XmlRpcMoodleClient {

    /**
     * Do a XML-RPC call to Moodle. Result are displayed in the console log.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {

        /// NEED TO BE CHANGED
        String token = "56853237abd40851e4bfcae24920d485";
//        String domainName = "http://192.168.71.21";
        String domainName = "http://192.168.71.60";

        /// PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
//        String functionName = "core_user_create_users";
//        String functionName = "moodle_group_get_groups";
        String functionName = "local_mediacenter_mod_url_access_user";
//        String functionName = "local_mediacenter_connect_test";
        
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
//        preference1.put("value", "preferencevalue1");
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
//        Object[] users = new Object[]{user1, user2};
        
//        Integer[] ids = new Integer[]{1, 2};
        Integer userId = 24;

        /// XML-RPC CALL
        String serverurl = domainName + "/moodle2.1/webservice/xmlrpc/server.php" + "?wstoken=" + token;// + "&wsfunction=" + functionName;
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(serverurl));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
//        Object[] params = new Object[]{users};
//        Object[] params = new Object[]{ids};
        Object[] params = new Object[]{"weinianjie", "http://www.baidu.com", "mod/url:view"};
//        Object[] params = new Object[]{};
//        Object[] result = (Object[]) client.execute(functionName, params);
        Object result = (Object) client.execute(functionName, params);
//        Integer a =  (Integer) client.execute(functionName, params);
//        System.out.println(a);
        //Display the result in the console log
        //This piece of code NEED TO BE CHANGED if you call another function        
//        System.out.println("An array has been returned. Length is " + result.length);
        HashMap hs = (HashMap)result;
        System.out.println("code=" + hs.get("code"));
//        for (int i = 0; i < result.length; i++) {
//                HashMap course = (HashMap) result[i];
//                Integer id = (Integer) course.get("code");
//                System.out.println("id = " + id);
////                String username = (String) createduser.get("username");
////                System.out.println("username = " + username);
//
//        }
    }
}
