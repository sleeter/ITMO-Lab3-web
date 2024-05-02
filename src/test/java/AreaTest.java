import checker.AreaChecker;
import entity.DotEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AreaTest {
    private DotEntity dot;
    @BeforeEach
    public void create() {
        dot = new DotEntity();
        dot.setX(0);
        dot.setY(0);
        dot.setR(1);
    }
    @AfterEach
    public void reset() {
        dot = null;
    }
    @Test
    public void firstTest() {
        dot.setX(3.2f);
        dot.setY(0.8f);
        dot.setR(1);
        assertFalse(AreaChecker.check(dot));
    }
    @Test
    void testIsCircleInside() {
        DotEntity dotInsideCircle = new DotEntity(1L, 1.0f, -1.0f, 4.0f, true, "12:00", 1000L);
        assertTrue(AreaChecker.check(dotInsideCircle));
    }

    @Test
    void testIsTriangleInside() {
        DotEntity dotInsideTriangle = new DotEntity(2L, 1.0f, 1.0f, 4.0f, true, "12:00", 1000L);
        assertTrue(AreaChecker.check(dotInsideTriangle));
    }

    @Test
    void testIsRectangleInside() {
        DotEntity dotInsideRectangle = new DotEntity(3L, -1.0f, -1.0f, 2.0f, true, "12:00", 1000L);
        assertTrue(AreaChecker.check(dotInsideRectangle));
    }

    @Test
    void testIsOutsideAllShapes() {
        DotEntity dotOutsideAllShapes = new DotEntity(4L, 3.0f, 3.0f, 2.0f, true, "12:00", 1000L);
        assertFalse(AreaChecker.check(dotOutsideAllShapes));
    }

}
