package vn.edu.hust.testrules.testruleshust.lcs;

import java.util.Arrays;

public class SimpleExponentialSmoothing {

    // Hàm tính toán giá trị trung bình trọng số
    public static float simpleExponentialSmoothing(double alpha, int[] data) {
        float forecast = data[0]; // Giá trị dự đoán ban đầu
        for (int i = 1; i < data.length; i++) {
            forecast = (float) (alpha * data[i] + (1 - alpha) * forecast); // Công thức smoothing
        }
        return forecast;
    }

    public static void main(String[] args) {
        int[] data = {1, 3, 2, 4, 1, 3, 3, 1}; // Các giá trị đã có
        int n = data.length;
        double alpha = 0.4;

        // Dự đoán 15 giá trị tiếp theo
        for (int i = 0; i < (20-data.length); i++) {
            float forecast = simpleExponentialSmoothing(alpha, Arrays.copyOfRange(data, 0, n + i));
            System.out.println("Dự đoán giá trị của câu " + (n + i + 1) + ": " + forecast);
        }
    }
}

