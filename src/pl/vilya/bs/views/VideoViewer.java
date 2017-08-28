package pl.vilya.bs.views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class VideoViewer extends JLabel {
    private Image _frameImage = null;

    public void setFrameImage(Image frameImage) {
        _frameImage = frameImage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(_frameImage != null) {
            drawScaledImage(
                    (BufferedImage)_frameImage,
                    getWidth(),
                    getHeight(),
                    g
            );
        }
    }

    private static void drawScaledImage(
            BufferedImage img,
            int targetWidth,
            int targetHeight,
            Graphics g

    ) {
        float ratio = getRatio(img, targetWidth, targetHeight);
        Point offset = getOffset(img, targetWidth, targetHeight, ratio);

        g.drawImage(
                getScaledImage(img, targetWidth, targetHeight, ratio),
                (int)offset.getX(),
                (int)offset.getY(),
                null
        );
    }

    private static float getRatio(BufferedImage img, int targetWidth, int targetHeight) {
        return Math.min(
                (float) targetWidth / img.getWidth(null),
                (float) targetHeight / img.getHeight(null)
        );
    }

    private static Point getOffset(
            BufferedImage img,
            int targetWidth,
            int targetHeight,
            float ratio
    ) {
        int width = (int)(img.getWidth(null) * ratio);
        int height = (int)(img.getHeight(null) * ratio);

        int x = 0, y = 0;

        if(targetWidth > width) {
            x = (targetWidth - width) / 2;
        }

        if(targetHeight > height) {
            y = (targetHeight - height) / 2;
        }

        return new Point(x, y);
    }

    private static BufferedImage getScaledImage(
            BufferedImage img,
            int targetWidth,
            int targetHeight,
            float ratio
    ) {
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(ratio, ratio);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(
                scaleTransform,
                AffineTransformOp.TYPE_BILINEAR
        );

        BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, img.getType());

        return bilinearScaleOp.filter(img, targetImage);
    }
}
