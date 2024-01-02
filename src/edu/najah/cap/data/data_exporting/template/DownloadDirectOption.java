package edu.najah.cap.data.data_exporting.template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadDirectOption extends DataExportingTemplate {
    /**
     * Overrides the 'upload' method to perform the download of the compressed data.
     */
    @Override
    protected void upload() {
        Path sourcePath = Paths.get("storage/data.zip");
        Path destinationFolder = Paths.get(System.getProperty("user.home"), "Downloads");

        try {
            String timestamp = Long.toString(System.currentTimeMillis());
            Path renamedDestinationPath = destinationFolder.resolve("renamed_file_" + timestamp + ".zip");
            Files.move(sourcePath, renamedDestinationPath);
            logger.info("File downloaded successfully to: " + renamedDestinationPath);
        }  catch (IOException e) {
            System.err.println("Error occurred while downloading the file: " + e.getMessage());
        }
    }
}
