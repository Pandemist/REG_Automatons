package test.FSM;

import main.FSM.NFA;
import org.junit.*;

import java.util.HashSet;
import java.util.Set;

import static main.FSM.NFA.*;
import static org.junit.Assert.*;

public class NFATest {

	@Test
	public void wrongRegexTest() {
		NFA testNFA = new NFA();
		assertEquals(testNFA.getNodeIdCounter(), makeNFAFromRegex("(a(").getNodeIdCounter());
	}

    @Test
    public void makeNFAFromRegexTest() {
        NFA regexTestNFA = makeNFAFromRegex("a*(b|bb)c+");
	//    System.out.println("\nREGEX");
    //    regexTestNFA.printNFA();
        assertTrue(regexTestNFA.testWord("aabbccc"));
        assertTrue(regexTestNFA.testWord("aabcc"));
        assertTrue(regexTestNFA.testWord("aabc"));
        assertFalse(regexTestNFA.testWord("aabbb"));
    }

    @Test
	public void getAllNodeIdsForTokenTest() {
    	NFA testNFA = makeNFAFromRegex("a+b+");
    //	testNFA.printNFA();
	    Set<Integer> testSet = new HashSet<>();
	    testSet.add(1);
	    testSet.add(2);
	    testSet.add(3);
	    testSet.add(4);
	    testSet.add(6);
	    testSet.add(7);
	    assertEquals(testSet, testNFA.getAllNodeIdsForToken('a', 0));
    }

    @Test
	public void printTest() {
		NFA testNFA = makeNFAFromRegex("a");
		testNFA.printNFA();
		assertTrue(true);
    }
}
