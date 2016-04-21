package com.sgip.def;

import com.sgip.def.base.ByteObject;
import com.sgip.def.command.SGIPCommand;
import com.sgip.util.BitUtil;

/**
 * 联通SGIP消息，使用SGIP1.2版本协议
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SGIPMsg implements ByteObject
{
	private SGIPHead head;
	private SGIPBody body;
	
	public SGIPMsg()
	{
	}
	
	public SGIPMsg(SGIPHead head, SGIPBody body)
	{
		this.head = head;
		this.body = body;
	}
	
	public SGIPMsg(SGIPCommand cmd)
	{
		this.head = new SGIPHead(cmd.getId());
		this.body = new SGIPBody(cmd);
	}
	
	public SGIPMsg(byte[] source) throws Exception
	{
		byte[] headBytes = new byte[20];
		BitUtil.copyBytes(source, headBytes, 0, 20, 0);
		this.head = new SGIPHead(headBytes);
		
		byte[] bodyBytes = new byte[source.length-20];
		BitUtil.copyBytes(source, bodyBytes, 20, source.length-20, 0);
		this.body = new SGIPBody(head.getCommandId(), bodyBytes);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] bytes = BitUtil.mergeBytes(head.getByteData(), body.getByteData());
		
		// 头四个字节替换成实际消息长度
		int messageLength = bytes.length;
		byte[] _messgeLength = BitUtil.int2Bytes(messageLength);
		BitUtil.copyBytes(_messgeLength, bytes, 0, 4, 0);
		
		System.out.println("serialize to bytes, commandId=" + body.getCommand().getId() + ",messageLength=" + messageLength);
		
		return bytes;
	}	
	
	public SGIPHead getHead()
	{
		return head;
	}

	public void setHead(SGIPHead head)
	{
		this.head = head;
	}

	public SGIPBody getBody()
	{
		return body;
	}

	public void setBody(SGIPBody body)
	{
		this.body = body;
	}
}
