import helper.GenerationHelper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class GenerationServiceTest {

    @Test
    public void generate() {
        try {
            new GenerationService("tmp", 2, System.getProperty("user.home") + "/test.tmp").generate();
        }catch (RuntimeException e){
            Assert.assertEquals(e.getMessage(), "java.nio.file.NoSuchFileException: tmp");
        }

    }
}