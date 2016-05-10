package com.smgp.def.base;

public class SeqCalculator {

	private static final long MIN_SEQ = 0;
	
	private static final long MAX_SEQ = Integer.MAX_VALUE;
	
	private static long CUR_SEQ = MIN_SEQ;
	
	public static synchronized long getSeq()
	{
		if(CUR_SEQ == MAX_SEQ)
		{
			CUR_SEQ = MIN_SEQ;
		}
		return CUR_SEQ++;
	}
}
