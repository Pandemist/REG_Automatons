package test.utils;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static main.utils.regexUtils.syntaxCheck;

public class regexUtilsTest {

	@Test
	public void syntaxCheckTest() {
		assertTrue(syntaxCheck("(a|b)*"));
	}
}
