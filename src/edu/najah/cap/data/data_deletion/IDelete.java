package edu.najah.cap.data.data_deletion;

import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;

public interface IDelete {
    /**
     * Deletes the specified type of data for the given user.
     *
     * @param userName the username for whom the data is to be deleted.
     * @throws SystemBusyException if the system is currently unable to process the request.
     * @throws NotFoundException   if the specified user or data to be deleted is not found.
     * @throws BadRequestException if the request is invalid.
     */
    void delete(String userName) throws SystemBusyException, NotFoundException, BadRequestException;
}
