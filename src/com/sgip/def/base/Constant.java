package com.sgip.def.base;


public class Constant {

	/**
	 * SMG地址
	 */
	public static final String SMG_IP = "112.90.92.218";
	
	/**
	 * SMG端口
	 */
	public static final int SMG_PORT = 8801;
	
	/**
	 * 节点编号
	 */
	public static final long NODE_NUMBER = 3020046152l;
	
	/**
	 * 用户名
	 */
	public static final String LOGIN_USER = "46152";
	
	/**
	 * 密码
	 */
	public static final String LOGIN_PASSWORD = "DEFEWRWR";
	
	/**
	 * 企业代码
	 */
	public static final String CORP_ID = "46152";
	
	/**
	 * SP的接入号码
	 */
	public static final String SP_NUMBER = "10655020073152";
	
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
	 * 接收消息编码
	 */
	public static final String RESPONSE_ECODING = "UTF-8";
	
	/**
	 * SGIP协议消息ID定义
	 */
	public static final int SGIP_BIND = (int)0x1l;
	
	public static final int SGIP_BIND_RESP = (int)0x80000001l;
	
	public static final int SGIP_UNBIND = (int)0x2l;
	
	public static final int SGIP_UNBIND_RESP = (int) 0x80000002l;
	
	public static final int SGIP_SUBMIT = (int) 0x3l;
	
	public static final int SGIP_SUBMIT_RESP = (int)0x80000003l;
	
	public static final int SGIP_DELIVER = (int)0x4l;
	
	public static final int SGIP_DELIVER_RESP = (int)0x80000004l;
	
	public static final int SGIP_REPORT = (int)0x5l;
	
	public static final int SGIP_REPORT_RESP = (int)0x80000005l;
}
