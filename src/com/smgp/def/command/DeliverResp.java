package com.smgp.def.command;

import com.sgip.util.BitUtil;
import com.smgp.def.base.Constant;

/**
 * deliver命令回复
 * @author weinianjie
 * @date   2016年4月1日
 */
public class DeliverResp extends SMGPCommand {

	private String msgId; // 消息ID，10字节
	private int status;// 0成功，其他失败，4字节

	public DeliverResp()
	{
		
	}
	
	public DeliverResp(byte[] source) throws Exception
	{
		byte[] _msgId = new byte[10];
		byte[] _status = new byte[4];
		BitUtil.copyBytes(source, _msgId, 0, 10, 0);
		BitUtil.copyBytes(source, _status, 10, 4, 0);
		msgId = new String(_msgId);
		status = BitUtil.byte4ToInt(_status);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] _msgId = msgId.getBytes();
		byte[] _status = BitUtil.int2Bytes(status);
		return BitUtil.mergeBytes(_msgId, _status);
	}
	
	@Override
	public long getId()
	{
		return Constant.SMGP_DELIVER_RESP;
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
