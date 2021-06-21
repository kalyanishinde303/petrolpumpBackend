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
import com.petrol.model.Transactions;

@Component
public class CustomerReport {


	@Autowired
	DateUtils utils;
	@Autowired
	ConfigPropertiesFactory factory;
	double creditTotal =0;
	double debitTotal =0;
	public String createPDF(List<Transactions> tranList, String fileName) throws URISyntaxException, IOException, DocumentException{
        	Document document = new Document();
        	PdfWriter.getInstance(document, new FileOutputStream(factory.getCustomerReportLoc() +fileName+".pdf"));
        	document.open();
        	PdfPTable table = new PdfPTable(4);
        	addRows(table,tranList);
        	document.add(table);
        	PdfPTable table1 = new PdfPTable(2);
        	 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("Credit transaction total"));
			    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table1.addCell(horizontalAlignCell);
			    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(String.valueOf(creditTotal)));
			    verticalAlignCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    table1.addCell(verticalAlignCell);
			    document.add(table1);
			    
			    PdfPTable table2 = new PdfPTable(2);
	        	 PdfPCell horizontalAlignCell2 = new PdfPCell(new Phrase("Debit transaction total"));
				    horizontalAlignCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table2.addCell(horizontalAlignCell2);
				    PdfPCell verticalAlignCell2 = new PdfPCell(new Phrase(String.valueOf(debitTotal)));
				    verticalAlignCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    table2.addCell(verticalAlignCell2);
				    document.add(table2);
        	document.close();
        return "File downloded in "+ factory.getCustomerReportLoc() + " this folder";
	}
	
	private void addRows(PdfPTable table,List<Transactions> tr) {
		Stream.of("Date","Type","Amount","Balance").forEach(h1 -> {
			PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        
	        header.setPhrase(new Phrase(h1));
	        table.addCell(header);
		});
			double t1=0;
			double t2=0;
			for(int j=0;j<tr.size(); j++) {
				 PdfPCell horizontalAlignCell = new PdfPCell(new Phrase(tr.get(j).getDate()));
				    horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(horizontalAlignCell);
				    PdfPCell verticalAlignCell = new PdfPCell(new Phrase(tr.get(j).getType()));
				    verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
				    table.addCell(verticalAlignCell);
				   
				    PdfPCell amountCell = new PdfPCell(new Phrase(String.valueOf(tr.get(j).getAmount())));
				    amountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    table.addCell(amountCell);
				    PdfPCell balanceCell = new PdfPCell(new Phrase(String.valueOf(tr.get(j).getAmount())));
				    balanceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				    table.addCell(balanceCell);
				    if(tr.get(j).getType().equals("Credit")){
				    	t1 += tr.get(j).getAmount();
				    } else if(tr.get(j).getType().equals("Debit")){
				    	t2 += tr.get(j).getAmount();
				    }
			}
			creditTotal =t1;
			debitTotal = t2;
			
	}

}
