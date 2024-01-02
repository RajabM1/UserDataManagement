package edu.najah.cap.data.data_exporting.compress;

import java.io.IOException;
import java.util.List;

public interface ICompressStrategy {
    void compress(String fileToCompress, List<String> filesToBeCompressed) throws IOException;
}
