package com.petrol.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.petrol.model.Customer;
import com.petrol.model.DiselCal;
import com.petrol.model.Meter;
import com.petrol.model.PetrolCal;
import com.petrol.model.Transactions;

@Component
public class MeterReportPdf {

	@Autowired
	DateUtils utils;
  @Autowired
  ConfigPropertiesFactory propertiesFactory;
	double petroltotal = 0;
	double diselTotal = 0;
	public String createPetrolPDF(List<PetrolCal> petrolSale, String fileName) throws URISyntaxException, IOException, DocumentException{
        	Document document = new Document();
        	try{
        	PdfWriter.getInstance(document, new FileOutputStream(propertiesFactory.getPetrolReportLoc()+fileName+".pdf"));
        	document.open();
        	PdfPTable table = new PdfPTable(4);
        	addRows(table,petrolSale);
        	PdfPTable table1 = new PdfPTable(2);
        	PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("Total Sale"));
			    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table1.addCell(horizontalAlignCell);
			    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(String.valueOf(petroltotal)));
			    verticalAlignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table1.addCell(verticalAlignCell);
        	document.add(table);
        	document.add(table1);
        	document.close();
        	} catch(Exception e) {
        		e.printStackTrace();
        		return "Folder not found or close opend file";
        	}
        return "Report File downloded in "+ propertiesFactory.getPetrolReportLoc()+" this folder";
	}
	
	private void addRows(PdfPTable table,List<PetrolCal> petrolSale) {
		Stream.of("Date","Petrol ltr","Petrol Rate","Total Sale").forEach(h1 -> {
			PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        
	        header.setPhrase(new Phrase(h1));
	        table.addCell(header);
		});
		double t =0;
		
			for(int j=0;j<petrolSale.size(); j++) {
				 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(petrolSale.get(j).getDate()));
				    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(horizontalAlignCell);
				    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(String.valueOf(petrolSale.get(j).getTotalPtLtr())));
				    verticalAlignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    table.addCell(verticalAlignCell);
				    PdfPCell petrolRate = new PdfPCell(new Phrase(String.valueOf(petrolSale.get(j).getPetrolRate())));
				    petrolRate.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    table.addCell(petrolRate);
				    PdfPCell petrolCell = new PdfPCell(new Phrase(String.valueOf(petrolSale.get(j).getPetrolSale())));
				    petrolCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    table.addCell(petrolCell);
				    t += petrolSale.get(j).getPetrolSale();
			}
			petroltotal = t;
	    
	}
	public String createDiselPDF(List<DiselCal> diselList, String fileName) throws URISyntaxException, IOException, DocumentException{
    	Document document = new Document();
    	try{
    	PdfWriter.getInstance(document, new FileOutputStream(propertiesFactory.getDiselReportLoc()+fileName+".pdf"));
    	document.open();
    	PdfPTable table = new PdfPTable(4);
    	addRows1(table,diselList);
    	document.add(table);
    	PdfPTable table1 = new PdfPTable(2);
    	PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("Total Sale"));
		    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table1.addCell(horizontalAlignCell);
		    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(String.valueOf(diselTotal)));
		    verticalAlignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    table1.addCell(verticalAlignCell);
		    document.add(table1);
    	document.close();
    	} catch(Exception e) {
    		return "Folder not found or close opend file";
    	}
    return "Report File downloded in "+propertiesFactory.getDiselReportLoc() +" this folder";
}

private void addRows1(PdfPTable table,List<DiselCal> diselList) {
	Stream.of("Date","Disel ltr","Disel Rate","Total Sale").forEach(h1 -> {
		PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        
        header.setPhrase(new Phrase(h1));
        table.addCell(header);
	});
	double t =0;
		for(int j=0;j<diselList.size(); j++) {
			 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(diselList.get(j).getDate()));
			    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(horizontalAlignCell);
			    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(String.valueOf(diselList.get(j).getTotalDtLtr())));
			    verticalAlignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(verticalAlignCell);
			    
			    PdfPCell diselRateCell = new PdfPCell(new Phrase(String.valueOf(diselList.get(j).getDiselRate())));
			    diselRateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(diselRateCell);
			    
			    PdfPCell diselSaleCell = new PdfPCell(new Phrase(String.valueOf(diselList.get(j).getDiselSale())));
			    diselSaleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(diselSaleCell);
			    t += diselList.get(j).getDiselSale();
		}
		
		diselTotal = t;
    
}
}
