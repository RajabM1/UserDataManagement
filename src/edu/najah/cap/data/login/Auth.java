package edu.najah.cap.data.login;

import edu.najah.cap.data.data_exporting.document_generation.strategy.AbstractPdfDocumentGeneration;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Auth {
    private static final Logger logger = LoggerFactory.getLogger(Auth.class);

    private static final IUserService userService = new UserService();

    public static String logIn(String userName){
        while (true) {
            try {
                userService.getUser(userName);
                logger.info("Login successfully user: " + userName);
                break;
            } catch (SystemBusyException e) {
                continue;
            } catch (NotFoundException | BadRequestException e) {
                logger.error("Wrong user name");
                System.out.println("User does not exist please enter a valid User Name");
            }
            Scanner scanner = new Scanner(System.in);
            userName = scanner.nextLine();
        }
        return userName;
    }
}
