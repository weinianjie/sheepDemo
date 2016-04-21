package com.sub;

public class O1 implements A1
{

	private A1 target = null;
	
	
	public A1 getTarget()
	{
		return target;
	}


	public void setTarget(A1 target)
	{
		this.target = target;
	}


	@Override
	public void a()
	{
		System.out.println("AA1");
		target.a();
		
	}

}
