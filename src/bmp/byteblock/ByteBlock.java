package bmp.byteblock;

public class ByteBlock implements ToByteArray {
    protected ToByteArray[] data;

    @Override
    public int size() {
        int ret = 0;
        for (ToByteArray block : data) {
            ret += block.size();
        }
        return ret;
    }

    @Override
    public byte[] toByteArray() {
        byte[] ret = new byte[size()];
        int cur = 0;
        for (ToByteArray block : data) {
            byte[] bytes = block.toByteArray();
            for (byte b : bytes) {
                ret[cur++] = b;
            }
        }
        return ret;
    }
}
