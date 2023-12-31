package edu.najah.cap.data.document_generation;

import edu.najah.cap.data.document_generation.strategy.*;
import edu.najah.cap.data.document_generation.strategy.IDocumentGeneration;
import edu.najah.cap.iam.UserType;

public class Factory {

    private static final IDocumentGeneration userActivity = new PdfUserActivityGeneration();
    private static final IDocumentGeneration userData = new PdfUserDataGeneration();
    private static final IDocumentGeneration userPayment = new PdfUserPaymentGeneration();
    private static final IDocumentGeneration userPost = new PdfUserPostGeneration();

    public static Generator create(UserType userType, String userName) {
        Generator generator = null;
        if (UserType.PREMIUM_USER.equals(userType)) {
            generator = new Generator(userName, userActivity, userData, userPayment, userPost);
        } else if (UserType.REGULAR_USER.equals(userType)) {
            generator = new Generator(userName, userActivity, userData, null, userPost);
        } else if (UserType.NEW_USER.equals(userType)) {
            generator = new Generator(userName, null, userData, null, userPost);
        }
        return generator;
    }
}
