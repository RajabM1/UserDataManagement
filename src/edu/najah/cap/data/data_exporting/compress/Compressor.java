package edu.najah.cap.data.data_exporting.compress;

import java.io.IOException;
import java.util.List;

/**
 * Singleton class responsible for compressing files using a specified compression strategy.
 */
public class Compressor {

    // Singleton instance of the Compressor class
    private static volatile Compressor compressor;

    // Strategy for compression (e.g., Zip, Gzip)
    private ICompressStrategy compressStrategy;

    // Private constructor to enforce singleton pattern
    private Compressor() {
    }

    /**
     * Retrieves the singleton instance of the Compressor class.
     *
     * @return The singleton instance of the Compressor class.
     */
    public static Compressor getCompressorInstance() {
        if (compressor == null) {
            synchronized (Compressor.class) {
                if (compressor == null) {
                    compressor = new Compressor();
                }
            }
        }
        return compressor;
    }

    /**
     * Sets the compression strategy to be used by the Compressor.
     *
     * @param compressStrategy The compression strategy implementation.
     */
    public void setCompressStrategy(ICompressStrategy compressStrategy) {
        this.compressStrategy = compressStrategy;
    }

    /**
     * Compresses the specified file and a list of files using the chosen compression strategy.
     *
     * @param fileToCompress      The main file to be compressed.
     * @param filesToBeCompressed The list of additional files to be included in the compression.
     * @throws IOException If an I/O error occurs during the compression process.
     */
    public void compress(String fileToCompress, List<String> filesToBeCompressed) throws IOException {
        this.compressStrategy.compress(fileToCompress, filesToBeCompressed);
    }
}
