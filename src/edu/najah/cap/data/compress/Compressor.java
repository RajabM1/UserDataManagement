package edu.najah.cap.data.compress;

import java.io.IOException;
import java.util.List;

public class Compressor {
    private static volatile Compressor compressor;
    private ICompressStrategy compressStrategy;

    private Compressor() {
    }

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

    public void setCompressStrategy(ICompressStrategy compressStrategy) {
        this.compressStrategy = compressStrategy;
    }

    public void compress(String fileToCompress, List<String> filesToBeCompressed) throws IOException {
        this.compressStrategy.compress(fileToCompress, filesToBeCompressed);
    }
}
