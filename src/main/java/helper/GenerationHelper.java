package helper;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Утилитарный класс для работы со списком точек продаж.
 */
public class GenerationHelper {
    private static final Logger LOGGER = Logger.getLogger(GenerationHelper.class);
    private static final java.util.Random RANDOM = new java.util.Random();

    public static boolean deleteData(String path) {
        try {
            return Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            LOGGER.error("Неудачная попытка удаления уже существующего файла " + path);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param offices      список офисов для рандомной вставки в записи
     * @param stream       куда писать
     * @param eventCounter количество итераций записи
     */
    public static void writeLines(List<String> offices, OutputStream stream, int eventCounter) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(stream, StandardCharsets.UTF_8.newEncoder()))) {
            for (int i = 1; i <= eventCounter; i++) {
                writer.write(GenerationHelper.getDate(true) + "__");
                writer.write(offices.get(GenerationHelper.getRandomInt(offices.size())) + "__");
                writer.write(i + "__");
                writer.write(GenerationHelper.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("Неудачная попытка создания файла: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @param fileName путь до файла.
     * @return список строк из файла.
     */
    public static List<String> readFile(String fileName) {
        try {
            List<String> result = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            LOGGER.info("Прочитанно " + result.size() + " филиалов из файла " + fileName);
            return result;
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
        return Double.toString(price);
    }
}
