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
     * Extracts string representations of the values associated with the specified key from each map in the provided list.
     * <p>
     * This method iterates over each map in the list, retrieves the value corresponding to the provided key,
     * and converts it to a String using {@code toString()}. If a map does not contain the key or its value is {@code null},
     * an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param mapList the list of maps from which to extract values. Each map is expected to have values of type {@code Integer}.
     * @param key the key whose associated values will be extracted.
     * @return a {@code List<String>} containing the string representations of the values.
     * @throws IllegalArgumentException if any map is missing the specified key or if the value is {@code null}.
     */
    public static List<String> extractStringValues(List<Map<String, Integer>> mapList, String key) {
        return mapList.stream()
                .map(map -> {
                    Object obj = map.get(key);
                    if (obj != null) {
                        return obj.toString();
                    } else {
                        throw new IllegalArgumentException("Missing value in map: " + map);
                    }
                })
                .toList();
    }

    /**
     * Extracts integer values associated with the specified key from each map in the provided list.
     * <p>
     * This method iterates over each map in the list, retrieves the value corresponding to the provided key,
     * and converts it to an integer using {@code intValue()}. If a map does not contain the key or its value is {@code null},
     * an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param mapList the list of maps from which to extract values. Each map is expected to have values of type {@code Integer}.
     * @param key the key whose associated values will be extracted.
     * @return a {@code List<Integer>} containing the integer values extracted from the maps.
     * @throws IllegalArgumentException if any map is missing the specified key or if the value is {@code null}.
     */
    public static List<Integer> extractIntValues(List<Map<String, Integer>> mapList, String key) {
        return mapList.stream()
                .map(map -> {
                    Number obj = map.get(key);
                    if (obj != null) {
                        return obj.intValue();
                    } else {
                        throw new IllegalArgumentException("Missing value in map: " + map);
                    }
                })
                .toList();
    }
}
