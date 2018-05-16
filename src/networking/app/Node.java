
package networking.app;

import java.awt.*;
import java.util.*;


public class Node {
    int id;
    double power;
    int distance;
    Point location;
    boolean isReceived;
    Node parent;
    Message message;
    ArrayList<Node> adjList = new ArrayList<Node>();
    static ArrayList<Node> AllNodes = new ArrayList<Node>();

    public Node(int id, double power, int distance, Point location) {
        this.id = id;
        this.power = power;
        this.distance = distance;
        this.location = location;
        adjList = new ArrayList();
    }
    public void send (Message m ){
        if(!m.recieved && !isReceived) {
            isReceived = true;

            for(Node child : adjList){
                if(child.id == m.reciever.id&&!child.isReceived&& !m.recieved){
                    child.isReceived = true;
                    m.recieved = true;
                    reset();
                    child.Recieve(new Message(child,m.sender,System.currentTimeMillis(),"Recieved"));
                    System.out.println("Delivered");
                    reset();
                    return;
                    
                }
                else if(!child.isReceived) 
                    child.send(m);
                
            
            }
        }
    }
    public void Recieve (Message m){
        if(!m.recieved && !isReceived) {
            isReceived = true;

            for(Node child : adjList){
                if(child.id == m.reciever.id&& !child.isReceived&& !m.recieved){
                    child.isReceived = true;
                    m.recieved = true;
                    System.out.println("Replied");
                    return;
                }
                else if(!child.isReceived) 
                    child.Recieve(m);
                
            
            }
        }
        
    }
    public static void reset (){
        for(Node node:AllNodes){
            node.isReceived= false;
        }
    }
    
    public static void MakeGraph (int noOfNodes){
        Random rand = new Random();
        for(int i =0;i<noOfNodes;i++)
            AllNodes.add(new Node(i,20,1000000,new Point(0, 0)));
        
        for(int i =0;i<noOfNodes/2;i++){
            Node Parent = AllNodes.get(i); 
            Point Location = new Point(Parent.location.x+rand.nextInt(20),Parent.location.y+rand.nextInt(20));
            if(Location.x>100){
                Location.x= Parent.location.x -rand.nextInt(20);
            }
            if(Location.y>100){
                Location.y= Parent.location.y -rand.nextInt(20);
            }
            AllNodes.get(2*i).location.setLocation(Location);
            //AllNodes.get(i).adjList.add(AllNodes.get(2*i));
                    
            Point Location2 = new Point(Parent.location.x+rand.nextInt(20),Parent.location.y+rand.nextInt(20));
            if(Location2.x>100){
                Location2.x= Parent.location.x -rand.nextInt(20);
            }
            if(Location2.y>100){
                Location2.y= Parent.location.y -rand.nextInt(20);
            }
            AllNodes.get(2*i+1).location.setLocation(Location2);
           // AllNodes.get(i).adjList.add(AllNodes.get(2*i + 1));
        }
        for(int i =0;i<noOfNodes-1;i++){
            for(int j =i+1;j<noOfNodes;j++){
                if ( AllNodes.get(i).location.distance(AllNodes.get(j).location)<=20){
                AllNodes.get(i).adjList.add(AllNodes.get(j ));
                AllNodes.get(j).adjList.add(AllNodes.get(i ));
            }
            }
        }
        
        }
           
        
           
    
    
    
    
}
