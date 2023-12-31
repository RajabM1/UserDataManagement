package edu.najah.cap.data.document_generation;

import edu.najah.cap.data.document_generation.strategy.IDocumentGeneration;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public class Generator {
    private final IDocumentGeneration userActivity;
    private final IDocumentGeneration userData;
    private final IDocumentGeneration userPayment;
    private final IDocumentGeneration userPost;
    private final String userName;


    public Generator(String userName, IDocumentGeneration userActivity, IDocumentGeneration userData, IDocumentGeneration userPayment, IDocumentGeneration userPost) {
        this.userActivity = userActivity;
        this.userData = userData;
        this.userPayment = userPayment;
        this.userPost = userPost;
        this.userName = userName;
    }

    public final void generateUserActivity() throws SystemBusyException, BadRequestException, NotFoundException {
        if (userActivity != null) {
            userActivity.generateDocument(userName);
        }
    }

    public final void generateUserData() throws SystemBusyException, NotFoundException, BadRequestException {
        if (userData != null) {
            userData.generateDocument(userName);
        }
    }

    public final void generateUserPayment() throws SystemBusyException, BadRequestException, NotFoundException {
        if (userPayment != null) {
            userPayment.generateDocument(userName);
        }
    }

    public final void generateUserPost() throws SystemBusyException, BadRequestException, NotFoundException {
        if (userPost != null) {
            userPost.generateDocument(userName);
        }
    }
}
