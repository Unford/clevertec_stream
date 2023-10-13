package ru.clevertec.course.stream.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CustomUtils {
    private static final Predicate<String> isNumber = Pattern.compile("-?\\d+").asMatchPredicate();

    private CustomUtils() {
    }

    private static CustomUtils instance;

    public static CustomUtils getInstance() {
        if (instance == null) {
            instance = new CustomUtils();
        }
        return instance;
    }

    public Map<String, Long> countWordUsage(Path input) throws IOException {
        try (Stream<String> stream = Files.lines(input)) {
            return stream
                    .flatMap(s -> Pattern.compile("[^\\wа-яА-Я]++").splitAsStream(s))
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        }
    }

    public Optional<Long> calculateGapBetweenFirstAndLast(List<LocalDate> dates) {
        if (dates.size() > 1) {
            return dates.stream()
                    .skip(dates.size() - 1)
                    .findFirst()
                    .map(s -> ChronoUnit.DAYS.between(s,
                            dates.stream().findFirst().get()))
                    .map(Math::abs);
        }
        return Optional.empty();
    }

    public OptionalDouble parseAndCalculateAverage(List<String> strings) {
        return strings.stream()
                .filter(isNumber)
                .mapToInt(Integer::parseInt)
                .average();
    }

    public long parallelSumOfRandomLong(int randomCount, int parallelism) {
        Random random = new Random(LocalTime.now().getNano());
        ForkJoinPool customThreadPool = new ForkJoinPool(parallelism);
        return customThreadPool.submit(() ->
                        random.longs(randomCount, 1, 100)
                                .parallel()
                                .sum())
                .join();
    }

    public long sequenceSumOfRandomLong(int randomCount) {
        Random random = new Random(LocalTime.now().getNano());
        return random
                .longs(randomCount, 1, 100)
                .sum();
    }
}
