package edu.najah.cap.data.document_generation.strategy;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public interface IDocumentGeneration {
    void generateDocument(String userName) throws SystemBusyException, BadRequestException, NotFoundException;

}
