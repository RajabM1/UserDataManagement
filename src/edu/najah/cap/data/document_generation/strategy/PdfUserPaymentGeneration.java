package edu.najah.cap.data.document_generation.strategy;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.PaymentService;
import edu.najah.cap.payment.Transaction;

import java.io.IOException;
import java.util.List;

public class PdfUserPaymentGeneration implements IDocumentGeneration {
    private static final IPayment paymentService = new PaymentService();

    @Override
    public void generateDocument(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        List<Transaction> userPayment = paymentService.getTransactions(userName);
        String fileName = "storage\\UserPayment.pdf";

        try (PdfWriter pdfWriter = new PdfWriter(fileName);
             PdfDocument pdfDocument = new PdfDocument(pdfWriter);
             Document document = new Document(pdfDocument)) {

            document.add(new Paragraph("Transaction Report \n"));

            for (Transaction transaction : userPayment) {
                document.add(new Paragraph("Transaction ID: " + transaction.getId()));
                document.add(new Paragraph("User Name: " + transaction.getUserName()));
                document.add(new Paragraph("Amount: " + transaction.getAmount()));
                document.add(new Paragraph("Description: " + transaction.getDescription()));
                document.add(new Paragraph("\n"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
