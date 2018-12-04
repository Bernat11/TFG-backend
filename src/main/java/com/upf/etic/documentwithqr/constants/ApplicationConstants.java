package com.upf.etic.documentwithqr.constants;

import java.io.File;

public class ApplicationConstants {

    public static final int WIDTH = 350;
    public static final int HEIGHT = 350;

    public static final String IMAGE_FORMAT = "PNG";

    public static final String FOLDER_SEPARATOR = File.separator;

    public static final String TEMPORARY_DIRECTORY = System.getProperty("java.io.tmpdir") + "QR-transformations"
            + FOLDER_SEPARATOR;

    private ApplicationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
