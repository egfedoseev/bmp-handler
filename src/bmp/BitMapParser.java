package bmp;

import bmp.byteblock.Field;
import bmp.byteblock.FileHeader;
import bmp.byteblock.Info;
import bmp.byteblock.PixelTable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

public class BitMapParser {
    private byte[] bytes;
    private int pos;

    private IllegalArgumentException error(String message) {
        throw new IllegalArgumentException("Error at position " + Integer.toHexString(pos).toUpperCase(Locale.ROOT) + ": " + message);
    }

    private void expect(byte[] expected) {
        if (!test(expected)) {
            throw error("Expected: " + Arrays.toString(expected) +
                    "\nFound: " + Arrays.toString(Arrays.copyOfRange(bytes, pos, pos + expected.length)));
        }
        pos += expected.length;
    }

    private void expect(byte expected) {
        if (!test(expected)) {
            String found = "";
            if (pos < bytes.length) {
                found = Byte.toString(bytes[pos]);
            }
            throw error("Expected: " + expected + "\nFound: " + found);
        }
    }

    private boolean test(byte expected) {
        return (pos < bytes.length && bytes[pos] == expected);
    }

    private boolean test(byte[] expected) {
        boolean ret = (pos + expected.length <= bytes.length);
        for (int i = pos; i < pos + expected.length; ++i) {
            if (expected[i - pos] != bytes[i]) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    private boolean take(byte expected) {
        if (test(expected)) {
            ++pos;
            return true;
        }
        return false;
    }

    private boolean take(byte[] expected) {
        if (test(expected)) {
            pos += expected.length;
            return true;
        }
        return false;
    }

    private byte take() {
        if (pos >= bytes.length) {
            throw error("Expected byte, found none");
        }
        return bytes[pos++];
    }

    private byte[] take(int count) {
        byte[] ret = new byte[count];
        for (int i = 0; i < count; ++i) {
            ret[i] = take();
        }
        return ret;
    }

    public BitMap parse(Path path) throws IOException {
        bytes = Files.readAllBytes(path);
        pos = 0;

        FileHeader fileHeader = parseFileHeader();
        Info info = parseInfo();
        PixelTable pixels = parsePixelTable(info.getBitsPerPixel(), info.getHeight(), info.getWidth());

        return new BitMap(fileHeader, info, pixels);
    }

    private FileHeader parseFileHeader() {
        Field[] fields = new Field[5];

        fields[0] = expectedField(new byte[]{0x42, 0x4d});
        fields[1] = parseField(4);
        fields[2] = expectedField(new byte[]{0x0, 0x0});
        fields[3] = expectedField(new byte[]{0x0, 0x0});
        fields[4] = parseField(4);

        return new FileHeader(fields);
    }

    private Info parseInfo() {
        Field[] fields = new Field[11];

        fields[0] = expectedField(new byte[]{0x28, 0x0, 0x0, 0x0});
        for (int i = 1; i < 11; ++i) {
            if (i == 3) {
                fields[i] = expectedField(new byte[]{0x1, 0x0});
                continue;
            }
            int count = 4;
            if (i == 4) {
                count = 2;
            }
            fields[i] = parseField(count);
        }

        return new Info(fields);
    }

    private PixelTable parsePixelTable(int bitsPerPixel, int height, int width) {
        if (bitsPerPixel % 8 != 0) {
            throw error("Can't parse divided bytes");
        }

        int bytesPerPixel = bitsPerPixel / 8;
        int additionalZeroes = (4 - ((bytesPerPixel * width) % 4)) % 4;

        Field[][] fields = new Field[height][width];
        PixelTable.PixelRow[] rows = new PixelTable.PixelRow[height];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                fields[i][j] = parseField(bytesPerPixel);
            }
            if (additionalZeroes > 0) {
                byte[] zeroes = new byte[additionalZeroes];
                Arrays.fill(zeroes, (byte) 0x0);
                expect(zeroes);
            }
        }

        for (int i = 0; i < height; ++i) {
            rows[i] = new PixelTable.PixelRow(fields[i]);
        }

        return new PixelTable(rows, height, width, bitsPerPixel);
    }

    private Field parseField(int count) {
        byte[] data = take(count);
        return new Field(data);
    }

    private Field expectedField(byte[] expected) {
        expect(expected);
        return new Field(expected);
    }
}
