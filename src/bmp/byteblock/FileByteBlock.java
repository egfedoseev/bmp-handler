package bmp.byteblock;

public class FileByteBlock extends ByteBlock {
    public FileByteBlock(Field[] data) {
        this.data = data;
    }

    protected void setField(int idx, Field field) {
        data[idx] = field;
    }

    protected byte[] getField(int idx) {
        return data[idx].toByteArray();
    }
}