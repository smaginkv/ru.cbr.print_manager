package ru.cbr.ui.request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.cbr.core.PrintManager;
import ru.cbr.service.sort.DocumentSortingType;
import ru.cbr.ui.Console;

public class UIRequestManager {
	private ExecutorService exec;

	public UIRequestManager() {
		exec = Executors.newCachedThreadPool();
	}

	public void generateDocuments(int documentsNumber) {
		execute(new TaskGenerateDocuments(documentsNumber));
	}

	public void cancelDocument(Console console, int documentumber) {
		//maybe its better to inject console directly in task 
		execute(new TaskCancelDocument(console, documentumber));
	}

	public void cancelPrintingDocuments(Console console) {
		execute(new TaskCancelPrinting(console));
	}

	public void getPrintedDocumentsList(Console console, PrintManager printManager, DocumentSortingType typeSorting) {
		execute(new TaskGetPrintedDocuments(console, typeSorting));
	}

	private void execute(Runnable task) {
		exec.execute(task);
	}

}
