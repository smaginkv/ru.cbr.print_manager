## Task text
Write a program that emulates the work of the document print manager.

1. The program must be written using Java 7.
1. The print manager can work with several types of documents (3-5 types).
1. Each type of document must have unique details: duration of printing, name of the type of document, paper size.
1. The dispatcher places an unlimited number of documents in the print queue. Moreover, each document can be processed only if another document is not processed at the same time, the processing time of each document is equal to the duration of the printing of this document.
1. The dispatcher must have the following methods:

  
 - stopping the dispatcher. Printing documents in the queue is canceled. The output should be a list of unprinted documents.
 - accept the document for printing. The method should not block the execution of the program.
 - cancel printing of the received document if it has not already been printed.
 - get a sorted list of printed documents. The list can be sorted by choice: by printing order, by type of documents, by duration of printing, by paper size.
 - calculate the average print duration of printed documents

## Using it
Run the command line, go to the project folder and create bin folder
Compile..

    javac -sourcepath src -d bin src/ru/cbr/Main.java

Run bytecode

    java -cp bin ru.cbr.Main
    
1. Testing printing: type "1" operation, than type "20", for example, to generate 20 documents. Type "2" operation.
1. Testing canceling document: Type "3" operation, than type "7", for example, to cancel seventh document
1. Testing canceling all documents: Type "4" operation
1. Testing getting printed list: Type "5" operation, than type "4", for example, to display list in paper-size order
1. Testing average print duration: Type "6" operation.

To exit the program type "0"