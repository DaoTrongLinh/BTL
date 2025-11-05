package org.example.arkanoid.object;

// Import các thư viện Test
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Đây là lớp Unit Test để kiểm tra logic của các loại gạch.
 * Nó KHÔNG cần chạy JavaFX.
 */
class BrickTest {

    private StrongBrick strongBrick;
    private NormalBrick normalBrick;

    // Hàm @BeforeEach sẽ chạy TRƯỚC mỗi hàm @Test
    // để đảm bảo chúng ta luôn có gạch "mới"
    @BeforeEach
    void setUp() {
        // "Arrange": Sắp xếp các đối tượng test
        strongBrick = new StrongBrick(0, 0, 10, 10);
        normalBrick = new NormalBrick(0, 0, 10, 10);
    }

    @Test
    void testNormalBrickHit() {
        System.out.println("Kiểm tra gạch thường vỡ ngay lần đầu");

        // "Act": Thực hiện hành động
        boolean wasDestroyed = normalBrick.hit(); // Lấy kết quả từ hàm hit()

        // "Assert": Khẳng định kết quả
        assertTrue(wasDestroyed, "Gạch thường PHẢI vỡ ở lần hit đầu tiên");
        assertTrue(normalBrick.isDestroyed(), "Trạng thái của gạch thường PHẢI là destroyed=true");
        assertEquals(10, normalBrick.getPoints(), "Gạch thường PHẢI trả về 10 điểm");
    }

    @Test
    void testStrongBrickHitSequence() {
        System.out.println("Kiểm tra gạch cứng cần 2 hit để vỡ");

        // --- Lần Hit 1 ---
        // "Act 1":
        boolean destroyedOnHit1 = strongBrick.hit();

        // "Assert 1":
        assertFalse(destroyedOnHit1, "Gạch cứng KHÔNG ĐƯỢC vỡ ở lần hit 1");
        assertFalse(strongBrick.isDestroyed(), "Trạng thái KHÔNG ĐƯỢC là destroyed");

        // <<< SỬA LỖI DÒNG NÀY: Expected phải là 1 >>>
        assertEquals(1, strongBrick.hits, "Số 'hits' PHẢI giảm còn 1 (vì 'hit()' trừ trước)");

        // --- Lần Hit 2 ---
        // "Act 2":
        boolean destroyedOnHit2 = strongBrick.hit();

        // "Assert 2":
        assertTrue(destroyedOnHit2, "Gạch cứng PHẢI vỡ ở lần hit 2");
        assertTrue(strongBrick.isDestroyed(), "Trạng thái PHẢI là destroyed");

        // <<< SỬA LỖI DÒNG NÀY: Expected phải là 0 >>>
        assertEquals(0, strongBrick.hits, "Số 'hits' PHẢI giảm còn 0");
        assertEquals(25, strongBrick.getPoints(), "Gạch cứng PHẢI trả về 25 điểm");
    }
}