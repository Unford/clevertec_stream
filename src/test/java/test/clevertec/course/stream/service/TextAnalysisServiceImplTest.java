package test.clevertec.course.stream.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.course.stream.service.impl.TextAnalysisServiceImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TextAnalysisServiceImplTest {
    private static final TextAnalysisServiceImpl service = new TextAnalysisServiceImpl();

    private static Path getTestResourcePath(String fileName) {
        ClassLoader classLoader = TextAnalysisServiceImplTest.class.getClassLoader();
        return Path.of(classLoader.getResource(fileName).getPath());
    }

    @ParameterizedTest
    @MethodSource("countWordUsageDataProvider")
    void givenList_whenCountWordUsage_thenReturnMap(Path path,
                                                    int size) throws IOException {
        Map<String, Long> actual = service.countWordUsage(path);
        assertThat(actual).size().isEqualTo(size);
    }


    static Stream<Arguments> countWordUsageDataProvider() {
        return Stream.of(
                Arguments.of(getTestResourcePath("input/Empty.txt"), 0),
                Arguments.of(getTestResourcePath("input/testText.txt"), 218),
                Arguments.of(getTestResourcePath("input/short.txt"), 8)

        );

    }


    @ParameterizedTest
    @MethodSource("calculateGapBetweenFirstAndLastDataProvider")
    void givenList_whenCalculateGapBetweenFirstAndLast_thenReturnLong(List<LocalDate> dates) {
        Long actual = service.calculateGapBetweenFirstAndLast(dates).get();
        LocalDate first = dates.get(0);
        LocalDate last = dates.get(dates.size() - 1);
        Long expect = Math.abs(ChronoUnit.DAYS.between(last, first));

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void givenEmptyList_whenCalculateGapBetweenFirstAndLast_thenReturnEmpty() {
        Optional<Long> actual = service.calculateGapBetweenFirstAndLast(List.of());
        assertThat(actual).isEmpty();
    }
    @Test
    void givenOnlyOneDate_whenCalculateGapBetweenFirstAndLast_thenReturnEmpty() {
        Optional<Long> actual = service.calculateGapBetweenFirstAndLast(List.of(LocalDate.of(2000, 10, 1)));
        assertThat(actual).isEmpty();
    }


    static Stream<Arguments> calculateGapBetweenFirstAndLastDataProvider() {
        return Stream.of(
                Arguments.of(List.of(LocalDate.of(2000, 10, 1),
                        LocalDate.of(2000, 10, 4),
                        LocalDate.of(2000, 10, 17),
                        LocalDate.of(2000, 10, 30))),
                Arguments.of(List.of(LocalDate.of(2000, 10, 3),
                        LocalDate.of(2000, 10, 8),
                        LocalDate.of(2000, 10, 15),
                        LocalDate.of(2000, 10, 23)))
        );

    }


    @ParameterizedTest
    @MethodSource("parseAndCalculateAverageDataProvider")
    void givenList_whenParseAndCalculateAverageDataProvider_thenAverage(List<String> strings,
                                                                        OptionalDouble expected) {
        OptionalDouble actual = service.parseAndCalculateAverage(strings);
        assertThat(actual).isEqualTo(expected);
    }


    static Stream<Arguments> parseAndCalculateAverageDataProvider() {
        return Stream.of(
                Arguments.of(List.of("1", "-1", "3s", "oi", "33", "10", "o"),
                        IntStream.of(1, -1, 33, 10).average()),
                Arguments.of(List.of("1", "-1", "33", "10", "1"),
                        IntStream.of(1, -1, 33, 10, 1).average()),
                Arguments.of(List.of("a", "b", "c", "d", "e"),
                        IntStream.of().average()),
                Arguments.of(
                        List.of("42", "0x1A", "052", "-0b1010", "12345", "Ox", "-Obx"),
                        IntStream.of(42, 26, 42, -10, 12345).average()),
                Arguments.of(
                        List.of("42", "0x", "052", "-0b1010", "12345", "Ox", "-Obx"),
                        IntStream.of(42, 42, -10, 12345).average()),
                Arguments.of(
                        List.of("a", "b", "05294", "-0b1010", "12345", "Ox", "-Obx"),
                        IntStream.of(-10, 12345).average())
        );

    }


}
