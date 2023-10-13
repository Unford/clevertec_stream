package ru.clevertec.course.stream.service.impl;

import ru.clevertec.course.stream.model.Gender;
import ru.clevertec.course.stream.model.Person;
import ru.clevertec.course.stream.model.Phone;
import ru.clevertec.course.stream.service.PersonService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class PersonServiceImpl implements PersonService {
    private static PersonServiceImpl instance;

    private PersonServiceImpl() {
    }


    public static PersonServiceImpl getInstance() {
        if (instance == null) {
            instance = new PersonServiceImpl();
        }
        return instance;
    }

    @Override
    public void findAllOlder(Collection<Person> people, int n) {

    }

    @Override
    public void printNameOfAllHeavier(Collection<Person> people, double n) {

    }

    @Override
    public void printNumbersWherePhoneAmountGreater(Collection<Person> people, int n) {

    }

    @Override
    public String joinAllNames(Collection<Person> people, char joiner) {
        return null;
    }

    @Override
    public void printSortedByAgeAndName(Collection<Person> people) {

    }

    @Override
    public Map<Gender, Person> groupByGender(Collection<Person> people) {
        return null;
    }

    @Override
    public boolean containsPhoneNumber(Collection<Person> people, String number) {
        return false;
    }

    @Override
    public List<Phone> getDistinctPersonPhones(Collection<Person> people, int n, String number) {
        return null;
    }

    @Override
    public double calculateAverageWeight(Collection<Person> people) {
        return 0;
    }

    @Override
    public Person getYoungest(Collection<Person> people) {
        return null;
    }

    @Override
    public List<String> flatGroupPhonesByOperator(Collection<Person> people) {
        return null;
    }

    @Override
    public int countGroupedByGender(Collection<Person> people) {
        return 0;
    }
}
