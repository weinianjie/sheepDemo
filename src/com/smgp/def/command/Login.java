package com.smgp.def.command;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import com.sgip.util.BitUtil;
import com.smgp.def.base.Constant;

/**
 *  * login命令
 *   * @author weinianjie
 *    * @date   2016年4月1日
 *     */
public class Login extends SMGPCommand {
	
	private String clientID;// 8字节，
	private byte[] authenticatorClient;// 16字节 MD5（ClientID+7 字节的二进制0（0x00） + Shared secret+Timestamp） Shared secret 由服务器端与客户端事先商定，最长15字节
	private int loginMode = 2;// 1字节0＝发送短消息（send mode）；1＝接收短消息（receive mode）；2＝收发短消息（transmit mode）；
	private long timeStamp;// 4字节
	private int clientVersion = 48;// 1字节 0x30 = 48
	
	public Login() throws NoSuchAlgorithmException
	{
		this.clientID = Constant.CLIENT_ID;
		String tsp = GetTimeStamp();
		timeStamp = Long.parseLong(tsp);
		byte[] buf = BitUtil.mergeBytes(clientID.getBytes(), new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00}, Constant.LOGIN_PASSWORD.getBytes(), tsp.getBytes());
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(buf);
		authenticatorClient = md5.digest();
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException
	{
		
		String clientID = Constant.CORP_ID;
		byte[] buf = BitUtil.mergeBytes(clientID.getBytes(), new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00}, Constant.LOGIN_PASSWORD.getBytes(), GetTimeStamp().getBytes());
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(buf);
		byte[] authenticatorClient = md5.digest();
		System.out.println(authenticatorClient.length);
	}
	
	public Login(byte[] source) throws Exception
	{
		byte[] _clientID = new byte[8];
		byte[] authenticatorClient = new byte[16];
		byte[] _loginMode = new byte[1];
		byte[] _timeStamp = new byte[4];
		byte[] _clientVersion = new byte[1];
		BitUtil.copyBytes(source, _clientID, 0, 8, 0);
		BitUtil.copyBytes(source, authenticatorClient, 8, 24, 0);
		BitUtil.copyBytes(source, _loginMode, 24, 1, 0);
		BitUtil.copyBytes(source, _timeStamp, 25, 4, 0);
		BitUtil.copyBytes(source, _clientVersion, 29, 1, 0);
		this.clientID = new String(_clientID);// 纯asc2
		this.loginMode = BitUtil.convertUnsignByte2Int(_loginMode[0]);
		this.timeStamp = BitUtil.byte4ToLong(_timeStamp);
		this.clientVersion = BitUtil.convertUnsignByte2Int(_clientVersion[0]);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] _clientID = BitUtil.String2Bytes(clientID, 8);
		byte[] _authenticatorClient = authenticatorClient;
		byte _loginMode = BitUtil.intLowOneByte(this.loginMode);
		byte[] _timeStamp = BitUtil.longLowFourBytes(timeStamp);
		byte _clientVersion = BitUtil.intLowOneByte(this.clientVersion);
		byte[] buf = BitUtil.mergeBytes(_clientID, _authenticatorClient, new byte[]{_loginMode}, _timeStamp, new byte[]{_clientVersion});
		
//		for(byte b : buf)
//		{
//			System.out.println(b + "|");
//		}		
		
		return buf;
		//return BitUtil.mergeBytes(_clientID, _authenticatorClient, new byte[]{_loginMode}, _timeStamp);
	}
	
	@Override
	public long getId()
	{
		return Constant.SMGP_LOGIN;
	}
	
	@SuppressWarnings("static-access")
	public static String GetTimeStamp() {
		String TimeStamp = "";
		Calendar now = Calendar.getInstance();
		TimeStamp = FormatInt(Integer.toString(now.MONTH + 1))
				+ FormatInt(Integer.toString(now.DAY_OF_MONTH + 1))
				+ FormatInt(Integer.toString(now.HOUR_OF_DAY + 1))
				+ FormatInt(Integer.toString(now.MINUTE + 1))
				+ FormatInt(Integer.toString(now.SECOND + 1));
		return TimeStamp;
	}

	private static String FormatInt(String value) {
		if (value.length() == 1) {
			return "0" + value;
		} else {
			return value;
		}
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public byte[] getAuthenticatorClient()
	{
		return authenticatorClient;
	}

	public void setAuthenticatorClient(byte[] authenticatorClient)
	{
		this.authenticatorClient = authenticatorClient;
	}

	public int getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(int loginMode) {
		this.loginMode = loginMode;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(int clientVersion) {
		this.clientVersion = clientVersion;
	}
}

