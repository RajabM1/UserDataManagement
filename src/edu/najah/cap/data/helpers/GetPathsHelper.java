package edu.najah.cap.data.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetPathsHelper {
    public static List<String> getFilesPathsList() {
        List<String> fileList = new ArrayList<>();
        File folder = new File("storage");

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".pdf")) {
                        fileList.add(file.getPath());
                    }
                }
            }
        }
        return fileList;
    }
}
