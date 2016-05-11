package com.smgp.def.command;

import com.smgp.def.base.Constant;
/**
 * activeTest命令回复
 * @author weinianjie
 * @date   2016年4月1日
 */
public class ActiveTestResp extends SMGPCommand {

	// 无消息体，0字节
	public ActiveTestResp()
	{
		
	}	
	
	public ActiveTestResp(byte[] source) throws Exception
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
		return Constant.SMGP_ACTIVE_TEST_RESP;
	}	
}
