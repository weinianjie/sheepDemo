package com.sgip.def;

import com.sgip.def.base.ByteObject;
import com.sgip.def.base.Constant;
import com.sgip.def.command.Bind;
import com.sgip.def.command.BindResp;
import com.sgip.def.command.SGIPCommand;
import com.sgip.def.command.Submit;
import com.sgip.def.command.SubmitResp;
import com.sgip.def.command.UnBind;
import com.sgip.def.command.UnBindResp;

/**
 * SGIP消息体
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SGIPBody implements ByteObject
{
	// 变长度字节数，每一种command又是固定长度，消息体仅含有一个command
	// 这为了直观的定义协议的数据结构，如果追求更高性能，可以把SGIPCommand直接暴露在SGIPMsg里，减少对象数
	private SGIPCommand command; 
	
	public SGIPBody()
	{
		
	}
	
	public SGIPBody(SGIPCommand command)
	{
		this.command = command;
	}
	
	public SGIPBody(int commandId, byte[] source)  throws Exception
	{
		if (Constant.SGIP_BIND == commandId)
		{
			command = new Bind(source);
		} else if (Constant.SGIP_BIND_RESP == commandId)
		{
			command = new BindResp(source);
		} else if (Constant.SGIP_SUBMIT == commandId)
		{
			command = new Submit(source);
		} else if (Constant.SGIP_SUBMIT_RESP == commandId)
		{
			command = new SubmitResp(source);
//		} else if (Constant.SGIP_DELIVER == commandId)
//		{
//			command = new Deliver();
//		}else if (Constant.SGIP_DELIVER_RESP == commandId)
//		{
//			command = new DeliverResp();
//		}else if (Constant.SGIP_REPORT == commandId)
//		{
//			command = new Report();
//		} else if (Constant.SGIP_REPORT_RESP == commandId)
//		{
//			command = new ReportResp();
		} else if (Constant.SGIP_UNBIND == commandId)
		{
			command = new UnBind(source);
		} else if (Constant.SGIP_UNBIND_RESP == commandId)
		{
			command = new UnBindResp(source);
		}else
		{
			throw new Exception("command is not exist ; commandId:" + commandId);
		}
	}
	
	@Override
	public byte[] getByteData()
	{
		return command.getByteData();
	}

	public SGIPCommand getCommand()
	{
		return command;
	}

	public void setCommand(SGIPCommand command)
	{
		this.command = command;
	}
}
