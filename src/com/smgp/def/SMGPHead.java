package com.smgp.def;

import com.sgip.util.BitUtil;
import com.smgp.def.base.ByteObject;
import com.smgp.def.base.SeqCalculator;

/**
 * SMGP消息头
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SMGPHead implements ByteObject
{
	// 固定12字节
	private int packetLength;// 消息长度，4字节，最后自动技算，不提供get和set方法，这里仅用于占位，注意由于java里有符号和无符号的关系，最大值2147483647，如果有超长数据，改为long
	private long requestId;// 请求标识，4字节
	private long sequenceId;// 消息流水号，4字节
	
	public SMGPHead()
	{
		
	}
	
	public SMGPHead(long requestId)
	{
		this.requestId = requestId;
		this.sequenceId = SeqCalculator.getSeq();
	}
	
	public SMGPHead(byte[] source) throws Exception
	{
		byte[] _packetLength = new byte[4];
		byte[] _requestId = new byte[4];
		byte[] _sequenceId = new byte[4];
		BitUtil.copyBytes(source, _packetLength, 0, 4, 0);
		BitUtil.copyBytes(source, _requestId, 4, 4, 0);
		BitUtil.copyBytes(source, _sequenceId, 8, 4, 0);
		this.packetLength = BitUtil.byte4ToInt(_packetLength);
		this.requestId = BitUtil.byte4ToLong(_requestId);
		this.sequenceId = BitUtil.byte4ToLong(_sequenceId);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] _packetLength = BitUtil.int2Bytes(this.packetLength);
		byte[] _requestId = BitUtil.longLowFourBytes(this.requestId);
		byte[] _sequenceId = BitUtil.longLowFourBytes(this.sequenceId);
		return BitUtil.mergeBytes(_packetLength, _requestId, _sequenceId);
	}

	public int getPacketLength() {
		return packetLength;
	}

	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(long sequenceId) {
		this.sequenceId = sequenceId;
	}
	
}
