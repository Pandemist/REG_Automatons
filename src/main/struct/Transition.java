package main.struct;

public class Transition {

    private int fromId;
    private char token;
    private int toId;

    public Transition(int fromId, char token, int toId) {
        this.fromId = fromId;
        this.token = token;
        this.toId = toId;
    }

    public int getFromNodeId() {
        return fromId;
    }

    public char getToken() {
        return token;
    }

    public int getToNodeId() {
        return toId;
    }
}
