package com.sgip.def.command;

import com.sgip.def.base.Constant;

/**
 * unbind命令回复
 * @author weinianjie
 * @date   2016年4月1日
 */
public class UnBindResp extends SGIPCommand {

	// 无消息体，0字节
	public UnBindResp()
	{
		
	}	
	
	public UnBindResp(byte[] source) throws Exception
	{
		
	}	
	
	@Override
	public byte[] getByteData()
	{
		return new byte[0];
	}
	
	@Override
	public int getId()
	{
		return Constant.SGIP_UNBIND_RESP;
	}	
}
