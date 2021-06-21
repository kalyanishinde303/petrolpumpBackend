package com.petrol.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.petrol.dao.DiselDao;
import com.petrol.dao.Meter1Dao;
import com.petrol.dao.MeterDao;
import com.petrol.dao.PetrolCalDao;
import com.petrol.helper.DateUtils;
import com.petrol.helper.MeterReportPdf;
import com.petrol.model.DiselCal;
import com.petrol.model.Meter;
import com.petrol.model.MeterDetails;
import com.petrol.model.PetrolCal;

@Service
public class MeterService {

	@Autowired
	MeterDao meterDao;
	@Autowired
	Meter1Dao dao1;
	@Autowired 
	PetrolCalDao ptDao;
	@Autowired 
	DiselDao dsDao;
	@Autowired
	DateUtils utils;
	@Autowired
	MeterReportPdf meterReportPdf;
	public MeterDetails getMeter(String date){
		
		List<Meter> detailsList = meterDao.getMeterOpenClose(date);
		if(detailsList.isEmpty()){
			String lastDate = dateMinus(date);
			return createOpenCloseDetails(meterDao.getMeterOpenClose(lastDate));
			
		} else {
			Optional<PetrolCal> ptcal = ptDao.findById(date);
			PetrolCal cal = ptcal.get();
			Optional<DiselCal> dsCal = dsDao.findById(date);
			DiselCal dCal = dsCal.get();
			MeterDetails meterDetails =  createDetails(detailsList);
			meterDetails.setDiselCal(dCal);
			meterDetails.setPtCal(cal);
			return	meterDetails;
		}
		
		
	}
public MeterDetails getBalance(String date){
		
		
			Optional<PetrolCal> ptcal = ptDao.findById(date);
			PetrolCal cal =	ptcal.isPresent() ?ptcal.get() : null;
			Optional<DiselCal> dsCal = dsDao.findById(date);
			DiselCal dCal = dsCal.isPresent() ? dsCal.get() : null;
			MeterDetails meterDetails =  new MeterDetails();
			meterDetails.setDiselCal(dCal);
			meterDetails.setPtCal(cal);
			return	meterDetails;
		
		
	}
public String deleteMeter(){
	
	ptDao.deleteAll();
	dsDao.deleteAll();
	dao1.deleteAll();
	return	"All Meter records are deleted";


}
    private String dateMinus(String date) {
		// TODO Auto-generated method stub
    	
    	String dateMinus = LocalDate.parse(date).minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	
		return dateMinus;
	}
	public String saveMeterDetails(MeterDetails details){
		
			if(meterDao.getMeterOpenClose(details.getDate()).isEmpty()){
				meterDao.saveMeterDetails(details.getM1());
				meterDao.saveMeterDetails(details.getM2());
				meterDao.saveMeterDetails(details.getM3());
				meterDao.saveMeterDetails(details.getM4());
				meterDao.savePetlCalDetails(details.getPtCal());
				meterDao.saveDsCalDetails(details.getDiselCal());
			} else {
//				meterDao.updateMeterDetails(details.getM1());
//				meterDao.updateMeterDetails(details.getM2());
//				meterDao.updateMeterDetails(details.getM3());
//				meterDao.updateMeterDetails(details.getM4());
				dao1.save(details.getM1());
				dao1.save(details.getM2());
				dao1.save(details.getM3());
				dao1.save(details.getM4());
				ptDao.save(details.getPtCal());
				dsDao.save(details.getDiselCal());
			}
		
		return "Meter Record saved successfully";
		
	}
	public MeterDetails createDetails(List<Meter> meters) {
		MeterDetails details =  new MeterDetails();
		meters.forEach(m -> {
			if(m.getMid().equals("meter1")){
				details.setM1(m);
			}else if(m.getMid().equals("meter2")){
				details.setM2(m);
			}else if(m.getMid().equals("meter3")){
				details.setM3(m);
			} else {
				details.setM4(m);
			}
		});
		
		return details;
	}
	
	public MeterDetails createOpenCloseDetails(List<Meter> meters) {
		MeterDetails details =  new MeterDetails();
		meters.forEach(m -> {
			if(m.getMid().equals("meter1")){
				details.getM1().setmOpen(m.getmClose());
			}else if(m.getMid().equals("meter2")){
				details.getM2().setmOpen(m.getmClose());
			}else if(m.getMid().equals("meter3")){
				details.getM3().setmOpen(m.getmClose());
			} else {
				details.getM4().setmOpen(m.getmClose());
			}
		});
		
		return details;
	}
	public List<Object> getMeterSaleReport(String meterType, String fromDate, String toDate) {
		
		try {
		SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
		Date fd= formatter1.parse(fromDate);
		Date td = formatter1.parse(toDate);
		LocalDateTime ldt = LocalDateTime.ofInstant(fd.toInstant(), ZoneId.systemDefault()).minusDays(1);
		Date fd1 = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime ldt1 = LocalDateTime.ofInstant(td.toInstant(), ZoneId.systemDefault()).plusDays(1);
		Date td1 = Date.from(ldt1.atZone(ZoneId.systemDefault()).toInstant());
		if(meterType.equals("Petrol")){
			List<PetrolCal> ptCal = (List<PetrolCal>) ptDao.findAll();
			List<Object> tmp = new ArrayList<Object>();
			ptCal.forEach(pt -> {
				try {
					if(utils.checkDateINBetween(pt.getDate(), fd1, td1)){
						pt.setDate(utils.dateModify(pt.getDate()));
						tmp.add(pt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return tmp;
		}
		if(meterType.equals("Disel")){
			List<DiselCal> ptCal = (List<DiselCal>) dsDao.findAll();
			List<Object> tmp = new ArrayList<Object>();
			ptCal.forEach(pt -> {
				try {
					if(utils.checkDateINBetween(pt.getDate(), fd1, td1)){
						pt.setDate(utils.dateModify(pt.getDate()));
						tmp.add(pt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return tmp;
		}
		} catch(Exception e ) {
			
		}
		return null;
	}
	
	public String printMeterReport(List<Object> meterList, String type, String fromDate, String toDate ) throws URISyntaxException, IOException, DocumentException, ParseException {
		String fileName;
		String response = null;
		if(type.equals("Petrol")) {
			List<PetrolCal> pt = new ArrayList<>();
			meterList.forEach( t -> {
				pt.add((PetrolCal) t);
			}
			);
			SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
			Date fd= formatter1.parse(fromDate);
			Date td = formatter1.parse(toDate);
			String name =  fromDate.toString()+ " to " +  toDate.toString();
			response = meterReportPdf.createPetrolPDF(pt, name);
		}
		if(type.equals("Disel")) {
			List<DiselCal> pt = new ArrayList<>();
			meterList.forEach( t -> {
				pt.add((DiselCal) t);
			}
			);
			SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
			Date fd= formatter1.parse(fromDate);
			Date td = formatter1.parse(toDate);
			String name = fromDate.toString()+ " to " +  toDate.toString();
			response = meterReportPdf.createDiselPDF(pt, name);
		}
		return response;
	}
}
