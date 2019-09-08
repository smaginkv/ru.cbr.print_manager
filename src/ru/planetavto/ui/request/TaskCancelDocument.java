package ru.planetavto.ui.request;

import ru.planetavto.Context;
import ru.planetavto.PrintManager;
import ru.planetavto.ui.Console;

public class TaskCancelDocument implements Requestable {
	private int documentNumber;
	private PrintManager printManager;
	private Console console;
	
	TaskCancelDocument(Console console, int documentNumber) {
		this.console = console;
		this.documentNumber = documentNumber;
		this.printManager = Context.getPrintManager();
	}

	@Override
	public void run() {
		boolean result = printManager.cancelDocument(documentNumber);
		console.onCancelAnyDocument(result, documentNumber);		
	}
}
