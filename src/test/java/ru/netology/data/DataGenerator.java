package ru.netology.data;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));

    private DataGenerator() {}

    public static String generateCity() {

        String[] cities = {"Москва", "Санкт-Петербург", "Казань", "Нижний Новгород", "Екатеринбург"};
        return cities[faker.random().nextInt(cities.length)];
    }

    public static String generateName() {

        return faker.name().fullName();
    }

    public static String generatePhone() {

        String phone = faker.phoneNumber().phoneNumber();

        String digits = phone.replaceAll("[^0-9]", "");
        if (digits.length() == 10) {
            return "+7" + digits;
        } else if (digits.length() == 11 && digits.startsWith("7")) {
            return "+" + digits;
        } else if (digits.length() == 11 && digits.startsWith("8")) {
            return "+7" + digits.substring(1);
        }
        // fallback
        return "+7" + faker.number().digits(10);
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static OrderInfo generateOrder(String date) {
        return new OrderInfo(generateCity(), generateName(), generatePhone(), date);
    }
}