package main.struct;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PowersetEntry {

	private Set<Integer> newNode = new HashSet<>();
	private HashMap<Character, Set<Integer>> entry = new HashMap<>();

	/**
	 * Constructor to init new PowersetEntry
	 * @param nodeSet new Set of Integer (nodeIds)
	 */
	public PowersetEntry(Set<Integer> nodeSet) {
		this.newNode = nodeSet;
	}

	/**
	 * Adds a nodeId and a token to the hashMap
	 * @param token character token
	 * @param nodeId integer nodeId
	 */
	public void add(char token, int nodeId) {
		if(entry.containsKey(token)) {
			entry.get(token).add(nodeId);
		}else{
			Set<Integer> newSet = new HashSet<>();
			newSet.add(nodeId);
			entry.put(token, newSet);
		}
	}

	/**
	 * Adds a Set of nodeIds and a token to the hashMap
	 * @param token new Token
	 * @param nodeId new Integer Set
	 */
	public void addAll(char token, Set<Integer> nodeId) {
		if(entry.containsKey(token)) {
			entry.get(token).addAll(nodeId);
		}else{
			entry.put(token, nodeId);
		}
	}

	/**
	 * Returns the Set of NodeIds for on token
	 * @param token given char token
	 * @return Integer set or null, if token is not in hashMap
	 */
	public Set<Integer> getNodeSetByToken(char token) {
		if(entry.containsKey(token)) {
			return entry.get(token);
		}
		return null;
	}

	/**
	 * Getter for newNode
	 * @return IntegerSet of nodeIds
	 */
	public Set<Integer> getNewNode() {
		return newNode;
	}
}