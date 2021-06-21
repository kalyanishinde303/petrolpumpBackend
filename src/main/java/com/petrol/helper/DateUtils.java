package com.petrol.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

	public boolean checkDateINBetween(String date, Date fDate, Date tDate) throws ParseException {
		
		SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
		Date d1= formatter1.parse(date);
		
		boolean f1 = d1.after(fDate);
		boolean f2 = d1.before(tDate);
		
		return f1==f2;
	}
	
	public String dateModify(String date) {
		SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
		Date d1= null;
		try {
			 d1= formatter1.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return date;
		}
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		return dateFormat.format(d1); 
	}
}
