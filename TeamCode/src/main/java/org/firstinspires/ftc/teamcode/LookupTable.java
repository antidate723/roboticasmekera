package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A lookup table that stores (x, y1, y2) points and provides linear interpolation.
 * Useful for mapping a single input (like distance) to multiple outputs (like angle and power).
 */
public class LookupTable {
    private final List<Entry> table = new ArrayList<>();

    private static class Entry implements Comparable<Entry> {
        double x, y1, y2;

        Entry(double x, double y1, double y2) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
        }

        @Override
        public int compareTo(Entry other) {
            return Double.compare(this.x, other.x);
        }
    }

    /**
     * Container class for the two variables returned by the lookup table.
     */
    public static class Result {
        public final double y1;
        public final double y2;

        public Result(double y1, double y2) {
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    /**
     * Adds a point to the lookup table. The table is automatically kept sorted by x.
     * @param x The input value (e.g., distance).
     * @param y1 The first output value (e.g., turret angle).
     * @param y2 The second output value (e.g., launcher power).
     */
    public void add(double x, double y1, double y2) {
        table.add(new Entry(x, y1, y2));
        Collections.sort(table);
    }

    /**
     * Gets interpolated values for the given input. 
     * If the input is outside the range of defined points, it returns the value of the nearest edge point.
     * @param x The input value to look up.
     * @return A Result object containing the two interpolated values (y1 and y2).
     */
    public Result get(double x) {
        if (table.isEmpty()) {
            return new Result(0, 0);
        }

        // Clamp to edges if x is out of range
        if (x <= table.get(0).x) {
            return new Result(table.get(0).y1, table.get(0).y2);
        }
        if (x >= table.get(table.size() - 1).x) {
            return new Result(table.get(table.size() - 1).y1, table.get(table.size() - 1).y2);
        }

        // Find the interval [e1, e2] that contains x and perform linear interpolation
        for (int i = 0; i < table.size() - 1; i++) {
            Entry e1 = table.get(i);
            Entry e2 = table.get(i + 1);

            if (x >= e1.x && x <= e2.x) {
                double t = (x - e1.x) / (e2.x - e1.x);
                double ry1 = e1.y1 + t * (e2.y1 - e1.y1);
                double ry2 = e1.y2 + t * (e2.y2 - e1.y2);
                return new Result(ry1, ry2);
            }
        }

        return new Result(0, 0);
    }
}
