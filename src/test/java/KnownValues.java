import org.example.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KnownValues
{
    @Test
    public void testSmall()
    {
        Assertions.assertEquals(1, Main.countAllFor(1, 1));
        Assertions.assertEquals(2, Main.countAllFor(1, 2));
        Assertions.assertEquals(2, Main.countAllFor(2, 1));
        Assertions.assertEquals(8, Main.countAllFor(2, 2));
    }
    
    @Test
    public void testSymmetric()
    {
        // Property-based testing, should be same number if you swap dimensions:
        for (int x = 1; x < 5; x++)
        {
            for (int y = 1; y < 5; y++)
            {
                Assertions.assertEquals(Main.countAllFor(x, y), Main.countAllFor(y, x));
            }
        }
        
    }
}