package pl.vilya.bs.core.subtractors;

import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;

import java.util.HashMap;
import java.util.Map;

public class SubtractorConfigurationsManager {
    private final Map<Class<? extends BackgroundSubtractor>, BackgroundSubtractorConfig> _configs;

    public SubtractorConfigurationsManager() {
        _configs = new HashMap<>(2);

        _configs.put(
                BackgroundSubtractorMOG2.class,
                new BackgroundSubtractorMog2Config(Video.createBackgroundSubtractorMOG2())
        );
    }

    public void setConfig(
            Class<? extends BackgroundSubtractor> subtractorType,
            BackgroundSubtractorConfig config
    ) {
        _configs.put(subtractorType, config);
    }

    public BackgroundSubtractorConfig getConfig(Class<? extends  BackgroundSubtractor> subtractorType) {
        return _configs.get(subtractorType);
    }
}
