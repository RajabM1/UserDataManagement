package edu.najah.cap.data;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.data.data_deletion.*;
import edu.najah.cap.data.data_exporting.helpers.LoggerConfig;
import edu.najah.cap.data.data_exporting.template.DataExportingTemplate;
import edu.najah.cap.data.data_exporting.template.DownloadDirectOption;
import edu.najah.cap.data.data_exporting.template.UploadToDriveOption;
import edu.najah.cap.data.exceptions.DocumentGenerationException;
import edu.najah.cap.data.login.Auth;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.Util;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.payment.Transaction;
import edu.najah.cap.posts.IPostService;
import edu.najah.cap.posts.Post;
import edu.najah.cap.posts.PostService;

import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Application {

    private static final IUserActivityService userActivityService = new UserActivityService();
    private static final IPayment paymentService = new PaymentService();
    private static final IUserService userService = new UserService();
    private static final IPostService postService = new PostService();

    private static String loginUserName;

    public static void main(String[] args) {
        generateRandomData();
        Instant start = Instant.now();
        System.out.println("Application Started: " + start);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        System.out.println("Note: You can use any of the following usernames: user0, user1, user2, user3, .... user99");
        String userName = scanner.nextLine();
        setLoginUserName(userName);
        //TODO Your application starts here. Do not Change the existing code

        LoggerConfig.setLoggerConfig();
        userName = Auth.logIn(userName);
        setLoginUserName(userName);
        System.out.println("1. Export Data & Download Direct");
        System.out.println("2. Export Data & Upload To Drive");
        System.out.println("3. Soft Delete");
        System.out.println("4. Hard Delete");
        System.out.println("0 to exit");

        System.out.println("Enter Your Choice: ");
        int choice = 0;
        choice = scanner.nextInt();


        IDelete userActivityDelete = new UserActivityDelete(userActivityService);
        IDelete userDataDelete = new UserDataDelete(userService);
        IDelete userPaymentDelete = new UserPaymentDelete(paymentService);
        IDelete userPostDelete = new UserPostDelete(postService);

        DataDeletionFactory deletionFactory = new DataDeletionFactory(
                userService,
                userActivityDelete,
                userDataDelete,
                userPaymentDelete,
                userPostDelete
        );

        while (choice != 0) {
            switch (choice) {
                case 1:
                    DataExportingTemplate dataDownloadingTemplate = new DownloadDirectOption();
                    try {
                        dataDownloadingTemplate.export(userName);
                    } catch (NotFoundException e) {
                        userName = Auth.logIn(userName);
                        setLoginUserName(userName);
                    } catch (DocumentGenerationException e) {
                        System.out.println("Failed to generate document please try again later");
                    }
                    break;
                case 2:
                    DataExportingTemplate dataUploadingTemplate = new UploadToDriveOption();
                    try {
                        dataUploadingTemplate.export(userName);
                    } catch (NotFoundException e) {
                        userName = Auth.logIn(userName);
                        setLoginUserName(userName);
                    } catch (DocumentGenerationException e) {
                        System.out.println("Failed to generate document please try again later");
                    }
                    break;
                case 3:
                    try {
                        DataDeletionContext deletionContext = deletionFactory.createDeletionContext(userName, false);
                        CompletableFuture<Void> deletionFuture = deletionContext.delete();

                        deletionFuture.join();
                        System.out.println("data deleted successfully ");

                    } catch (Exception e) {
                        System.out.println("Exception " + e);
                    }
                    break;
                case 4:
                    try {
                        DataDeletionContext deletionContext = deletionFactory.createDeletionContext(userName, true);
                        CompletableFuture<Void> deletionFuture = deletionContext.delete();

                        deletionFuture.join();
                        System.out.println("data deleted successfully ");

                    } catch (Exception e) {
                        System.out.println("Exception " + e);
                    } finally {
                        System.out.println("Session expired please login again");
                        userName = Auth.logIn(userName);
                        setLoginUserName(userName);
                    }
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
            System.out.println("\nEnter Your Choice: ");
            choice = scanner.nextInt();
        }

        //TODO Your application ends here. Do not Change the existing code
        Instant end = Instant.now();
        System.out.println("Application Ended: " + end);
    }


    private static void generateRandomData() {
        Util.setSkipValidation(true);
        for (int i = 0; i < 100; i++) {
            generateUser(i);
            generatePost(i);
            generatePayment(i);
            generateActivity(i);
        }
        System.out.println("Data Generation Completed");
        Util.setSkipValidation(false);
    }


    private static void generateActivity(int i) {
        for (int j = 0; j < 100; j++) {
            try {
                if(UserType.NEW_USER.equals(userService.getUser("user" + i).getUserType())) {
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Error while generating activity for user" + i);
            }
            userActivityService.addUserActivity(new UserActivity("user" + i, "activity" + i + "." + j, Instant.now().toString()));
        }
    }

    private static void generatePayment(int i) {
        for (int j = 0; j < 100; j++) {
            try {
                if (userService.getUser("user" + i).getUserType() == UserType.PREMIUM_USER) {
                    paymentService.pay(new Transaction("user" + i, i * j, "description" + i + "." + j));
                }
            } catch (Exception e) {
                System.err.println("Error while generating post for user" + i);
            }
        }
    }

    private static void generatePost(int i) {
        for (int j = 0; j < 100; j++) {
            postService.addPost(new Post("title" + i + "." + j, "body" + i + "." + j, "user" + i, Instant.now().toString()));
        }
    }

    private static void generateUser(int i) {
        UserProfile user = new UserProfile();
        user.setUserName("user" + i);
        user.setFirstName("first" + i);
        user.setLastName("last" + i);
        user.setPhoneNumber("phone" + i);
        user.setEmail("email" + i);
        user.setPassword("pass" + i);
        user.setRole("role" + i);
        user.setDepartment("department" + i);
        user.setOrganization("organization" + i);
        user.setCountry("country" + i);
        user.setCity("city" + i);
        user.setStreet("street" + i);
        user.setPostalCode("postal" + i);
        user.setBuilding("building" + i);
        user.setUserType(getRandomUserType(i));
        userService.addUser(user);
    }

    private static UserType getRandomUserType(int i) {
        if (i > 0 && i < 3) {
            return UserType.NEW_USER;
        } else if (i > 3 && i < 7) {
            return UserType.REGULAR_USER;
        } else {
            return UserType.PREMIUM_USER;
        }
    }

    public static String getLoginUserName() {
        return loginUserName;
    }

    private static void setLoginUserName(String loginUserName) {
        Application.loginUserName = loginUserName;
    }
}
