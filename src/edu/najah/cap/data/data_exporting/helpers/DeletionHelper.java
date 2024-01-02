package edu.najah.cap.data.data_exporting.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class DeletionHelper {
    public static void delete(List<String> filesToBeDeleted) throws IOException {
        for (String file : filesToBeDeleted) {
            Files.delete(Path.of(file));
        }
    }
}
