package edu.najah.cap.data.data_exporting.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class DeletionHelper {
    /**
     * Deletes the specified list of files.
     *
     * @param filesToBeDeleted The list of files to be deleted.
     * @throws IOException If an I/O error occurs during the deletion process.
     */
    public static void delete(List<String> filesToBeDeleted) throws IOException {
        for (String file : filesToBeDeleted) {
            Files.delete(Path.of(file));
        }
    }
}
