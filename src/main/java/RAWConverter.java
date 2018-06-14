import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

public class RAWConverter {

    private int width, height;

    public RAWConverter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean toPng(String inputRawFilename, String outputPngFilename) throws IOException {

        System.out.printf("Converting to png: %s%n", inputRawFilename);

        try {

            int samplesPerPixel = 3;
            int[] bandOffsets = {0, 1, 2}; // RGB order

            byte[] rgbPixelData = getBytesFromFile(new File(inputRawFilename));

            DataBuffer buffer = new DataBufferByte(rgbPixelData, rgbPixelData.length);
            WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, samplesPerPixel * width, samplesPerPixel, bandOffsets, null);

            ColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

            BufferedImage image = new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);

            File outputPNG = new File(outputPngFilename);
            outputPNG.createNewFile();

            ImageIO.write(image, "PNG", outputPNG);

            return true;

        } catch(RasterFormatException rasterException) {

            System.err.printf("Could not create raster for %s: %s%n", inputRawFilename, rasterException.getMessage());

        }

        return false;

    }

    /**
     * Read raw data into a byte array.
     *
     * @param file
     * @return byte array with RAW data
     * @throws IOException
     * @uathor Cuga (https://stackoverflow.com/questions/858980/file-to-byte-in-java)
     */
    public byte[] getBytesFromFile(File file) throws IOException {
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }

}
