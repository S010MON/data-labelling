import labelling.display.BoundingBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIOU
{
    @Test void testNoIntersect()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(100,100,10,10);
        assertEquals(0, b1.IoU(b2));
    }

    @Test void testFullIntersect()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(0,0,10,10);
        assertEquals(1, b1.IoU(b2));
    }

    @Test void testHalfIntersect()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(5,5,10,10);
        assertEquals(0.5, b1.IoU(b2));
    }
}
