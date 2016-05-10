package com.smgp.def.command;

import com.sgip.util.BitUtil;
import com.smgp.def.base.Constant;

/**
 * deliver命令
 * @author weinianjie
 * @date   2016年4月1日
 */
public class Deliver extends SMGPCommand {
	
	private String msgId;// 10
	private int isReport;// 1
	private int msgFormat;// 1  0＝ASCII编码；3＝短消息写卡操作；4＝二进制短消息；8＝UCS2编码；15＝GB18030编码；246（F6）＝(U)SIM相关消息；
	private String recvTime; // 14
	private String srcTermID;// 21
	private String destTermID;// 21
	private int msgLength; // 1
	
	// 如果是上行，则为消息的内容，非固定字节；如果是状态报告，参见6.2.63状态报告内容格式
	private String msgContent=""; 
	private ReportContent reportContent = null;
	
	private String reserve = "";// 8
	
	public Deliver()
	{
	}
	
	public Deliver(byte[] source) throws Exception
	{
		byte[] _msgId = new byte[10];
		byte[] _isReport = new byte[1];
		byte[] _msgFormat = new byte[1];
		byte[] _recvTime = new byte[14];
		byte[] _srcTermID = new byte[21];
		byte[] _destTermID = new byte[21];
		byte[] _msgLength = new byte[1];
		
		BitUtil.copyBytes(source, _msgId, 0, 10, 0);
		BitUtil.copyBytes(source, _isReport, 10, 1, 0);
		BitUtil.copyBytes(source, _msgFormat, 11, 1, 0);
		BitUtil.copyBytes(source, _recvTime, 12, 14, 0);
		BitUtil.copyBytes(source, _srcTermID, 26, 21, 0);
		BitUtil.copyBytes(source, _destTermID, 47, 21, 0);
		BitUtil.copyBytes(source, _msgLength, 68, 1, 0);
		
		this.msgId = BitUtil.bcd2Str(_msgId);
		this.isReport = BitUtil.convertUnsignByte2Int(_isReport[0]);
		this.msgFormat = BitUtil.convertUnsignByte2Int(_msgFormat[0]);
		this.recvTime = new String(_recvTime);
		this.srcTermID = new String(_srcTermID);
		this.destTermID = new String(_destTermID);
		this.msgLength = BitUtil.convertUnsignByte2Int(_msgLength[0]);

		byte[] _msgContent = new byte[msgLength];
		byte[] _reserve = new byte[8];
		BitUtil.copyBytes(source, _msgContent, 69, msgLength, 0);
		BitUtil.copyBytes(source, _reserve, 69 + msgLength, 8, 0);
		
		if(this.isReport == 1)
		{
			// 状态报告 
			//System.out.println(new String(_msgContent));
			this.reportContent = new ReportContent(_msgContent);
		}else
		{
			// 上行 
			if(this.msgFormat == 8)
			{
				// UCS2
				this.msgContent = new String(_msgContent, "UnicodeBigUnmarked");
			}else if(this.msgFormat == 15)
			{
				// GBK
				this.msgContent = new String(_msgContent, "GBK");
			}else
			{
				this.msgContent = new String(_msgContent);
			}			
		}
		
		this.reserve = new String(_reserve);
	}
	
	@Override
	public byte[] getByteData()
	{
		// 作为客户端才需要，这里不解析
		return null;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getIsReport() {
		return isReport;
	}

	public void setIsReport(int isReport) {
		this.isReport = isReport;
	}

	public int getMsgFormat() {
		return msgFormat;
	}

	public void setMsgFormat(int msgFormat) {
		this.msgFormat = msgFormat;
	}

	public String getRecvTime() {
		return recvTime;
	}

	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}

	public String getSrcTermID() {
		return srcTermID;
	}

	public void setSrcTermID(String srcTermID) {
		this.srcTermID = srcTermID;
	}

	public String getDestTermID() {
		return destTermID;
	}

	public void setDestTermID(String destTermID) {
		this.destTermID = destTermID;
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

	public ReportContent getReportContent() {
		return reportContent;
	}

	public void setReportContent(ReportContent reportContent) {
		this.reportContent = reportContent;
	}

	@Override
	public long getId()
	{
		return Constant.SMGP_DELIVER;
	}
}
