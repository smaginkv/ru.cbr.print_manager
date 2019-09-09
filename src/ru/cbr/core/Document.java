package ru.cbr.core;

public class Document {
	private static int counter;
	private int id = counter++;
	private final DocumentType type;

	public Document(DocumentType type) {
		this.type = type;
	}

	// access to instantiate - package
	Document(int id, DocumentType type) {
		this.id = id;
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("Document %s №%s", type, id);
	}

	public Integer getPrintDuration() {
		return type.getPrintDuration();
	}

	public DocumentType getDocumentType() {
		return type;
	}

	public PaperSize getPaperSize() {
		return type.getPaperSize();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Document)) {
			return false;
		}
		Document other = (Document) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
}
