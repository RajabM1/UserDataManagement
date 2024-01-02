package edu.najah.cap.data.data_exporting.document_generation.strategy;

public interface IDocumentGeneration {
    /**
     * Generates a document for a given user.
     *
     * @param userName The username for whom the document is generated.
     */
    void generateDocument(String userName);

}
