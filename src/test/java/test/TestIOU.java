package test;

import app.display.BoundingBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIOU
{
    @Test void testNoIntersect()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(100,100,10,10);
        assertEquals(0, b1.IoU(b2), 0.001);
    }

    @Test void testFullIntersect()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(0,0,10,10);
        assertEquals(1, b1.IoU(b2), 0.001);
    }

    @Test void testHalfIntersect()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(5,0,10,10);
        assertEquals(0.333, b1.IoU(b2), 0.001);
    }

    @Test void cornerIntersect()
    {
        BoundingBox b1 = new BoundingBox(0,0,3,3);
        BoundingBox b2 = new BoundingBox(2,2,3,3);
        assertEquals(0.0588, b1.IoU(b2), 0.001);
    }
}
