package ru.netology.setting;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public class Registration {
        public static RegistrationByCardInfo registrationInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationByCardInfo(faker.address().cityName(),
                    faker.name().fullName(), faker.phoneNumber().phoneNumber());
        }
    }
}
