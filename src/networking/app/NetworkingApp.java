/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.app;

import java.util.*;

/**
 *
 * @author eslam
 */
public class NetworkingApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Node.MakeGraph(30);
        /* for(Node node:Node.AllNodes){
           System.out.println(node.location);
           
       }*/
        // Node.AllNodes.get(0).Boadcast(new Message(Node.AllNodes.get(0),Node.AllNodes.get(Node.AllNodes.size()-1),System.currentTimeMillis(),"Message"));
        //sendToNode(Node.AllNodes.get(0), Node.AllNodes.get(Node.AllNodes.size()-1), (new Message(Node.AllNodes.get(0),Node.AllNodes.get(Node.AllNodes.size()-1),System.currentTimeMillis(),"Message")));

        System.out.println("=======Question I===========");
        System.out.println("Creating Graph of 10 Nodes");
        Node.MakeGraph(10);
        for (Node node : Node.AllNodes) {
            System.out.print("Adjacents of Node " + node.id + "==>");
            for (Node adj : node.adjList) {
                System.out.print(adj.id + "==> ");
            }
            System.out.println("null");
        }

        System.out.println("Example of applyiing Dijkstra");
        System.out.println("Sending a message from Node 1 to Node 8");
        sendToNode(Node.AllNodes.get(1), Node.AllNodes.get(8), new Message(Node.AllNodes.get(1), Node.AllNodes.get(8), System.currentTimeMillis(), "test"),1);
        System.out.println("The minimum path from Node 1 to 8");
        Dijkstra.printShortestPath(Node.AllNodes.get(8));
        Node.reset();

        System.out.println("=======Quesiton J with variable message cost ===========");
        double avgPower = findAvgPower(5,1);
        System.out.print("Average of N = 5 : ");
        System.out.println(avgPower);
        avgPower = findAvgPower(10,1);
        System.out.print("Average of N = 10 : ");
        System.out.println(avgPower);
        avgPower = findAvgPower(15,1);
        System.out.print("Average of N = 15 : ");
        System.out.println(avgPower);
        avgPower = findAvgPower(20,1);
        System.out.print("Average of N = 20 : ");
        System.out.println(avgPower);

        System.out.println("=======Send a message using broadcastring with constant message cost of 1 mw ===========");
         avgPower = findAvgPower(5,2);
        System.out.print("Average of N = 5 : ");
        System.out.println(avgPower);
        avgPower = findAvgPower(10,2);
        System.out.print("Average of N = 10 : ");
        System.out.println(avgPower);
        avgPower = findAvgPower(15,2);
        System.out.print("Average of N = 15 : ");
        System.out.println(avgPower);
        avgPower = findAvgPower(20,2);
        System.out.print("Average of N = 20 : ");
        System.out.println(avgPower);
    }

    public static void sendToNode(Node source, Node target, Message m, int option) {
        Dijkstra.computePaths(source);
        List<Node> nodes = Dijkstra.getShortestPathTo(target);
        int i;
        if (option == 1) {

            for (i = 0; i < nodes.size() - 1; i++) {
                //System.out.println("Send to Node");
                if (!nodes.get(i).send(m, nodes.get(i + 1))) {
                    //  System.out.println("Battery wasn't enough");
                    m.recieved = false;
                    break;
                }
            }
            if (i == nodes.size() - 1) {
                // System.out.println("Message delivered successfully");
                m.recieved = true;
                /*for(Node n : nodes) {
              System.out.println(n.id);
          }  */
            }
        } else {
             source.Boadcast(m);
             

        }
    }

    public static double findAvgPower(int n, int option) {
        Node.MakeGraph(n);
        Random rand = new Random();
        double totalPower = 0.0;
        for (int i = 0; i < 1000; i++) {
            Node firstNode = Node.AllNodes.get(rand.nextInt(n));
            Node secondNode = Node.AllNodes.get(rand.nextInt(n));
            // System.out.println("For Loop "+ i);
            while (firstNode.id == secondNode.id) {
                secondNode = Node.AllNodes.get(rand.nextInt(n));
                // System.out.println("Loop "+ i);
            }
            Message m = new Message(firstNode, secondNode, System.currentTimeMillis(), "message " + i);
            sendToNode(firstNode, secondNode, m, option);
            totalPower += m.power;
            Node.reset();
        }

        return totalPower / 1000.0;
    }

}
