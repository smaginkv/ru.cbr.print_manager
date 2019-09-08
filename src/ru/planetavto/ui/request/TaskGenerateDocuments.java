package ru.planetavto.ui.request;

import java.util.ArrayList;

import ru.planetavto.Context;
import ru.planetavto.PrintManager;
import ru.planetavto.domain.Document;
import ru.planetavto.service.DocumentGenerator;

public class TaskGenerateDocuments implements Requestable {
	private PrintManager printManager;
	private int documentsNumber;
	
	TaskGenerateDocuments(int documentsNumber){
		this.printManager = Context.getPrintManager();
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
