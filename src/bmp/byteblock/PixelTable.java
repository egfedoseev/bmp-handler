package bmp.byteblock;

import java.util.Arrays;

public class PixelTable extends FileByteBlock {
    private int width;
    private int height;
    private int bytesPerPixel;

    public static class PixelRow extends FileByteBlock {
        public PixelRow(Field[] data) {
            super(data);
        }

        @Override
        public int size() {
            int ret = super.size();
            if (ret % 4 != 0) {
                ret = (ret / 4 + 1) * 4;
            }
            return ret;
        }

        @Override
        public byte[] toByteArray() {
            byte[] bytes = super.toByteArray();
            byte[] ret = bytes;
            if (bytes.length % 4 != 0) {
                ret = Arrays.copyOf(bytes, (bytes.length / 4 + 1) * 4);
            }
            return ret;
        }
    }

    public PixelTable(PixelRow[] data, int height, int width) {
        super(data);
        this.height = height;
        this.width = width;
    }

    public byte[] get(int row, int col) {
        return Arrays.copyOfRange(getField(row), bytesPerPixel * col, bytesPerPixel * (col + 1));
    }

    public void rotate() {
        PixelRow[] newRows = new PixelRow[width];

        for (int i = 0; i < width; ++i) {
            newRows[i] = new PixelRow(new Field[height]);
        }
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                newRows[width - j - 1].setField(i, new Field(((PixelRow) data[i]).getField(j)));
            }
        }

        data = newRows;
        width = height;
        height = newRows.length;

        System.out.println("pixel height: " +  height + '\n' +
                "pixel width: " + width);
    }

    public void cut(int x1, int x2, int y1, int y2) {
        Field[] newPixels = new Field[(x2 - x1 + 1) * (y2 - y1 + 1)];
        for (int i = x1; i <= x2; ++i) {
            for (int j = y1; j <= y2; ++j) {
                newPixels[(i - x1) * (y2 - y1 + 1) + j - y1] = (Field) data[i * height + width];
            }
        }
        data = newPixels;
        height = x2 - x1;
        width = y2 - y1;
    }
}
