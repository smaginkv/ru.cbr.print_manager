package ru.planetavto.ui.request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.planetavto.PrintManager;
import ru.planetavto.domain.TypeDocumentSorting;
import ru.planetavto.ui.Console;

public class UIRequestManager {
	private ExecutorService exec;

	public UIRequestManager() {
		exec = Executors.newCachedThreadPool();
	}

	public void generateDocuments(int documentsNumber) {
		execute(new TaskGenerateDocuments(documentsNumber));
	}

	public void cancelDocument(Console console, int documentumber) {
		execute(new TaskCancelDocument(console, documentumber));
	}

	public void cancelPrintingDocuments(Console console) {
		execute(new TaskCancelPrinting(console));
	}

	public void getPrintedDocumentsList(Console console, PrintManager printManager, TypeDocumentSorting typeSorting) {
		execute(new TaskGetPrintedDocuments(console, typeSorting));
	}

	private void execute(Runnable task) {
		exec.execute(task);
	}

}
