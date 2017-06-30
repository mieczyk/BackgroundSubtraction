package pl.vilya.bs.core;

import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class VideoFrame {
    protected final Mat _mat;

    public VideoFrame(Mat mat) {
        _mat = mat;
    }

    public Image toImage() {
        BufferedImage resultImage = new BufferedImage(
                _mat.cols(),
                _mat.rows(),
                _mat.channels() > 1
                        ? BufferedImage.TYPE_3BYTE_BGR
                        : BufferedImage.TYPE_BYTE_GRAY
        );

        copyDataToBufferedImage(resultImage);

        return resultImage;
    }

    private void copyDataToBufferedImage(BufferedImage image) {
        int bufferSize = _mat.channels() * _mat.cols() * _mat.rows();
        byte[] buffer = new byte[bufferSize];
        _mat.get(0, 0, buffer);

        final byte[] targetPixels = ((DataBufferByte)image
                .getRaster()
                .getDataBuffer())
                .getData();

        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
    }
}
