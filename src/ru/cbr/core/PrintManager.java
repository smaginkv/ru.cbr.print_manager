package ru.cbr.core;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ru.cbr.Context;
import ru.cbr.exception.EmptyPrintQueueException;
import ru.cbr.service.ArrayListToString;
import ru.cbr.service.AverageCount;
import ru.cbr.service.sort.DocumentSortingType;

/**
 * The class <code>PrintManager</code> is used for all document printing operations.
 * @author Smagin Kirill
 *
 */

public class PrintManager {
	private static ExecutorService executor;
	private volatile boolean cancelPrintingDocuments = false;
	private AverageCount averageCount;
	private Queue<Document> documentsToPrinting;
	private Queue<Document> printedDocuments;

	/**
	 * Constructs and initialize print manager
	 */
	public PrintManager() {
		executor = Executors.newCachedThreadPool();
		averageCount = Context.getAverageCount();
		documentsToPrinting = Context.getDocumentsToPrinting();
		printedDocuments = Context.getPrintedDocuments();
	}

	/**
	 * Runs a task to execute in a separate thread
	 */
	public void printDocuments() {
		cancelPrintingDocuments = false;
		executor.execute(new PrintTask());
	}

	/**
	 * Stop all threads and return not printed documents
	 * 
	 * @return a list of not printed documents
	 * @throws InterruptedException
	 */
	public List<Document> cancelPrintingDocuments() throws InterruptedException {
		// Cancel thread
		cancelPrintingDocuments = true;
		executor.shutdown();
		executor.awaitTermination(100, TimeUnit.SECONDS);

		List<Document> list = new ArrayListToString<Document>();
		list.addAll(documentsToPrinting);
		return list;

	}

	/**
	 * Add document to printing queue
	 * 
	 * @param document - document to be queued for printing
	 */
	public void acceptPrintDocument(Document document) {
		documentsToPrinting.add(document);
	}

	/**
	 * Cancel document printing, if it is still possible
	 * 
	 * @param documentNumber
	 * @return <tt>true<tt> if the print was canceled
	 */
	public boolean cancelDocument(int documentNumber) {
		// we believe that search operations will be carried out rarely and on an empty
		// queue;
		// otherwise, instead of the queue, we need to use ConcurrentHashMap, with high
		// speed random access
		return documentsToPrinting.remove(new Document(documentNumber, DocumentType.INVOICE));
	}

	/**
	 * Get printed document list
	 * 
	 * @param typeSorting - one of the predefined sorting options
	 * @return list of sorted documents
	 */
	public List<Document> getPrintedDocumentsList(DocumentSortingType typeSorting) {
		return typeSorting.sort(printedDocuments);
	}

	/**
	 * Calculate average duration of printing one document
	 * 
	 * @return duration is seconds
	 */
	public float getAveragePrintingDuration() {
		return averageCount.getAveragePrintingDurationInSeconds();
	}

	/**
	 * Task of printing a documents that can be executed in a separate thread 
	 * @author SmaginKV
	 *
	 */
	private class PrintTask implements Runnable {
		@Override
		public void run() {
			try {
				boolean success;
				do {
					success = printDocument();
				} while (success);

			} catch (InterruptedException e) {
				Context.getUI().onFinishPrint(this + "interrupted");
			} catch (EmptyPrintQueueException e) {
				Context.getUI().onFinishPrint(this + " finished work");
			}
		}

		private boolean printDocument() throws InterruptedException, EmptyPrintQueueException {
			Document document;
			long start;

			// printing
			synchronized (documentsToPrinting) {
				if (cancelPrintingDocuments) {
					return false;
				}
				start = System.currentTimeMillis();
				document = documentsToPrinting.poll();
				if (document == null) {
					throw new EmptyPrintQueueException();
				}
				TimeUnit.SECONDS.sleep(document.getPrintDuration());
			}
			Context.getUI().onFinishPrint(document + " printed!");
			// persist related info
			long difference = System.currentTimeMillis() - start;
			averageCount.putPrintingDuration(difference);
			printedDocuments.add(document);

			return true;
		}
	}
}
