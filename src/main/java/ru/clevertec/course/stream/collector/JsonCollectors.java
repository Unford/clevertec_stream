package ru.clevertec.course.stream.collector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public final class JsonCollectors {
    private JsonCollectors(){}
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> Collector<T, List<T>, String> toJson() {
        return Collector.of(
                ArrayList::new,
                List::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                list -> {
                    try {
                        return objectMapper.writeValueAsString(list);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

}
