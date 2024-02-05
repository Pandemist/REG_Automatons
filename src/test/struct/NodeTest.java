package test.struct;

import main.struct.Node;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    static Node testNode;

    @Before
    public void initNode() {
        testNode = new Node();
        testNode.setFullName("n_1");
        testNode.setId(1);
    }

    @Test
    public void fullNameTest() {
        assertEquals("n_1", testNode.getFullName());
    }
    @Test
    public void idTest() {
        assertEquals(1, testNode.getId());
    }
}














