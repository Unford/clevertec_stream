package ru.clevertec.course.stream.util;

import java.io.File;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public final class CustomUtils {
    private CustomUtils() {
    }

    private CustomUtils instance;

    public CustomUtils getInstance() {
        if (instance == null) {
            instance = new CustomUtils();
        }
        return instance;
    }

    public Map<String, BigInteger> countWordUsage(File input){
        return null;
    }

    public int calculateGapBetweenFirstAndLast(List<LocalDate> dates){
        return 1;
    }

    public double parseAndCalculateAverage(List<String> strings){
        return 1;
    }

    public BigInteger parallelSumOfRandomInt(int randomCount){
        return BigInteger.ONE;
    }
}
