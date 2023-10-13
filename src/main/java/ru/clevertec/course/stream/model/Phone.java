package ru.clevertec.course.stream.model;

import java.util.Objects;

public class Phone {
    private String number;

    private Operator operator;

    public Phone(String number, Operator operator) {
        this.number = number;
        this.operator = operator;
    }

    public Phone() {

    }

    public String getNumber() {
        return number;
    }

    public Phone setNumber(String number) {
        this.number = number;
        return this;
    }

    public Operator getOperator() {
        return operator;
    }

    public Phone setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone phone)) return false;

        if (!Objects.equals(number, phone.number)) return false;
        return operator == phone.operator;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Phone{");
        sb.append("number='").append(number).append('\'');
        sb.append(", operator=").append(operator);
        sb.append('}');
        return sb.toString();
    }
}
