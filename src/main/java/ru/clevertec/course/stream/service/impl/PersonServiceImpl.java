package ru.clevertec.course.stream.service.impl;

import ru.clevertec.course.stream.model.Gender;
import ru.clevertec.course.stream.model.Operator;
import ru.clevertec.course.stream.model.Person;
import ru.clevertec.course.stream.model.Phone;
import ru.clevertec.course.stream.service.PersonService;

import java.util.*;
import java.util.stream.Collectors;

public final class PersonServiceImpl implements PersonService {


    @Override
    public List<Person> findAllOlder(Collection<Person> people, int age) {
        return people.stream()
                .filter(person -> person.getAge() > age)
                .toList();
    }

    @Override
    public List<String> findNamesOfAllHeavier(Collection<Person> people, double weight) {
        return people.stream()
                .filter(person -> person.getWeight() > weight)
                .map(Person::getName)
                .toList();
    }

    @Override
    public List<String> findNumbersWherePhoneAmountGreater(Collection<Person> people, int phoneAmount) {
        return people.stream()
                .filter(person -> person.getPhones().size() > phoneAmount)
                .flatMap(person -> person.getPhones().stream())
                .map(Phone::getNumber)
                .toList();
    }

    @Override
    public String joinAllNames(Collection<Person> people, CharSequence delimiter) {
        return people.stream()
                .map(Person::getName)
                .collect(Collectors.joining(delimiter));
    }

    @Override
    public List<Person> getSortedByAgeAndName(Collection<Person> people) {
        return people.stream()
                .sorted(Comparator.comparingInt(Person::getAge)
                        .reversed()
                        .thenComparing(Person::getName))
                .toList();
    }

    @Override
    public Map<Gender, List<Person>> groupByGender(Collection<Person> people) {
        return people.stream()
                .filter(p -> p.getGender() != null)
                .collect(Collectors.groupingBy(Person::getGender));
    }

    @Override
    public boolean containsPhoneNumber(Collection<Person> people, String number) {
        return people.stream()
                .flatMap(person -> person.getPhones().stream())
                .map(Phone::getNumber)
                .anyMatch(phone -> phone.equals(number));
    }

    @Override
    public List<Operator> getDistinctPersonOperators(Collection<Person> people, int n) {
        return people.stream()
                .skip(n)
                .limit(1)
                .flatMap(person -> person.getPhones().stream())
                .map(Phone::getOperator)
                .distinct()
                .toList();
    }

    @Override
    public double calculateAverageWeight(Collection<Person> people) {
        return people.stream()
                .mapToDouble(Person::getWeight)
                .average()
                .orElse(0.0);
    }

    @Override
    public Optional<Person> getYoungest(Collection<Person> people) {
        return people.stream().min(Comparator.comparingInt(Person::getAge));
    }

    @Override
    public Map<Operator, List<String>> flatGroupPhonesByOperator(Collection<Person> people) {
        return people
                .stream()
                .flatMap(person -> person.getPhones().stream())
                .collect(Collectors.groupingBy(Phone::getOperator,
                        Collectors.mapping(Phone::getNumber, Collectors.toList())));
    }

    @Override
    public Map<Gender, Long> countGroupedByGender(Collection<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(Person::getGender,
                        Collectors.counting()));
    }
}
