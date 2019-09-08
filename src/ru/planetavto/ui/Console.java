package ru.planetavto.ui;

import java.util.List;
import java.util.Scanner;

import ru.planetavto.Context;
import ru.planetavto.PrintManager;
import ru.planetavto.domain.Document;
import ru.planetavto.domain.TypeDocumentSorting;
import ru.planetavto.ui.request.UIRequestManager;

//maybe use di an context?
public class Console {
	private PrintManager printManager = Context.getPrintManager();
	private UIRequestManager requestManager = Context.getRequestManager();

	public void run() {
		printGreeting();

		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print("Enter operation number: ");
			int operationNumber = in.nextInt();

			if (!chooseOperationNumber(operationNumber, in)) {
				break;
			}
		}
		in.close();// terminate Treads?
		System.out.println("Good bay!");
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
		TypeDocumentSorting[] typesSorting = TypeDocumentSorting.values();
		for (TypeDocumentSorting typeSorting : typesSorting) {
			System.out.printf("%s. by %s\n", ++index, typeSorting);
		}
		System.out.print("Enter type of sorting: ");

		int sortingNumber = in.nextInt();
		if (sortingNumber < 1 || sortingNumber > typesSorting.length) {
			System.out.println("Invalid type of sorting");
			return;
		}

		TypeDocumentSorting typeSorting = typesSorting[sortingNumber - 1];
		requestManager.getPrintedDocumentsList(this, printManager, typeSorting);
	}

	// asynchronous event
	public void onCancelAnyDocument(boolean result, int documentNumber) {
		if (result) {
			System.out.printf("Ñancel printing of document ¹%s was successful\n", documentNumber);
		} else {
			System.out.printf("Document ¹%s printed already\n", documentNumber);
		}
	}

	public void onCancelPrinting(List<Document> listDocuments) {
		System.out.println(listDocuments);
		System.out.println("Printing canceled!");
	}
	
	public void onGetSortedDocumentList(List<Document> printedDocuments) {
		System.out.println(printedDocuments);		
	}
	
	public void onFinishPrinting(String message) {
		System.out.println(message);
	}
}
