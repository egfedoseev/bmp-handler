package bmp.byteblock;

import java.util.Arrays;

public class PixelTable extends FileByteBlock {
    private int width;
    private int height;
    private final int bytesPerPixel;

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

        private static PixelRow[] getEmptyRows(int height, int width) {
            PixelRow[] rows = new PixelRow[height];
            for (int i = 0; i < height; ++i) {
                rows[i] = new PixelRow(new Field[width]);
            }
            return rows;
        }
    }

    public PixelTable(PixelRow[] data, int height, int width, int bytesPerPixel) {
        super(data);
        this.height = height;
        this.width = width;
        this.bytesPerPixel = bytesPerPixel;
    }

    public byte[] get(int row, int col) {
        return Arrays.copyOfRange(getField(row), bytesPerPixel * col, bytesPerPixel * (col + 1));
    }

    public void rotate() {
        PixelRow[] newRows = PixelRow.getEmptyRows(width, height);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                newRows[width - j - 1].setField(i, new Field(((PixelRow) data[i]).getField(j)));
            }
        }
        data = newRows;
        width = newRows[0].data.length;
        height = newRows.length;
    }

    public void cut(int x1, int x2, int y1, int y2) {
        PixelRow[] newRows = PixelRow.getEmptyRows(x2 - x1 + 1, y2 - y1 + 1);
        for (int i = x1; i <= x2; ++i) {
            for (int j = y1; j <= y2; ++j) {
//                newRows[(i - x1) * (y2 - y1 + 1) + j - y1] = (PixelRow) data[i * height + width];
                System.out.println(i + " " + (i - x1) + ' ' + data.length);
                newRows[i - x1].setField(j - y1, new Field(((PixelRow) data[i]).getField(j)));
            }
        }
        data = newRows;
        height = x2 - x1 + 1;
        width = y2 - y1 + 1;
    }
}
