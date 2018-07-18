import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RunnerTest {

    @Test
    public void main() {
        try {
            Runner.main(new String[]{"", "", "", ""});
        }catch (RuntimeException e){
            Assert.assertNotEquals("", e.getMessage());
        }
        try {
            Runner.main(new String[]{"", ""});
        }catch (RuntimeException e){
            Assert.assertNotEquals("", e.getMessage());
        }
        try {
            Runner.main(new String[]{""});
        }catch (RuntimeException e){
            Assert.assertNotEquals("", e.getMessage());
        }
        try {
            Runner.main(new String[]{});
        }catch (RuntimeException e){
            Assert.assertNotEquals("", e.getMessage());
        }
    }
}