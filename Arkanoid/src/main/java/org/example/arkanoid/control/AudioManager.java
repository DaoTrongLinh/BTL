package org.example.arkanoid.control;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

/**
 * Lớp chuyên dụng để quản lý tất cả Âm thanh trong game.
 */
public class AudioManager {

    private MediaPlayer backgroundMusicPlayer;

    public AudioManager() {
        initMusic();
    }

    /**
     * Tải tệp nhạc nền và thiết lập MediaPlayer.
     */
    private void initMusic() {
        try {
            String musicFile = "/Music/BrainRotRap.mp3"; //
            URL resource = getClass().getResource(musicFile);

            if (resource == null) {
                System.err.println("Không tìm thấy tệp nhạc: " + musicFile);
                return;
            }

            Media media = new Media(resource.toString());
            backgroundMusicPlayer = new MediaPlayer(media);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp lại vô hạn
            backgroundMusicPlayer.setVolume(0.5); // Đặt âm lượng

        } catch (Exception e) {
            System.err.println("Lỗi khi tải nhạc: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Bắt đầu phát nhạc nền.
     */
    public void playBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    /**
     * Dừng phát nhạc nền.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    /**
     *
     * @param volume gía trị từ 0.0-1.0
     */
    public void setVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(volume);
        }
    }

    /**
     *
     * @return âm lượng hiện tại
     */
    public double getVolume() {
        if (backgroundMusicPlayer != null) {
            return backgroundMusicPlayer.getVolume();
        }
        return 0.5; // Trả về giá trị mặc định ban đầu
    }
}