package by.ita.chernook.dto.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW("Новый"),
    PROCESSING("В обработке"),
    READY("Готов"),
    OUT_FOR_DELIVERY("В пути"),
    DELIVERED("Доставлен"),
    COMPLETED("Завершен"),
    CANCELED("Отменен");

    private final String name;

    OrderStatusEnum(String name) {
        this.name = name;
    }
}