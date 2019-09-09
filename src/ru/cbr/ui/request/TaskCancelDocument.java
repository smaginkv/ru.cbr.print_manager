package ru.cbr.ui.request;

import ru.cbr.Context;
import ru.cbr.core.PrintManager;
import ru.cbr.ui.Console;

public class TaskCancelDocument implements Requestable {
	private int documentNumber;
	private PrintManager printManager;
	private Console console;
	
	//access to instantiate - package
	TaskCancelDocument(Console console, int documentNumber) {
		this.console        = console;
		this.documentNumber = documentNumber;
		this.printManager   = Context.getPrintManager();
	}

	@Override
	public void run() {
		boolean result = printManager.cancelDocument(documentNumber);
		console.onCancelAnyDocument(result, documentNumber);		
	}
}
