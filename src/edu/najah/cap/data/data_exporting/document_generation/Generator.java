package edu.najah.cap.data.data_exporting.document_generation;

import edu.najah.cap.data.data_exporting.document_generation.strategy.IDocumentGeneration;

public class Generator {
    private final IDocumentGeneration userActivity;
    private final IDocumentGeneration userData;
    private final IDocumentGeneration userPayment;
    private final IDocumentGeneration userPost;
    private final String userName;

    /**
     * Constructs a new Generator with specific strategies for generating documents.
     *
     * @param userName     The username associated with the documents to be generated.
     * @param userActivity Strategy for generating user activity documents.
     * @param userData     Strategy for generating user data documents.
     * @param userPayment  Strategy for generating user payment documents.
     * @param userPost     Strategy for generating user post documents.
     */
    public Generator(String userName, IDocumentGeneration userActivity, IDocumentGeneration userData, IDocumentGeneration userPayment, IDocumentGeneration userPost) {
        this.userActivity = userActivity;
        this.userData = userData;
        this.userPayment = userPayment;
        this.userPost = userPost;
        this.userName = userName;
    }

    /**
     * Generates a document detailing the user's activities.
     */
    public final void generateUserActivity() {
        if (userActivity != null) {
            userActivity.generateDocument(userName);
        }
    }

    /**
     * Generates a document containing the user's data.
     */
    public final void generateUserData() {
        if (userData != null) {
            userData.generateDocument(userName);
        }
    }

    /**
     * Generates a document with the user's payment information.
     */
    public final void generateUserPayment() {
        if (userPayment != null) {
            userPayment.generateDocument(userName);
        }
    }

    /**
     * Generates a document with the user's posts.
     */
    public final void generateUserPost() {
        if (userPost != null) {
            userPost.generateDocument(userName);
        }
    }
}
