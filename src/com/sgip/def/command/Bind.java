package com.sgip.def.command;

import com.sgip.def.base.Constant;
import com.sgip.util.BitUtil;

/**
 * bind命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public class Bind extends SGIPCommand {
	
	private int loginType;// 1字节，
	private String loginName;// 16字节
	private String loginPassword;// 16字节
	
	public Bind()
	{
		this.loginType = Constant.LOGIN_TYPE_SP_SMG;
		this.loginName = Constant.LOGIN_USER;
		this.loginPassword = Constant.LOGIN_PASSWORD;
	}
	
	public Bind(byte[] source) throws Exception
	{
		byte[] _loginType = new byte[1];
		byte[] _loginName = new byte[16];
		byte[] _loginPassword = new byte[16];
		byte[] _reserve = new byte[8];
		BitUtil.copyBytes(source, _loginType, 0, 1, 0);
		BitUtil.copyBytes(source, _loginName, 1, 16, 0);
		BitUtil.copyBytes(source, _loginPassword, 17, 16, 0);
		BitUtil.copyBytes(source, _reserve, 33, 8, 0);
		this.loginType = BitUtil.convertUnsignByte2Int(_loginType[0]);
		this.loginName = new String(_loginName, Constant.RESPONSE_ECODING);
		this.loginPassword = new String(_loginPassword, Constant.RESPONSE_ECODING);
		super.setReserve(new String(_reserve, Constant.RESPONSE_ECODING));
	}
	
	@Override
	public byte[] getByteData()
	{
		byte _loginType = BitUtil.intLowOneByte(this.loginType);
		byte[] _loginName = BitUtil.String2Bytes(loginName, 16);
		byte[] _loginPassword = BitUtil.String2Bytes(loginPassword, 16);
		byte[] _reserve = super.getByteData();
		return BitUtil.mergeBytes(new byte[]{_loginType}, _loginName, _loginPassword, _reserve);
	}
	
	@Override
	public int getId()
	{
		return Constant.SGIP_BIND;
	}

	public int getLoginType()
	{
		return loginType;
	}
	public void setLoginType(int loginType)
	{
		this.loginType = loginType;
	}
	public String getLoginName()
	{
		return loginName;
	}
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	public String getLoginPassword()
	{
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword)
	{
		this.loginPassword = loginPassword;
	}
}
