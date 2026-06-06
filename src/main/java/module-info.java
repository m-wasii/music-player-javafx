module com.example.musicplayer2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.musicplayer2 to javafx.fxml;
    exports com.example.musicplayer2;
}