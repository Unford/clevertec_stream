package ru.clevertec.course.stream.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CustomUtils {
    private static final String EMPTY_STRING = "";
    private static final String BINARY_PREFIX = "0[bB]";
    private static final String BINARY_PATTERN = "^-?" + BINARY_PREFIX + ".+";
    private static final String HEX_PREFIX = "0[xX]";
    private static final String HEX_PATTERN = "^-?" + HEX_PREFIX + ".+";
    private static final String OCTAL_PATTERN = "^-?0.+";

    private static final Map<Predicate<String>, Function<String, Integer>> functions = new LinkedHashMap<>();

    static {
        functions.put(s -> s.matches(BINARY_PATTERN),
                s -> parseNullableInteger(s.replaceAll(BINARY_PREFIX, EMPTY_STRING), 2));
        functions.put(s -> s.matches(HEX_PATTERN),
                s -> parseNullableInteger(s.replaceAll(HEX_PREFIX, EMPTY_STRING), 16));
        functions.put(s -> s.matches(OCTAL_PATTERN),
                s -> parseNullableInteger(s, 8));
        functions.put(s -> true, s -> parseNullableInteger(s, 10));
    }

    private CustomUtils() {
    }

    private static CustomUtils instance;

    public static CustomUtils getInstance() {
        if (instance == null) {
            instance = new CustomUtils();
        }
        return instance;
    }

    private static Integer parseInteger(String s) {
        return functions.entrySet().stream()
                .filter(e -> e.getKey().test(s))
                .findFirst()
                .map(Map.Entry::getValue)
                .map(f -> f.apply(s))
                .orElse(null);
    }

    private static Integer parseNullableInteger(String num, int radix) {
        try {
            return Integer.parseInt(num, radix);
        } catch (NumberFormatException ignored) {
        }
        return null;
    }

    public Map<String, Long> countWordUsage(Path input) throws IOException {
        try (Stream<String> stream = Files.lines(input)) {
            return stream
                    .flatMap(s -> Pattern.compile("[^\\wа-яА-Я]+").splitAsStream(s))
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        }
    }

    public Optional<Long> calculateGapBetweenFirstAndLast(List<LocalDate> dates) {
        if (dates.size() > 1) {
            return dates.stream()
                    .skip(dates.size() - 1)
                    .findFirst()
                    .map(s -> ChronoUnit.DAYS.between(s, dates.stream().findFirst().get()))
                    .map(Math::abs);
        }
        return Optional.empty();
    }


    public OptionalDouble parseAndCalculateAverage(List<String> strings) {
        return strings.stream()
                .map(CustomUtils::parseInteger)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
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
