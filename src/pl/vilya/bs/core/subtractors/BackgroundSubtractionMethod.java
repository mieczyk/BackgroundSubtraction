package pl.vilya.bs.core.subtractors;

import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorKNN;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import pl.vilya.bs.core.VideoFrame;

import java.util.HashMap;
import java.util.Map;

public class BackgroundSubtractionMethod {
    private final static Object _lockObject = new Object();

    private BackgroundSubtractor _subtractor;

    /**
     * The value between 0 and 1 that indicates how fast the background model is learnt.
     * Negative parameter value makes the algorithm to use some automatically chosen learning rate.
     * 0 means that the background model is not updated at all,
     * 1 means that the background model is completely reinitialized from the last frame.
     */
    private double _learningRate;

    public BackgroundSubtractionMethod() {
        _learningRate = -1;
        _subtractor = Video.createBackgroundSubtractorMOG2();
    }

    public double getLearningRate() {
        return _learningRate;
    }

    public void setLearningRate(double learningRate) {
        synchronized (_lockObject) {
            _learningRate = learningRate;
        }
    }

    public void setSubtractor(BackgroundSubtractor subtractor, BackgroundSubtractorConfig config) {
        synchronized (_lockObject) {
            _subtractor = subtractor;
            config.apply(_subtractor);
        }
    }

    public BackgroundSubtractor getSubtractor() {
        return _subtractor;
    }

    public VideoFrame apply(VideoFrame frame) {
        Mat mask = new Mat();

        synchronized (_lockObject) {
            _subtractor.apply(frame.getMat(), mask, _learningRate);
        }

        return new VideoFrame(mask);
    }

    public static BackgroundSubtractor createSubtractor(Class<? extends BackgroundSubtractor> subtractorType) {
        if(subtractorType == BackgroundSubtractorMOG2.class) {
            return Video.createBackgroundSubtractorMOG2();
        }

        return null;
    }
}
