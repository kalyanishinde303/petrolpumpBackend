package com.petrol.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;





import java.util.stream.Stream;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.petrol.model.Customer;
import com.petrol.model.Transactions;

@Component
public class CreatePDF {

	@Autowired
	DateUtils utils;
	@Autowired
	ConfigPropertiesFactory factory;
	public String createPDF(List<Customer> custList, String fileName) throws URISyntaxException, IOException, DocumentException{
        	Document document = new Document();
        	try{
        	PdfWriter.getInstance(document, new FileOutputStream(factory.getDailyReportLoc()+fileName+".pdf"));
        	document.open();
        	PdfPTable table = new PdfPTable(5);
        	addRows(table,custList);
        	document.add(table);
        	document.close();
        	} catch(Exception e) {
        		return "Folder not found or close opend file";
        	}
        return "File downloded in"+ factory.getDailyReportLoc()+" this folder";
	}
	
	private void addRows(PdfPTable table,List<Customer> custList) {
		Stream.of("Name","Date","Type","Medium","Amount").forEach(h1 -> {
			PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        
	        header.setPhrase(new Phrase(h1));
	        table.addCell(header);
		});
		for(int i=0;i<custList.size();i++){
			List<Transactions> tr = custList.get(i).getTranList();
			for(int j=0;j<tr.size(); j++) {
				 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(custList.get(i).getName()));
				    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(horizontalAlignCell);
				    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(utils.dateModify(tr.get(j).getDate())));
				    verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				    table.addCell(verticalAlignCell);
				   
				    table.addCell(tr.get(j).getType());
				    table.addCell(tr.get(j).getMedium());
				    PdfPCell amountCell = new PdfPCell(new Phrase(String.valueOf(tr.get(j).getAmount())));
				    amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    table.addCell(amountCell);
			}
			
			
		}
	    
	}
}
