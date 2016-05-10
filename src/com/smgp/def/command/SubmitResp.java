package com.smgp.def.command;

import java.io.UnsupportedEncodingException;

import com.sgip.util.BitUtil;
import com.smgp.def.base.Constant;

/**
 * submit命令回复
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SubmitResp extends SMGPCommand {

	private String msgId; // 消息ID，10字节,3字节SMGW代码，4字节时间，3字节序号，结果BCD编码，比如解码后有755061  04291029  232460
	private int status;// 0成功，其他失败，4字节

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		byte[] bs = new byte[]{117,80,97,4,41,16,41,35,36,96};
		String result = BitUtil.bcd2Str(bs);		
		System.out.println(result);
	}
	
	public SubmitResp(byte[] source) throws Exception
	{
		byte[] _msgId = new byte[10];
		byte[] _status = new byte[4];
		BitUtil.copyBytes(source, _msgId, 0, 10, 0);
		BitUtil.copyBytes(source, _status, 10, 4, 0);
		msgId = BitUtil.bcd2Str(_msgId);
		status = BitUtil.byte4ToInt(_status);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] _msgId = BitUtil.str2Bcd(msgId);
		byte[] _status = BitUtil.int2Bytes(status);
		return BitUtil.mergeBytes(_msgId, _status);
	}
	
	@Override
	public long getId()
	{
		return Constant.SMGP_SUBMIT_RESP;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
}
