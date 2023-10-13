package ru.clevertec.course.stream.util;

import net.datafaker.Faker;
import ru.clevertec.course.stream.model.Gender;
import ru.clevertec.course.stream.model.Operator;
import ru.clevertec.course.stream.model.Person;
import ru.clevertec.course.stream.model.Phone;

import java.util.stream.Stream;

public final class EntityGenerator {
    private static final Faker faker = new Faker();
    private EntityGenerator(){}
    public static Person generatePerson(){
        return new Person()
                .setAge(faker.number().numberBetween(40, 100))
                .setName(faker.name().name())
                .setGender(faker.options().option(Gender.class))
                .setWeight(faker.number().randomDouble(1, 40, 100))
                .setPhones(Stream.generate(EntityGenerator::generatePhone)
                        .limit(faker.number().numberBetween(0, 4))
                        .toList());

    }
    public static Phone generatePhone(){
        return new Phone()
                .setNumber(faker.phoneNumber().phoneNumber())
                .setOperator(faker.options().option(Operator.class));
    }
}
