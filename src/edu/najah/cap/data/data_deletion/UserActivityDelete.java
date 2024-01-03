package edu.najah.cap.data.data_deletion;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.exceptions.Util;

import java.util.ArrayList;
import java.util.List;

public class UserActivityDelete extends BaseDataDelete {
    private final IUserActivityService userActivityService;

    public UserActivityDelete(IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    @Override
    protected void performDeletion(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        Util.setSkipValidation(true);
        logger.info("Start deleting user activities data 1");
        List<UserActivity> userActivities = userActivityService.getUserActivity(userName);
        List<String> activityIdsToDelete = new ArrayList<>();

        for (UserActivity userActivity : userActivities) {
            activityIdsToDelete.add(userActivity.getId());
        }

        for (String activityId : activityIdsToDelete) {
            userActivityService.removeUserActivity(userName, activityId);
        }
        logger.info("User activities deleted successfully");
        Util.setSkipValidation(false);
    }
}
