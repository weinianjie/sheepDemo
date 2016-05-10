package com.smgp.def.command;

import com.smgp.def.base.ByteObject;

/**
 * SMGP消息命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public abstract class SMGPCommand implements ByteObject
{
	public abstract long getId();
}
