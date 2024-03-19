package bmp.byteblock;

import java.util.Arrays;

public class Field implements ToByteArray {
    private final byte[] data;

    public Field(byte[] data) {
        this.data = data;
    }

    public int size() {
        return data.length;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(data, data.length);
    }
}
