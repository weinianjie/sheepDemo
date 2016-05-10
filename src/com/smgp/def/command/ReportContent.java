package com.smgp.def.command;

import com.sgip.util.BitUtil;

/**
 * deliver命令如果是状态报告时候对应的msgContent
 * @author weinianjie
 * @date   2016年4月1日
 */
public class ReportContent
{
//	注：“状态报告格式”采用SMPP V3.4中的规定，即“id:IIIIIIIIII sub:SSS dlvrd:DDD Submit date:YYMMDDhhmm done date: YYMMDDhhmm stat:DDDDDDD err:E Text:……”。
	private String msgId;//	Id	10	Octet String	状态报告对应原短消息的MsgID
	private String sub;//	sub	3	Octet String	取缺省值001
	private String dlvrd;//	Dlvrd	3	Octet String	取缺省值001
	private String submitDate;//	Submit_date	10	Octet String	短消息提交时间（格式：年年月月日日时时分分，例如010331200000）
	private String doneDate;//	done_date	10	Octet String	短消息下发时间（格式：年年月月日日时时分分，例如010331200000）
	private String stat;//	Stat	7	Octet String	短消息的最终状态（参见第6.2.63.1节短消息状态表）
	private String err;//	Err	3	Octet String	参见第6.2.63.2节错误代码表
	private String txt;//	Txt	20	Octet String	前3个字节，表示短消息长度（用ASCII码表示），后17个字节表示短消息的内容（保证内容不出现乱码）
	
	public ReportContent(byte[] source) throws Exception
	{
System.out.println("reportContent.length=" + source.length);
		byte[] _msgId = new byte[10];
		byte[] _sub = new byte[3];
		byte[] _dlvrd = new byte[3];
		byte[] _submitDate = new byte[10];
		byte[] _doneDate = new byte[10];
		byte[] _stat = new byte[7];
		byte[] _err = new byte[3];
		byte[] _txt = new byte[20];
		
		BitUtil.copyBytes(source, _msgId, 3, 10, 0);
		BitUtil.copyBytes(source, _sub, 18, 3, 0);
		BitUtil.copyBytes(source, _dlvrd, 28, 3, 0);
		BitUtil.copyBytes(source, _submitDate, 44, 10, 0);
		BitUtil.copyBytes(source, _doneDate, 65, 10, 0);
		BitUtil.copyBytes(source, _stat, 81, 7, 0);
		BitUtil.copyBytes(source, _err, 93, 3, 0);
		BitUtil.copyBytes(source, _txt, 102, 20, 0);
		
		this.msgId = BitUtil.bcd2Str(_msgId);
		this.sub = new String(_sub);
		this.dlvrd = new String(_dlvrd);
		this.submitDate = new String(_submitDate);
		this.doneDate = new String(_doneDate);
		this.stat = new String(_stat);
		this.err = new String(_err);
//		this.txt = new String(_txt);
	}
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getDlvrd() {
		return dlvrd;
	}
	public void setDlvrd(String dlvrd) {
		this.dlvrd = dlvrd;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getDoneDate() {
		return doneDate;
	}
	public void setDoneDate(String doneDate) {
		this.doneDate = doneDate;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
}
