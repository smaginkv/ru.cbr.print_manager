package ru.planetavto.ui.request;

import java.util.List;

import ru.planetavto.Context;
import ru.planetavto.PrintManager;
import ru.planetavto.domain.Document;
import ru.planetavto.domain.TypeDocumentSorting;
import ru.planetavto.ui.Console;

public class TaskGetPrintedDocuments implements Requestable {
	private PrintManager printManager;
	private TypeDocumentSorting typeSorting;
	private Console console;
	
	TaskGetPrintedDocuments(Console console, TypeDocumentSorting typeSorting) {
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
