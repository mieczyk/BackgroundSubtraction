package pl.vilya.bs.views;

import javax.swing.*;
import java.awt.*;

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
                    _frameImage,
                    getWidth(),
                    getHeight(),
                    g
            );
        }
    }

    private static void drawScaledImage(
            Image img,
            int targetWidth,
            int targetHeight,
            Graphics g

    ) {
        int currentWidth = img.getWidth(null);
        int currentHeight = img.getHeight(null);

        float ratio = Math.min(
                (float) targetWidth / currentWidth,
                (float) targetHeight / currentHeight
        );

        int width = (int)(currentWidth * ratio);
        int height = (int)(currentHeight * ratio);

        int x = 0, y = 0;

        if(targetWidth > width) {
            x = (targetWidth - width) / 2;
        }

        if(targetHeight > height) {
            y = (targetHeight - height) / 2;
        }

        g.drawImage(img, x, y, width, height, null);
    }
}
