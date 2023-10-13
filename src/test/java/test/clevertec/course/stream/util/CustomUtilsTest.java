package test.clevertec.course.stream.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.course.stream.util.CustomUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CustomUtilsTest {
    private static final CustomUtils customUtils = CustomUtils.getInstance();

    private static Path getTestResourcePath(String fileName) {
        ClassLoader classLoader = CustomUtilsTest.class.getClassLoader();
        return Path.of(classLoader.getResource(fileName).getPath());
    }

    @ParameterizedTest
    @MethodSource("countWordUsageDataProvider")
    void givenList_whenCountWordUsage_thenReturnMap(Path path,
                                                    int size) throws IOException {
        Map<String, Long> actual = customUtils.countWordUsage(path);
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
        Long actual = customUtils.calculateGapBetweenFirstAndLast(dates).get();
        LocalDate first = dates.get(0);
        LocalDate last = dates.get(dates.size() - 1);
        Long expect = Math.abs(ChronoUnit.DAYS.between(last, first));

        assertThat(actual).isEqualTo(expect);
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
        OptionalDouble actual = customUtils.parseAndCalculateAverage(strings);
        assertThat(actual).isEqualTo(expected);
    }


    static Stream<Arguments> parseAndCalculateAverageDataProvider() {
        return Stream.of(
                Arguments.of(List.of("1", "-1", "3s", "oi", "33", "10", "o"),
                        IntStream.of(1, -1, 33, 10).average()),
                Arguments.of(List.of("1", "-1", "33", "10", "1"),
                        IntStream.of(1, -1, 33, 10, 1).average()),
                Arguments.of(List.of("a", "b", "c", "d", "e"),
                        IntStream.of().average())
        );

    }


}
