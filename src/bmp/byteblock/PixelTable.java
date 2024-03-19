package bmp.byteblock;

public class PixelTable extends FileByteBlock {
    private int width;

    private int height;

    public PixelTable(Field[] data, int height, int width) {
        super(data);
        this.height = height;
        this.width = width;
    }

    public byte[] get(int row, int col) {
        return getField(row * width + col);
    }

    public void rotate() {
        Field[] newPixels = new Field[width * height];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                newPixels[j * height + height - i - 1] = (Field) data[i * height + width];
            }
        }
        data = newPixels;
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
