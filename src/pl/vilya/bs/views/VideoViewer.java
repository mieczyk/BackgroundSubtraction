package pl.vilya.bs.views;

import pl.vilya.bs.core.VideoFrame;

import javax.swing.*;
import java.awt.*;

public class VideoViewer extends JLabel {
    private VideoFrame _frame = null;

    public void setFrame(VideoFrame frame) {
        _frame = frame;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(_frame != null) {
            int width = getWidth();
            int height = getHeight();

            Image scaledFrame = _frame.resizeAndKeepAspectRatio(width, height).toImage();

            g.drawImage(scaledFrame, 0, 0, null);
        }
    }
}
