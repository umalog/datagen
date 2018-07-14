package helper;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerationHelperTest {

    @Test
    public void createTestFile() throws NoSuchFieldException, IllegalAccessException, IOException {
        Field field = GenerationHelper.class.getDeclaredField("DEFAULTPATH");
        field.setAccessible(true);
        String s = (String) field.get(GenerationHelper.class);
        GenerationHelper.createTestFile();
        Assert.assertTrue(Files.deleteIfExists(Paths.get(s)));

    }

    @Test
    public void writeData() {
        try {
            GenerationHelper.writeData("C:/wrong address", "test");
        } catch (RuntimeException e) {
            Assert.assertEquals("java.nio.file.NoSuchFileException: C:/wrong address", e.getMessage());
        }
    }

    @Test
    public void readFile() {
        try {
            GenerationHelper.readFile("C:/wrong address");
        } catch (RuntimeException e) {
            Assert.assertEquals("java.nio.file.NoSuchFileException: C:/wrong address", e.getMessage());
        }
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
        LocalDate max = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate min = LocalDate.of(LocalDate.now().getYear() - 1, 1, 1);
        Assert.assertTrue(Collections.min(list).toEpochDay() >= min.toEpochDay() && Collections.max(list).toEpochDay() < max.toEpochDay());
    }

    @Test
    public void getPrice() {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add(Double.valueOf(GenerationHelper.getPrice().replace(',', '.')));
        }
        Assert.assertTrue(Collections.min(list) >= 10000.00 && Collections.max(list) < 100_000.00);
    }
}