package vn.edu.hust.testrules.testruleshust.lcs;

public class DoubleExponentialSmoothing {
    private double alpha; // hệ số smoothing level
    private double beta; // hệ số smoothing trend
    private double[] smoothedValues; // mảng chứa các giá trị smoothed
    private double[] trendValues; // mảng chứa các giá trị trend
    private int n; // số lượng giá trị ban đầu
    private double lastSmoothedValue; // giá trị smoothed cuối cùng
    private double lastTrendValue; // giá trị trend cuối cùng

    public DoubleExponentialSmoothing(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    public int[] smooth(int[] values, int numForecasts) {
        n = values.length;
        smoothedValues = new double[n + numForecasts];
        trendValues = new double[n + numForecasts];

        // khởi tạo smoothed values và trend values ban đầu
        smoothedValues[0] = values[0];
        trendValues[0] = values[1] - values[0];

        for (int i = 1; i < n + numForecasts; i++) {
            if (i < n) {
                smoothedValues[i] = alpha * values[i] + (1 - alpha) * (smoothedValues[i-1] + trendValues[i-1]);
                trendValues[i] = beta * (smoothedValues[i] - smoothedValues[i-1]) + (1 - beta) * trendValues[i-1];
            } else {
                smoothedValues[i] = alpha * lastSmoothedValue + (1 - alpha) * (lastSmoothedValue + lastTrendValue);
                trendValues[i] = beta * (smoothedValues[i] - lastSmoothedValue) + (1 - beta) * lastTrendValue;
            }

            lastSmoothedValue = smoothedValues[i];
            lastTrendValue = trendValues[i];
        }

        int[] forecastedValues = new int[numForecasts];
        for (int i = 0; i < numForecasts; i++) {
            forecastedValues[i] = (int) Math.round(smoothedValues[n + i]);
        }

        return forecastedValues;
    }
}

