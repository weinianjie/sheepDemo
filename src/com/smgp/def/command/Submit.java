package com.smgp.def.command;

import java.util.ArrayList;
import java.util.List;

import com.sgip.util.BitUtil;
import com.smgp.def.base.Constant;

/**
 * submit命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public class Submit extends SMGPCommand {
	
	private int msgType = 6;// 1       Integer 短消息类型
	private int needReport = 1;//      1       Integer SP是否要求返回状态报告
	private int priority = 0;//        1       Integer 短消息发送优先级 
	private String serviceID = "";//       10      Octet String
	private String feeType = "00";// 2       Octet String    收费类型
	private String feeCode = "000";// 6       Octet String    资费代码
	private String fixedFee = "000";//        6       Octet String    包月费/封顶费
	private int msgFormat = 15;//       1       Integer 短消息格式  0＝ASCII编码；3＝短消息写卡操作；4＝二进制短消息；8＝UCS2编码；15＝GB18030编码；246（F6）＝(U)SIM相关消息；
	private String validTime = "";//       17      Octet String    短消息有效时间
	private String atTime = "";//  17      Octet String    短消息定时发送时间
	private String srcTermID = "";//       21      Octet String
	private String chargeTermID = "";//    21      Octet String
	private int destTermIDCount;// 1       Integer 短消息接收号码总数 
	private List<String> destTermIDList;// 21*DestTermCount        Octet String*   短消息接收号码
	private int msgLength;//       1       Integer 短消息长度
	private String msgContent = "";//      MsgLength       Octet String    短消息内容
	private String reserve = "";// 8       Octet String    保留

	public Submit()
	{
	}
	
	public Submit(List<String> phones, String content)
	{
//		srcTermID = "18967441128";
		srcTermID = Constant.SP_NUMBER;
//		srcTermID = "1069071061";
//		chargeTermID = phones.get(0);
		chargeTermID = Constant.SP_NUMBER;
		
		this.destTermIDList = phones;
		this.destTermIDCount = phones.size();
		
		this.msgContent = content;
		try {
			this.msgLength = content.getBytes(Constant.MESSAGE_CODING_TEXT).length;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		for(String userNumber : destTermIDList)
		{
			listUserNumberByte.add(BitUtil.String2Bytes(userNumber, 21));
		}
		byte[] listUserNumberBytes = BitUtil.mergeBytes(listUserNumberByte);

		byte[] messageContentBytes = new byte[0];
		try
		{
			messageContentBytes = msgContent.getBytes(Constant.MESSAGE_CODING_TEXT);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		byte[] buf = BitUtil.mergeBytes(
				new byte[]{BitUtil.intLowOneByte(msgType)},
				new byte[]{BitUtil.intLowOneByte(needReport)},
				new byte[]{BitUtil.intLowOneByte(priority)},
				BitUtil.String2Bytes(serviceID, 10),
				BitUtil.String2Bytes(feeType, 2),
				BitUtil.String2Bytes(feeCode, 6),
				BitUtil.String2Bytes(fixedFee, 6),
				new byte[]{BitUtil.intLowOneByte(msgFormat)},
				BitUtil.String2Bytes(validTime, 17),
				BitUtil.String2Bytes(atTime, 17),
				BitUtil.String2Bytes(srcTermID, 21),
				BitUtil.String2Bytes(chargeTermID, 21),
				new byte[]{BitUtil.intLowOneByte(destTermIDCount)},
				listUserNumberBytes,
				new byte[]{BitUtil.intLowOneByte(msgLength)},
				messageContentBytes,
				BitUtil.String2Bytes(reserve, 8)
				);
		
		return buf;
	}
	
	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getNeedReport() {
		return needReport;
	}

	public void setNeedReport(int needReport) {
		this.needReport = needReport;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
	}

	public int getMsgFormat() {
		return msgFormat;
	}

	public void setMsgFormat(int msgFormat) {
		this.msgFormat = msgFormat;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getAtTime() {
		return atTime;
	}

	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}

	public String getSrcTermID() {
		return srcTermID;
	}

	public void setSrcTermID(String srcTermID) {
		this.srcTermID = srcTermID;
	}

	public String getChargeTermID() {
		return chargeTermID;
	}

	public void setChargeTermID(String chargeTermID) {
		this.chargeTermID = chargeTermID;
	}

	public int getDestTermIDCount() {
		return destTermIDCount;
	}

	public void setDestTermIDCount(int destTermIDCount) {
		this.destTermIDCount = destTermIDCount;
	}

	public List<String> getDestTermIDList() {
		return destTermIDList;
	}

	public void setDestTermIDList(List<String> destTermIDList) {
		this.destTermIDList = destTermIDList;
	}

	public int getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(int msgLength) {
		this.msgLength = msgLength;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	@Override
	public long getId()
	{
		return Constant.SMGP_SUBMIT;
	}
	
}
