import helpers.FileReader;
import huffman.Huffman;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HuffmanTest {

    private final String DELIMITER = "****";
    private final FileReader fileReader = new FileReader();
    private final boolean DEBUG = false;

    @Test
    public void testOneLineFile() {
        testHuffman("oneLine.txt");
    }

    @Test
    public void testTwoLinesFile() {
        testHuffman("twoLines.txt");
    }

    @Test
    public void testSmallText() {
        testHuffman("lorem.txt");
    }

    @Test
    public void testLargeText() {
        testHuffman("leipzig1M.txt");
    }

    @Test
    public void testSpacedText() {
        testHuffman("spaaaace.txt");
    }

    @Test
    public void testEmptyLineBetweenTwoLines() {
        testHuffman("emptyLineBetweenTwoLines.txt");
    }

    private void testHuffman(String originalFile) {
        String originalContent = fileReader.getFile(originalFile);
        Huffman.encode(originalContent, originalFile);

        String[] linesOfCmp = fileReader.getFile(getDefaultAbsolutePath() + originalFile + ".cmp").split(System.lineSeparator());
        int delLine = findLineWithDelimiter(linesOfCmp);
        assertNotEquals(delLine, -1);

        String content = new FileReader().getFile(getDefaultAbsolutePath() + originalFile + ".cmp");
        String decoded = Huffman.decode(content);

        if (DEBUG)
            printOrigAndDecoded(originalContent, decoded);

        assertEquals(originalContent, decoded);
    }

    private void printOrigAndDecoded(String original, String decoded) {
        System.out.println(System.lineSeparator() + "******************" + System.lineSeparator());
        System.out.println(original);
        System.out.println("----");
        System.out.println(decoded);

    }

    private byte[] md5sum(String str){
        try {
            byte[] bytesOfMessage = str.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);

            return thedigest;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private int findLineWithDelimiter(String[] lines) {
        for (int i = 0; i < lines.length; i++)
            if (lines[i].equals(DELIMITER))
                return i + 1;
        return -1;
    }

    private String getDefaultAbsolutePath() {
        String absPath = new File(".").getAbsolutePath(); // a little hack...
        absPath = absPath.substring(0, absPath.length() - 1);
        return absPath;
    }

}