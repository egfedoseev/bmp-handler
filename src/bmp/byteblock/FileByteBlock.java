package bmp.byteblock;

public abstract class FileByteBlock extends ByteBlock {
    public FileByteBlock(ToByteArray[] data) {
        this.data = data;
    }

    protected void setField(int idx, Field field) {
        data[idx] = field;
    }

    protected byte[] getField(int idx) {
        return data[idx].toByteArray();
    }
}