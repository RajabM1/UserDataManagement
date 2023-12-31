package edu.najah.cap.data.document_generation.strategy;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.iam.UserService;

import java.io.IOException;

public class PdfUserDataGeneration implements IDocumentGeneration {
    private static final IUserService userService = new UserService();


    @Override
    public void generateDocument(String userName) throws SystemBusyException, NotFoundException, BadRequestException {
        UserProfile userProfile = userService.getUser(userName);
        String filename = "storage\\UserData.pdf";

        try (PdfWriter pdfWriter = new PdfWriter(filename);
             PdfDocument pdfDocument = new PdfDocument(pdfWriter);
             Document document = new Document(pdfDocument)) {

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
