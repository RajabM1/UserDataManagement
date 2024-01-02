package edu.najah.cap.data.data_exporting.document_generation;

import edu.najah.cap.data.data_exporting.document_generation.strategy.*;
import edu.najah.cap.iam.UserType;

public class DocumentGenerationFactory {

    /**
     * Creates a document generator for a given user type.
     *
     * @param userType The type of user for whom the document generator is created.
     * @param userName The username for whom the documents will be generated.
     * @return A Generator configured with appropriate document generation strategies.
     */
    public static Generator create(UserType userType, String userName) {
        Generator generator = null;
        if (UserType.PREMIUM_USER.equals(userType)) {
            generator = new Generator(userName, createUserActivityStrategy(), createUserDataStrategy(), createUserPaymentStrategy(), createUserPostStrategy());
        } else if (UserType.REGULAR_USER.equals(userType)) {
            generator = new Generator(userName, createUserActivityStrategy(), createUserDataStrategy(), null, createUserPostStrategy());
        } else if (UserType.NEW_USER.equals(userType)) {
            generator = new Generator(userName, null, createUserDataStrategy(), null, createUserPostStrategy());
        }
        return generator;
    }

    /**
     * Creates an instance of the user activity strategy for generating PDF documents.
     *
     * @return IDocumentGeneration instance specialized in user activity document generation.
     */
    private static IDocumentGeneration createUserActivityStrategy() {
        return new PdfUserActivityGeneration();
    }

    /**
     * Creates an instance of the user data strategy for generating PDF documents.
     *
     * @return IDocumentGeneration instance specialized in user data document generation.
     */
    private static IDocumentGeneration createUserDataStrategy() {
        return new PdfUserDataGeneration();
    }

    /**
     * Creates an instance of the user payment strategy for generating PDF documents.
     *
     * @return IDocumentGeneration instance specialized in user payment document generation.
     */
    private static IDocumentGeneration createUserPaymentStrategy() {
        return new PdfUserPaymentGeneration();
    }

    /**
     * Creates an instance of the user post strategy for generating PDF documents.
     *
     * @return IDocumentGeneration instance specialized in user post document generation.
     */
    private static IDocumentGeneration createUserPostStrategy() {
        return new PdfUserPostGeneration();
    }
}
