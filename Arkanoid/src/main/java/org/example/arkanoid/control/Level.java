package org.example.arkanoid.control;

import org.example.arkanoid.object.Brick;
import org.example.arkanoid.object.NormalBrick;
import org.example.arkanoid.object.StrongBrick;
import org.example.arkanoid.object.InvisibleBrick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp này chịu trách nhiệm đọc các file .txt từ thư mục resources
 * và chuyển đổi chúng thành một List<Brick>.
 */
public class Level {

    /**
     * Tải một màn chơi từ một file resource.
     *
     * @param resourcePath Đường dẫn đến file level (ví dụ: "/levels/level1.txt")
     * @param screenWidth  Chiều rộng màn chơi (từ GameManager)
     * @param startX       Lề trái
     * @param startY       Lề trên
     * @return Một danh sách các viên gạch cho màn chơi đó.
     */
    public List<Brick> loadLevel(String resourcePath, double screenWidth, double startX, double startY) {
        List<Brick> bricks = new ArrayList<>();
        List<String[]> levelLayout = new ArrayList<>(); // Lưu tạm layout để tính toán

        int numCols = 0;

        // --- Đọc file và lưu layout ---
        try (InputStream is = getClass().getResourceAsStream(resourcePath); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                System.err.println("Không thể tìm thấy file level: " + resourcePath);
                return bricks; // Trả về danh sách rỗng
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Bỏ qua dòng trống

                String[] tokens = line.trim().split("\\s+"); // Tách bằng dấu cách
                if (numCols == 0) {
                    numCols = tokens.length; // Lấy số cột từ dòng đầu tiên
                }
                levelLayout.add(tokens);
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            // Nếu có lỗi (ví dụ: hết level), trả về danh sách rỗng
            return bricks;
        }

        // --- Tính toán kích thước gạch ---
        if (levelLayout.isEmpty() || numCols == 0) {
            return bricks; // File rỗng
        }

        int numRows = levelLayout.size();

        // Trừ đi lề 2 bên và chia đều
        double brickWidth = (screenWidth - (startX * 2)) / numCols;
        double brickHeight = 35; // Chiều cao cố định (có thể thay đổi)

        // --- Tạo các đối tượng Brick ---
        for (int i = 0; i < numRows; i++) {
            String[] row = levelLayout.get(i);
            for (int j = 0; j < row.length; j++) {
                String brickType = row[j];

                if (!brickType.equals("0")) { // "0" là ô trống
                    double brickX = startX + (j * brickWidth);
                    double brickY = startY + (i * brickHeight);

                    switch (brickType) {
                        case "1":
                            bricks.add(new NormalBrick(brickX, brickY, brickWidth, brickHeight));
                            break;
                        case "2":
                            bricks.add(new StrongBrick(brickX, brickY, brickWidth, brickHeight));
                            break;
                        case "3":
                            bricks.add(new InvisibleBrick(brickX, brickY, brickWidth, brickHeight));
                            break;
                        //  có thể thêm các case khác ở đây
                    }
                }
            }
        }

        return bricks;
    }
}