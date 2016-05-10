package com.smgp.def.base;


public class Constant {

/*
	public static final String SMG_IP = "120.24.223.163";
	public static final int SMG_PORT = 8890;
	public static final String CLIENT_ID = "100101";
	public static final String LOGIN_PASSWORD = "qw100101";
	public static final String CORP_ID = "12002";
	public static final String SP_NUMBER = "1069071061";
*/
	
	public static final String SMG_IP = "115.236.100.12";
	public static final int SMG_PORT = 8890;
	public static final String CLIENT_ID = "70015";
	public static final String LOGIN_PASSWORD = "GFDGERWR";
	public static final String CORP_ID = "70015";
	public static final String SP_NUMBER = "1069043415";

	/**
	 * 通道ID
	 */
	public static final long CHANNEL_ID = 53;
	
	/**
	 * 消息编码格式
	 */
	public static final byte MESSAGE_CODING = 15; //0：纯ASCII字符串 3：写卡操作 4：二进制编码 8：UCS2编码 15: GBK编码
	public static final String MESSAGE_CODING_TEXT = "GBK";
	
	/**
	 * SP向SMG建立的连接，用于发送命令
	 */
	public static final int LOGIN_TYPE_SP_SMG = 1;
	
	/**
	 * SMG向SP建立的连接，用于发送命令
	 */
	public static final int LOGIN_TYPE_SMG_SP = 2;
	
	/**
	 * SP与SMG以及SMG之间建立的测试连接，用于跟踪测试
	 */
	public static final int LOGIN_TYPE_SP_SMG_TEST = 11;
	
	/**
	 * 接收响应超时时间
	 */
	public static final int RESPONSE_TIMEOUT = 20;
	
	/**
	 * SMGP协议消息ID定义
	 */
	public static final long SMGP_LOGIN = 0x00000001l;
	
	public static final long SMGP_LOGIN_RESP = 0x80000001l;
	
	public static final long SMGP_EXIT = 0x00000006l;
	
	public static final long SMGP_EXIT_RESP = 0x80000006l;
	
	public static final long SMGP_SUBMIT = 0x00000002l;
	
	public static final long SMGP_SUBMIT_RESP = 0x80000002l;
	
	public static final long SMGP_DELIVER = 0x00000003l;
	
	public static final long SMGP_DELIVER_RESP = 0x80000003l;
	
	public static final long SMGP_QUERY= 0x00000007l;
	
	public static final long SMGP_QUERY_RESP = 0x80000007l;
}
