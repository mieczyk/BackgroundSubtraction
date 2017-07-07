package pl.vilya.bs.core;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

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

    public VideoFrame resizeAndKeepAspectRatio(int targetWidth, int targetHeight) {
        float ratio = Math.min((float)targetWidth / _mat.cols(), (float)targetHeight / _mat.rows());
        int width = (int)(_mat.cols() * ratio);
        int height = (int)(_mat.rows() * ratio);

        int x = 0, y = 0;

        if(targetWidth > width) {
            x = (targetWidth - width) / 2;
        }

        if(targetHeight > height) {
            y = (targetHeight - height) / 2;
        }

        Mat targetMat = Mat.zeros(targetHeight, targetWidth, _mat.type());
        Mat targetRoi = targetMat.submat(new Rect(x, y, width, height));

        Imgproc.resize(_mat, targetRoi, targetRoi.size());

        return new VideoFrame(targetMat);
    }
}
