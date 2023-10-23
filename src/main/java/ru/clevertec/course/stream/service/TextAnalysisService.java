package ru.clevertec.course.stream.service;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

public interface TextAnalysisService {
    Map<String, Long> countWordUsage(Path input) throws IOException;

    Optional<Long> calculateGapBetweenFirstAndLast(List<LocalDate> dates);


    OptionalDouble parseAndCalculateAverage(List<String> strings);

    long parallelSumOfRandomLong(int randomCount, int parallelism);

    long sequenceSumOfRandomLong(int randomCount);
}
