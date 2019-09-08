package ru.planetavto.ui.request;

import java.util.List;

import ru.planetavto.Context;
import ru.planetavto.PrintManager;
import ru.planetavto.domain.Document;
import ru.planetavto.ui.Console;

public class TaskCancelPrinting implements Requestable {
	private PrintManager printManager;
	private Console console;

	TaskCancelPrinting(Console console) {
		this.console      = console;
		this.printManager = Context.getPrintManager();
	}

	@Override
	public void run() {
		List<Document> listDocuments;
		try {
			listDocuments = printManager.cancelPrintingDocuments();
		} catch (InterruptedException e) {
			return;
		}		
		console.onCancelPrinting(listDocuments);
	}
}
