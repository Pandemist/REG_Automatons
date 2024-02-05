package main.utils;

import main.FSM.NFA;
import main.struct.Node;
import main.struct.Transition;

public class RegexRules {

	/**
	 * Creates a new NFA only contains a transition with the given Token form start to end node
	 * @param token given token from alphabet
	 * @return new NFA
	 */
	public static NFA createTokenNFA(char token) {
		NFA nfa = new NFA();
		nfa.addEndStateId(nfa.addNode());
		nfa.addTransition(nfa.getStartStateId().get(0), token, nfa.getEndStateId().get(0));
		return nfa;
	}

	/** UNION
	 * Creates a new nfa which has the parts nfa1 or nfa2
	 * @param nfa1 Given NFA
	 * @param nfa2 Given NFA
	 * @return NFA with the or Parts nfa1 and nfa2
	 */
	public static NFA createOrNFA(NFA nfa1, NFA nfa2) {
		NFA orNFA = new NFA();
		orNFA.addEndStateId(orNFA.addNode());
		int difference = orNFA.getNodeIdCounter();
		//Add NFA1
		orNFA.addTransition(orNFA.getStartStateId().get(0), Character.MIN_VALUE, nfa1.getStartStateId().get(0)+difference);
		for(Node node : nfa1.getNodeList()) {
			orNFA.addNode(node.getId()+difference);
		}
		orNFA.addTransition(nfa1.getEndStateId().get(0)+difference, Character.MIN_VALUE, orNFA.getEndStateId().get(0));

		for(Transition t : nfa1.getTransitionList()) {
			orNFA.addTransition(t.getFromNodeId()+difference, t.getToken(), t.getToNodeId()+difference);
		}
		//Add NFA2
		difference = orNFA.getNodeIdCounter();
		orNFA.addTransition(orNFA.getStartStateId().get(0), Character.MIN_VALUE, nfa2.getStartStateId().get(0)+difference);
		for(Node node : nfa2.getNodeList()) {
			orNFA.addNode(node.getId()+difference);
		}
		orNFA.addTransition(nfa2.getEndStateId().get(0)+difference, Character.MIN_VALUE, orNFA.getEndStateId().get(0));

		for(Transition t : nfa2.getTransitionList()) {
			orNFA.addTransition(t.getFromNodeId()+difference, t.getToken(), t.getToNodeId()+difference);
		}
		return orNFA;
	}

	/** CONCAT
	 * Creates a new NFA which concatenates the given NFAs 1 and 2
	 * @param nfa1 first NFA
	 * @param nfa2 second NFA
	 * @return new concat NFA
	 */
	public static NFA createConcatNFA(NFA nfa1, NFA nfa2) {
		NFA concatNFA = new NFA();
		int difference = concatNFA.getNodeIdCounter();
		concatNFA.addTransition(concatNFA.getStartStateId().get(0), Character.MIN_VALUE, nfa1.getStartStateId().get(0)+difference);
		for(Node node : nfa1.getNodeList()) {
			concatNFA.addNode(node.getId()+difference);
		}
		for(Transition t : nfa1.getTransitionList()) {
			concatNFA.addTransition(t.getFromNodeId()+difference, t.getToken(), t.getToNodeId()+difference);
		}
		int endOfNFA1 = nfa1.getEndStateId().get(0)+difference;

		difference = concatNFA.getNodeIdCounter();
		for(Node node : nfa2.getNodeList()) {
			concatNFA.addNode(node.getId()+difference);
		}
		for(Transition t : nfa2.getTransitionList()) {
			concatNFA.addTransition(t.getFromNodeId()+difference, t.getToken(), t.getToNodeId()+difference);
		}
		concatNFA.addTransition(endOfNFA1, Character.MIN_VALUE, nfa2.getStartStateId().get(0)+difference);
		concatNFA.addEndStateId(nfa2.getEndStateId().get(0)+difference);
		return concatNFA;
	}

	/** KLEENE STAR
	 * Creates a new NFA which is a kleene star version of the in given NFA
	 * @param nfa1 Given NFA
	 * @return NFA with a kleene star wrapping
	 */
	public static NFA createKleeneStarNFA(NFA nfa1) {
		nfa1.addTransition(nfa1.getEndStateId().get(0), Character.MIN_VALUE, nfa1.getStartStateId().get(0));
		nfa1.getEndStateId().clear();
		nfa1.addEndStateId(nfa1.getStartStateId().get(0));
		return nfa1;
	}

	/** KLEENE PLUS
	 * Creates a new NFA which is a kleene plus version of the in given NFA
	 * @param nfa1 Given NFA
	 * @return NFA with a kleen plus wrapping
	 */
	public static NFA createKleenePlusNFA(NFA nfa1) {
		NFA plusNFA = new NFA();
		int difference = plusNFA.getNodeIdCounter();
		plusNFA.addTransition(plusNFA.getStartStateId().get(0), Character.MIN_VALUE, nfa1.getStartStateId().get(0)+difference);
		for(Node node : nfa1.getNodeList()) {
			plusNFA.addNode(node.getId()+difference);
		}
		for(Transition t : nfa1.getTransitionList()) {
			plusNFA.addTransition(t.getFromNodeId()+difference, t.getToken(), t.getToNodeId()+difference);
		}
		plusNFA.addEndStateId(nfa1.getEndStateId().get(0)+difference);

		difference = plusNFA.getNodeIdCounter();
		for(Node node : nfa1.getNodeList()) {
			plusNFA.addNode(node.getId()+difference);
		}
		for(Transition t : nfa1.getTransitionList()) {
			plusNFA.addTransition(t.getFromNodeId()+difference, t.getToken(), t.getToNodeId()+difference);
		}
		plusNFA.addTransition(plusNFA.getEndStateId().get(0), Character.MIN_VALUE, nfa1.getStartStateId().get(0)+difference);
		plusNFA.addTransition(nfa1.getEndStateId().get(0)+difference, Character.MIN_VALUE, plusNFA.getEndStateId().get(0));

		return plusNFA;
	}

}
