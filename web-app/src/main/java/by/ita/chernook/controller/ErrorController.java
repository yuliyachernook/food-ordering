package by.ita.chernook.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ErrorController {

    private final static String ERROR_PAGE_NAME = "error";
    private final static String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleHttpClientErrorException(HttpClientErrorException ex, Model model) {
        model.addAttribute(ERROR_ATTRIBUTE_NAME, String.format("Произошла ошибка: %s", ex.getMessage()));
        return ERROR_PAGE_NAME;
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public String handleHttpServerErrorException(HttpServerErrorException ex, Model model) {
        if (ex.getResponseBodyAsString().contains("Insufficient funds")) {
            model.addAttribute(ERROR_ATTRIBUTE_NAME, "Не удалось создать заказ. Недостаточно средств.");
        } else {
            model.addAttribute(ERROR_ATTRIBUTE_NAME, String.format("Произошла ошибка: %s", ex.getMessage()));
        }
        return ERROR_PAGE_NAME;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute(ERROR_ATTRIBUTE_NAME, String.format("Произошла ошибка: %s", ex.getMessage()));
        return ERROR_PAGE_NAME;
    }
}
