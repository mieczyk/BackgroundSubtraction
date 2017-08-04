package pl.vilya.bs.presenters;

import pl.vilya.bs.core.VideoFrame;
import pl.vilya.bs.core.VideoStream;
import pl.vilya.bs.core.subtractors.BackgroundSubtractionMethod;
import pl.vilya.bs.views.MainWindow;
import pl.vilya.bs.viewmodels.VideoInfo;

import javax.swing.*;

public class FramesProcessor extends SwingWorker<Void, VideoInfo> {
    private final MainWindow _view;
    private final VideoStream _video;
    private volatile boolean _paused;
    private final BackgroundSubtractionMethod _bgSubtractionMethod;

    public FramesProcessor(
            MainWindow view,
            VideoStream video,
            BackgroundSubtractionMethod bgSubtractionMethod
    ) {
        _view = view;
        _video = video;
        _bgSubtractionMethod = bgSubtractionMethod;
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

                VideoFrame mask = _bgSubtractionMethod.applyCurrent(frame);
                VideoInfo output = new VideoInfo(frame.toImage(), mask.toImage());

                publish(output);

                frame.release();
                mask.release();

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
    protected void process(java.util.List<VideoInfo> output) {
        output.forEach(_view::displayOutput);
    }

    @Override
    protected void done() {
        _view.enableStartButton(true);
        _view.enableStopButton(false);
    }
}
