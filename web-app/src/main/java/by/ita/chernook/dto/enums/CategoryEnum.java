package by.ita.chernook.dto.enums;

import lombok.Getter;

@Getter
public enum CategoryEnum {
    PIZZA("Пицца"),
    SUSHI("Суши"),
    SEAFOOD("Морепродукты"),
    SALADS("Салаты"),
    DESSERTS("Десерты"),
    DRINKS("Напитки");

    private final String name;

    CategoryEnum(String name) {
        this.name = name;
    }
}
