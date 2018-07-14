package helper;

import com.github.javafaker.Faker;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Утилитарный класс для работы со списком точек продаж.
 */
public class GenerationHelper {
    private static final Logger LOGGER = Logger.getLogger(GenerationHelper.class);
    private static final String DEFAULTPATH = System.getProperty("user.home") + "/Downloads/offices.txt";
    private static final java.util.Random RANDOM = new java.util.Random();

    /**
     * Создает файл с рандомным количеством рандомных адресов.
     * Используется при запуске приложения без агрументов.
     *
     * @return путь сохранения файла с сгенерированными адресами точек продаж.
     */
    public static String createTestFile() {
        Faker faker = new Faker(Locale.getDefault());
        int length = getRandomInt(60, 40);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(faker.address().city())
                    .append(", ")
                    .append(faker.address().streetAddress())
                    .append("\n");
        }
        faker.date().birthday(3, 4);
        writeData(DEFAULTPATH, builder.toString());
        return DEFAULTPATH;
    }

    /**
     * @param path куда писать.
     * @param data что писать.
     */
    public static void writeData(String path, String data) {
        try {
            Files.write(Paths.get(path), data.getBytes());
        } catch (IOException e) {
            LOGGER.error("Неудачная попытка создания файла: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @param fileName путь до файла.
     * @return массив строк из файла.
     */
    public static String[] readFile(String fileName) {
        try {
            String allLines = new String(Files.readAllBytes(Paths.get(fileName)));
            return allLines.split(System.lineSeparator());
        } catch (IOException e) {
            LOGGER.error("Неудачная попытка чтения файла: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static int getRandomInt(int multiplicity) {
        return getRandomInt(multiplicity, 0);
    }

    /**
     * @param multiplicity величина разброса значений.
     * @param minValue     минимально возможное возвращаемое значение.
     * @return рандомное число.
     */
    public static int getRandomInt(int multiplicity, int minValue) {
        return RANDOM.nextInt(multiplicity) + minValue;
    }

    /**
     * Генерирует дату в пределах предшествующего года.
     *
     * @param withTime возвращать "дата-время", вместо "дата"
     * @return строковое представление даты
     */
    public static String getDate(boolean withTime) {
        int lastYear = LocalDate.now().getYear() - 1;
        LocalDate date = LocalDate.of(lastYear, 1, 1);
        if (withTime) {
            int x = getRandomInt(date.lengthOfYear() * 24 * 60);
            LocalDateTime dateTime = LocalDateTime.of(lastYear, 1, 1, 0, 0);
            return dateTime.plusMinutes(x).toString().replace('T', ' ');
        }
        int x = getRandomInt(date.lengthOfYear());
        return date.plusDays(x).toString();
    }

    /**
     * @return рандомное число в виде строки.
     */
    public static String getPrice() {
        double price = 10_000.00 + (RANDOM.nextDouble() * (90_000.00));
        return new DecimalFormat("#0.00").format(price);
    }
}
