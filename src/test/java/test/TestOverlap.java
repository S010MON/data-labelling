package test;

import app.display.BoundingBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestOverlap
{
    @Test void true_x()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(0,9,10,10);
        assertTrue(b1.overlap(b2));
    }

    @Test void true_y()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(9,0,10,10);
        assertTrue(b1.overlap(b2));
    }

    @Test void false_x()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(11,0,10,10);
        assertFalse(b1.overlap(b2));
    }

    @Test void false_y()
    {
        BoundingBox b1 = new BoundingBox(0,0,10,10);
        BoundingBox b2 = new BoundingBox(0,11,10,10);
        assertFalse(b1.overlap(b2));
    }

}
