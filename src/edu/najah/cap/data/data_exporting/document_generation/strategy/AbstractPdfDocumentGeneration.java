package edu.najah.cap.data.data_exporting.document_generation.strategy;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import edu.najah.cap.data.data_exporting.helpers.DeletionHelper;
import edu.najah.cap.data.data_exporting.helpers.GetPathsHelper;
import edu.najah.cap.data.exceptions.DocumentGenerationException;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.System.exit;

public abstract class AbstractPdfDocumentGeneration implements IDocumentGeneration {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Adds content to the PDF document. This method is to be implemented by subclasses
     * to define specific content that should be added to the document.
     *
     * @param document The document to which content should be added.
     * @param userName The username for whom the document is being generated.
     * @throws SystemBusyException If the system is busy and cannot add content.
     * @throws BadRequestException If the request parameters are invalid.
     * @throws NotFoundException   If the user is not found.
     */
    protected abstract void addContent(Document document, String userName) throws SystemBusyException, BadRequestException, NotFoundException;

    /**
     * Generates a PDF document using a predefined structure and delegates the addition
     * of specific content to the {@code addContent} method.
     *
     * @param userName The username for whom the document is generated.
     */
    @Override
    public void generateDocument(String userName) throws DocumentGenerationException {
        int maxRetries = 4;
        int currentRetry = 0;
        boolean success = false;

        while (currentRetry < maxRetries && !success) {

            String fileName = getFileName();

            try (PdfWriter pdfWriter = new PdfWriter(fileName);
                 PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                 Document document = new Document(pdfDocument)) {

                addContent(document, userName);
                logger.info("Document generation successful for user: {}", userName);
                success = true;

            } catch (IOException | SystemBusyException e) {
                logger.error("Error during document generation for user: {} in {}: {}", userName, fileName, e.getMessage(), e);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (BadRequestException | NotFoundException e) {
                logger.error("Critical error in document generation for user: {} in {}: {}", userName, fileName, e.getMessage(), e);
                break;
            } catch (Exception e) {
                logger.error("Unexpected error in document generation for user: {} in {}: {}", userName, fileName, e.getMessage(), e);
            }
            currentRetry++;
            if (!success) {
                logger.info("Retrying document generation for user: {}, attempt: {}", userName, currentRetry);
            }
        }
        if (!success) {
            logger.error("Failed to generate document for user: {} after {} attempts", userName, maxRetries);
            try {
                DeletionHelper.delete(GetPathsHelper.getFilesPathsList(".pdf"));
            } catch (IOException ioException) {
                logger.error("IO error during deleting pdf files: {} in {}: {} ", userName, getFileName(), ioException.getMessage());
            }
            throw new DocumentGenerationException("Failed to generate document for user: " + userName);
        }
    }


    /**
     * Provides the file name for the PDF document. This method needs to be implemented
     * by subclasses to define how the file name is determined.
     *
     * @return The file name for the generated PDF document.
     */
    protected abstract String getFileName();
}


