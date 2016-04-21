package com.sgip.def;

import com.sgip.def.base.ByteObject;
import com.sgip.util.BitUtil;

/**
 * SGIP消息头
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SGIPHead implements ByteObject
{
	// 固定20字节
	private int messageLength;// 消息长度，4字节，最后自动技算，不提供get和set方法，这里仅用于占位
	private int commandId;// 消息ID，4字节
	private SGIPSequence sequence;// 消息序列号，12字节
	
	public SGIPHead()
	{
		
	}
	
	public SGIPHead(int commandId)
	{
		this.commandId = commandId;
		this.sequence = new SGIPSequence();
	}
	
	public SGIPHead(byte[] source) throws Exception
	{
		byte[] _messageLength = new byte[4];
		byte[] _commandId = new byte[4];
		BitUtil.copyBytes(source, _messageLength, 0, 4, 0);
		BitUtil.copyBytes(source, _commandId, 4, 4, 0);
		this.messageLength = BitUtil.byte4ToInt(_messageLength);
		this.commandId = BitUtil.byte4ToInt(_commandId);
		
		byte[] seqBytes = new byte[12];
		BitUtil.copyBytes(source, seqBytes, 8, 12, 0);
		this.sequence = new SGIPSequence(seqBytes);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] _messageLength = BitUtil.int2Bytes(this.messageLength);
		byte[] _commandId = BitUtil.int2Bytes(this.commandId);
		return BitUtil.mergeBytes(_messageLength, _commandId, sequence.getByteData());
	}

	public int getMessageLength()
	{
		return messageLength;
	}

	public void setMessageLength(int messageLength)
	{
		this.messageLength = messageLength;
	}

	public int getCommandId()
	{
		return commandId;
	}

	public void setCommandId(int commandId)
	{
		this.commandId = commandId;
	}

	public SGIPSequence getSequence()
	{
		return sequence;
	}

	public void setSequence(SGIPSequence sequence)
	{
		this.sequence = sequence;
	}
}
