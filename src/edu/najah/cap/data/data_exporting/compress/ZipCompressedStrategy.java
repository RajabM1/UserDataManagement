package edu.najah.cap.data.data_exporting.compress;

import edu.najah.cap.data.data_exporting.helpers.DeletionHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompressedStrategy implements ICompressStrategy {
    /**
     * Compresses the specified files into a ZIP archive.
     *
     * @param fileToCompress      The path to the ZIP archive file to be created.
     * @param filesToBeCompressed The list of files to be included in the ZIP archive.
     * @throws IOException If an I/O error occurs during the compression process.
     */
    @Override
    public void compress(String fileToCompress, List<String> filesToBeCompressed) throws IOException {
        try (ZipOutputStream compressedFileOutputStream = new ZipOutputStream(Files.newOutputStream(Path.of(fileToCompress)))) {
            for (String file : filesToBeCompressed) {
                Path path = Paths.get(file);
                ZipEntry zipEntryFile = new ZipEntry(path.getFileName().toString());
                compressedFileOutputStream.putNextEntry(zipEntryFile);
                Files.copy(path, compressedFileOutputStream);
            }
        }
        DeletionHelper.delete(filesToBeCompressed);
    }
}
