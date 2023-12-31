package edu.najah.cap.data.document_generation.strategy;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

import java.io.IOException;
import java.util.List;

public class PdfUserActivityGeneration implements IDocumentGeneration {
    private static final IUserActivityService userActivityService = new UserActivityService();

    @Override
    public void generateDocument(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        List<UserActivity> userActivities = userActivityService.getUserActivity(userName);
        String fileName = "storage\\UserActivity.pdf";

        try (PdfWriter pdfWriter = new PdfWriter(fileName);
             PdfDocument pdfDocument = new PdfDocument(pdfWriter);
             Document document = new Document(pdfDocument)) {

            document.add(new Paragraph("User Activities Report \n"));

            for (UserActivity activity : userActivities) {
                document.add(new Paragraph("Activity ID: " + activity.getId()));
                document.add(new Paragraph("User ID: " + activity.getUserId()));
                document.add(new Paragraph("Activity Type: " + activity.getActivityType()));
                document.add(new Paragraph("Activity Date: " + activity.getActivityDate()));
                document.add(new Paragraph("\n"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
