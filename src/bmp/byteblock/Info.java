package bmp.byteblock;

import bytemethods.ByteMethods;

import java.util.Arrays;

public class Info extends FileByteBlock {
    public Info(Field[] data) {
        super(data);
    }

    public void setWidth(int width) {
        System.out.println("new width: " + width + " " + Arrays.toString(ByteMethods.intToByteArray(width)));
        setField(1, new Field(ByteMethods.intToByteArray(width)));

        System.out.println(getWidth());
    }

    public void setHeight(int height) {
        System.out.println("new height: " + height + " " + Arrays.toString(ByteMethods.intToByteArray(height)));
        setField(2, new Field(ByteMethods.intToByteArray(height)));

        System.out.println(getHeight());
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
