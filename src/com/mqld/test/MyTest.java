package com.mqld.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.mqld.constant.Constant;

public class MyTest {

	@Test
	public void test() throws ParseException {
		
//		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");//Из2016-08-10 20:40  
//		String fromDate = simpleFormat.format("2016-05-01 12:00");  
//		String toDate = simpleFormat.format("2016-05-01 12:50");  
//		long from = simpleFormat.parse(fromDate).getTime();  
//		long to = simpleFormat.parse(toDate).getTime();  
//		int minutes = (int) ((to - from)/(1000 * 60));  
//		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		

		    Date d1 = df.parse("2004-01-02 13:31");

		    Date d2 = df.parse("2004-01-02 11:30");

		    long diff = d1.getTime() - d2.getTime();

		    long days = diff / (1000 * 60  );
		    System.out.println(days);
		
	}

}
