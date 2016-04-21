package com;

import com.google.gson.Gson;

public class SXXendSmsResult
{
	private String respCode;
	private String failCount;
	private String createTime;
	private String smsId;
	
	public String getRespCode()
	{
		return respCode;
	}
	public void setRespCode(String respCode)
	{
		this.respCode = respCode;
	}
	public String getFailCount()
	{
		return failCount;
	}
	public void setFailCount(String failCount)
	{
		this.failCount = failCount;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	public String getSmsId()
	{
		return smsId;
	}
	public void setSmsId(String smsId)
	{
		this.smsId = smsId;
	}
	
	public static void main(String[] args)
	{
		String a = "{\"respCode\":\"00000\",\"failCount\":\"0\",\"createTime\":\"2015-11-11 11:32:19\",\"smsId\":\"6bd36f9ee2ec4a92b1c5e8a3b0501d83\"}";
		SXXendSmsResult b = new Gson().fromJson(a, SXXendSmsResult.class);
		System.out.println(b.respCode);
	}
}
