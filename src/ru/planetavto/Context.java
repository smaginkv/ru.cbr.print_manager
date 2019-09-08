package ru.planetavto;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import ru.planetavto.domain.Document;
import ru.planetavto.service.AverageCount;
import ru.planetavto.service.DocumentGenerator;
import ru.planetavto.ui.Console;
import ru.planetavto.ui.request.UIRequestManager;

public class Context {
	
	private static Queue<Document> documentsToPrinting;
	private static DocumentGenerator documentGenerator;
	private static UIRequestManager requestManager;
	private static PrintManager printManager;
	private static AverageCount averageCount;
	private static Queue<Document> printedDocuments;
	private static Console console;
	
	public static Queue<Document> getDocumentsToPrinting() {
		if(documentsToPrinting == null) {
			documentsToPrinting = new LinkedBlockingQueue<Document>();
		}
		return documentsToPrinting;
	}
	
	public static DocumentGenerator getDocumentGenerator() {
		if(documentGenerator == null) {
			documentGenerator = new DocumentGenerator();
		}
		return documentGenerator;
	}
	
	public static UIRequestManager getRequestManager() {
		if(requestManager == null) {
			requestManager = new UIRequestManager();
		}
		return requestManager;
	}
	
	public static PrintManager getPrintManager() {
		if(printManager == null) {
			printManager = new PrintManager();
		}
		return printManager;
	}
	
	public static AverageCount getAverageCount() {
		if(averageCount == null) {
			averageCount = new AverageCount();
		}
		return averageCount;
	}
	
	public static Queue<Document> getPrintedDocuments() {
		if(printedDocuments == null) {
			printedDocuments = new LinkedBlockingQueue<Document>();
		}
		return printedDocuments;
	}
	
	public static Console getUI() {
		if(console == null) {
			console = new Console();
		}
		return console;
	}
}
