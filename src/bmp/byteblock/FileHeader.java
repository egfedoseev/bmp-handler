package bmp.byteblock;

import bytemethods.ByteMethods;

public class FileHeader extends FileByteBlock {
    public FileHeader(Field[] data) {
        super(data);
    }

    public void setWholeSize(int size) {
        setField(1, new Field(ByteMethods.intToByteArray(size)));
    }

    public int getWholeSize() {
        return ByteMethods.byteArrayToInt(getField(1));
    }
}
