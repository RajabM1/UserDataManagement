package edu.najah.cap.data.document_generation.strategy;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.payment.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PdfUserPaymentGeneration extends AbstractPdfDocumentGeneration {
    private static final Logger logger = LoggerFactory.getLogger(PdfUserPaymentGeneration.class);

    private static final IPayment paymentService = new PaymentService();

    /**
     * Adds user payment content to the PDF document.
     *
     * @param document The document to which user activity content is added.
     * @param userName The username for whom the activity report is being generated.
     * @throws SystemBusyException If the system is busy and cannot add content.
     * @throws BadRequestException If the request parameters are invalid.
     * @throws NotFoundException   If the user is not found.
     */
    @Override
    protected void addContent(Document document, String userName) throws SystemBusyException, BadRequestException, NotFoundException {

        logger.info("Starting to add user payment content for {}", userName);
        List<Transaction> userPayment = paymentService.getTransactions(userName);

        document.add(new Paragraph("Transaction Report \n"));

        for (Transaction transaction : userPayment) {
            document.add(new Paragraph("Transaction ID: " + transaction.getId()));
            document.add(new Paragraph("User Name: " + transaction.getUserName()));
            document.add(new Paragraph("Amount: " + transaction.getAmount()));
            document.add(new Paragraph("Description: " + transaction.getDescription()));
            document.add(new Paragraph("\n"));

        }
        logger.info("User payment content added successfully for {}", userName);

    }

    /**
     * Provides the file name for the user payment PDF document.
     *
     * @return The file name for the generated user activity PDF document.
     */
    @Override
    protected String getFileName() {
        return "storage\\UserPayment.pdf";
    }
}
