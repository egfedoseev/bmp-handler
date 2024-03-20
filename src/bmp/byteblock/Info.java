package bmp.byteblock;

import bytemethods.ByteMethods;

public class Info extends FileByteBlock {
    public Info(Field[] data) {
        super(data);
    }

    public void setWidth(int width) {
        setField(1, new Field(ByteMethods.intToByteArray(width)));
    }

    public void setHeight(int height) {
        int trueHeight = getTrueHeight();
        if (trueHeight / Math.abs(trueHeight) != height / Math.abs(height)) {
            height *= -1;
        }
        setField(2, new Field(ByteMethods.intToByteArray(height)));
    }

    public int getWidth() {
        return ByteMethods.byteArrayToInt(getField(1));
    }

    public int getTrueHeight() {
        return ByteMethods.byteArrayToInt(getField(2));
    }

    public int getPositiveHeight() {
        return Math.abs(ByteMethods.byteArrayToInt(getField(2)));
    }

    public short getBitsPerPixel() {
        return ByteMethods.byteArrayToShort(getField(4));
    }
}
