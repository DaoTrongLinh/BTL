package org.example.arkanoid.control;

/**
 * Một lớp đơn giản để chứa dữ liệu của một người chơi (Tên và Điểm).
 * implements Comparable để chúng ta có thể sắp xếp danh sách.
 */
public class ScoreEntry implements Comparable<ScoreEntry> {

    private String name;
    private int score;

    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        // Sắp xếp theo thứ tự điểm GIẢM DẦN (từ cao xuống thấp)
        return Integer.compare(other.score, this.score);
    }
}