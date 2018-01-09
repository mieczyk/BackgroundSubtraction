package pl.vilya.bs.presenters;

import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import pl.vilya.bs.core.VideoFileStream;
import pl.vilya.bs.core.VideoFrame;
import pl.vilya.bs.core.VideoStream;
import pl.vilya.bs.core.subtractors.BackgroundSubtractionMethod;
import pl.vilya.bs.core.subtractors.BackgroundSubtractorMog2Config;
import pl.vilya.bs.core.subtractors.SubtractorConfigurationsManager;
import pl.vilya.bs.viewmodels.BgSubtractionMethodSettings;
import pl.vilya.bs.viewmodels.VideoInfo;
import pl.vilya.bs.views.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainPresenter {
    private final MainWindow _view;
    private File _lastSelectedDirectory = null;
    private VideoStream _video = null;
    private FramesProcessor _processor = null;
    private final SubtractorConfigurationsManager _configsManager;
    private final BackgroundSubtractionMethod _bgSubtractionMethod;

    public MainPresenter(MainWindow view) {
        _view = view;
        _configsManager = new SubtractorConfigurationsManager();
        _bgSubtractionMethod = new BackgroundSubtractionMethod();
    }

    public void openVideoFile() {
        stopVideoProcessing();

        File videoFile = _view.getVideoFile(_lastSelectedDirectory);
        if(videoFile == null) {
            return;
        }

        _lastSelectedDirectory = videoFile.getParentFile();

        try {
            _video = new VideoFileStream(videoFile.getAbsolutePath());
        } catch (IOException e) {
            _view.showErrorMessage(e.getMessage());
            return;
        }

        _processor = null;

        // Reset the current subtractor.
        BackgroundSubtractor subtractor = _bgSubtractionMethod.getSubtractor();
        _bgSubtractionMethod.setSubtractor(
                BackgroundSubtractionMethod.createSubtractor(subtractor.getClass()),
                _configsManager.getConfig(subtractor.getClass())
        );

        setButtonsStateForPausedVideo();

        VideoFrame firstFrame = _video.getFrame();
        _view.displayOutput(new VideoInfo(firstFrame.toImage(), null, 0, 0));
    }

    public void startVideoProcessing() {
        if(_video == null) {
            _view.showErrorMessage("No video file has been opened!");
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
                if(reopenVideo()) {
                    _processor = new FramesProcessor(_view, _video , _bgSubtractionMethod);
                    _processor.execute();
                    break;
                } else {
                    return;
                }

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
        BackgroundSubtractorMog2Config config = (BackgroundSubtractorMog2Config)
                _configsManager.getConfig(BackgroundSubtractorMOG2.class);

        BgSubtractionMethodSettings<BackgroundSubtractorMog2Config> settings =
                _view.showBackgroundSubtractorMog2Dialog(config, _bgSubtractionMethod.getLearningRate());

        if(settings != null) {
            _configsManager.setConfig(BackgroundSubtractorMOG2.class ,settings.getConfig());
            _bgSubtractionMethod.setLearningRate(settings.getLearningRate());
            _bgSubtractionMethod.setSubtractor(Video.createBackgroundSubtractorMOG2(), settings.getConfig());

            reopenVideo();
        }
    }

    public void selectBackgroundSubtractorKnn() {
        _view.showBackgroundSubtractorKnnDialog();
        reopenVideo();
    }

    public void showManual() {
        try {
            URL index = getClass().getResource("/help/index.html");
            Desktop.getDesktop().browse(index.toURI());
        } catch (Exception e) {
            _view.showErrorMessage(e.getMessage());
        }
    }

    private boolean reopenVideo() {
        boolean success = false;

        if(_video != null) {
            try {
                _video.reopen();
                success = true;
            } catch (IOException e) {
                _view.showErrorMessage(e.getMessage());
            }
        }

        return success;
    }
}
