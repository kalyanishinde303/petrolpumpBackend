package com.petrol.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("classpath:pumpConfig.properties")
public class ConfigPropertiesFactory {

	@Value("${petrolMeterReportFile}")
	private String petrolReportLoc;
	
	@Value("${diselMeterReportFile}")
	private String diselReportLoc;

	@Value("${customerReportFile}")
	private String customerReportLoc;
	
	@Value("${expensesReportFile}")
	private String espensesReportLoc;
	
	@Value("${dailyReportFile}")
	private String dailyReportLoc;
	
	@Value("${balanceSheetReportFile}")
	private String balanceSheetReportLoc;
	
	
	public String getBalanceSheetReportLoc() {
		return balanceSheetReportLoc;
	}

	public void setBalanceSheetReportLoc(String balanceSheetReportLoc) {
		this.balanceSheetReportLoc = balanceSheetReportLoc;
	}

	public String getPetrolReportLoc() {
		return petrolReportLoc;
	}

	public void setPetrolReportLoc(String petrolReportLoc) {
		this.petrolReportLoc = petrolReportLoc;
	}

	public String getDiselReportLoc() {
		return diselReportLoc;
	}

	public void setDiselReportLoc(String diselReportLoc) {
		this.diselReportLoc = diselReportLoc;
	}

	public String getCustomerReportLoc() {
		return customerReportLoc;
	}

	public void setCustomerReportLoc(String customerReportLoc) {
		this.customerReportLoc = customerReportLoc;
	}

	public String getEspensesReportLoc() {
		return espensesReportLoc;
	}

	public void setEspensesReportLoc(String espensesReportLoc) {
		this.espensesReportLoc = espensesReportLoc;
	}

	public String getDailyReportLoc() {
		return dailyReportLoc;
	}

	public void setDailyReportLoc(String dailyReportLoc) {
		this.dailyReportLoc = dailyReportLoc;
	}
	 
	
}
