package bytemethods;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ByteMethods {
    private static final ByteBuffer bufferLong = ByteBuffer.allocate(Long.BYTES).order(ByteOrder.LITTLE_ENDIAN);
    private static final ByteBuffer bufferInt = ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN);
    private static final ByteBuffer bufferShort = ByteBuffer.allocate(Short.BYTES).order(ByteOrder.LITTLE_ENDIAN);
    private static final StringBuilder byteSB = new StringBuilder(8);

    static {
        byteSB.setLength(8);
    }

    public static byte[] shortToByteArray(short i) {
        bufferShort.clear().putShort(0, i);
        return Arrays.copyOf(bufferShort.array(), bufferShort.array().length);
    }

    public static byte[] intToByteArray(int i) {
        bufferInt.clear().putInt(0, i);
        return Arrays.copyOf(bufferInt.array(), bufferInt.array().length);
    }

    public static byte[] longToByteArray(long i) {
        bufferLong.clear().putLong(0, i);
        return Arrays.copyOf(bufferLong.array(), bufferLong.array().length);
    }

    public static short byteArrayToShort(byte[] bytes) {
        bufferShort.clear().put(bytes, 0, bytes.length);
        bufferShort.flip();
        return bufferShort.getShort();
    }

    public static int byteArrayToInt(byte[] bytes) {
        bufferInt.clear().put(bytes, 0, bytes.length);
        bufferInt.flip();
        return bufferInt.getInt();
    }

    public static long byteArrayToLong(byte[] bytes) {
        bufferLong.clear().put(bytes, 0, bytes.length);
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
