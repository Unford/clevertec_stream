package ru.clevertec.course.stream.service;

import ru.clevertec.course.stream.model.Gender;
import ru.clevertec.course.stream.model.Operator;
import ru.clevertec.course.stream.model.Person;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface PersonService {
    List<Person> findAllOlder(Collection<Person> people, int age);

    List<String> findNamesOfAllHeavier(Collection<Person> people, double weight);

    List<String> findNumbersWherePhoneAmountGreater(Collection<Person> people, int phoneAmount);

    String joinAllNames(Collection<Person> people, CharSequence delimiter);

    List<Person> getSortedByAgeAndName(Collection<Person> people);

    Map<Gender, List<Person>> groupByGender(Collection<Person> people);

    boolean containsPhoneNumber(Collection<Person> people, String number);

    List<Operator> getDistinctPersonOperators(Collection<Person> people, int n);

    double calculateAverageWeight(Collection<Person> people);

    Optional<Person> getYoungest(Collection<Person> people);

    Map<Operator, List<String>> flatGroupPhonesByOperator(Collection<Person> people);
    Map<Gender, Long> countGroupedByGender(Collection<Person> people);


}
