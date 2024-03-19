package bmp;

import java.awt.*;
import java.io.File;

public class BitMap {
    public static class FileHeader {
        private short type; // 2 unsigned
        private int bfSize; // 4 unsigned
        private short reserved1; // 2 unsigned
        private short reserved2; // 2 unsigned
        private int offset; // 4 unsigned
    }

    public static class Info {
        private int size; // 4 unsigned
        private int width; // 4 signed
        private int height; // 4 signed
        private short planes; // 2 unsigned
        private short bitCount; // 2 unsigned
        private int compression; // 4 unsigned
        private int imageSize; // 4 unsigned
        private int horizontalResolution; // 4 signed
        private int verticalResolution; // 4 signed
        private int colorsUsed; // 4 unsigned
        private int importantColors; // 4 unsigned
    }

    private FileHeader fileHeader;
    private Info info;
    private Color[][] pixels;

    public BitMap(FileHeader fileHeader, Info info, Color[][] pixels) {
        this.fileHeader = fileHeader;
        this.info = info;
        this.pixels = pixels;
    }

    public void rotate() {
        Color[][] newPixels = new Color[pixels[0].length][pixels.length];
        for (int i = 0; i < pixels.length; ++i) {
            for (int j = 0; j < pixels[i].length; ++j) {
                newPixels[j][pixels.length - i - 1] = pixels[i][j];
            }
        }
        pixels = newPixels;
        info.width = pixels[0].length;
        info.height = pixels.length;
    }

    public void cut(int x1, int x2, int y1, int y2) {

    }

//    public byte[] toByteArray() {
//        byte[] res =
//    }
}
