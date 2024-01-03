package edu.najah.cap.data.data_deletion;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDataDelete implements IDelete {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract void performDeletion(String userName) throws SystemBusyException, BadRequestException, NotFoundException;

    @Override
    public void delete(String userName) throws SystemBusyException, BadRequestException, NotFoundException {
        performDeletion(userName);
    }
}
