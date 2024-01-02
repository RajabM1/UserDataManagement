package edu.najah.cap.data.template;

import edu.najah.cap.data.document_generation.DocumentGenerationFactory;
import edu.najah.cap.data.document_generation.Generator;
import edu.najah.cap.data.document_generation.strategy.AbstractPdfDocumentGeneration;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserService;
import edu.najah.cap.iam.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class DataExportingTemplate {
    private static final Logger logger = LoggerFactory.getLogger(AbstractPdfDocumentGeneration.class);
    private static final IUserService userService = new UserService();

    public final void export(String userName)  {
        generatePdf(userName);

    }

    protected void generatePdf(String userName) {
        UserType userType = null;

        try {
            userType = userService.getUser(userName).getUserType();
        } catch (SystemBusyException systemBusyException) {
            logger.error("System busy while get user data: " + userName, systemBusyException.getMessage());
        } catch (BadRequestException badRequestException) {
            logger.error("Bad request in get user service: " + userName, badRequestException.getMessage());
        } catch (NotFoundException notFoundException) {
            logger.error("Required resource not found in get user service: " + userName, notFoundException.getMessage());
        } catch (Exception exception) {
            logger.error("Unexpected error in get user data: " + userName, exception.getMessage());
        }

        Generator generator = DocumentGenerationFactory.create(userType, userName);
        generator.generateUserActivity();
        generator.generateUserData();
        generator.generateUserPost();
        generator.generateUserPayment();
    }

}
