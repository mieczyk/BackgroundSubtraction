package pl.vilya.bs.core.subtractors;

import org.opencv.video.BackgroundSubtractor;

public abstract class BackgroundSubtractorConfig<T extends BackgroundSubtractor> {
    public abstract void apply(T subtractor);
}
