package by.ita.chernook.dto.enums;

import lombok.Getter;

@Getter
public enum CouponTypeEnum {
    PERCENT("Процентная скидка"),
    FIXED("Фиксированная скидка");

    private final String name;

    CouponTypeEnum(String name) {
        this.name = name;
    }
}
