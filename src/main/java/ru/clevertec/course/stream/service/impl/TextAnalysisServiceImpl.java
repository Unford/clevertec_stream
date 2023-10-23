package ru.clevertec.course.stream.service.impl;

import ru.clevertec.course.stream.service.TextAnalysisService;

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

public final class TextAnalysisServiceImpl implements TextAnalysisService {
    private static final String EMPTY_STRING = "";
    private static final String BINARY_PREFIX = "0[bB]";
    private static final String BINARY_REGEX = "^-?[01]+";

    private static final String BINARY_PATTERN = "^-?" + BINARY_PREFIX + ".+";
    private static final String HEX_PREFIX = "0[xX]";
    private static final String HEX_REGEX = "^-?[0-9a-fA-F]+";
    private static final String HEX_PATTERN = "^-?" + HEX_PREFIX + ".+";
    private static final String OCTAL_PATTERN = "^-?0.+";
    private static final String OCTAL_REGEX = "^-?[0-7]+";

    private static final String DECIMAL_REGEX = "^-?[0-9]+";
    private static final String NOT_SYMBOL_PATTERN = "[^\\wа-яА-Я]+";

    private static final Map<Predicate<String>, Function<String, Optional<Integer>>> functions = new LinkedHashMap<>();

    static {
        functions.put(s -> s.matches(BINARY_PATTERN),
                s -> parseNullableInteger(s.replaceAll(BINARY_PREFIX, EMPTY_STRING), BINARY_REGEX, 2));
        functions.put(s -> s.matches(HEX_PATTERN),
                s -> parseNullableInteger(s.replaceAll(HEX_PREFIX, EMPTY_STRING), HEX_REGEX, 16));
        functions.put(s -> s.matches(OCTAL_PATTERN),
                s -> parseNullableInteger(s, OCTAL_REGEX, 8));
        functions.put(s -> true, s -> parseNullableInteger(s, DECIMAL_REGEX, 10));
    }

    private static Optional<Integer> parseInteger(String s) {
        return functions.entrySet().stream()
                .filter(e -> e.getKey().test(s))
                .findFirst()
                .map(Map.Entry::getValue)
                .flatMap(f -> f.apply(s));
    }

    private static Optional<Integer> parseNullableInteger(String num, String regex, int radix) {
        return Optional.ofNullable(num).stream()
                .filter(s -> s.matches(regex))
                .map(s -> Integer.parseInt(s, radix))
                .findFirst();

    }

    @Override
    public Map<String, Long> countWordUsage(Path input) throws IOException {
        try (Stream<String> stream = Files.lines(input)) {
            return stream
                    .flatMap(s -> Pattern.compile(NOT_SYMBOL_PATTERN).splitAsStream(s))
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        }
    }

    @Override
    public Optional<Long> calculateGapBetweenFirstAndLast(List<LocalDate> dates) {
        return Optional.ofNullable(dates).stream()
                .filter(dates1 -> dates1.size() > 1)
                .flatMap(Collection::stream)
                .skip(Math.abs(dates.size() - 1))
                .findFirst()
                .map(s -> ChronoUnit.DAYS.between(s,
                        dates.stream()
                                .findFirst()
                                .get()))
                .map(Math::abs);
    }

    @Override
    public OptionalDouble parseAndCalculateAverage(List<String> strings) {
        return strings.stream()
                .map(TextAnalysisServiceImpl::parseInteger)
                .filter(Optional::isPresent)
                .mapToInt(Optional::get)
                .average();
    }

    @Override
    public long parallelSumOfRandomLong(int randomCount, int parallelism) {
        Random random = new Random(LocalTime.now().getNano());
        ForkJoinPool customThreadPool = new ForkJoinPool(parallelism);
        long result = customThreadPool.submit(() ->
                        random.longs(randomCount, 1, 100)
                                .parallel()
                                .sum())
                .join();
        customThreadPool.shutdown();
        return result;
    }

    @Override
    public long sequenceSumOfRandomLong(int randomCount) {
        Random random = new Random(LocalTime.now().getNano());
        return random
                .longs(randomCount, 1, 100)
                .sum();
    }
}
