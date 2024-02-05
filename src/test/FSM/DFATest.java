package test.FSM;

import main.FSM.DFA;
import main.FSM.NFA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static main.FSM.NFA.makeNFAFromRegex;
import static org.junit.Assert.assertFalse;

public class DFATest {

	DFA dfa1;

	@Before
	public void initDFA() {
		dfa1 = new DFA();
		dfa1.addNode();
		dfa1.addNode();
		dfa1.addTransition(0, 'a', 1);
		dfa1.addTransition(1, 'b', 2);
		dfa1.addEndStateId(2);
	}

	@Test
	public void testWordTest() {
		assertTrue(dfa1.testWord("ab"));
		assertFalse(dfa1.testWord("a"));
	}

	@Test
	public void DFAbyNFA() {
		DFA dfa2 = new DFA("a*b");
		assertTrue(dfa2.testWord("aaab"));
		assertTrue(dfa2.testWord("b"));
		assertFalse(dfa2.testWord("aa"));
	}

	@Test
	public void printTest() {
		DFA testDFA = new DFA();
		testDFA.printDFA();
		Assert.assertTrue(true);
	}
}
