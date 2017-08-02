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
    private final Map<Class<? extends BackgroundSubtractor>, BackgroundSubtractor> _subtractors;
    private Class<? extends BackgroundSubtractor> _current;

    /**
     * The value between 0 and 1 that indicates how fast the background model is learnt.
     * Negative parameter value makes the algorithm to use some automatically chosen learning rate.
     * 0 means that the background model is not updated at all,
     * 1 means that the background model is completely reinitialized from the last frame.
     */
    private double _learningRate;

    public BackgroundSubtractionMethod() {
        _subtractors = new HashMap<>(2);
        _subtractors.put(BackgroundSubtractorMOG2.class, Video.createBackgroundSubtractorMOG2());
        _subtractors.put(BackgroundSubtractorKNN.class, Video.createBackgroundSubtractorKNN());

        _current = BackgroundSubtractorMOG2.class;
        _learningRate = -1;
    }

    public double getLearningRate() {
        return _learningRate;
    }

    public void setLearningRate(double learningRate) {
        synchronized (_lockObject) {
            _learningRate = learningRate;
        }
    }

    public BackgroundSubtractor getSubtractor(Class<? extends BackgroundSubtractor> subtractorType) {
        return _subtractors.get(subtractorType);
    }

    public void setCurrent(Class<? extends BackgroundSubtractor> subtractorType, BackgroundSubtractorConfig config) {
        synchronized (_lockObject) {
            _current = subtractorType;
            config.apply(_subtractors.get(_current));
        }
    }

    public VideoFrame applyCurrent(VideoFrame frame) {
        Mat mask = new Mat();

        synchronized (_lockObject) {
            BackgroundSubtractor subtractor = _subtractors.get(_current);
            subtractor.apply(frame.getMat(), mask, _learningRate);
        }

        return new VideoFrame(mask);
    }
}
