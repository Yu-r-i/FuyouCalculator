/**
 * FuyouCalculator.java
 * 
 * Class to calculate total and wall status for Fuyou.
 */

public class FuyouCalculator {
    /*
     * Calculate total from monthly amounts.
     */
    public static double calcTotal(double[] monthly) {
        double total = 0;
        for (double m : monthly)
            total += m;
        return total;
    }

    /*
     * Calculate wall status given total, wall, and label.
     */
    public static String wallStatus(double total, double wall, String label) {
        double diff = wall - total;
        if (diff > 0)
            return String.format("%s：あと %,d 円で到達", label, (int) diff);
        else
            return String.format("%s：%,d 円超過", label, (int) Math.abs(diff));
    }
}
