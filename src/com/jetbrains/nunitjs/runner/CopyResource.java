package com.jetbrains.nunitjs.runner;

import javax.tools.JavaCompiler;
import java.io.*;
import java.net.URL;

public class CopyResource {

    /**
     * Writes an embedded resource to a local file by the resource name.
     *
     * @param resourcePath an embedded resource name
     * @param outPath   a file we write the resource to
     * @return true when write was successful and false otherwise
     */
    public boolean writeEmbeddedResourceToLocalFile(final String resourcePath, final String outPath) {
        File configFile = new File(outPath);

        boolean result = false;

        InputStream inputStream = getClass().getResourceAsStream(resourcePath);

        // 1Kb buffer
        byte[] buffer = new byte[1024];
        int byteCount = 0;

        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(configFile);

            while ((byteCount = inputStream.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, byteCount);
            }

            // report success
            result = true;
        } catch (final IOException e) {
            System.out.println("Failure on saving the embedded resource " + resourcePath + " to the file " + configFile.getAbsolutePath());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    System.out.println("Problem closing an input stream while reading data from the embedded resource " + resourcePath);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (final IOException e) {
                    System.out.println("Problem closing the output stream while writing the file " + configFile.getAbsolutePath());
                }
            }
        }

        return result;
    }

}
