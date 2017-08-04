package pl.vilya.bs.viewmodels;

import java.awt.*;

public class VideoInfo {
    private final Image _inputFrameImage;
    private final Image _maskFrameImage;

    public VideoInfo(Image inputFrameImage, Image maskFrameImage) {
        _inputFrameImage = inputFrameImage;
        _maskFrameImage = maskFrameImage;
    }

    public Image getInputFrameImage() {
        return _inputFrameImage;
    }

    public Image getMaskFrameImage() {
        return _maskFrameImage;
    }
}
