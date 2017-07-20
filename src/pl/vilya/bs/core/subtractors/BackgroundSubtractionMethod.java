package pl.vilya.bs.core.subtractors;

import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG2;
import pl.vilya.bs.core.VideoFrame;

public class BackgroundSubtractionMethod {
    private BackgroundSubtractor _subtractor;
    private BackgroundSubtractorConfig _config;

    public BackgroundSubtractionMethod(BackgroundSubtractor subtractor) {
        _subtractor = subtractor;
        _config = new BackgroundSubtractorMog2Config((BackgroundSubtractorMOG2)subtractor);
    }

    public BackgroundSubtractionMethod(
            BackgroundSubtractor subtractor,
            BackgroundSubtractorConfig config
    ) {
        _subtractor = subtractor;
        _config = config;

        _config.apply(subtractor);
    }

    public VideoFrame apply(VideoFrame frame) {
        Mat mask = new Mat();

        _subtractor.apply(frame.getMat(), mask, _config.getLearningRate());

        return new VideoFrame(mask);
    }
}
