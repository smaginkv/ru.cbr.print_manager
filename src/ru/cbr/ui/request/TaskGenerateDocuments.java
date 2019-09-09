package ru.cbr.ui.request;

import java.util.ArrayList;

import ru.cbr.Context;
import ru.cbr.core.Document;
import ru.cbr.core.PrintManager;
import ru.cbr.service.DocumentGenerator;

public class TaskGenerateDocuments implements Requestable {
	private PrintManager printManager;
	private int documentsNumber;
	
	//access to instantiate - package
	TaskGenerateDocuments(int documentsNumber){
		this.printManager    = Context.getPrintManager();
		this.documentsNumber = documentsNumber;
	}

	@Override
	public void run() {
		DocumentGenerator documentGenerator = Context.getDocumentGenerator();
		ArrayList<Document> listDocuments = documentGenerator.generate(documentsNumber);
		
		for(Document document: listDocuments) {
			printManager.acceptPrintDocument(document);
		}				
	}
}
