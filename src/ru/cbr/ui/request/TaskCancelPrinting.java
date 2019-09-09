package ru.cbr.ui.request;

import java.util.List;

import ru.cbr.Context;
import ru.cbr.core.Document;
import ru.cbr.core.PrintManager;
import ru.cbr.ui.Console;

public class TaskCancelPrinting implements Requestable {
	private PrintManager printManager;
	private Console console;

	//access to instantiate - package
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
