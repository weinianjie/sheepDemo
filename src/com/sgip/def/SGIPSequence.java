package com.sgip.def;

import com.sgip.def.base.ByteObject;
import com.sgip.def.base.Constant;
import com.sgip.def.base.SeqCalculator;
import com.sgip.util.BitUtil;

/**
 * SGIP序列号
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SGIPSequence implements ByteObject
{
	
    // 序列号由三个无符号32位整型数组成。对于SP节点编号（3开头的10位整数）只有无符号整型可以放下，另外序号部分的最大值也是无符号32位整数的最大值。
	// 固定12字节，基于上面描述，这里先用long来存储，转成字节的时候再切成4个字节的int
	private long sourceNodeNumber;// 源节点编号，4字节
	private long commandGenerateDate;// 命令生产日期，4字节
	private long commandCircleNumber;// 命令循环序号，4字节
	
	// 自动生成
	public SGIPSequence()
	{
		this.sourceNodeNumber = Constant.NODE_NUMBER;
		this.commandGenerateDate = BitUtil.getCurrentDate();
		this.commandCircleNumber = SeqCalculator.getSeq();
		SeqCalculator.reset();
		
	}
	
	public SGIPSequence(byte[] source)  throws Exception
	{
		byte[] _sourceNodeNumber = new byte[4];
		byte[] _commandGenerateDate = new byte[4];
		byte[] _commandCircleNumber = new byte[4];
		BitUtil.copyBytes(source, _sourceNodeNumber, 0, 4, 0);
		BitUtil.copyBytes(source, _commandGenerateDate, 4, 4, 0);
		BitUtil.copyBytes(source, _commandCircleNumber, 8, 4, 0);
		this.sourceNodeNumber = BitUtil.byte4ToLong(_sourceNodeNumber);
		this.commandGenerateDate = BitUtil.byte4ToLong(_commandGenerateDate);
		this.commandCircleNumber = BitUtil.byte4ToLong(_commandCircleNumber);
	}
	
	@Override
	public byte[] getByteData()
	{
		byte[] _sourceNodeNumber = BitUtil.longLowFourBytes(this.sourceNodeNumber);
		byte[] _commandGenerateDate = BitUtil.longLowFourBytes(this.commandGenerateDate);
		byte[] _commandCircleNumber = BitUtil.longLowFourBytes(this.commandCircleNumber);
		return BitUtil.mergeBytes(_sourceNodeNumber, _commandGenerateDate, _commandCircleNumber);
	}

	public long getSourceNodeNumber()
	{
		return sourceNodeNumber;
	}

	public void setSourceNodeNumber(long sourceNodeNumber)
	{
		this.sourceNodeNumber = sourceNodeNumber;
	}

	public long getCommandGenerateDate()
	{
		return commandGenerateDate;
	}

	public void setCommandGenerateDate(long commandGenerateDate)
	{
		this.commandGenerateDate = commandGenerateDate;
	}

	public long getCommandCircleNumber()
	{
		return commandCircleNumber;
	}

	public void setCommandCircleNumber(long commandCircleNumber)
	{
		this.commandCircleNumber = commandCircleNumber;
	}
}
