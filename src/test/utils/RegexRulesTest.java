package test.utils;

import main.FSM.NFA;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static main.utils.RegexRules.*;

public class RegexRulesTest {

	static NFA nfa1, nfa2;

	@Before
	public void initNFAs() {
		nfa1 = createTokenNFA('a');
		nfa2 = createTokenNFA('b');
	}

	@Test
	public void createTokenNFATest() {
		assertTrue(nfa1.testWord("a"));
		assertFalse(nfa1.testWord("aa"));

		assertTrue(nfa2.testWord("b"));
		assertFalse(nfa2.testWord("bb"));
	}
	@Test
	public void createOrNFATest() {
		NFA orNFA = createOrNFA(nfa1, nfa2);
		assertTrue(orNFA.testWord("a"));
		assertTrue(orNFA.testWord("b"));
		assertFalse(orNFA.testWord("ab"));
	}
	@Test
	public void createConcatNFATest() {
		NFA concatNFA = createConcatNFA(nfa1, nfa2);
		assertTrue(concatNFA.testWord("ab"));
		assertFalse(concatNFA.testWord("a"));
		assertFalse(concatNFA.testWord("abb"));
	}
	@Test
	public void createKleeneStarTest() {
		NFA starNFA = createKleeneStarNFA(nfa1);
		assertTrue(starNFA.testWord(""));
		assertTrue(starNFA.testWord("a"));
		assertTrue(starNFA.testWord("aaa"));
	}
	@Test
	public void createKleenePlusTest() {
		NFA plusNFA = createKleenePlusNFA(nfa1);
		assertFalse(plusNFA.testWord(""));
		assertTrue(plusNFA.testWord("a"));
		assertTrue(plusNFA.testWord("aaa"));
	}
}
