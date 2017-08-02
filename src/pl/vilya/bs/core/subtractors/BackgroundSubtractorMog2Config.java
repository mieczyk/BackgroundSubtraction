package pl.vilya.bs.core.subtractors;

import org.opencv.video.BackgroundSubtractorMOG2;

/**
 * Configuration parameters for the cv::BackgroundSubtractorMOG2 algorithm.
 */
public class BackgroundSubtractorMog2Config extends BackgroundSubtractorConfig<BackgroundSubtractorMOG2> {

    /**
     * The number of last frames that affects the background model.
     */
    private int _history;

    public int getHistory() {
        return _history;
    }

    public void setHistory(int history) {
        _history = history;
    }

    /**
     * Threshold for the squared Mahalanobis distance that helps decide
     * when a sample is close to the existing components. If it is not close to any component,
     * a new component is generated. In other words, it decides if the sample is well described
     * by the background model or not.
     */
    private double _threshold;

    public double getThreshold() {
        return _threshold;
    }

    public void setThreshold(double threshold) {
        _threshold = threshold;
    }

    /**
     *  Maximum allowed number of mixture components.
     *  In other words, The number of Gaussian components in the background model.
     */
    private int _mixtures;

    public int getMixtures() {
        return _mixtures;
    }

    public void setMixtures(int mixtures) {
        _mixtures = mixtures;
    }

    /**
     * Threshold defining whether the component is significant
     * enough to be included into the background model.
     */
    private double _backgroundRatio;

    public double getBackgroundRatio() {
        return _backgroundRatio;
    }

    public void setBackgroundRatio(double backgroundRatio) {
        _backgroundRatio = backgroundRatio;
    }

    /**
     * Initial variance for the newly generated components.
     * It affects the speed of adaptation. The parameter value is based on your
     * estimate of the typical standard deviation from the images.
     */
    private double _initialVariance;

    public double getInitialVariance() {
        return  _initialVariance;
    }

    public void setInitialVariance(double initialVariance) {
        _initialVariance = initialVariance;
    }

    private double _maxVariance;

    public double getMaxVariance() {
        return  _maxVariance;
    }

    public void setMaxVariance(double maxVariance) {
        _maxVariance = maxVariance;
    }

    private double _minVariance;

    public double getMinVariance() {
        return  _minVariance;
    }

    public void setMinVariance(double minVariance) {
        _minVariance = minVariance;
    }

    /**
     * This parameter defines the number of samples needed
     * to accept to prove the component exists.
     */
    private double _complexityReduction;

    public double getComplexityReduction() {
        return _complexityReduction;
    }

    public void setComplexityReduction(double complexityReduction) {
        _complexityReduction = complexityReduction;
    }

    /**
     *  Parameter defining whether shadow detection should be enabled.
     */
    private boolean _detectShadow;

    public boolean getDetectShadow() {
        return  _detectShadow;
    }

    public void setDetectShadow(boolean detectShadow) {
        _detectShadow = detectShadow;
    }

    /**
     * The value for marking shadow pixels in the output foreground mask.
     * Value 0 always means background, 255 means foreground.
     */
    private int _shadowValue;

    public int getShadowValue() {
        return _shadowValue;
    }

    public void setShadowValue(int shadowValue) {
        _shadowValue = shadowValue;
    }

    /**
     *  A shadow is detected if pixel is a darker version of the background.
     *  It is a threshold defining how much darker the shadow can be.
     *  Value 0.5 means that if a pixel is more than twice darker then it's not a shadow.
     */
    private double _shadowThreshold;

    public double getShadowThreshold() {
        return _shadowThreshold;
    }

    public void setShadowThreshold(double shadowThreshold) {
        _shadowThreshold = shadowThreshold;
    }

    public BackgroundSubtractorMog2Config(BackgroundSubtractorMOG2 subtractor) {
        _history = subtractor.getHistory();
        _threshold = subtractor.getVarThreshold();
        _mixtures = subtractor.getNMixtures();
        _backgroundRatio = subtractor.getBackgroundRatio();
        _initialVariance = subtractor.getVarInit();
        _maxVariance = subtractor.getVarMax();
        _minVariance = subtractor.getVarMin();
        _complexityReduction = subtractor.getComplexityReductionThreshold();
        _detectShadow = subtractor.getDetectShadows();
        _shadowValue = subtractor.getShadowValue();
        _shadowThreshold = subtractor.getShadowThreshold();
    }

    public BackgroundSubtractorMog2Config(BackgroundSubtractorMog2Config config) {
        _history = config.getHistory();
        _threshold = config.getThreshold();
        _mixtures = config.getMixtures();
        _backgroundRatio = config.getBackgroundRatio();
        _initialVariance = config.getInitialVariance();
        _maxVariance = config.getMaxVariance();
        _minVariance = config.getMinVariance();
        _complexityReduction = config.getComplexityReduction();
        _detectShadow = config.getDetectShadow();
        _shadowValue = config.getShadowValue();
        _shadowThreshold = config.getShadowThreshold();
    }

    @Override
    public void apply(BackgroundSubtractorMOG2 subtractor) {
        subtractor.setHistory(_history);
        subtractor.setVarThreshold(_threshold);
        subtractor.setNMixtures(_mixtures);
        subtractor.setBackgroundRatio(_backgroundRatio);
        subtractor.setVarInit(_initialVariance);
        subtractor.setVarMax(_maxVariance);
        subtractor.setVarMin(_minVariance);
        subtractor.setComplexityReductionThreshold(_complexityReduction);
        subtractor.setDetectShadows(_detectShadow);
        subtractor.setShadowValue(_shadowValue);
        subtractor.setShadowThreshold(_shadowThreshold);
    }
}
