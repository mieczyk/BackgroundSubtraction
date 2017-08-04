package pl.vilya.bs.viewmodels;

import java.awt.*;

public class VideoInfo {
    private final Image _inputFrameImage;
    private final Image _maskFrameImage;
    private final int _frameNumber;
    private final int _fps;

    public VideoInfo(
            Image inputFrameImage,
            Image maskFrameImage,
            int frameNumber,
            int fps
    ) {
        _inputFrameImage = inputFrameImage;
        _maskFrameImage = maskFrameImage;
        _frameNumber = frameNumber;
        _fps = fps;
    }

    public Image getInputFrameImage() {
        return _inputFrameImage;
    }

    public Image getMaskFrameImage() {
        return _maskFrameImage;
    }

    public int getFrameNumber() {
        return _frameNumber;
    }

    public int getFps() {
        return _fps;
    }
}
