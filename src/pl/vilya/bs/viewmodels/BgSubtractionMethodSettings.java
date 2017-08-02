package pl.vilya.bs.viewmodels;

import pl.vilya.bs.core.subtractors.BackgroundSubtractorConfig;

public class BgSubtractionMethodSettings<T extends BackgroundSubtractorConfig> {
    private T _config;
    private double _learningRate;

    public BgSubtractionMethodSettings(T config, double learningRate) {
        _config = config;
        _learningRate = learningRate;
    }

    public T getConfig() {
        return _config;
    }

    public double getLearningRate() {
        return _learningRate;
    }

    public void setLearningRate(double learningRate) {
        _learningRate = learningRate;
    }
}
