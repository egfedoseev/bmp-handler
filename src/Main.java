import bmp.BitMap;
import bmp.BitMapParser;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final String USAGE = """
            USAGE:
            Input file: fileIn
            Output file: fileOut
            Operation: cut-rotate
            Borders: x1, x2, y1, y2 (works only with cut)
            """;

    private static void cut(BitMap bmp, String[] args) {
        if (args.length < 7) {
            return;
        }
        int y1 = Integer.parseInt(args[3]);
        int x1 = Integer.parseInt(args[4]);
        int y2 = Integer.parseInt(args[5]);
        int x2 = Integer.parseInt(args[6]);
        bmp.cut(x1, x1 + x2 - 1, y1, y1 + y2 - 1);
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println(USAGE);
            return;
        }

        File input = new File(args[0]);
        File output = new File(args[1]);
        try {
            output.createNewFile();
        } catch (IOException e) {
            System.err.println("Can't create new file\nIOException: " + e.getMessage());
            return;
        }

        BitMapParser parser = new BitMapParser();
        BitMap bmp;
        try {
            bmp = parser.parse(input.toPath());
        } catch (IOException e) {
            System.err.println("Can't access file " + input + "\nIOException: " + e.getMessage());
            return;
        }

        if (args[2].equals("cut-rotate")) {
            cut(bmp, args);
            bmp.rotate();
        } else {
            System.err.println("Incorrect operation\n" + USAGE);
            return;
        }

        try {
            bmp.writeToFile(output);
        } catch (IOException e) {
            System.err.println("Can't write to file\nIOException: " + e.getMessage());
        }
    }
}
