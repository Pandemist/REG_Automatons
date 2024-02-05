package test.struct;

import main.struct.Transition;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransitionTest {

    static Transition transition;

    @Before
    public void initTransition() {
        transition = new Transition(1, 'c', 2);
    }

    @Test
    public void getFromNodeTest() {
        assertEquals(1, transition.getFromNodeId());
    }
    @Test
    public void getTokenTest() {
        assertEquals('c', transition.getToken());
    }
    @Test
    public void getToNodeTest() {
        assertEquals(2, transition.getToNodeId());
    }
}
