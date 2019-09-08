package ru.planetavto;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ru.planetavto.domain.Document;
import ru.planetavto.domain.DocumentType;
import ru.planetavto.domain.TypeDocumentSorting;
import ru.planetavto.exception.EmptyPrintQueueException;
import ru.planetavto.service.ArrayListToString;
import ru.planetavto.service.AverageCount;

public class PrintManager {
	private static ExecutorService executor;
	private volatile boolean cancelPrintingDocuments = false;
	private AverageCount averageCount;
	private Queue<Document> documentsToPrinting;
	private Queue<Document> printedDocuments;

	public PrintManager() {
		executor = Executors.newCachedThreadPool();
		averageCount = Context.getAverageCount();
		documentsToPrinting = Context.getDocumentsToPrinting();
		printedDocuments = Context.getPrintedDocuments();
	}

	public void printDocuments() {
		cancelPrintingDocuments = false;
		executor.execute(new PrintTask());
	}

	private class PrintTask implements Runnable {
		@Override
		public void run() {
			try {
				boolean success;
				do {
					success = printDocument();
				} while (success);

			} catch (InterruptedException e) {
				Context.getUI().onFinishPrinting(this + "interrupted");
			} catch (EmptyPrintQueueException e) {
				Context.getUI().onFinishPrinting(this + " finished work");
			}
		}

		private boolean printDocument() throws InterruptedException, EmptyPrintQueueException {
			Document document;
			long start;
			synchronized (documentsToPrinting) {
				if (cancelPrintingDocuments) {
					return false;
				}
				start = System.currentTimeMillis();
				document = documentsToPrinting.poll();
				if (document == null) {
					throw new EmptyPrintQueueException();
				}
				System.out.println(this + ": " + document + " is printing!");
				TimeUnit.SECONDS.sleep(document.getPrintDuration());
				System.out.println(this + ": " + document + " printed!");
			}
			
			long difference = System.currentTimeMillis() - start;			
			averageCount.putPrintingDuration(difference);
			printedDocuments.add(document);		

			return true;
		}
	}

	public List<Document> cancelPrintingDocuments() throws InterruptedException {
		cancelPrintingDocuments = true;
		executor.shutdown();
		executor.awaitTermination(100, TimeUnit.SECONDS);

		List<Document> list = new ArrayListToString<Document>();
		list.addAll(documentsToPrinting);
		return list;

	}

	public void acceptPrintDocument(Document document) {
		documentsToPrinting.add(document);
	}

	public boolean cancelDocument(int documentNumber) {
		return documentsToPrinting.remove(new Document(documentNumber, DocumentType.INVOICE));
	}

	public List<Document> getPrintedDocumentsList(TypeDocumentSorting typeSorting) {
		return typeSorting.sort(printedDocuments);
	}

	public float getAveragePrintingDuration() {

		return averageCount.getAveragePrintingDurationInSeconds();
	}
}
