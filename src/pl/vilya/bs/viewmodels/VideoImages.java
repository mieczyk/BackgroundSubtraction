package pl.vilya.bs.viewmodels;

import java.awt.*;

public class VideoImages {
    private final Image _inputFrameImage;
    private final Image _maskFrameImage;

    public VideoImages(Image inputFrameImage, Image maskFrameImage) {
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
