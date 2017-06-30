package pl.vilya.bs.core;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.IOException;

public class VideoStream {
    protected VideoCapture _cap;

    /**
     * Loads video files or RTSP/HTTP stream.
     * @param source Video file name or RTSP/HTTP stream URL.
     */
    public VideoStream(String source) throws IOException {
        _cap = new VideoCapture(source);

        if(!_cap.isOpened()) {
            throw new IOException("Could not open the stream: " + source);
        }
    }

    /**
     * Loads web camera by device number (indexed from 0).
     * @param device Installed webcam number.
     */
    public VideoStream(int device) throws IOException {
        _cap = new VideoCapture(device);

        if(!_cap.isOpened()) {
            throw new IOException("Could not read from the device: " + device);
        }
    }

    public VideoFrame getFrame() {
        Mat frame = new Mat();

        if(!_cap.read(frame)) {
            _cap.release();

            return null;
        }

        return new VideoFrame(frame);
    }

    public double getFps() {
        return _cap.get(Videoio.CAP_PROP_FPS);
    }
}