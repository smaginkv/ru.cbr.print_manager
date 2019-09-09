package ru.cbr.service;

import java.util.ArrayList;
import java.util.Random;

import ru.cbr.core.Document;
import ru.cbr.core.DocumentType;

public class DocumentGenerator {
	private static Random rand = new Random(47);

	public ArrayList<Document> generate(int documentsNumber) {
		DocumentType[] arrayTypes = DocumentType.values();
		ArrayList<Document> listDocuments = new ArrayList<Document>(documentsNumber);
		for (int i = 0; i < documentsNumber; i++) {
			int randTypeIndex = rand.nextInt(arrayTypes.length);
			
			DocumentType randType = arrayTypes[randTypeIndex];
			
			listDocuments.add(new Document(randType));
			
		}
		return listDocuments;
		
	}
}
