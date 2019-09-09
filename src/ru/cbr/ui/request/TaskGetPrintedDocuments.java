package ru.cbr.ui.request;

import java.util.List;

import ru.cbr.Context;
import ru.cbr.core.Document;
import ru.cbr.core.PrintManager;
import ru.cbr.service.sort.DocumentSortingType;
import ru.cbr.ui.Console;

public class TaskGetPrintedDocuments implements Requestable {
	private PrintManager printManager;
	private DocumentSortingType typeSorting;
	private Console console;
	
	//access to instantiate - package
	TaskGetPrintedDocuments(Console console, DocumentSortingType typeSorting) {
		this.console      = console;
		this.printManager = Context.getPrintManager();
		this.typeSorting  = typeSorting;
	}
	
	@Override
	public void run() {
		List<Document> sortedDocumentsList = printManager.getPrintedDocumentsList(typeSorting);
		console.onGetSortedDocumentList(sortedDocumentsList);
	}

}
