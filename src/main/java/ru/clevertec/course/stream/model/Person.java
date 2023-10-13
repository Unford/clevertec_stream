package ru.clevertec.course.stream.model;

import java.util.List;

public class Person {
    private String name;
    private int age;
    private double weight;
    Gender gender;
    List<Phone> phones;

    public Person(String name, int age, double weight, Gender gender, List<Phone> phones) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.phones = phones;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public Person setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Person setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public Person setPhones(List<Phone> phones) {
        this.phones = phones;
        return this;
    }
}
