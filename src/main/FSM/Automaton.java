package main.FSM;

import main.struct.Node;
import main.struct.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Automaton {

	private int nodeIdCounter = 0;
	private Set<Character> alphabet = new HashSet<>();
	private ArrayList<Node> nodeList = new ArrayList<>();
	private ArrayList<Transition> transitionList = new ArrayList<>();
	private ArrayList<Integer> endStateId = new ArrayList<>();

	public Automaton() {

	}

	//---GETTER UND SETTER---------------------------------
	public int getNodeIdCounter() {
		return nodeIdCounter;
	}

	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public ArrayList<Transition> getTransitionList() {
		return transitionList;
	}

	public ArrayList<Integer> getEndStateId() {
		return endStateId;
	}

	public void addEndStateId(Integer endStateId) {
		this.endStateId.add(endStateId);
	}

	public void setNodeIdCounter(int id) {
		this.nodeIdCounter = id;
	}

	public void addToAlphabet(char token) {
		alphabet.add(token);
	}

	public Set<Character> getAlphabet() {
		return alphabet;
	}

	/**
	 * Methode that adds a new node to the nodelist and increse the nodecounter
	 * @return id of the new Node
	 */
	public int addNode() {
		Node node = new Node("n_"+nodeIdCounter, nodeIdCounter);
		nodeList.add(node);
		nodeIdCounter++;
		return node.getId();
	}

	/**
	 * methode thats adds a new node th the nodeList with given NodeId
	 * @param nodeId nodeId
	 * @return id of the new Node
	 */
	public int addNode(int nodeId) {
		if(nodeId>nodeIdCounter) {
			nodeIdCounter = nodeId;
		}
		Node node = new Node("n_"+nodeId, nodeId);
		nodeList.add(node);
		nodeIdCounter++;
		return node.getId();
	}

	/**
	 * Adds a new Transition to the NFA, if it doesnt contains this transition already
	 * @param startNodeId StartNode
	 * @param token token of the transition
	 * @param endNodeId EndNode
	 */
	public void addTransition(int startNodeId, char token, int endNodeId) {
		Transition t = new Transition(startNodeId, token, endNodeId);
		if(!transitionList.contains(t)) {
			transitionList.add(t);
		}
	}

	//---DEBUG---------------------------------------------
	/**
	 * Methode to formate the Nodelist into a String
	 * @return String with notelist
	 */
	protected String nodeListString() {
		String nodes = "{";
		for(Node node : getNodeList()) {
			nodes+=node.getId()+", ";
		}
		nodes = nodes.substring(0, nodes.length()-2);
		nodes+="}";
		return nodes;
	}

	/**
	 * Methode to formate all Transitions into a String
	 * @return String of transition
	 */
	protected String transitionString() {
		if(getTransitionList().size()<1) {
			return "{}";
		}
		String transitions = "{";
		for(Transition t : getTransitionList()) {
			transitions += "("+t.getFromNodeId()+", "+t.getToken()+", "+t.getToNodeId()+"), ";
		}
		transitions = transitions.substring(0, transitions.length()-2);
		transitions += "}";
		return transitions;
	}
}
