package edu.najah.cap.data.data_exporting.compress;

import java.io.IOException;
import java.util.List;

/**
 * Interface for defining compression strategies.
 * Implementations of this interface should provide a way to compress a specified file and a list of files.
 */
public interface ICompressStrategy {

    /**
     * Compresses the specified file and a list of files according to the strategy.
     *
     * @param fileToCompress      The main file to be compressed.
     * @param filesToBeCompressed The list of additional files to be included in the compression.
     * @throws IOException If an I/O error occurs during the compression process.
     */
    void compress(String fileToCompress, List<String> filesToBeCompressed) throws IOException;
}
