package pl.vilya.bs.core;

import org.opencv.videoio.VideoCapture;

import java.io.IOException;

public class WebcamStream extends VideoStream {
    private final int _device;

    /**
     * Loads web camera by device number (indexed from 0).
     * @param device Installed webcam number.
     */
    public WebcamStream(int device) throws IOException {
        _device = device;
        reopen();
    }

    @Override
    public void reopen() throws IOException {
        _cap = new VideoCapture(_device);

        if(!_cap.isOpened()) {
            throw new IOException("Could not read from the device: " + _device);
        }
    }
}
