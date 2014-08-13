Document-Classifier
===================

Java standalone application to classify research papers(pdf format) based on context

The application extracts and filters words from all PDF file in a directory. The resulting bag of words in classified into one of few select categories by using TF-IDF ( term frequency - inverted document frequency ). Following is a detailed description of the process:

1. Loading all PDF documents from a directory: From a File instance of the folder containing all documents, an ArrayList of files is created containing only the PDF files in that folder. (implemented in WindowsAPI.java)
      
2. Extraction of words from the files: We've used PDFTextStream library for extracting all the words in a pdf file. This is done by passing filename to a PDFTextStream constructor and making a PDFTextStream instance. A StringBuffer instance is created and the contents are piped into it by the earlier created PDFTextStream instance.(implemented in WindowsAPI.java)

3. Filtering stop words: The ArrayList of stopwords is iterated through and every item is checked by a list of punctuation marks and stop word bag. All unnecessary content are filtered out before classification.(implemented in Pdf_read.java)

4. Classification: There are a list of words commonly occuring in a research paper of every subject. We have created lists for physics, biology, computer science for testing, for adding a new category, a new bag of words has to be created. The filtered out list of words is checked for occurences of the words of all categories. A score is generated for each category based on frequency of occurrence of words from that category and importance of the category. Comparison of these scores gives the final class of the research paper.(implemented in Tf_idf.java)

5. GUI: The GUI is built using java.awt and javax.swing.(implemented in GUI.java)

6. Search: The search field searches for a term among all paper names, categories, authors, publication using a prefix tree data structure.(implemented in PrefixTree.java)

7. Print: A standard windows dialog for print opens up when a paper is selected for printing.
