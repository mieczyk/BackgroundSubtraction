package pl.vilya.bs;

import org.opencv.core.Core;
import pl.vilya.bs.views.MainWindow;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary("opencv_ffmpeg320_64");

        SwingUtilities.invokeLater(
            () -> new MainWindow("Background Subtraction")
        );
    }
}
