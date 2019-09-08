package ru.planetavto.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import ru.planetavto.service.ArrayListToString;

public enum TypeDocumentSorting {
	PRINT_ORDER {
		public List<Document> sort(Queue<Document> printedDocuments) {
			Document[] array = new Document[0];
			array = printedDocuments.toArray(array);
			List<Document> list = new ArrayList<Document>(array.length);
			Collections.addAll(list, array);
			return list;
		}
	},
	DOCUMENT_TYPE {
		public List<Document> sort(Queue<Document> printedDocuments) {
			return sortImplementation(printedDocuments);
		}

		@SuppressWarnings({ "unchecked", "hiding" })
		<DocumentType> DocumentType getSortingAttribute(Document document) {
			return (DocumentType) document.getDocumentType();
		}
	},
	PRINT_DURATION {
		public List<Document> sort(Queue<Document> printedDocuments) {
			return sortImplementation(printedDocuments);
		}

		@SuppressWarnings({ "unchecked", "hiding" })
		<Integer> Integer getSortingAttribute(Document document) {
			return (Integer) document.getPrintDuration();
		}
	},
	PAPER_SIZE {
		public List<Document> sort(Queue<Document> printedDocuments) {
			return sortImplementation(printedDocuments);
		}

		@SuppressWarnings({ "unchecked", "hiding" })
		<PaperSize> PaperSize getSortingAttribute(Document document) {
			return (PaperSize) document.getPaperSize();
		}
	};

	public abstract List<Document> sort(Queue<Document> printedDocuments);

	<T> List<Document> sortImplementation(Queue<Document> printedDocuments) {

		Map<T, List<Document>> mapBySortingAttribute = populateMapBySortingAttribute(printedDocuments);

		return collectTotalArrayBySortingMapEntries(mapBySortingAttribute);

	}
	
	<T> T getSortingAttribute(Document document) {
		return null;
	}

	<T> Map<T, List<Document>> populateMapBySortingAttribute(Queue<Document> printedDocuments) {
		Map<T, List<Document>> mapBySortingAttribute = new HashMap<T, List<Document>>();
		Iterator<Document> iter = printedDocuments.iterator();

		List<Document> documentList;
		while (iter.hasNext()) {
			Document document = iter.next();
			T sortingAttribute = this.getSortingAttribute(document);
			
			documentList = mapBySortingAttribute.get(sortingAttribute);
			if (documentList == null) {
				documentList = new ArrayList<Document>();
				mapBySortingAttribute.put(sortingAttribute, documentList);
			}
			
			documentList.add(document);
		}
		return mapBySortingAttribute;
	}

	<T> List<Document> collectTotalArrayBySortingMapEntries(Map<T, List<Document>> mapBySortingAttribute) {
		@SuppressWarnings("unchecked")
		T[] keyArray = (T[]) mapBySortingAttribute.keySet().toArray();
		Arrays.sort(keyArray);

		List<Document> returnedList = new ArrayListToString<Document>();
		for (T documentType : keyArray) {
			returnedList.addAll(mapBySortingAttribute.get(documentType));
		}
		return returnedList;
	}
}
