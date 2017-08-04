package pl.vilya.bs.core;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.IOException;

public abstract class VideoStream {
    protected VideoCapture _cap;

    public VideoFrame getFrame() {
        Mat frame = new Mat();

        if(!_cap.read(frame)) {
            _cap.release();

            return null;
        }

        return new VideoFrame(frame);
    }

    public int getFps() {
        return (int)_cap.get(Videoio.CAP_PROP_FPS);
    }

    public int getFrameNumber() {
        return (int)_cap.get(Videoio.CAP_PROP_POS_FRAMES);
    }

    public abstract void reopen() throws IOException;
}