package ru.netology.setting;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Getter
public class DataGenerator {

    public DataGenerator() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getRandomCity() {
        String city = faker.address().city();
        return city;
    }

    public static String getRandomName() {
        String name = faker.name().name();
        return name;
    }

    public static String getRandomPhone() {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static RegistrationByCardInfo getUser() {
        RegistrationByCardInfo user = new RegistrationByCardInfo(getRandomCity(), getRandomName(), getRandomPhone());
        return user;
    }
}