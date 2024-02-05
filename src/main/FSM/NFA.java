package main.FSM;

import main.struct.MultiMap;
import main.struct.Node;
import main.struct.Transition;

import java.util.*;

import static main.utils.RegexRules.*;

public class NFA extends Automaton {

    private ArrayList<Integer> startStateId;

    //GETTER AND SETTER
	public int getNodeIdCounter() {
		return super.getNodeIdCounter();
	}

	public ArrayList<Node> getNodeList() {
		return super.getNodeList();
	}

	public ArrayList<Transition> getTransitionList() {
		return super.getTransitionList();
	}

	public ArrayList<Integer> getStartStateId() {
		return startStateId;
	}

	public ArrayList<Integer> getEndStateId() {
		return super.getEndStateId();
	}

	public void addEndStateId(Integer endStateId) {
		super.addEndStateId(endStateId);
	}


	/**
	 * Constructor of the NFA
	 * NOTE: Default start node has id 0
	 */
	public NFA() {
        super.setNodeIdCounter(0);
        startStateId = new ArrayList<>();

        int startNodeId = addNode();
        startStateId.add(startNodeId);
    }

    public Set<Integer> getAllNodeIdsForToken(char token, int nodeId) {
//true wenn wort abgearbeitet wurde
//false wenn wort nicht abgearbeitet wurde

	    Comparator<MultiMap<Integer, Boolean>> comperator = new CustomComperator();

		Queue<MultiMap<Integer, Boolean>> nodeIds = new PriorityQueue<>(comperator);

		Set<Integer> returnSet = new HashSet<>();
	    for(Transition t : getTransitionList()) {
	    	if(t.getFromNodeId() == nodeId && t.getToken() == token) {
	    		MultiMap<Integer, Boolean> mltmp = new MultiMap<>();
	    		mltmp.put(t.getToNodeId(), true);
				nodeIds.add(mltmp);
		    }else if(t.getFromNodeId() == nodeId && t.getToken() == Character.MIN_VALUE) {
			    MultiMap<Integer, Boolean> mltmp = new MultiMap<>();
			    mltmp.put(t.getToNodeId(), false);
			    nodeIds.add(mltmp);
		    }
	    }
	    while(!nodeIds.isEmpty()) {
		    MultiMap<Integer, Boolean> accSet = nodeIds.poll();
			for(Integer i : accSet.getKey()) {
				for(Transition t : getTransitionList()) {
				/*	System.out.println(t.getFromNodeId()+"=="+i+" && "+t.getToken()+"=="+token
							                   +" && "+accSet.containsValueForKey(i, false));*/
				/*	System.out.println(t.getFromNodeId()+"=="+i+" && "+t.getToken()+"=="+Character.MIN_VALUE
							                   +" && "+accSet.containsValueForKey(i, false));*/
					if(t.getFromNodeId() == i && t.getToken() == token && accSet.containsValueForKey(i, false)) {
						MultiMap<Integer, Boolean> mltmp = new MultiMap<>();
						mltmp.put(t.getToNodeId(), true);
						nodeIds.add(mltmp);
					}else if(t.getFromNodeId() == i && t.getToken() == Character.MIN_VALUE
							&& accSet.containsValueForKey(i, false)) {
						MultiMap<Integer, Boolean> mltmp = new MultiMap<>();
						mltmp.put(t.getToNodeId(), false);
						nodeIds.add(mltmp);
					}else if(t.getFromNodeId() == i && t.getToken() == Character.MIN_VALUE
							&& accSet.containsValueForKey(i, true)) {
						MultiMap<Integer, Boolean> mltmp = new MultiMap<>();
						mltmp.put(t.getToNodeId(), true);
						nodeIds.add(mltmp);
					}
				}
			}
			returnSet.addAll(accSet.getKey());
	    }
		return returnSet;
    }

	/**
	 * Methode to test a Word if it will be accepted
	 * @param word Word to test
	 * @return boolean if the word will be accepted
	 */
	public boolean testWord(String word) {
        for(int startNodeId : startStateId) {
            if(testWordRecursive(startNodeId, word)) {
                return true;
            }
        }
        return false;
    }

	/**
	 * Recursive testing of all possible paths in the NFA to test a word.
	 * @param actualNodeId actual state
	 * @param restWord Rest of the word that, has to be tested
	 * @return boolean if the checked path accepts the word
	 */
	private boolean testWordRecursive(int actualNodeId, String restWord) {
        if(restWord.isEmpty()) {
        	//Abort Condition
	        //If the word was read and the Node is a FinalState, the word will be accepted and return true
            if(getEndStateId().contains(actualNodeId)) {
                return true;
            }else{
            	//If the word was read but the Node is no finalState, there is the posibility to use epsilon Transition
	            //to get into a finalState
            	for(Transition t : getTransitionList()) {
					if(t.getFromNodeId()==actualNodeId&&t.getToken()==Character.MIN_VALUE) {
						if (testWordRecursive(t.getToNodeId(), "")) {
							return true;
						}
					}
	            }
                return false;
            }
        }
        for(Transition t : getTransitionList()) {
        	//Handling normal transition
            if(t.getFromNodeId()==actualNodeId&&t.getToken()==restWord.charAt(0)) {
                if (testWordRecursive(t.getToNodeId(), restWord.substring(1))) {
                    return true;
                }
            }
            //Handle epsilon transition
	        if(t.getFromNodeId()==actualNodeId&&t.getToken()==Character.MIN_VALUE) {
		        if (testWordRecursive(t.getToNodeId(), restWord)) {
			        return true;
		        }
	        }
        }
        return false;
    }

	/**
	 * Methode that creates the NFA by a given regular expression, following the Thompson construction
	 * @param regex given regular expression allowed control chars,
	 *        in order of binding force:
	 *              '(',')' brackets
	 *              '*' zero or multiple times
	 *              '+' once or multiple times
	 *              '|' or
	 */
	public static NFA makeNFAFromRegex(String regex) {
		if(!main.utils.regexUtils.syntaxCheck(regex)) {
			System.out.println("Error: Given regular Expression is not valid.");
			return new NFA();
		}
		Stack<Character> operators = new Stack<>();
		Stack<NFA> operands = new Stack<>();
		Stack<NFA> concat_stack = new Stack<>();
		NFA nfa1, nfa2;
		boolean concatFlag = false;
		char operator;

		for(char c : regex.toCharArray()) {
			if(c == '(') {
				operators.push(c);
				concatFlag = false;
			}else if(c == ')') {
				concatFlag = false;
				while(!operators.empty() && operators.peek() != '(') {
					operator = operators.pop();
					if(operator == '.') {
						nfa2 = operands.pop();
						nfa1 = operands.pop();
						operands.push(createConcatNFA(nfa1, nfa2));
					}else if(operator == '|') {
						nfa2 = operands.pop();

						if(!operators.empty() && operators.peek() == '.') {
							concat_stack.push(operands.pop());
							while(!operators.empty() && operators.peek() == '.') {
								concat_stack.push(operands.pop());
								operators.pop();
							}
							nfa1 = createConcatNFA(concat_stack.pop(), concat_stack.pop());

							while(concat_stack.size() > 0) {
								nfa1 = createConcatNFA(nfa1, concat_stack.pop());
							}
						}else{
							nfa1 = operands.pop();
						}
						operands.push(createOrNFA(nfa1, nfa2));
					}
				}
			}else if(c == '+') {
				operands.push(createKleenePlusNFA(operands.pop()));
				concatFlag = true;
			}else if(c == '*') {
				operands.push(createKleeneStarNFA(operands.pop()));
				concatFlag = true;
			}else if(c == '|') {
				operators.push(c);
				concatFlag = false;
			}else{
				operands.push(createTokenNFA(c));
				if(concatFlag) {
					operators.push('.');
				}else{
					concatFlag = true;
				}
			}
		}
		while(operators.size() > 0) {
			if(operands.empty()) {
				System.err.println("Error: Unbalancierte Operatoren");
			}
			operator = operators.pop();
			if(operator == '.') {
				nfa2 = operands.pop();
				nfa1 = operands.pop();
				operands.push(createConcatNFA(nfa1, nfa2));
			}else if(operator == '|') {
				nfa2 = operands.pop();
				if(!operators.empty() && operators.peek() == '.') {
					concat_stack.push(operands.pop());
					concatFlag=concatFlag;

					while(!operators.empty() && operators.peek() == '.') {
						concat_stack.push(operands.pop());
						operators.pop();
					}
					nfa1 = createConcatNFA(concat_stack.pop(), concat_stack.pop());

					while(concat_stack.size() > 0) {
						nfa1 = createConcatNFA(nfa1, concat_stack.pop());
					}
				}else{
					nfa1 = operands.pop();
				}
				operands.push(createOrNFA(nfa1, nfa2));
			}
		}
		while(operands.size() > 1) {
			nfa2 = operands.pop();
			nfa1 = operands.pop();
			operands.push(createConcatNFA(nfa1, nfa2));
		}
		operands.peek().makeAlphabet();
		return operands.pop();
    }

    private void makeAlphabet() {
		for(Transition t : getTransitionList()) {
			if(t.getToken()!=Character.MIN_VALUE) {
				super.addToAlphabet(t.getToken());
			}
		}
    }

//---DEBUG---------------------------------------------

	/**
	 * Methode to print the actual NFA
	 */
	public void printNFA() {
	    System.out.println("Z: "+nodeListString());
	    System.out.println("Delta: "+transitionString());
	    System.out.println("Q: "+startStateId.toString());
	    System.out.println("E: "+getEndStateId().toString());
    }

    class CustomComperator implements Comparator {

	    @Override
	    public int compare(Object o1, Object o2) {
		    return 0;
	    }
    }
}











