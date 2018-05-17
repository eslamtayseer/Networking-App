package networking.app;

import java.util.ArrayList;

public class Message {
    Node sender;
    Node reciever;
    ArrayList<Node> path;
    long ID;
    String text;
    boolean recieved;
    double power;

    public Message(Node sender, Node reciever, long ID, String text) {
        this.sender = sender;
        this.reciever = reciever;
        this.ID = ID;
        this.text = text;
        path = new ArrayList();
        recieved = false;
    }
    public void addToPath(Node n){
        path.add(n);
    }
}
