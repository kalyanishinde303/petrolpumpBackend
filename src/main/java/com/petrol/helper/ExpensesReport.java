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
import com.petrol.model.Expenses;
import com.petrol.model.Transactions;

@Component
public class ExpensesReport {

	
	@Autowired
	DateUtils utils;
	@Autowired
	ConfigPropertiesFactory factory;
	
	double totalExpenses;
	public String createPDF(List<Expenses> expList, String fileName) throws URISyntaxException, IOException, DocumentException{
    	Document document = new Document();
    	try{
    	PdfWriter.getInstance(document, new FileOutputStream(factory.getEspensesReportLoc() +fileName+".pdf"));
    	document.open();
    	PdfPTable table = new PdfPTable(3);
    	addRows(table,expList);
    	document.add(table);
    	PdfPTable table1 = new PdfPTable(2);
    	 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("Total Expneses"));
		    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table1.addCell(horizontalAlignCell);
		    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(String.valueOf(totalExpenses)));
		    verticalAlignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    table1.addCell(verticalAlignCell);
		    document.add(table1);
    	document.close();
    	} catch(Exception e) {
    		return "Folder not found or close opend file";
    	}
    return "File downloded in "+ factory.getEspensesReportLoc() + " this folder";
}

private void addRows(PdfPTable table,List<Expenses> expList) {
	Stream.of("Date","Name","Amount").forEach(h1 -> {
		PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        
        header.setPhrase(new Phrase(h1));
        table.addCell(header);
	});
	
		for(int j=0;j<expList.size(); j++) {
			 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(expList.get(j).getDate()));
			    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(horizontalAlignCell);
			    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(expList.get(j).getExpName()));
			    verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
			    table.addCell(verticalAlignCell);
			   
			    PdfPCell costCell = new PdfPCell(new Phrase(String.valueOf(expList.get(j).getExpCost())));
			    costCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(costCell);
			    totalExpenses += expList.get(j).getExpCost();
		}
		
}

}
