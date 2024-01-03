package edu.najah.cap.data.data_exporting.template;

import com.google.api.services.drive.Drive;
import edu.najah.cap.data.data_exporting.cloud_storage.UploadToDrive;
import edu.najah.cap.data.data_exporting.helpers.DeletionHelper;
import edu.najah.cap.data.data_exporting.helpers.GetPathsHelper;

import java.io.IOException;

public class UploadToDriveOption extends DataExportingTemplate {
    /**
     * Overrides the 'upload' method to perform the download of the compressed data.
     */
    @Override
    protected void upload() {
        try {
            logger.info("Start data uploading to drive.");
            Drive service = UploadToDrive.getDriveService();
            UploadToDrive.uploadFilesAndGetURIs(service, "storage");
            DeletionHelper.delete(GetPathsHelper.getFilesPathsList(".zip"));
            logger.info("Data uploaded successfully to drive.");
        } catch (IOException ioException) {
            logger.error("Error while trying to upload to drive, folder not found : " + ioException.getMessage());
        } catch (Exception exception) {
            logger.error("Unexpected error while trying to upload to drive : " + exception.getMessage());
        }
    }
}
