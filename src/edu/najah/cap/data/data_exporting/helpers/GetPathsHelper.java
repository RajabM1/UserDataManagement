package edu.najah.cap.data.data_exporting.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetPathsHelper {
    /**
     * Retrieves a list of file paths with the specified file extension in the "storage" folder.
     *
     * @param fileExtension The file extension to filter files (e.g., ".txt").
     * @return A list of file paths matching the specified file extension.
     */
    public static List<String> getFilesPathsList(String fileExtension) {
        List<String> fileList = new ArrayList<>();
        File folder = new File("storage");

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(fileExtension)) {
                        fileList.add(file.getPath());
                    }
                }
            }
        }
        return fileList;
    }
}
