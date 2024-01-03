package edu.najah.cap.data.data_exporting.document_generation.strategy;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.activity.UserActivityService;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

import java.util.List;

public class PdfUserActivityGeneration extends AbstractPdfDocumentGeneration {
    private static final IUserActivityService userActivityService = new UserActivityService();

    /**
     * Adds user activity content to the PDF document.
     *
     * @param document The document to which user activity content is added.
     * @param userName The username for whom the activity report is being generated.
     * @throws SystemBusyException If the system is busy and cannot add content.
     * @throws BadRequestException If the request parameters are invalid.
     * @throws NotFoundException   If the user is not found.
     */
    @Override
    protected void addContent(Document document, String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        logger.info("Starting to add user activity content for: {}", userName);
        List<UserActivity> userActivities = userActivityService.getUserActivity(userName);
        document.add(new Paragraph("User Activities Report \n"));

        for (UserActivity activity : userActivities) {
            document.add(new Paragraph("Activity ID: " + activity.getId()));
            document.add(new Paragraph("User ID: " + activity.getUserId()));
            document.add(new Paragraph("Activity Type: " + activity.getActivityType()));
            document.add(new Paragraph("Activity Date: " + activity.getActivityDate()));
            document.add(new Paragraph("\n"));
        }
        logger.info("User activity content added successfully for: {}", userName);
    }

    /**
     * Provides the file name for the user activity PDF document.
     *
     * @return The file name for the generated user activity PDF document.
     */
    @Override
    protected String getFileName() {
        return "storage\\UserActivity.pdf";
    }
}
