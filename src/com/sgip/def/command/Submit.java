package com.sgip.def.command;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sgip.def.base.Constant;
import com.sgip.util.BitUtil;

/**
 * submit命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public class Submit extends SGIPCommand {
	
	private String spNumber = Constant.SP_NUMBER;//SP的接入号码，21字节
	private String chargeNumber="000000000000000000000";//由SP支付，21字节
	private int userCount;//接收短消息的手机数量，取值范围1至100，1字节
	private List<String> listUserNumber;//接收该短消息的手机号，该字段重复UserCount指定的次数，手机号码前加“86”国别标志，21*size字节
	private String corpId = Constant.CORP_ID;// 企业代码，5字节
	private String serviceType="";//业务代码，由SP定义，10字节
	private int feeType = 1;//计费类型，1字节
	private String feeValue = "0";//取值范围0-99999，该条短消息的收费值，单位为分，6字节
	private String givenValue = "0"; //取值范围0-99999，赠送用户的话费，单位为分，由SP定义，特指由SP向用户发送广告时的赠送话费，6字节
	private int agentFlag = 0; //代收费标志，0：应收；1：实收，1字节
	private int morelatetoMTFlag = 2; //引起MT消息的原因，1字节
	private int priority = 0;//优先级0-9从低到高，默认为0，1字节
	private String expireTime = "";// 16字节
	private String scheduleTime = "";//短消息定时发送的时间，如果为空，表示立刻发送该短消息，16字节
	private int reportFlag = 0; //状态报告标记 0-该条消息只有最后出错时要返回状态报告，1字节
	private int tpPid;//GSM协议类型，1字节
	private int tpUdhi; //GSM协议类型。详细解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐，1字节
	private int messageCoding = Constant.MESSAGE_CODING; //GBK 短消息的编码格式，1字节
	private int messageType = 0; //信息类型：0-短消息信息，1字节
	private int messageLength; // 短消息的长度 根据消息内容计算，4字节
	private String messageContent=""; //短消息的内容，非固定字节

	public Submit()
	{
	}
	
	public Submit(List<String> phones, String content) throws Exception
	{
		this.listUserNumber = phones;
		this.userCount = phones.size();
		
		this.messageContent = content;
		this.messageLength = content.getBytes(Constant.MESSAGE_CODING_TEXT).length;
	}
	
	public Submit(byte[] source)
	{
		System.out.println("作为服务端才用到，暂不解析");
	}
	
	@Override
	public byte[] getByteData()
	{
		// 手机号列表
		List<byte[]> listUserNumberByte = new ArrayList<byte[]>();
		for(String userNumber : listUserNumber)
		{
			listUserNumberByte.add(BitUtil.String2Bytes(userNumber, 21));
		}
		byte[] listUserNumberBytes = BitUtil.mergeBytes(listUserNumberByte);

		byte[] messageContentBytes = new byte[0];
		try
		{
			messageContentBytes = messageContent.getBytes(Constant.MESSAGE_CODING_TEXT);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		byte[] _reserve = super.getByteData();
		
		return BitUtil.mergeBytes(
				BitUtil.String2Bytes(spNumber, 21),
				BitUtil.String2Bytes(chargeNumber, 21),
				new byte[]{BitUtil.intLowOneByte(userCount)},
				listUserNumberBytes,
				BitUtil.String2Bytes(corpId, 5),
				BitUtil.String2Bytes(serviceType, 10),
				new byte[]{BitUtil.intLowOneByte(feeType)},
				BitUtil.String2Bytes(feeValue, 6),
				BitUtil.String2Bytes(givenValue, 6),
				new byte[]{BitUtil.intLowOneByte(agentFlag)},
				new byte[]{BitUtil.intLowOneByte(morelatetoMTFlag)},
				new byte[]{BitUtil.intLowOneByte(priority)},
				BitUtil.String2Bytes(expireTime, 16),
				BitUtil.String2Bytes(scheduleTime, 16),
				new byte[]{BitUtil.intLowOneByte(reportFlag)},
				new byte[]{BitUtil.intLowOneByte(tpPid)},
				new byte[]{BitUtil.intLowOneByte(tpUdhi)},				
				new byte[]{BitUtil.intLowOneByte(messageCoding)},				
				new byte[]{BitUtil.intLowOneByte(messageType)},	
				BitUtil.longLowFourBytes(messageLength),
				messageContentBytes,
				_reserve
				);
	}
	
	public String getSpNumber()
	{
		return spNumber;
	}

	public void setSpNumber(String spNumber)
	{
		this.spNumber = spNumber;
	}

	public String getChargeNumber()
	{
		return chargeNumber;
	}

	public void setChargeNumber(String chargeNumber)
	{
		this.chargeNumber = chargeNumber;
	}

	public int getUserCount()
	{
		return userCount;
	}

	public void setUserCount(int userCount)
	{
		this.userCount = userCount;
	}

	public List<String> getListUserNumber()
	{
		return listUserNumber;
	}

	public void setListUserNumber(List<String> listUserNumber)
	{
		this.listUserNumber = listUserNumber;
	}

	public String getCorpId()
	{
		return corpId;
	}

	public void setCorpId(String corpId)
	{
		this.corpId = corpId;
	}

	public String getServiceType()
	{
		return serviceType;
	}

	public void setServiceType(String serviceType)
	{
		this.serviceType = serviceType;
	}

	public int getFeeType()
	{
		return feeType;
	}

	public void setFeeType(int feeType)
	{
		this.feeType = feeType;
	}

	public String getFeeValue()
	{
		return feeValue;
	}

	public void setFeeValue(String feeValue)
	{
		this.feeValue = feeValue;
	}

	public String getGivenValue()
	{
		return givenValue;
	}

	public void setGivenValue(String givenValue)
	{
		this.givenValue = givenValue;
	}

	public int getAgentFlag()
	{
		return agentFlag;
	}

	public void setAgentFlag(int agentFlag)
	{
		this.agentFlag = agentFlag;
	}

	public int getMorelatetoMTFlag()
	{
		return morelatetoMTFlag;
	}

	public void setMorelatetoMTFlag(int morelatetoMTFlag)
	{
		this.morelatetoMTFlag = morelatetoMTFlag;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public String getExpireTime()
	{
		return expireTime;
	}

	public void setExpireTime(String expireTime)
	{
		this.expireTime = expireTime;
	}

	public String getScheduleTime()
	{
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime)
	{
		this.scheduleTime = scheduleTime;
	}

	public int getReportFlag()
	{
		return reportFlag;
	}

	public void setReportFlag(int reportFlag)
	{
		this.reportFlag = reportFlag;
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

	public int getMessageType()
	{
		return messageType;
	}

	public void setMessageType(int messageType)
	{
		this.messageType = messageType;
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
	public String toString()
	{
		return "Submit [spNumber=" + spNumber + ", chargeNumber="
				+ chargeNumber + ", userCount=" + userCount
				+ ", listUserNumber=" + listUserNumber + ", corpId=" + corpId
				+ ", serviceType=" + serviceType + ", feeType=" + feeType
				+ ", feeValue=" + feeValue + ", givenValue=" + givenValue
				+ ", agentFlag=" + agentFlag + ", morelatetoMTFlag="
				+ morelatetoMTFlag + ", priority=" + priority + ", expireTime="
				+ expireTime + ", scheduleTime=" + scheduleTime
				+ ", reportFlag=" + reportFlag + ", tpPid=" + tpPid
				+ ", tpUdhi=" + tpUdhi + ", messageCoding=" + messageCoding
				+ ", messageType=" + messageType + ", messageLength="
				+ messageLength + ", messageContent=" + messageContent + "]";
	}

	@Override
	public int getId()
	{
		return Constant.SGIP_SUBMIT;
	}
	
}
