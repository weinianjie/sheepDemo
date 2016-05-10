package com.smgp.def;

import com.sgip.util.BitUtil;
import com.smgp.def.base.ByteObject;
import com.smgp.def.command.SMGPCommand;

/**
 * 电信SMGP消息，使用SMGP3.0版本协议
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SMGPMsg implements ByteObject
{
	private SMGPHead head;
	private SMGPBody body;
	
	public SMGPMsg()
	{
	}
	
	public SMGPMsg(SMGPHead head, SMGPBody body)
	{
		this.head = head;
		this.body = body;
	}
	
	public SMGPMsg(SMGPCommand cmd)
	{
		this.head = new SMGPHead(cmd.getId());
		this.body = new SMGPBody(cmd);
	}
	
	public SMGPMsg(byte[] source) throws Exception
	{
		byte[] headBytes = new byte[20];
		BitUtil.copyBytes(source, headBytes, 0, 12, 0);
		this.head = new SMGPHead(headBytes);
		
		byte[] bodyBytes = new byte[source.length-12];
		BitUtil.copyBytes(source, bodyBytes, 12, source.length-12, 0);
		this.body = new SMGPBody(head.getRequestId(), bodyBytes);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] bytes = BitUtil.mergeBytes(head.getByteData(), body.getByteData());
		
		// 头四个字节替换成实际消息长度
		int messageLength = bytes.length;
		byte[] _messgeLength = BitUtil.int2Bytes(messageLength);
		BitUtil.copyBytes(_messgeLength, bytes, 0, 4, 0);
		return bytes;
	}	
	
	public SMGPHead getHead()
	{
		return head;
	}

	public void setHead(SMGPHead head)
	{
		this.head = head;
	}

	public SMGPBody getBody()
	{
		return body;
	}

	public void setBody(SMGPBody body)
	{
		this.body = body;
	}
}
