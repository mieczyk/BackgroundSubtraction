package pl.vilya.bs.presenters;

import pl.vilya.bs.core.VideoFileStream;
import pl.vilya.bs.core.VideoStream;
import pl.vilya.bs.views.MainWindow;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class MainPresenter {
    private final MainWindow _view;
    private File _lastSelectedDirectory = null;
    private VideoStream _video = null;
    private FramesProcessor _processor = null;

    public MainPresenter(MainWindow view) {
        _view = view;
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

        // Display the first frame
        _view.displayVideoFrame(_video.getFrame());
    }

    public void startVideoProcessing() {
        if(_video == null) {
            _view.showErrorMessage("No video file opened!");
            return;
        }

        if(_processor == null) {
            _processor = new FramesProcessor(_view, _video);
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

                _processor = new FramesProcessor(_view, _video);
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
}
