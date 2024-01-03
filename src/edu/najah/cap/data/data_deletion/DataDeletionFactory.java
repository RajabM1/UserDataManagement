package edu.najah.cap.data.data_deletion;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserType;

public class DataDeletionFactory {
    private final IUserService userService;
    private final IDelete userActivityDelete;
    private final IDelete userPostDelete;
    private final IDelete userDataDelete;
    private final IDelete userPaymentDelete;

    public DataDeletionFactory(IUserService userService, IDelete userActivityDelete, IDelete userDataDelete, IDelete userPaymentDelete, IDelete userPostDelete) {
        this.userService = userService;
        this.userActivityDelete = userActivityDelete;
        this.userDataDelete = userDataDelete;
        this.userPaymentDelete = userPaymentDelete;
        this.userPostDelete = userPostDelete;
    }

    /**
     * Creates a DataDeletionContext for a given user based on their type.
     *
     * @param userName the username of the user whose data is to be deleted.
     * @param status   flag to indicate if user data should be deleted.
     * @return DataDeletionContext configured for the specific user type.
     * @throws NotFoundException if the user is not found.
     */
    public DataDeletionContext createDeletionContext(String userName, boolean status) throws NotFoundException, SystemBusyException, BadRequestException {
        UserType userType;
        while (true) {
            try {
                userType = userService.getUser(userName).getUserType();
                break;
            } catch (SystemBusyException s) {

            }

        }

        DataDeletionContext deletionContext = null;
        if (UserType.PREMIUM_USER.equals(userType)) {
            deletionContext = new DataDeletionContext(userName, userActivityDelete, status ? userDataDelete : null, userPaymentDelete, userPostDelete);
        } else if (UserType.REGULAR_USER.equals(userType)) {
            deletionContext = new DataDeletionContext(userName, userActivityDelete, status ? userDataDelete : null, null, userPostDelete);
        } else if (UserType.NEW_USER.equals(userType)) {
            deletionContext = new DataDeletionContext(userName, null, status ? userDataDelete : null, null, userPostDelete);
        }
        return deletionContext;
    }

}
