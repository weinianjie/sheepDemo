package com.sgip.def.command;

import com.sgip.def.base.Constant;
import com.sgip.util.BitUtil;

/**
 * deliver命令回复
 * @author weinianjie
 * @date   2016年4月1日
 */
public class DeliverResp extends SGIPCommand {

	private int result;// 0成功，其他失败，1字节

	public DeliverResp(byte[] source) throws Exception
	{
		byte[] _result = new byte[1];
		byte[] _reserve = new byte[8];
		BitUtil.copyBytes(source, _result, 0, 1, 0);
		BitUtil.copyBytes(source, _reserve, 1, 8, 0);
		this.result = BitUtil.convertUnsignByte2Int(_result[0]);
		super.setReserve(new String(_reserve, Constant.RESPONSE_ECODING));
	}
	
	@Override
	public byte[] getByteData()
	{
		byte _result = BitUtil.intLowOneByte(this.result);
		byte[] _reserve = super.getByteData();
		return BitUtil.mergeBytes(new byte[]{_result}, _reserve);
	}
	
	@Override
	public int getId()
	{
		return Constant.SGIP_DELIVER_RESP;
	}	
	
	public int getResult()
	{
		return result;
	}

	public void setResult(int result)
	{
		this.result = result;
	}	
}
