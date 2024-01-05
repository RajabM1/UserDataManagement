package edu.najah.cap.data.data_exporting.document_generation.strategy;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserService;

public class PdfUserDataGeneration extends AbstractPdfDocumentGeneration {

    private static final IUserService userService = new UserService();

    /**
     * Adds user data content to the PDF document.
     *
     * @param document The document to which user activity content is added.
     * @param userName The username for whom the activity report is being generated.
     * @throws SystemBusyException If the system is busy and cannot add content.
     * @throws BadRequestException If the request parameters are invalid.
     * @throws NotFoundException   If the user is not found.
     */
    @Override
    protected void addContent(Document document, String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        logger.info("Starting to add user data content for {}", userName);
        UserProfile userProfile = userService.getUser(userName);
        document.add(new Paragraph("User Data: \n"));
        document.add(new Paragraph("Username: " + userProfile.getUserName()));
        document.add(new Paragraph("First Name: " + userProfile.getFirstName()));
        document.add(new Paragraph("Last Name: " + userProfile.getLastName()));
        document.add(new Paragraph("Phone Number: " + userProfile.getPhoneNumber()));
        document.add(new Paragraph("Email: " + userProfile.getEmail()));
        document.add(new Paragraph("Role: " + userProfile.getRole()));
        document.add(new Paragraph("Department: " + userProfile.getDepartment()));
        document.add(new Paragraph("Organization: " + userProfile.getOrganization()));
        document.add(new Paragraph("Country: " + userProfile.getCountry()));
        document.add(new Paragraph("City: " + userProfile.getCity()));
        document.add(new Paragraph("Street: " + userProfile.getStreet()));
        document.add(new Paragraph("Postal Code: " + userProfile.getPostalCode()));
        document.add(new Paragraph("Building: " + userProfile.getBuilding()));
        document.add(new Paragraph("User Type: " + userProfile.getUserType()));
        logger.info("User data content added successfully for {}", userName);
    }

    /**
     * Provides the file name for the user data PDF document.
     *
     * @return The file name for the generated user activity PDF document.
     */
    @Override
    protected String getFileName() {
        return "storage\\UserData.pdf";
    }
}
