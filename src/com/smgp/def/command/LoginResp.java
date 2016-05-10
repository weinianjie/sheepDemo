package com.smgp.def.command;

import com.sgip.util.BitUtil;
import com.smgp.def.base.Constant;

/**
 * login命令回复
 * @author weinianjie
 * @date   2016年4月1日
 */
public class LoginResp extends SMGPCommand {

	private int status;// 0成功，其他失败，4字节
	private String authenticatorServer;// 16字节
	private int serverVersion;// 1字节

	public LoginResp()
	{
		
	}
	
	public LoginResp(byte[] source) throws Exception
	{
		byte[] _status = new byte[4];
		byte[] _authenticatorServer = new byte[16];
		byte[] _serverVersion = new byte[1];
		BitUtil.copyBytes(source, _status, 0, 4, 0);
		BitUtil.copyBytes(source, _authenticatorServer, 4, 16, 0);
		BitUtil.copyBytes(source, _serverVersion, 20, 1, 0);
		this.status = BitUtil.byte4ToInt(_status);
		this.authenticatorServer = new String(_authenticatorServer);
		this.serverVersion = BitUtil.convertUnsignByte2Int(_serverVersion[0]);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] _status = BitUtil.int2Bytes(status);
		byte[] _authenticatorServer = BitUtil.String2Bytes(authenticatorServer, 16);
		byte _serverVersion = BitUtil.intLowOneByte(this.serverVersion);
		return BitUtil.mergeBytes(_status, _authenticatorServer, new byte[]{_serverVersion});
	}
	
	@Override
	public long getId()
	{
		return Constant.SMGP_LOGIN_RESP;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAuthenticatorServer() {
		return authenticatorServer;
	}

	public void setAuthenticatorServer(String authenticatorServer) {
		this.authenticatorServer = authenticatorServer;
	}

	public int getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(int serverVersion) {
		this.serverVersion = serverVersion;
	}	
}
