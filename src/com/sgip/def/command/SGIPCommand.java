package com.sgip.def.command;

import com.sgip.def.base.ByteObject;
import com.sgip.util.BitUtil;

/**
 * SGIP消息命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public abstract class SGIPCommand implements ByteObject
{
	// 公共属性，保留备用
	private String reserve;// 8字节
	
	@Override
	public byte[] getByteData()
	{
		return BitUtil.String2Bytes(reserve, 8); 
	}	
	
	public abstract int getId();
	
	public String getReserve()
	{
		return reserve;
	}

	public void setReserve(String reserve)
	{
		this.reserve = reserve;
	}
}
