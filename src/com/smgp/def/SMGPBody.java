package com.smgp.def;

import com.smgp.def.base.ByteObject;
import com.smgp.def.base.Constant;
import com.smgp.def.command.Deliver;
import com.smgp.def.command.DeliverResp;
import com.smgp.def.command.Exit;
import com.smgp.def.command.ExitResp;
import com.smgp.def.command.Login;
import com.smgp.def.command.LoginResp;
import com.smgp.def.command.SMGPCommand;
import com.smgp.def.command.Submit;
import com.smgp.def.command.SubmitResp;

/**
 * SMGP消息体
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SMGPBody implements ByteObject
{
	// 变长度字节数，每一种command又是固定长度，消息体仅含有一个command
	// 这为了直观的定义协议的数据结构，如果追求更高性能，可以把SMGPCommand直接暴露在SMGPMsg里，减少对象数
	private SMGPCommand command;
	
	public SMGPBody()
	{
		
	}
	
	public SMGPBody(SMGPCommand command)
	{
		this.command = command;
	}
	
	public SMGPBody(long commandId, byte[] source)  throws Exception
	{
		if (Constant.SMGP_LOGIN == commandId)
		{
			command = new Login(source);
		} else if (Constant.SMGP_LOGIN_RESP == commandId)
		{
			command = new LoginResp(source);
		} else if (Constant.SMGP_SUBMIT == commandId)
		{
			command = new Submit(source);
		} else if (Constant.SMGP_SUBMIT_RESP == commandId)
		{
			command = new SubmitResp(source);
		} else if (Constant.SMGP_DELIVER == commandId)
		{
			command = new Deliver(source);
		}else if (Constant.SMGP_DELIVER_RESP == commandId)
		{
			command = new DeliverResp(source);
		}else if (Constant.SMGP_EXIT == commandId)
		{
			command = new Exit(source);
		} else if (Constant.SMGP_EXIT_RESP == commandId)
		{
			command = new ExitResp(source);
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

	public SMGPCommand getCommand()
	{
		return command;
	}

	public void setCommand(SMGPCommand command)
	{
		this.command = command;
	}
}
