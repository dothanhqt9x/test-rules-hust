package vn.edu.hust.testrules.testruleshust.lcs;

public class Main {
    public static void main(String[] args) {
        int[] values = {2, 4, 1, 3, 4};
        int numForecasts = 5;
        double alpha = 0.2;
        double beta = 0.1;

        DoubleExponentialSmoothing des = new DoubleExponentialSmoothing(alpha, beta);
        int[] forecastedValues = des.smooth(values, numForecasts);

        System.out.println("Cac gia tri du doan:");
        for (int i = 0; i < forecastedValues.length; i++) {
            System.out.println(forecastedValues[i]);
        }
    }

}
