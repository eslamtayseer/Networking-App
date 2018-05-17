
package networking.app;

import java.awt.*;
import java.util.*;


public class Node implements Comparable<Node> {
    int id;
    double power;
    double distance;
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
    public void Boadcast (Message m ){
        if(!m.recieved && !isReceived) {
            isReceived = true;
            m.power++;
            for(Node child : adjList){
                if(child.id == m.reciever.id&&!child.isReceived&& !m.recieved){
                    child.isReceived = true;
                    m.recieved = true;
                    reset();
                    Message reply = new Message(child,m.sender,System.currentTimeMillis(),"Recieved");
                    child.BoadcastReply(reply);
                    m.power+=reply.power;
                    //System.out.println("Delivered");
                    reset();
                    return;
                    
                }
                else if(!child.isReceived) 
                    child.Boadcast(m);
                
            
            }
        }
    }
    public void BoadcastReply (Message m){
        if(!m.recieved && !isReceived) {
            
            isReceived = true;
            m.power++;
            for(Node child : adjList){
                if(child.id == m.reciever.id&& !child.isReceived&& !m.recieved){
                    child.isReceived = true;
                    m.recieved = true;
                   // System.out.println("Replied");
                    return;
                }
                else if(!child.isReceived) 
                    child.BoadcastReply(m);
                
            
            }
        }
        
    }
    public boolean send(Message m, Node target){
        power -= Math.sqrt(Math.pow(this.location.x-target.location.x, 2)+Math.pow(this.location.y-target.location.y,2))*0.05;
        if(power >= 0) {
            m.power+=Math.sqrt(Math.pow(this.location.x-target.location.x, 2)+Math.pow(this.location.y-target.location.y,2))*0.05;
            target.isReceived = true;
            target.message = m;
            //power -= 1;
            return true;
        } 
        
        return false;
    }
    public static void reset (){
        for(Node node:AllNodes){
            node.isReceived= false;
            node.distance=1000000;
            node.parent=null;
            node.message=null;
        }
    }
    
    public static void MakeGraph (int noOfNodes){
        AllNodes = new ArrayList<Node>();
        Random rand = new Random();
        for(int i =0;i<noOfNodes;i++)
            AllNodes.add(new Node(i,2000,1000000,new Point(0, 0)));
        
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

    @Override
    public int compareTo(Node t) {
        return Double.compare(distance, t.distance);
    }
           
        
           
    
    
    
    
}
