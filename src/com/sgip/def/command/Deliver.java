package com.sgip.def.command;

import com.sgip.def.base.Constant;
import com.sgip.util.BitUtil;

/**
 * deliver命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public class Deliver extends SGIPCommand {
	
	private String userNumber;// 86开头的手机号，21字节
	private String spNumber;// SP接入号，21字节
	private int tpPid;//GSM协议类型，1字节
	private int tpUdhi; //GSM协议类型。详细解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐，1字节
	private int messageCoding = Constant.MESSAGE_CODING; //GBK 短消息的编码格式，1字节
	private int messageLength; // 短消息的长度 根据消息内容计算，4字节
	private String messageContent=""; //短消息的内容，非固定字节
	
	public Deliver()
	{
	}
	
	public Deliver(byte[] source) throws Exception
	{
		byte[] _userNumber = new byte[21];
		byte[] _spNumber = new byte[21];
		byte[] _tpPid = new byte[1];
		byte[] _tpUdhi = new byte[1];
		byte[] _messageCoding = new byte[1];
		byte[] _messageLength = new byte[4];
		BitUtil.copyBytes(source, _userNumber, 0, 21, 0);
		BitUtil.copyBytes(source, _spNumber, 21, 21, 0);
		BitUtil.copyBytes(source, _tpPid, 42, 1, 0);
		BitUtil.copyBytes(source, _tpUdhi, 43, 1, 0);
		BitUtil.copyBytes(source, _messageCoding, 44, 1, 0);
		BitUtil.copyBytes(source, _messageLength, 45, 4, 0);
		
		this.userNumber = new String(_userNumber, Constant.RESPONSE_ECODING);
		this.spNumber = new String(_spNumber, Constant.RESPONSE_ECODING);
		this.tpPid = BitUtil.convertUnsignByte2Int(_tpPid[0]);
		this.tpUdhi = BitUtil.convertUnsignByte2Int(_tpUdhi[0]);
		this.messageCoding = BitUtil.convertUnsignByte2Int(_messageCoding[0]);
		this.messageLength = BitUtil.byte4ToInt(_messageLength);

		byte[] _messageContent = null;
		byte[] _reserve = new byte[8];
		BitUtil.copyBytes(source, _messageContent, 49, messageLength, 0);
		BitUtil.copyBytes(source, _reserve, 49 + messageLength, 8, 0);
		
		this.setMessageContent(new String(_messageContent, Constant.RESPONSE_ECODING));// 正确的作法是根据messageCoding来解析，这里看情况写死
		
		super.setReserve(new String(_reserve, Constant.RESPONSE_ECODING));
	}
	
	@Override
	public byte[] getByteData()
	{
		// 作为客户端才需要，这里不解析
		return null;
	}

	public String getUserNumber()
	{
		return userNumber;
	}

	public void setUserNumber(String userNumber)
	{
		this.userNumber = userNumber;
	}

	public String getSpNumber()
	{
		return spNumber;
	}

	public void setSpNumber(String spNumber)
	{
		this.spNumber = spNumber;
	}

	public int getTpPid()
	{
		return tpPid;
	}

	public void setTpPid(int tpPid)
	{
		this.tpPid = tpPid;
	}

	public int getTpUdhi()
	{
		return tpUdhi;
	}

	public void setTpUdhi(int tpUdhi)
	{
		this.tpUdhi = tpUdhi;
	}

	public int getMessageCoding()
	{
		return messageCoding;
	}

	public void setMessageCoding(int messageCoding)
	{
		this.messageCoding = messageCoding;
	}

	public int getMessageLength()
	{
		return messageLength;
	}

	public void setMessageLength(int messageLength)
	{
		this.messageLength = messageLength;
	}

	public String getMessageContent()
	{
		return messageContent;
	}

	public void setMessageContent(String messageContent)
	{
		this.messageContent = messageContent;
	}

	@Override
	public int getId()
	{
		return Constant.SGIP_DELIVER;
	}
}
