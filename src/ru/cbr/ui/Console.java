package ru.cbr.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ru.cbr.Context;
import ru.cbr.core.Document;
import ru.cbr.core.PrintManager;
import ru.cbr.service.sort.DocumentSortingType;
import ru.cbr.ui.request.UIRequestManager;

public class Console {
	private PrintManager printManager;
	private UIRequestManager requestManager;

	public Console(){
		printManager   = Context.getPrintManager();
		requestManager = Context.getRequestManager();
	}
	
	public void run() {
		printGreeting();

		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print("Enter operation number: ");
			try {
				int operationNumber = in.nextInt();

				if (!chooseOperationNumber(operationNumber, in)) {
					break;
				}
			} catch (InputMismatchException e) {
				in.next();
				System.out.println("wrong operation, try again.");				
			}
		}
		in.close();// terminate Treads?
		System.exit(0);
	}

	private void printGreeting() {
		System.out.println("1. add documents to the print queue");
		System.out.println("2. print documents queue");
		System.out.println("3. cancel printing of any document");
		System.out.println("4. cancel printing documents");
		System.out.println("5. get a sorted list of printed documents");
		System.out.println("6. calculate average print duration");
		System.out.println("0. exit");
	}

	private boolean chooseOperationNumber(int operationNumber, Scanner in) {
		if (operationNumber == 1) {
			generateDocuments(in);

		} else if (operationNumber == 2) {
			printDocuments();

		} else if (operationNumber == 3) {
			cancelAnyDocument(in);

		} else if (operationNumber == 4) {
			cancelPrintingDocuments();

		} else if (operationNumber == 5) {
			displayPrintedDocumentsList(in);
			
		} else if (operationNumber == 6) {
			displayAveragePrintingDuration();

		} else if (operationNumber == 0) {
			cancelPrintingDocuments();
			return false;
			
		} else {
			System.out.println("wrong operation, try again.");
		}
		return true;
	}

	private void generateDocuments(Scanner in) {
		System.out.print("Enter the number of documents you want to generate: ");
		int documentsNumber = in.nextInt();
		if (documentsNumber <= 0) {
			System.out.println("Number of documents must be a positive number");
			return;
		}
		requestManager.generateDocuments(documentsNumber);
		System.out.println("Done!");

	}

	private void printDocuments() {
		printManager.printDocuments();
		System.out.println("Done!");
	}

	private void cancelAnyDocument(Scanner in) {
		System.out.println("Enter the number of document you want to cancel");
		int documentNumber = in.nextInt();
		if (documentNumber <= 0) {
			System.out.println("Number of document must be a positive number");
			return;
		}
		requestManager.cancelDocument(this, documentNumber);
	}

	private void cancelPrintingDocuments() {
		requestManager.cancelPrintingDocuments(this);
		System.out.println("Done!");
	}

	private void displayAveragePrintingDuration() {
		float averageDuration = printManager.getAveragePrintingDuration();
		System.out.printf("Average document printing duration is %15.3f s.\n", averageDuration);
	}

	private void displayPrintedDocumentsList(Scanner in) {
		int index = 0;
		DocumentSortingType[] typesSorting = DocumentSortingType.values();
		for (DocumentSortingType typeSorting : typesSorting) {
			System.out.printf("%s. by %s\n", ++index, typeSorting);
		}
		System.out.print("Enter type of sorting: ");

		int sortingNumber = in.nextInt();
		if (sortingNumber < 1 || sortingNumber > typesSorting.length) {
			System.out.println("Invalid type of sorting");
			return;
		}

		DocumentSortingType typeSorting = typesSorting[sortingNumber - 1];
		requestManager.getPrintedDocumentsList(this, printManager, typeSorting);
	}

	// asynchronous event
	public void onCancelAnyDocument(boolean result, int documentNumber) {
		if (result) {
			System.out.printf("\nCancel printing of document №%s was successful\n", documentNumber);
		} else {
			System.out.printf("\nDocument №%s printed already\n", documentNumber);
		}
	}

	public void onCancelPrinting(List<Document> listDocuments) {
		System.out.println(listDocuments);
		System.out.println("\nPrinting canceled!");
	}
	
	public void onGetSortedDocumentList(List<Document> printedDocuments) {
		System.out.println("\n"+printedDocuments);		
	}
	
	public void onFinishPrint(String message) {
		System.out.println("\n"+message);
	}
}
