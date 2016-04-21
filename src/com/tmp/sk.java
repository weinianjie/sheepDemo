package com.tmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class sk {
	public static void main(String[] args) {
		System.out.println("123");
        try{
        	execCmd("java fk");
        }catch(Exception e){
                System.out.println("789");
        }
	}
	
    private static void execCmd(String cmd) {
    	Process process = null;
    	BufferedReader reader = null;
    	try {
			process = Runtime.getRuntime().exec(cmd);
			if (process != null) {
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
				process.waitFor();
				reader.close();
			}
			
		} catch (IOException e) {
		} catch (InterruptedException e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
    }
}
