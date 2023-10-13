package test.clevertec.course.stream.collector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.course.stream.collector.JsonCollectors;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.clevertec.course.stream.util.EntityGenerator.generatePerson;
import static test.clevertec.course.stream.util.EntityGenerator.generatePhone;

class JsonCollectorTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest
    @MethodSource("toJsonObjectsDataProvider")
    void givenList_whenCollectToJson_thenReturnString(List<Object> objects) throws JsonProcessingException {
        String expected = mapper.writeValueAsString(objects);
        String actual = objects.stream().collect(JsonCollectors.toJson());

        assertEquals(expected, actual);
    }



    static Stream<Arguments> toJsonObjectsDataProvider() {
        return Stream.of(
                Arguments.of(List.of(generatePerson(), generatePerson(),
                        generatePerson(), generatePerson())),
                Arguments.of(List.of(generatePhone(), generatePhone())));

    }
}
