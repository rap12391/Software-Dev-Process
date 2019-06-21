package edu.gatech.seclass;

import org.junit.Test;
import static org.junit.Assert.*;

public class WhiteboxClassPC2 {
    @Test
    public void PC2Test1() {
        assertEquals(1, WhiteboxClass.whiteboxMethod2(5));
    }

    @Test
    public void PC2Test2() {
        assertEquals(0, WhiteboxClass.whiteboxMethod2(20));
    }
}
