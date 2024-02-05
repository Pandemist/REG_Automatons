package test.struct;

import main.struct.PowersetEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PowersetEntryTest {

	PowersetEntry entry1;

	@Before
	public void initPowersetEntry() {
		Set<Integer> nodeEntrys = new HashSet<>();
		nodeEntrys.add(0);
		nodeEntrys.add(1);
		nodeEntrys.add(2);
		entry1 = new PowersetEntry(nodeEntrys);
		entry1.add('a', 1);
		entry1.add('a', 2);
		entry1.add('c', 1);
	}

	@Test
	public void getNodeSetByTokenNotExistTest() {
		assertEquals(null, entry1.getNodeSetByToken('x'));
	}

	@Test
	public void getNodeSetByTokenTest() {
		Set<Integer> nodeEntrys = new HashSet<>();
		nodeEntrys.add(1);
		nodeEntrys.add(2);
		assertEquals(nodeEntrys, entry1.getNodeSetByToken('a'));
	}
	@Test
	public void addAllTest() {
		Set<Integer> nodeEntrys = new HashSet<>();
		nodeEntrys.add(4);
		nodeEntrys.add(5);
		entry1.addAll('e', nodeEntrys);
		assertEquals(nodeEntrys, entry1.getNodeSetByToken('e'));
	}
}
