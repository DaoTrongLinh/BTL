package org.example.arkanoid.control;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lớp static (không cần tạo đối tượng) để xử lý việc
 * lưu và đọc điểm từ file "ranking.txt".
 */
public class RankingManager {

    // Tên file sẽ được tạo ở thư mục gốc của dự án
    private static final String RANKING_FILE = "ranking.txt";

    /**
     * Lưu một cặp Tên và Điểm vào cuối file ranking.txt.
     */
    public static void saveScore(String name, int score) {
        // Sử dụng try-with-resources để tự động đóng file
        // 'true' nghĩa là "append" (ghi tiếp vào cuối file)
        try (FileWriter fw = new FileWriter(RANKING_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Ghi theo định dạng: Ten,Diem
            out.println(name + "," + score);

        } catch (IOException e) {
            System.err.println("Lỗi khi lưu điểm vào file: " + e.getMessage());
        }
    }

    /**
     * Đọc toàn bộ file ranking.txt, chuyển thành danh sách ScoreEntry
     * và sắp xếp theo thứ tự điểm giảm dần.
     */
    public static List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RANKING_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Tách chuỗi bằng dấu phẩy
                if (parts.length == 2) {
                    try {
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        scores.add(new ScoreEntry(name, score));
                    } catch (NumberFormatException e) {
                        // Bỏ qua dòng nếu điểm không phải là số
                        System.err.println("Bỏ qua dòng lỗi trong file ranking: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Đây không phải là lỗi. File chưa tồn tại (lần chơi đầu tiên).
            // Chỉ cần trả về danh sách rỗng.
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file điểm: " + e.getMessage());
        }

        // Sắp xếp danh sách (dùng hàm compareTo trong ScoreEntry)
        Collections.sort(scores);

        return scores;
    }
}