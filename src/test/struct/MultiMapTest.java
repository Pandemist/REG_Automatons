package test.struct;

import main.struct.MultiMap;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.*;

public class MultiMapTest {

	MultiMap<Integer, String> testMap = new MultiMap<>();

	@Before
	public void initMultiMap() {
		testMap.put(1, "aaa");
		testMap.put(1, "bbb");
		testMap.put(2, "ccc");
	}

	@Test
	public void getNodeNotExistTest() {
		assertEquals(null, testMap.get(20));
	}

	@Test
	public void getTest() {
		Collection<String> testCol = new HashSet<>();
		testCol.add("ccc");
		assertEquals(testCol, testMap.get(2));
	}

	@Test
	public void getKeyTest() {
		Set<Integer> testSet = new HashSet<>();
		testSet.add(1);
		testSet.add(2);
		assertEquals(testSet, testMap.getKey());
	}

	@Test
	public void containsKeyTest() {
		assertTrue(testMap.containsKey(1));
	}
}
