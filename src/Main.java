import bmp.BitMap;
import bmp.BitMapParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        BitMapParser parser = new BitMapParser();
        BitMap bitMap = parser.parse(Paths.get("./tests/t1-01-small-one.bmp"));

        bitMap.rotate();

        File resultFile = new File("./tests/result.bmp");
        resultFile.createNewFile();

        bitMap.writeToFile(resultFile);
    }
}
