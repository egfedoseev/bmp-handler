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

    public boolean isReversedRows() {
        return (info.getTrueHeight() > 0);
    }

    public void rotate() {
        pixels.rotate();
        int newHeight = info.getWidth();
        int newWidth = info.getPositiveHeight();
        info.setHeight(newHeight);
        info.setWidth(newWidth);
    }

    public void cut(int x1, int x2, int y1, int y2) {
        int positiveHeight = info.getPositiveHeight();
        int lostBytes = info.getBitsPerPixel() *
                (positiveHeight * info.getWidth() - (x2 - x1 + 1) * (y2 - y1 + 1)) / 8;

        if (isReversedRows()) {
            int newX1 = positiveHeight - x2 - 1;
            int newX2 = positiveHeight - x1 - 1;
            x1 = newX1;
            x2 = newX2;
        }

        fileHeader.setWholeSize(fileHeader.getWholeSize() - lostBytes);
        info.setWidth(y2 - y1 + 1);
        info.setHeight(x2 - x1 + 1);
        pixels.cut(x1, x2, y1, y2);
    }

    @Override
    public void writeToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(fileHeader.toByteArray());
        fos.write(info.toByteArray());
        fos.write(pixels.toByteArray());
        fos.close();
    }
}
