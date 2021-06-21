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
import com.petrol.model.BalanceSheet;

@Component
public class BalanceSheetReport {
	@Autowired
	DateUtils utils;
	@Autowired
	ConfigPropertiesFactory factory;
	
	double totalExpenses;
	public String createPDF(List<BalanceSheet> sheetList, String fileName) throws URISyntaxException, IOException, DocumentException{
    	Document document = new Document();
    	try{
    	PdfWriter.getInstance(document, new FileOutputStream(factory.getBalanceSheetReportLoc() +fileName+".pdf"));
    	document.open();
    	PdfPTable table = new PdfPTable(6);
    	addRows(table,sheetList);
    	document.add(table);
    	document.close();
    	} catch(Exception e) {
    		return "Folder not found or close opend file";
    	}
    return "File downloded in "+ factory.getBalanceSheetReportLoc() + " this folder";
}

private void addRows(PdfPTable table,List<BalanceSheet> sheetList) {
	Stream.of("Date","Total Sale","Bank Payment","Debit Payment","Last Balance","Cash In Hand").forEach(h1 -> {
		PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        
        header.setPhrase(new Phrase(h1));
        table.addCell(header);
	});
	
		for(int j=0;j<sheetList.size(); j++) {
			 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(utils.dateModify(sheetList.get(j).getSheetDate().toString())));
			    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(horizontalAlignCell);
			    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(String.valueOf(sheetList.get(j).getTotalSale())));
			    verticalAlignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(verticalAlignCell);
			   
			    PdfPCell costCell1 = new PdfPCell(new Phrase(String.valueOf(String.valueOf(sheetList.get(j).getTotalBP()))));
			    costCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(costCell1);
			    PdfPCell costCell2 = new PdfPCell(new Phrase(String.valueOf(String.valueOf(sheetList.get(j).getTotalDBP()))));
			    costCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(costCell2);
			    PdfPCell costCell3 = new PdfPCell(new Phrase(String.valueOf(String.valueOf(sheetList.get(j).getBalance()))));
			    costCell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(costCell3);
			    PdfPCell costCell4 = new PdfPCell(new Phrase(String.valueOf(String.valueOf(sheetList.get(j).getTotalCashInHnd()))));
			    costCell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table.addCell(costCell4);
		}
		
}


}
