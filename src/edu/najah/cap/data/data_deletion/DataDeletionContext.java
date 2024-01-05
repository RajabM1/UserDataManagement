package edu.najah.cap.data.data_deletion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class DataDeletionContext {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String userName;
    private final IDelete userActivity;
    private final IDelete userData;
    private final IDelete userPayment;
    private final IDelete userPost;

    /**
     * Constructs a DataDeletionContext with specified deletion handlers for different data types.
     *
     * @param userName     the username associated with the data to be deleted.
     * @param userActivity the handler for deleting user activity.
     * @param userData     the handler for deleting user data.
     * @param userPayment  the handler for deleting user payment information.
     * @param userPost     the handler for deleting user posts.
     */
    public DataDeletionContext(String userName, IDelete userActivity, IDelete userData, IDelete userPayment, IDelete userPost) {
        this.userName = userName;
        this.userActivity = userActivity;
        this.userData = userData;
        this.userPayment = userPayment;
        this.userPost = userPost;
    }

    private CompletableFuture<Void> executeDeletion(IDelete deletionService) {
        return deletionService != null ?
                CompletableFuture.runAsync(() -> {
                    try {
                        deletionService.delete(userName);
                    } catch (Exception e) {
                        logger.error("Error deleting data for user {}: {}", userName, e.getMessage());
                    }
                }) : CompletableFuture.completedFuture(null);

    }

    public CompletableFuture<Void> delete() {
        return CompletableFuture.allOf(
                executeDeletion(userActivity),
                executeDeletion(userData),
                executeDeletion(userPayment),
                executeDeletion(userPost)
        );
    }
}
