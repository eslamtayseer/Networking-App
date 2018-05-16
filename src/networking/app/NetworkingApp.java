/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.app;

/**
 *
 * @author eslam
 */
public class NetworkingApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Node.MakeGraph(30);
       for(Node node:Node.AllNodes){
           System.out.println(node.location);
           
       }
       Node.AllNodes.get(0).send(new Message(Node.AllNodes.get(0),Node.AllNodes.get(Node.AllNodes.size()-1),System.currentTimeMillis(),"Message"));
    }
    
}
