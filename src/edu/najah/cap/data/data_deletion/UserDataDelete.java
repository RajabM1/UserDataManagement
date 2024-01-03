package edu.najah.cap.data.data_deletion;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;

public class UserDataDelete extends BaseDataDelete {

    private final IUserService userService;

    public UserDataDelete(IUserService userService) {
        this.userService = userService;
    }

    @Override
    protected void performDeletion(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        logger.info("Start deleting user data 2");
        userService.deleteUser(userName);
        logger.info("User data deleted successfully");
    }
}
