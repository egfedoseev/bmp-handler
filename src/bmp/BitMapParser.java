package bmp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BitMapParser {
    private byte[] bytes;
    private int pos;

    public BitMap parse(Path path) throws IOException {
        bytes = Files.readAllBytes(path);
        pos = 0;
        BitMap.FileHeader fileHeader = parseFileHeader();
        BitMap.Info info = parseInfo();

        return null;
    }

    private BitMap.FileHeader parseFileHeader() {
        return null;
    }

    private BitMap.Info parseInfo() {
        return null;
    }
}
