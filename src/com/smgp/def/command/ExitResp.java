package com.smgp.def.command;

import com.smgp.def.base.Constant;

/**
 * exit命令回复
 * @author weinianjie
 * @date   2016年4月1日
 */
public class ExitResp extends SMGPCommand {

	// 无消息体，0字节
	public ExitResp()
	{
		
	}	
	
	public ExitResp(byte[] source) throws Exception
	{
		
	}	
	
	@Override
	public byte[] getByteData()
	{
		return new byte[0];
	}
	
	@Override
	public long getId()
	{
		return Constant.SMGP_EXIT_RESP;
	}	
}
