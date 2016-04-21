package com.sgip.def.command;

import com.sgip.def.base.Constant;
import com.sgip.util.BitUtil;

/**
 * report命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public class Report extends SGIPCommand {
	
	private String submitSequenceNumber;// 所设计的submit或者deliver命令的序列号，12字节，只能用String
	private int reportType;// 0：对先前一条Submit命令的状态报告， 1：对先前一条前转Deliver命令的状态报告， 1字节，
	private String userNumber;// 86开头的手机号，21字节
	private int state;// 0：发送成功 1：等待发送 2：发送失败，1字节
	private int errorCode;// 当State=2时为错误码值，否则为0，1字节
	
	public Report()
	{
	}
	
	public Report(byte[] source) throws Exception
	{
		byte[] _submitSequenceNumber1 = new byte[4];
		byte[] _submitSequenceNumber2 = new byte[4];
		byte[] _submitSequenceNumber3 = new byte[4];
		byte[] _reportType = new byte[1];
		byte[] _userNumber = new byte[21];
		byte[] _state = new byte[1];
		byte[] _errorCode = new byte[1];
		byte[] _reserve = new byte[8];
		BitUtil.copyBytes(source, _submitSequenceNumber1, 0, 4, 0);
		BitUtil.copyBytes(source, _submitSequenceNumber2, 4, 4, 0);
		BitUtil.copyBytes(source, _submitSequenceNumber3, 8, 4, 0);
		BitUtil.copyBytes(source, _reportType, 12, 1, 0);
		BitUtil.copyBytes(source, _userNumber, 13, 21, 0);
		BitUtil.copyBytes(source, _state, 34, 1, 0);
		BitUtil.copyBytes(source, _errorCode, 35, 1, 0);
		BitUtil.copyBytes(source, _reserve, 36, 8, 0);
		submitSequenceNumber = String.valueOf(BitUtil.byte4ToInt(_submitSequenceNumber1)) 
				+ String.valueOf(BitUtil.byte4ToInt(_submitSequenceNumber2))
				+ String.valueOf(BitUtil.byte4ToInt(_submitSequenceNumber3));
		this.reportType = BitUtil.convertUnsignByte2Int(_reportType[0]);
		this.userNumber = new String(_userNumber, Constant.RESPONSE_ECODING);
		this.state = BitUtil.convertUnsignByte2Int(_state[0]);
		this.errorCode = BitUtil.convertUnsignByte2Int(_errorCode[0]);
		super.setReserve(new String(_reserve, Constant.RESPONSE_ECODING));
	}
	
	@Override
	public byte[] getByteData()
	{
		// 作为客户端才需要，这里不解析
		return null;
	}
	
	@Override
	public int getId()
	{
		return Constant.SGIP_REPORT;
	}

	public String getSubmitSequenceNumber()
	{
		return submitSequenceNumber;
	}

	public void setSubmitSequenceNumber(String submitSequenceNumber)
	{
		this.submitSequenceNumber = submitSequenceNumber;
	}

	public int getReportType()
	{
		return reportType;
	}

	public void setReportType(int reportType)
	{
		this.reportType = reportType;
	}

	public String getUserNumber()
	{
		return userNumber;
	}

	public void setUserNumber(String userNumber)
	{
		this.userNumber = userNumber;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}
}
