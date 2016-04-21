package com.sub;

public class B
{
	public static void main (String[] arges)
	{
		O1 a = new O1();
		AAA1 b = new AAA1();
		a.setTarget(b);
		a.a();
		
		String s="asd>asdf<asdfasdf=asdfas&as";
		s = s.replaceAll("[<>&]", "");
		System.out.println(s);
	}
}
