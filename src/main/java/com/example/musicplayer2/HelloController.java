package com.example.musicplayer2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class HelloController implements Initializable {
    private final int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    doublyLinkedList<File>.Node temp;
    private boolean flag = false;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton, shuffleButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    private Media media;
    private MediaPlayer mediaPlayer;
    private File directory;
    private File[] files;
    private doublyLinkedList<File> songs;
    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        songs = new doublyLinkedList<>();
        directory = new File("music");
        files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                songs.insertEnd(file);
            }
        }
        temp = songs.head;
        media = new Media(temp.data.toURI().toString());
        mediaPlayer = new MediaPlayer(media);


        volumeSlider.valueProperty().addListener(new ChangeListener<>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

//        songProgressBar.setStyle("-fx-accent: #00FF00;");
    }

    public void playMedia() {
        if (playButton.getText().equals(">")) {
            mediaPlayer.play();
            beginTimer();
            playButton.setText("||");
        } else {
            mediaPlayer.pause();
            if (running) {
                cancelTimer();
            }
            playButton.setText(">");
        }
        checkSongs();
    }

    public void checkSongs() {
        nextButton.setDisable(temp.next == null);
        previousButton.setDisable(temp.prev == null);
    }

    public void previousMedia() {

        if (temp.prev != null) {
            mediaPlayer.pause();
            if (running) {
                cancelTimer();
            }
            playButton.setText(">");
            temp = temp.prev;
            checkSongs();
            media = new Media(temp.data.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            beginTimer();
            playButton.setText("||");

        }
    }

    public void nextMedia() {

        if (temp.next != null) {
            mediaPlayer.pause();
            if (running) {
                cancelTimer();
            }
            playButton.setText(">");
            temp = temp.next;
            checkSongs();
            media = new Media(temp.data.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            beginTimer();
            playButton.setText("||");
        }
    }

    public void shuffle() {
        if (!flag) {
            System.out.println(shuffleButton.isArmed());
            doublyLinkedList<File> shuffleSongs = songs;
            doublyLinkedList<File>.Node new_temp = shuffleSongs.head;
            int randomX = (int) (Math.random() * 10 + 1);

            while (randomX-- > 0 && new_temp.next != null) {
                new_temp = new_temp.next;
            }

            new_temp.prev.next = new_temp.next;

            if (new_temp.next != null) {
                new_temp.next.prev = new_temp.prev;
            }

            new_temp.next = shuffleSongs.head;
            new_temp.prev = null;

            shuffleSongs.head = new_temp;

            mediaPlayer.pause();
            if (running) {
                cancelTimer();
            }
            playButton.setText(">");
            media = new Media(new_temp.data.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            playMedia();
            flag = true;
        } else {
            temp = songs.head;
            media = new Media(temp.data.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            flag = false;
        }
    }

    public void beginTimer() {

        timer = new Timer();

        task = new TimerTask() {

            public void run() {

                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current / end);

                if (current / end == 1) {

                    cancelTimer();
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 100);
    }

    public void cancelTimer() {

        running = false;
        timer.cancel();
    }

}