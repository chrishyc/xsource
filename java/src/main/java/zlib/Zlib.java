package zlib;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.*;

public class Zlib {
    @Test
    public void test() throws UnsupportedEncodingException, DataFormatException {
        // Encode a String into bytes
        String inputString = "blahblahblah";
        byte[] input = inputString.getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[100];
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        int compressedDataLength = compresser.deflate(output);
        compresser.end();
        
        // Decompress the bytes
        Inflater decompresser = new Inflater();
        decompresser.setInput(output, 0, compressedDataLength);
        byte[] result = new byte[100];
        int resultLength = decompresser.inflate(result);
        decompresser.end();
        
        // Decode the bytes into a String
        String outputString = new String(result, 0, resultLength, "UTF-8");
    }
    
    @Test
    public void testZipOutput() throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("compression.zip"));
        InputStream input1 = new FileInputStream("/Users/chris/xsource/java/src/main/java/zlib/Zlib.java");
        InputStream input2 = new FileInputStream("/Users/chris/xsource/java/src/main/java/zlib/Zlib.java");
        try {
            Arrays.asList(input1, input2)
                    .forEach(input -> {
                        ZipEntry zipEntry = new ZipEntry(Long.toString(System.currentTimeMillis()) + ".java");
                        try {
                            zipOut.putNextEntry(zipEntry);
                            byte[] out = new byte[input.available()];
                            input.read(out);
                            zipOut.write(out);
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } finally {
            zipOut.close();
        }
        
    }
}
