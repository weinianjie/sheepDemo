package com.sgip.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

/**
 * @author weinianjie
 * @date 2016年4月1日
 */
public class BitUtil
{

	private static final String CURRENT_DATE_FILL_SIGN = "0";

	// 4byte数组转成long
	public static long byte4ToLong2(byte[] b)
	{
		return (((long) b[7] & 0xff) << 0) | (((long) b[6] & 0xff) << 8) | (((long) b[5] & 0xff) << 16)
				| (((long) b[4] & 0xff) << 24) | (((long) b[3] & 0xff) << 32) | (((long) b[2] & 0xff) << 40)
				| (((long) b[1] & 0xff) << 48) | (((long) b[0] & 0xff) << 56);
	}

	public static long byte4ToLong(byte[] b)
	{
		return ((long) b[3] & 0xff) | (((long) b[2] & 0xff) << 8) | (((long) b[1] & 0xff) << 16)
				| (((long) b[0] & 0xff) << 24);
	}

	// 4byte数组转成int
	public static int byte4ToInt(byte[] b)
	{
		return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16 | (b[0] & 0xff) << 24;
	}

	/**
	 * 转换字符串去指定长度的字节数组 字节数组长度不能小于字符串转换后的长度
	 * 
	 * @param data
	 * @param charsetName
	 * @param length
	 * @return
	 */
	public static byte[] convertString2Bytes(String data, String charsetName, int length)
	{
		try
		{
			byte[] bytes = data.getBytes(charsetName);
			int copyLength = bytes.length;
			byte[] result = new byte[length];
			copyBytes(bytes, result, 1, copyLength, 1);
			return result;
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return new byte[length];
	}

	public static byte[] convertString2Bytes(String data, int length)
	{
		return convertString2Bytes(data, "UTF-8", length);
	}

	public static byte intLowOneByte(int data)
	{
		return (byte) (0xFF & data);
	}

	public static byte[] int2Bytes(int data)
	{
		byte[] result = new byte[4];
		result[3] = ((byte) (0xFF & data));
		result[2] = ((byte) ((0xFF00 & data) >> 8));
		result[1] = ((byte) ((0xFF0000 & data) >> 16));
		result[0] = ((byte) ((0xFF000000 & data) >> 24));
		return result;
	}

	public static byte[] longLowFourBytes(long data)
	{
		byte[] result = new byte[4];
		result[3] = ((byte) (0xFF & data));
		result[2] = ((byte) ((0xFF00 & data) >> 8));
		result[1] = ((byte) ((0xFF0000 & data) >> 16));
		result[0] = ((byte) ((0xFF000000 & data) >> 24));
		return result;
	}

	public static byte[] String2Bytes(String data, int length)
	{
		byte[] result = new byte[length];
		if (data != null)
		{
			byte[] dataBytes = data.getBytes();
			length = Math.min(dataBytes.length, length);
			copyBytes(dataBytes, result, 0, length, 0);
		}
		return result;
	}

	/**
	 * 复制字节
	 * 
	 * @param source
	 * @param dest
	 * @param sourceFrom
	 * @param copyLength
	 *            需要复制的长度
	 * @param destFrom
	 */
	public static void copyBytes(byte[] source, byte[] dest, int sourceFrom, int copyLength, int destFrom)
	{
		for (int i = destFrom; i < destFrom + copyLength; i++)
		{
			dest[i] = source[sourceFrom++];
		}
	}

	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2)
	{
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}	
	
	public static byte[] mergeBytes(byte[]... bytes)
	{
		byte[] temp = new byte[0];
		for (int i = 0; i < bytes.length; i++)
		{
//			temp = org.apache.commons.lang.ArrayUtils.addAll(temp, bytes[i]);
			temp = byteMerger(temp, bytes[i]);
		}
		return temp;
	}

	public static byte[] mergeBytes(List<byte[]> list)
	{
		byte[] temp = new byte[0];
		if (null != list)
		{
			for (byte[] bs : list)
			{
//				temp = org.apache.commons.lang.ArrayUtils.addAll(temp, bs);
				temp = byteMerger(temp, bs);
			}
		}
		return temp;
	}

	/**
	 * 得到当前日期 如1017093720 表示 10月17号9点37分20秒
	 * 
	 * @return 日期的long表示形式
	 */
	public static long getCurrentDate()
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		String monthStr = month < 10 ? CURRENT_DATE_FILL_SIGN + month : month + "";
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String dayStr = day < 10 ? CURRENT_DATE_FILL_SIGN + day : day + "";
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		String hourStr = hour < 10 ? CURRENT_DATE_FILL_SIGN + hour : hour + "";
		int minute = cal.get(Calendar.MINUTE);
		String minuteStr = minute < 10 ? CURRENT_DATE_FILL_SIGN + minute : minute + "";
		int second = cal.get(Calendar.SECOND);
		String secondStr = second < 10 ? CURRENT_DATE_FILL_SIGN + second : second + "";
		String cur = monthStr + dayStr + hourStr + minuteStr + secondStr;
		return Long.valueOf(cur);

	}

	public static void main(String[] args)
	{
		// System.out.println(Arrays.toString(convertLong2FourBytes(0x1l)));
		// System.out.println(getCurrentDate());
		/*
		 * byte[] source = new byte[]{1,2,3,4}; byte[] dest = new byte[12]; //
		 * copyBytes(source, dest, 1, 4, 9); //
		 * System.out.println(Arrays.toString(dest));
		 * 
		 * byte[] source2 = new byte[]{3,4,7,7};
		 * 
		 * byte[] source3 = new byte[]{7,7,9,9}; List<byte[]> list = null; dest
		 * = mergeBytes(list); dest = mergeBytes(source,source2,source3);
		 * System.out.println(Arrays.toString(dest));
		 */

		/*
		 * long d = 1102020202l; System.out.println(Long.toBinaryString(d));
		 * byte[] bs = convertLong2FourBytes(d);
		 * System.out.println(Arrays.toString(bs));
		 * 
		 * System.out.println(byte4ToLong(bs));
		 */
		// byte b = -4;

		// System.out.println(convertUnsignByte2Int(b));
	}

	public static int convertUnsignByte2Int(byte b)
	{
		int i = (b & 0xFF);
		return i;
	}

}
