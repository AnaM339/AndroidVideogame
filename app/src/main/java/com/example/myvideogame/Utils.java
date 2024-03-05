package com.example.myvideogame;

public class Utils {
    /**
     * Obtener la distancia entre los untos bidimensionales p1 y p2
     * @param p1x
     * @param p1y
     * @param p2x
     * @param p2y
     * @return
     */
    public static double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt(
                Math.pow(p1x - p2x, 2) +
                Math.pow(p1y - p2y, 2)
        );
    }
}
