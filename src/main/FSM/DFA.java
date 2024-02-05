package main.FSM;

import main.struct.PowersetEntry;
import main.struct.Transition;

import java.util.*;

import static main.FSM.NFA.makeNFAFromRegex;

public class DFA extends Automaton {

	private int startStateId;

	/**
	 * Constructor of the NFA
	 * NOTE: Default start node has id 0
	 */
	public DFA() {
		super.setNodeIdCounter(0);

		int startNodeId = addNode();
		startStateId = startNodeId;
	}

	/**
	 * Constructs a DFA by a given NFA by Powerset construction (Potenzmengenkonstruktion)
	 * @param regex Regular Expression to create the DFA from
	 */
	public DFA(String regex) {
		NFA nfa = makeNFAFromRegex(regex);
		startStateId = -1;

		HashMap<Set, Integer> oldNewNodeMapping = new HashMap<>();
		ArrayList<PowersetEntry> newNodes = new ArrayList<>();
		ListIterator<PowersetEntry> it = newNodes.listIterator();

		PowersetEntry newPow = new PowersetEntry(new HashSet<>(nfa.getStartStateId()));
		it.add(newPow);
		it.previous();

		Set<Integer> acctualNodes;
		PowersetEntry pow;
		while(it.hasNext()) {
			pow = it.next();
			acctualNodes = pow.getNewNode();
			for(int i : acctualNodes) {
				for(char token : nfa.getAlphabet()) {
					pow.addAll(token, nfa.getAllNodeIdsForToken(token, i));
				}
			}
			for(char alphabetToken : nfa.getAlphabet()) {
				Set<Integer> is = pow.getNodeSetByToken(alphabetToken);
				newPow = new PowersetEntry(is);
				boolean isIn = false;
				for(PowersetEntry pwSets : newNodes) {
					if(pwSets.getNewNode().equals(is)) {
						isIn = true;
					}
				}
				if(!isIn && is.size()!=0) {
					it.add(newPow);
					it.previous();
				}
			}
		}

		super.setNodeIdCounter(0);

		for(PowersetEntry poSet : newNodes) {
			Set<Integer> s = poSet.getNewNode();
			int newNodeId = addNode();
			oldNewNodeMapping.put(s, newNodeId);
			for(int id : s) {
				if(nfa.getEndStateId().contains(id)) {
					addEndStateId(newNodeId);
				}
			}
			boolean startState = true;
			for(int id : s) {
				if(!nfa.getStartStateId().contains(id)) {
					startState = false;
				}
			}
			if(startState) {
				startStateId = newNodeId;
			}
		}
		//Adds an ErrorNode and copy the alphabet from NFA to DFA
		int errorNodeId = addNode();
		oldNewNodeMapping.put(new HashSet<Integer>(), errorNodeId);
		for(char c : nfa.getAlphabet()) {
			this.addToAlphabet(c);
			addTransition(errorNodeId, c, errorNodeId);
		}

		for(PowersetEntry powSet : newNodes) {
			for(char c : nfa.getAlphabet()) {
				int startNodeId = oldNewNodeMapping.get(powSet.getNewNode());
				int endNodeId = oldNewNodeMapping.get(powSet.getNodeSetByToken(c));
				addTransition(startNodeId, c, endNodeId);
			}
		}
	}

	public boolean testWord(String word) {
		int nodeId = startStateId;
		for(char c : word.toCharArray()) {
			for(Transition t : getTransitionList()) {
				if(t.getFromNodeId() == nodeId && t.getToken() == c) {
					nodeId = t.getToNodeId();
					break;
				}
			}
		}
		if(getEndStateId().contains(nodeId)) {
			return true;
		}
		return false;
	}

	//---DEBUG---------------------------------------------

	/**
	 * Methode to print the actual NFA
	 */
	public void printDFA() {
		System.out.println("Z: "+ nodeListString());
		System.out.println("Delta: "+ transitionString());
		System.out.println("q: "+startStateId);
		System.out.println("E: "+getEndStateId().toString());
	}
}
