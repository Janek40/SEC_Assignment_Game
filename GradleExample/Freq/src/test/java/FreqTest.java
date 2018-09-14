import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class FreqTest
{
    @Test
    public void testFreq()
    {
        final String[] args = {"abs", "abs", "d", "z"};
	final String expected = "abs:2\nd:1\nz:1\n";
	assertEquals(expected, Freq.getFreq(args));
    }
}
