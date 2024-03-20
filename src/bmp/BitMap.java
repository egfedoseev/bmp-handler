package bmp;

import bmp.byteblock.BinaryFile;
import bmp.byteblock.FileHeader;
import bmp.byteblock.Info;
import bmp.byteblock.PixelTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitMap implements BinaryFile {
    private final FileHeader fileHeader;
    private final Info info;
    private final PixelTable pixels;

    public BitMap(FileHeader fileHeader, Info info, PixelTable pixels) {
        this.fileHeader = fileHeader;
        this.info = info;
        this.pixels = pixels;
    }

    public void rotate() {
        pixels.rotate();
        int newHeight = info.getWidth();
        int newWidth = info.getHeight();
        info.setHeight(newHeight);
        info.setWidth(newWidth);
    }

    public void cut(int x1, int x2, int y1, int y2) {
        int lostBytes = info.getBitsPerPixel() * (info.getHeight() * info.getWidth() - (x2 - x1 + 1) * (y2 - y1 + 1)) / 8;
        fileHeader.setWholeSize(fileHeader.getWholeSize() - lostBytes);
        info.setWidth(y2 - y1 + 1);
        info.setHeight(x2 - x1 + 1);
        pixels.cut(x1, x2, y1, y2);
    }

    @Override
    public void writeToFile(File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileHeader.toByteArray());
            fos.write(info.toByteArray());
            fos.write(pixels.toByteArray());
        } catch (IOException e) {
            System.err.println("Can't write to file\nIOException: " + e.getMessage());
        }
    }
}
