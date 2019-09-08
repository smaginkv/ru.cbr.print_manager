Write a program that emulates the work of the document print manager.

1. The program must be written using Java 7.
2. The print manager can work with several types of documents (3-5 types).
3. Each type of document must have unique details: duration of printing, name of the type of document, paper size.
4. The dispatcher places an unlimited number of documents in the print queue. Moreover, each document can be processed only if another document is not processed at the same time, the processing time of each document is equal to the duration of the printing of this document.
5. The dispatcher must have the following methods:
* Stopping the dispatcher. Printing documents in the queue is canceled. The output should be a list of unprinted documents.
* Accept the document for printing. The method should not block the execution of the program.
* Cancel printing of the received document if it has not already been printed.
* Get a sorted list of printed documents. The list can be sorted by choice: by printing order, by type of documents, by duration of printing, by paper size.
* Calculate the average print duration of printed documents


