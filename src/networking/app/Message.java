package networking.app;

import java.util.ArrayList;

public class Message {
    Node sender;
    Node reciever;
    long ID;
    String text;
    boolean recieved;
    double power;

    public Message(Node sender, Node reciever, long ID, String text) {
        this.sender = sender;
        this.reciever = reciever;
        this.ID = ID;
        this.text = text;
        power = 0;
        recieved = false;
    }
    
}
