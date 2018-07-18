package helper;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenerationHelperTest {

    private LocalDateTime maxTime = LocalDateTime.of(LocalDate.now().getYear(), 1, 1, 0, 0);
    private LocalDateTime minTime = LocalDateTime.of(LocalDate.now().getYear() - 1, 1, 1, 0, 0);

    @Test
    public void writeLines() {
        List<String> list = Arrays.asList("home", "office");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GenerationHelper.writeLines(list, baos, 5);
        String[] lines = baos.toString().split(System.getProperty("line.separator"));
        Assert.assertEquals(5, lines.length);
        for (int i = 0; i < 5; i++) {
            String[] words = lines[i].split("__");
            LocalDateTime date = LocalDateTime.parse(words[0].trim().replace(' ', 'T'));
            Assert.assertTrue(dateToLong(maxTime) > dateToLong(date) && dateToLong(date) >= dateToLong(minTime));
            Assert.assertTrue(words[1].equals("home") || words[1].equals("office"));
            int counter = Integer.parseInt(words[2]);
            Assert.assertEquals(counter, i + 1);
            double d = Double.parseDouble(words[3]);
            Assert.assertTrue(d >= 10000.00 && d < 100_000.00);
        }
    }

    private static long dateToLong(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Test
    public void getRandomInt() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(GenerationHelper.getRandomInt(60, 10));
        }
        Assert.assertTrue(Collections.min(list) >= 10 && Collections.max(list) < 70);
    }

    @Test
    public void getRandomInt1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(GenerationHelper.getRandomInt(60));
        }
        Assert.assertTrue(Collections.min(list) >= 0 && Collections.max(list) < 60);
    }

    @Test
    public void getDate() {
        List<LocalDate> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(LocalDate.parse(GenerationHelper.getDate(false)));
        }
        LocalDate max = maxTime.toLocalDate();
        LocalDate min = minTime.toLocalDate();
        Assert.assertTrue(Collections.min(list).toEpochDay() >= min.toEpochDay() && Collections.max(list).toEpochDay() < max.toEpochDay());
    }

    @Test
    public void getPrice() {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(Double.valueOf(GenerationHelper.getPrice()));
        }
        Assert.assertTrue(Collections.min(list) >= 10000.00 && Collections.max(list) < 100_000.00);
    }
}