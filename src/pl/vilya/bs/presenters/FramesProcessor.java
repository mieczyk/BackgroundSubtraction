package pl.vilya.bs.presenters;

import pl.vilya.bs.core.VideoFrame;
import pl.vilya.bs.core.VideoStream;
import pl.vilya.bs.views.MainWindow;

import javax.swing.*;
import java.awt.*;

public class FramesProcessor extends SwingWorker<Void, Image> {
    private final MainWindow _view;
    private final VideoStream _video;
    private volatile boolean _paused;

    public FramesProcessor(MainWindow view, VideoStream video) {
        _view = view;
        _video = video;
    }

    public void pause() {
        _paused = true;
    }

    public void resume() {
        _paused = false;
    }

    @Override
    protected Void doInBackground() throws Exception {
        VideoFrame frame = _video.getFrame();

        long interval = getIntervalFromFps();

        while (!isCancelled()) {
            if(!_paused && frame != null) {
                publish(frame.toImage());

                frame = _video.getFrame();

                Thread.sleep(interval);
            } else if(frame == null) {
                break;
            }
        }

        return null;
    }

    private long getIntervalFromFps() {
        return 1000 / Math.round(_video.getFps());
    }

    @Override
    protected void process(java.util.List<Image> images) {
        images.forEach(_view::displayVideoImage);
    }

    @Override
    protected void done() {
        _view.enableStartButton(true);
        _view.enableStopButton(false);
    }
}
