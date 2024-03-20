import bmp.BitMap;
import bmp.BitMapParser;
import bytemethods.ByteMethods;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        /*byte[] bytes = new byte[]{0x0, 0x0, 0x0, 0x0};
        int x = ByteMethods.byteArrayToInt(bytes);
        System.out.println(x);

        bytes[0] = (byte)(0x1);
        x = ByteMethods.byteArrayToInt(bytes);
        System.out.println(x);*/

        BitMapParser parser = new BitMapParser();
        BitMap bitMap = parser.parse(Paths.get("./tests/t1-01-small-one.bmp"));

        bitMap.rotate();

        File resultFile = new File("./tests/result.bmp");
        resultFile.createNewFile();

        bitMap.writeToFile(resultFile);
    }
}
