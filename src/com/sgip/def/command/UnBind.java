package com.sgip.def.command;

import com.sgip.def.base.Constant;

/**
 * unbind命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public class UnBind extends SGIPCommand {
	
	// 无消息体，0字节
	
	public UnBind()
	{
		
	}
	
	public UnBind(byte[] source) throws Exception
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
		return Constant.SGIP_UNBIND;
	}	
}
