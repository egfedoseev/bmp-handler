package bmp.byteblock;

import java.io.File;
import java.io.IOException;

public interface BinaryFile {
    void writeToFile(File file) throws IOException;
}
