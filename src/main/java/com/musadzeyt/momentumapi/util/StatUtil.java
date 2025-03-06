package com.musadzeyt.momentumapi.util;

import com.musadzeyt.momentumapi.enums.stat.TrendEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for statistical calculations.
 * <p>
 * Provides methods to calculate the trend based on a list of data points using linear regression
 * and to sum corresponding elements of multiple lists. Alongside with other helper methods.
 * </p>
 */
public class StatUtil {
    /**
     * Calculates the trend of a given list of integer data points using linear regression.
     * <p>
     * The method computes the slope of the data by treating the indices as x-values and the list values as y-values.
     * Based on a defined threshold, it returns a {@link TrendEnum} indicating if the trend is upward, downward, or neutral.
     * </p>
     *
     * @param data a {@code List<Integer>} representing the data points over time.
     * @return a {@code TrendEnum} indicating the overall trend (UP, DOWN, or NEUTRAL).
     */
    public static TrendEnum trendCalculator(List<Integer> data) {
        int n = data.size();
        double slope = getSlope(data, n);

        // Define a threshold for considering the slope "neutral"
        double threshold = 0.1;
        TrendEnum trend;
        if (slope > threshold) {
            trend = TrendEnum.UP;
        } else if (slope < -threshold) {
            trend = TrendEnum.DOWN;
        } else {
            trend = TrendEnum.NEUTRAL;
        }

        return trend;
    }

    /**
     * Computes the slope of the provided data using the linear regression formula.
     * <p>
     * The slope is calculated by treating each index as an x-coordinate and each corresponding list value as a y-coordinate.
     * </p>
     *
     * @param data the list of integer data points.
     * @param n    the number of data points.
     * @return a {@code double} representing the slope of the data.
     */
    private static double getSlope(List<Integer> data, int n) {
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        // Calculate sums for the regression formula using indices as the x-values
        for (int i = 0; i < n; i++) {
            int y = data.get(i);
            sumX += i;
            sumY += y;
            sumXY += i * y;
            sumX2 += i * i;
        }

        // TODO: think of doing something with slope value in the future
        // Compute the slope (b) using the formula:
        // slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX^2)
        return (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
    }

    /**
     * Sums the corresponding (parallel) elements of three lists.
     * <p>
     * For each index in the lists, this method calculates the sum of the elements from each list and returns
     * a new list containing these sums.
     * </p>
     *
     * @param listA the first list of integers.
     * @param listB the second list of integers.
     * @param listC the third list of integers.
     * @return a {@code List<Integer>} where each element is the sum of the elements from the corresponding index of the input lists.
     * @throws IllegalArgumentException if any of the lists are null or if the lists do not have the same size.
     */
    public static List<Integer> sumParallelValues(List<Integer> listA, List<Integer> listB, List<Integer> listC) {
        if (listA == null || listB == null || listC == null) {
            throw new IllegalArgumentException("Input lists cannot be null");
        }
        if (listA.size() != listB.size() || listA.size() != listC.size()) {
            throw new IllegalArgumentException("All lists must have the same size");
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < listA.size(); i++) {
            result.add(listA.get(i) + listB.get(i) + listC.get(i));
        }
        return result;
    }

    /**
     * Extracts month values from a list of maps and converts them to Strings.
     * <p>
     * Each map in the provided list is expected to contain a key "month" with a non-null value.
     * The value is converted to a String using its {@code toString()} method.
     * </p>
     *
     * @param mapList a list of maps where each map has a key "month" associated with a value.
     * @return a List of month values as Strings.
     * @throws IllegalArgumentException if any map does not contain a "month" key or if its value is null.
     */
    public static List<String> getMonths(List<Map<String, Integer>> mapList) {
        return mapList.stream()
                .map(map -> {
                    Object monthObj = map.get("month");
                    if (monthObj != null) {
                        return monthObj.toString();
                    } else {
                        throw new IllegalArgumentException("Missing 'month' value in map: " + map);
                    }
                })
                .toList();
    }

    /**
     * Extracts the year values from a list of maps and converts them into a list of strings.
     * <p>
     * Each map in the provided list is expected to contain a key "year" with a non-null value.
     * The value is converted to a String using its {@code toString()} method.
     * If a map does not contain a "year" key or its value is {@code null}, an
     * {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param mapList a list of maps where each map should have a key "year" with an integer value.
     * @return a {@code List<String>} containing the year values as strings.
     * @throws IllegalArgumentException if any map in the list is missing the "year" key or has a {@code null} value.
     */
    public static List<String> getYears(List<Map<String, Integer>> mapList) {
        return mapList.stream()
                .map(map -> {
                    Object yearObj = map.get("year");
                    if (yearObj != null) {
                        return yearObj.toString();
                    } else {
                        throw new IllegalArgumentException("Missing 'year' value in map: " + map);
                    }
                })
                .toList();
    }

    public static List<String> getDates(List<Map<String, Integer>> mapList) {
        return mapList.stream()
                .map(map -> {
                    Object dateObj = map.get("date");
                    if (dateObj != null) {
                        return dateObj.toString();
                    } else {
                        throw new IllegalArgumentException("Missing 'date' value in map: " + map);
                    }
                })
                .toList();
    }

    /**
     * Extracts amount values from a list of maps and converts them to Integers.
     * <p>
     * Each map in the provided list is expected to contain a key "amount" with a non-null numeric value.
     * The numeric value is converted to an int using {@code intValue()}.
     * </p>
     *
     * @param mapList a list of maps where each map has a key "amount" associated with a numeric value.
     * @return a List of amounts as Integers.
     * @throws IllegalArgumentException if any map does not contain an "amount" key or if its value is null.
     */
    public static List<Integer> getAmounts(List<Map<String, Integer>> mapList) {
        return mapList.stream()
                .map(map -> {
                    Number amountObj = map.get("amount");
                    if (amountObj != null) {
                        return amountObj.intValue();
                    } else {
                        throw new IllegalArgumentException();
                    }
                })
                .toList();
    }
}
