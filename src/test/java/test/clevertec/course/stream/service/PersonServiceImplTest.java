package test.clevertec.course.stream.service;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.course.stream.model.Gender;
import ru.clevertec.course.stream.model.Operator;
import ru.clevertec.course.stream.model.Person;
import ru.clevertec.course.stream.model.Phone;
import ru.clevertec.course.stream.service.PersonService;
import ru.clevertec.course.stream.service.impl.PersonServiceImpl;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static test.clevertec.course.stream.util.EntityGenerator.generatePerson;

class PersonServiceImplTest {
    private List<Person> personList;

    @BeforeEach
    void setUp() {
        personList = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            personList.add(generatePerson());
        }
    }

    private static final PersonService service = PersonServiceImpl.getInstance();

    @ParameterizedTest
    @MethodSource("findAllOlderDataProvider")
    void givenList_whenFindAllOlder_thenReturnList(List<Person> people,
                                                   int age) {
        List<Person> actual = service.findAllOlder(people, age);
        assertThat(actual)
                .extracting(Person::getAge)
                .allMatch(a -> a > age);
    }


    static Stream<Arguments> findAllOlderDataProvider() {
        return Stream.of(
                Arguments.of(List.of(generatePerson().setAge(1),
                                generatePerson().setAge(73),
                                generatePerson().setAge(93),
                                generatePerson().setAge(25)),
                        25),
                Arguments.of(List.of(generatePerson().setAge(1),
                                generatePerson().setAge(73),
                                generatePerson().setAge(93),
                                generatePerson().setAge(25)),
                        100),
                Arguments.of(List.of(), 1));

    }

    @ParameterizedTest
    @MethodSource("findNamesOfAllHeavierDataProvider")
    void givenList_whenFindNamesOfAllHeavier_thenReturnString(List<Person> people,
                                                              double weight) {
        List<String> expected = new ArrayList<>();
        for (Person person : people) {
            if (person.getWeight() > weight) {
                expected.add(person.getName());
            }
        }

        List<String> actual = service.findNamesOfAllHeavier(people, weight);

        assertThat(actual).containsAll(expected);
    }


    static Stream<Arguments> findNamesOfAllHeavierDataProvider() {
        return Stream.of(
                Arguments.of(List.of(generatePerson().setWeight(1),
                                generatePerson().setWeight(73),
                                generatePerson().setWeight(93),
                                generatePerson().setWeight(25)),
                        25),
                Arguments.of(List.of(generatePerson().setWeight(1),
                                generatePerson().setWeight(73),
                                generatePerson().setWeight(93),
                                generatePerson().setWeight(25)),
                        100),
                Arguments.of(List.of(), 1));

    }

    @ParameterizedTest
    @MethodSource("findNumbersWherePhoneAmountGreaterDataProvider")
    void givenList_whenFindNumbersWherePhoneAmountGreater_thenReturnList(List<Person> people,
                                                                         int phoneAmount) {
        List<String> expected = new ArrayList<>();
        for (Person person : people) {
            if (person.getPhones().size() > phoneAmount) {
                for (Phone phone : person.getPhones()) {
                    expected.add(phone.getNumber());
                }
            }
        }

        List<String> actual = service.
                findNumbersWherePhoneAmountGreater(people, phoneAmount);

        assertThat(actual).containsAll(expected);
    }


    static Stream<Arguments> findNumbersWherePhoneAmountGreaterDataProvider() {
        return Stream.of(
                Arguments.of(List.of(
                                generatePerson(),
                                generatePerson(),
                                generatePerson(),
                                generatePerson()),
                        2),
                Arguments.of(List.of(generatePerson(),
                                generatePerson(),
                                generatePerson(),
                                generatePerson()),
                        100),
                Arguments.of(List.of(), 1));

    }


    @Test
    void givenList_whenJoinAllNames_thenReturnString() {
        String del = ",";
        StringJoiner joiner = new StringJoiner(del);
        for (Person person : personList) {
            joiner.add(person.getName());
        }

        String expected = joiner.toString();
        String actual = service.joinAllNames(personList, del);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenList_whenGetSortedByAgeAndName_thenReturnList() {
        List<Person> actual = service.getSortedByAgeAndName(personList);
        assertThat(actual)
                .isSortedAccordingTo(Comparator.comparingInt(Person::getAge)
                        .reversed()
                        .thenComparing(Person::getName));
    }

    @Test
    void givenList_whenGroupByGender_thenReturnMap() {
        Map<Gender, List<Person>> actual = service.groupByGender(personList);
        assertThat(actual)
                .containsKeys(Gender.MALE, Gender.FEMALE)
                .allSatisfy((k, v) -> assertThat(v).allMatch(person -> person.getGender() == k));
    }

    @ParameterizedTest
    @MethodSource("containsPhoneNumberDataProvider")
    void givenList_whenContainsPhoneNumber_thenReturn(List<Person> people,
                                                      String number,
                                                      boolean expected) {
        boolean actual = service.containsPhoneNumber(people, number);
        assertThat(actual).isEqualTo(expected);

    }

    static Stream<Arguments> containsPhoneNumberDataProvider() {
        return Stream.of(
                Arguments.of(List.of(generatePerson(),
                                generatePerson(),
                                generatePerson(),
                                generatePerson()
                                        .setPhones(List.of(new Phone()
                                                .setNumber("1053")
                                                .setOperator(Operator.A1)))),
                        "1053", true),
                Arguments.of(List.of(generatePerson(),
                                generatePerson(),
                                generatePerson(),
                                generatePerson()),
                        "Definitely not a number", false),
                Arguments.of(List.of(), "empty", false));

    }

    @ParameterizedTest
    @MethodSource("getDistinctPersonOperatorsDataProvider")
    void givenList_whenContainsPhoneNumber_thenReturn(List<Person> people,
                                                      int n) {
        Set<Operator> expected = EnumSet.noneOf(Operator.class);
        Person cur = people.get(n);
        for (Phone phone : cur.getPhones()) {
            expected.add(phone.getOperator());
        }

        List<Operator> actual = service.getDistinctPersonOperators(people, n);
        assertThat(actual).containsAll(expected);

    }

    static Stream<Arguments> getDistinctPersonOperatorsDataProvider() {
        return Stream.of(
                Arguments.of(List.of(generatePerson(),
                                generatePerson(),
                                generatePerson(),
                                generatePerson()
                                        .setPhones(List.of(new Phone()
                                                .setNumber("1")
                                                .setOperator(Operator.A1)))),
                        3),
                Arguments.of(List.of(generatePerson(),
                                generatePerson().setPhones(
                                        List.of(new Phone().setNumber("1").setOperator(Operator.A1),
                                                new Phone().setNumber("2").setOperator(Operator.A1),
                                                new Phone().setNumber("3").setOperator(Operator.MTS),
                                                new Phone().setNumber("4").setOperator(Operator.MTS))),
                                generatePerson(),
                                generatePerson()),
                        1));
    }

    @Test
    void givenList_whenCalculateAverageWeight_thenReturn() {
        double expect = 0.0;
        for (Person p : personList) {
            expect += p.getWeight();
        }
        expect /= personList.size();
        double actual = service.calculateAverageWeight(personList);

        assertThat(actual).isCloseTo(expect, Offset.offset(0.1));
    }

    @Test
    void givenList_whenGetYoungest_thenReturn() {
        Person expect = null;
        int min = Integer.MAX_VALUE;
        for (Person p : personList) {
            if (min > p.getAge()) {
                min = p.getAge();
                expect = p;
            }
        }

        Person actual = service.getYoungest(personList).get();

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void givenList_whenFlatGroupPhonesByOperator_thenReturnMap() {
        Map<Operator, List<String>> actual = service.flatGroupPhonesByOperator(personList);
        assertThat(actual)
                .containsKeys(Operator.A1, Operator.MTS, Operator.LIFE, Operator.BEELINE)
                .allSatisfy((k, v) ->
                        assertThat(v).allMatch(number -> personList.stream()
                                .flatMap(person -> person.getPhones().stream())
                                .filter(phone -> phone.getOperator() == k)
                                .anyMatch(phone -> phone.getNumber().equals(number))));
    }


    @ParameterizedTest
    @MethodSource("countGroupedByGenderDataProvider")
    void givenList_whenCountGroupedByGender_thenReturn(List<Person> people) {
        Long expectedMale = countByGender(people, Gender.MALE);
        Long expectedFemale = countByGender(people, Gender.FEMALE);

        Map<Gender, Long> actual = service.countGroupedByGender(people);
        assertThat(actual)
                .extractingByKeys(Gender.FEMALE, Gender.MALE)
                .containsExactly(expectedFemale, expectedMale);

    }

    private Long countByGender(List<Person> people, Gender gender) {
        long size = 0L;
        for (Person p : people) {
            if (p.getGender() == gender) {
                size++;
            }
        }
        return size == 0L ? null : size;
    }

    static Stream<Arguments> countGroupedByGenderDataProvider() {
        return Stream.of(
                Arguments.of(List.of(generatePerson().setGender(Gender.FEMALE),
                                generatePerson().setGender(Gender.FEMALE),
                                generatePerson().setGender(Gender.FEMALE),
                                generatePerson().setGender(Gender.FEMALE)),
                        Arguments.of(List.of(generatePerson().setGender(Gender.MALE),
                                generatePerson().setGender(Gender.MALE),
                                generatePerson().setGender(Gender.MALE),
                                generatePerson().setGender(Gender.MALE))),
                        Arguments.of(List.of(generatePerson().setGender(Gender.FEMALE),
                                generatePerson().setGender(Gender.MALE),
                                generatePerson().setGender(Gender.FEMALE),
                                generatePerson().setGender(Gender.MALE)))
                ));
    }
}
