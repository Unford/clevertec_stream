package ru.clevertec.course.stream.service;

import ru.clevertec.course.stream.model.Gender;
import ru.clevertec.course.stream.model.Person;
import ru.clevertec.course.stream.model.Phone;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface PersonService {
    void findAllOlder(Collection<Person> people, int n);

    void printNameOfAllHeavier(Collection<Person> people, double n);

    void printNumbersWherePhoneAmountGreater(Collection<Person> people, int n);

    String joinAllNames(Collection<Person> people, char joiner);

    void printSortedByAgeAndName(Collection<Person> people);

    Map<Gender, Person> groupByGender(Collection<Person> people);

    boolean containsPhoneNumber(Collection<Person> people, String number);

    List<Phone> getDistinctPersonPhones(Collection<Person> people, int n, String number);

    double calculateAverageWeight(Collection<Person> people);

    Person getYoungest(Collection<Person> people);

    List<String> flatGroupPhonesByOperator(Collection<Person> people);
    int countGroupedByGender(Collection<Person> people);


}
