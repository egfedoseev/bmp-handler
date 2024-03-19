package bmp;

import bmp.byteblock.FileHeader;
import bmp.byteblock.Info;
import bmp.byteblock.PixelTable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class BitMapParser {
    private byte[] bytes;
    private int pos;

    private IllegalArgumentException error(String message) {
        throw new IllegalArgumentException("Error at position " + pos + ": " + message);
    }

    private void expect(byte[] expected) {
        boolean ret = (pos + expected.length <= bytes.length);
        for (int i = pos; i < pos + expected.length; ++i) {
            if (expected[i - pos] != bytes[i]) {
                ret = false;
                break;
            }
        }
        if (!ret) {
            throw error("Expected: " + Arrays.toString(expected) +
                    "\nFound: " + Arrays.toString(Arrays.copyOfRange(bytes, pos, pos + expected.length)));
        }
        pos += expected.length;
    }

    private void expect(byte expected) {
        if (pos >= bytes.length || bytes[pos] != expected) {
            String found = "";
            if (pos < bytes.length) {
                found = Byte.toString(bytes[pos]);
            }
            throw error("Expected: " + expected + "\nFound: " + found);
        }
    }

    public BitMap parse(Path path) {
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Can't read file\nIOException: " + e.getMessage());
            return null;
        }
        pos = 0;

        FileHeader fileHeader = parseFileHeader();
        Info info = parseInfo();
        PixelTable pixels = parsePixelTable();

        return new BitMap(fileHeader, info, pixels);
    }

    private FileHeader parseFileHeader() {

    }

    private Info parseInfo() {

    }

    private PixelTable parsePixelTable() {

    }
}
