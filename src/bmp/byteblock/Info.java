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
        setField(2, new Field(ByteMethods.intToByteArray(height)));
    }

    public int getWidth() {
        return ByteMethods.byteArrayToInt(getField(1));
    }

    public int getHeight() {
        return ByteMethods.byteArrayToInt(getField(2));
    }

    public short getBitsPerPixel() {
        return ByteMethods.byteArrayToShort(getField(4));
    }
}
