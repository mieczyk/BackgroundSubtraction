package pl.vilya.bs.presenters;

import org.opencv.video.BackgroundSubtractorMOG2;
import pl.vilya.bs.core.VideoFileStream;
import pl.vilya.bs.core.VideoFrame;
import pl.vilya.bs.core.VideoStream;
import pl.vilya.bs.core.subtractors.BackgroundSubtractionMethod;
import pl.vilya.bs.core.subtractors.BackgroundSubtractorMog2Config;
import pl.vilya.bs.viewmodels.BgSubtractionMethodSettings;
import pl.vilya.bs.views.MainWindow;
import pl.vilya.bs.viewmodels.VideoInfo;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MainPresenter {
    private final MainWindow _view;
    private File _lastSelectedDirectory = null;
    private VideoStream _video = null;
    private FramesProcessor _processor = null;
    private final BackgroundSubtractionMethod _bgSubtractionMethod;

    public MainPresenter(MainWindow view) {
        _view = view;
        _bgSubtractionMethod = new BackgroundSubtractionMethod();
    }

    public void openVideoFile() {
        stopVideoProcessing();

        File videoFile = _view.getVideoFile(_lastSelectedDirectory);
        if(videoFile == null) {
            return;
        }

        _lastSelectedDirectory = videoFile.getParentFile();
        _processor = null;

        try {
            _video = new VideoFileStream(videoFile.getAbsolutePath());
        } catch (IOException e) {
            _view.showErrorMessage(e.getMessage());
            return;
        }

        setButtonsStateForPausedVideo();

        VideoFrame firstFrame = _video.getFrame();
        _view.displayOutput(new VideoInfo(firstFrame.toImage(), null, 0, 0));
    }

    public void startVideoProcessing() {
        if(_video == null) {
            _view.showErrorMessage("No video file opened!");
            return;
        }

        if(_processor == null) {
            _processor = new FramesProcessor(_view, _video, _bgSubtractionMethod);
        }

        SwingWorker.StateValue state = _processor.getState();

        switch(state) {
            case PENDING:
                _processor.execute();
                break;

            case DONE:
                try {
                    _video.reopen();
                } catch (IOException e) {
                    _view.showErrorMessage(e.getMessage());
                    return;
                }

                _processor = new FramesProcessor(_view, _video , _bgSubtractionMethod);
                _processor.execute();
                break;

            default:
                _processor.resume();
        }

        setButtonsStateForRunningVideo();
    }

    public void stopVideoProcessing() {
        if(_processor != null) {
            _processor.pause();
        }

        setButtonsStateForPausedVideo();
    }

    private void setButtonsStateForPausedVideo() {
        _view.enableStartButton(true);
        _view.enableStopButton(false);
    }

    private void setButtonsStateForRunningVideo() {
        _view.enableStartButton(false);
        _view.enableStopButton(true);
    }

    public void selectBackgroundSubtractorMog2() {
        BackgroundSubtractorMOG2 subtractor = (BackgroundSubtractorMOG2)
                _bgSubtractionMethod.getSubtractor(
                        BackgroundSubtractorMOG2.class
                );

        BackgroundSubtractorMog2Config config = new BackgroundSubtractorMog2Config(subtractor);

        BgSubtractionMethodSettings<BackgroundSubtractorMog2Config> settings =
                _view.showBackgroundSubtractorMog2Dialog(config, _bgSubtractionMethod.getLearningRate());

        if(settings != null) {
            _bgSubtractionMethod.setCurrent(BackgroundSubtractorMOG2.class, settings.getConfig());
            _bgSubtractionMethod.setLearningRate(settings.getLearningRate());
        }
    }
}
