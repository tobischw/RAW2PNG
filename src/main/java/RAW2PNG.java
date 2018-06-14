import com.sun.tools.javac.util.Convert;

import java.io.FileFilter;
import java.io.IOException;
import java.io.File;

public class RAW2PNG {

    public static void main(String [] args) {

        // Get the required arguments to start the program.
        final String inputExtensionForRaw = Arguments.getOptArg("--extension", "-x", args, ".raw");
        final String inputDirectory = Arguments.getOptArg("--directory", "-d", args, "raw");
        final String outputDirectory = Arguments.getOptArg("--output", "-o", args, "png");

        int width = Integer.parseInt(Arguments.getOptArg("--width", "-w", args, "1920"));
        int height = Integer.parseInt(Arguments.getOptArg("--height", "-h", args, "1080"));

        // Needs to be initialized with constant image width and height.
        RAWConverter converter = new RAWConverter(width, height);

        try {

            // Get only .RAW files (or whatever the user decided it ends with).
            File [] files = new File(inputDirectory).listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith(inputExtensionForRaw);
                }
            });

            int numFiles = files.length, numConvertedFiles = 0;

            System.out.printf("Discovered %d images to be converted.%n", numFiles);

            // Iterate and use converter.
            for(File file : files) {
                if(converter.toPng(file.getAbsolutePath(), outputDirectory + "/" + file.getName() + ".png")) {
                    numConvertedFiles++;
                }
            }

            System.out.printf("Finished converting %d images.%n", numConvertedFiles);

        } catch(IOException ioException) {

            System.out.println("Error with writing or reading: " + ioException);

        } catch(Exception exception) {

            System.out.println("Unnown exception: " + exception);

        }

    }

}
