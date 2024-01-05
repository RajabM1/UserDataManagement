package edu.najah.cap.data.data_deletion;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.exceptions.Util;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserPaymentDelete extends BaseDataDelete {

    private final IPayment paymentService;

    public UserPaymentDelete(IPayment paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected void performDeletion(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        Util.setSkipValidation(true);
        logger.info("Start deleting user payments data 3");
        List<Transaction> transactionByAuthor = paymentService.getTransactions(userName);
        List<String> transactionIdsToDelete = new ArrayList<>();

        for (Transaction transaction : transactionByAuthor) {
            transactionIdsToDelete.add(transaction.getId());
        }

        for (String transactionId : transactionIdsToDelete) {
            paymentService.removeTransaction(userName, transactionId);
        }
        logger.info("User payments deleted successfully");
        Util.setSkipValidation(false);
    }

}
