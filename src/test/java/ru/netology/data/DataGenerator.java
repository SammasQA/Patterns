package ru.netology.data;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private DataGenerator() {}

    //  дата  dd.MM.yyyy
    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    public static String generateCity(Faker faker) {
        String[] cities = {"Москва", "Санкт-Петербург", "Казань", "Нижний Новгород", "Екатеринбург"};
        int index = ThreadLocalRandom.current().nextInt(cities.length);
        return cities[index];
    }


    public static String generateName(Faker faker) {
        return faker.name().fullName();
    }


    public static String generatePhone(Faker faker) {
        return faker.phoneNumber().phoneNumber();
    }


    public static class Registration {
        private Registration() {}

        public static OrderInfo generateUser(String locale) {
            Faker faker = new Faker(new Locale(locale));
            String city = generateCity(faker);
            String name = generateName(faker);
            String phone = generatePhone(faker);
            return new OrderInfo(city, name, phone);
        }
    }
}