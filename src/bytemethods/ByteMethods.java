package bytemethods;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteMethods {
    private static final ByteBuffer bufferLong = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
    private static final ByteBuffer bufferInt = ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN);
    private static final ByteBuffer bufferShort = ByteBuffer.allocate(Short.BYTES).order(ByteOrder.LITTLE_ENDIAN);
    private static final StringBuilder byteSB = new StringBuilder(8);

    static {
        byteSB.setLength(8);
    }

    public static byte[] shortToByteArray(short i) {
        bufferShort.putShort(0, i);
        return bufferShort.array();
    }

    public static byte[] intToByteArray(int i) {
        bufferInt.putInt(0, i);
        return bufferInt.array();
    }

    public static byte[] longToByteArray(long i) {
        bufferLong.putLong(0, i);
        return bufferLong.array();
    }

    public static short byteArrayToShort(byte[] bytes) {
        bufferShort.put(bytes, 0, bytes.length);
        bufferShort.flip();
        return bufferShort.getShort();
    }

    public static int byteArrayToInt(byte[] bytes) {
        bufferInt.put(bytes, 0, bytes.length);
        bufferInt.flip();
        return bufferInt.getInt();
    }

    public static long byteArrayToLong(byte[] bytes) {
        bufferLong.put(bytes, 0, bytes.length);
        bufferLong.flip();
        return bufferLong.getLong();
    }

    public static String byteToString(byte b) {
        for (int i = 7; i >= 0; --i) {
            char ch = '0';
            if ((b & (1 << i)) != 0) {
                ch = '1';
            }
            byteSB.setCharAt(7 - i, ch);
        }
        return byteSB.toString();
    }
}
