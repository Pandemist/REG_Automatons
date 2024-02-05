package test.FSM;

import main.FSM.Automaton;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AutomatonTest {

	Automaton autom;

	@Before
	public void initAutomaton() {
		autom = new Automaton();
		int node1 = autom.addNode();
		int node2 = autom.addNode(2);
		autom.addTransition(node1, 'b', node2);
	}

	@Test
	public void addNodeTest() {
		assertEquals(3, autom.addNode());
	}
	@Test
	public void addNodeIdTest() {
		assertEquals(5, autom.addNode(5));
	}
}
