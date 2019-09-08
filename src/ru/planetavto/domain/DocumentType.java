package ru.planetavto.domain;

//Ideally, we need to store information about the print duration in DB
//In this case, we are sure that the information is not subject to change
public enum DocumentType {
	INVOICE {
		public Integer getPrintDuration() {
			return 5;
		}
		public String getDocumentTypeName() {
			return "invoice";
		}		
		public PaperSize getPaperSize() {
			return PaperSize.A3;
		}		
	},
	UPD {
		public Integer getPrintDuration() {
			return 8;
		}
		public String getDocumentTypeName() {
			return "universal transmission document";
		}
		public PaperSize getPaperSize() {
			return PaperSize.A4;
		}		
	},
	NOTIFICATION {
		public Integer getPrintDuration() {
			return 2;
		}
		public String getDocumentTypeName() {
			return "notification";
		}
		public PaperSize getPaperSize() {
			return PaperSize.LETTER;
		}
	},
	WAY_BILL {
		public Integer getPrintDuration() {
			return 8;
		}		
		public String getDocumentTypeName() {
			return "way bill";
		}
		public PaperSize getPaperSize() {
			return PaperSize.A4;
		}
	};
	
	public abstract Integer getPrintDuration();
	
	public abstract String getDocumentTypeName();
	
	public abstract PaperSize getPaperSize();
}
