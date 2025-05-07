/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalprojectpartii;
import java.io.*;

/**
 *
 * @author chenhenr
 * 
 * 5.2 Update:
 * single agent play with single agent function;
 * single agent play with all agents function;
 * all agents play with all agents function (one round game);
 * eliminate and refresh after finish one round;
 * 
 * what to do next:
 * 1. learn the strategy from neighbors
 * 2. check end
 * 
 */
public class FinalProjectPartII {
    public static void main(String[] args) throws Exception {
        String path = "option6.txt";
        BufferedReader bfro = new BufferedReader (new FileReader(path));
        String st;
        int N = 0;
        bfro = new BufferedReader (new FileReader(path));
        while((st = bfro.readLine()) != null){
            String[] arr = st.split(" ");
            int i1 = Integer.parseInt(arr[0]);
            if (i1 > N){N = i1;}
            int i2 = Integer.parseInt(arr[1]);
            if (i2 > N){N = i2;}
        }
        double m = 0.3;
        double T = 1;
        double b = 2.0;
        Network network = new Network(N, b, m, T);
        bfro = new BufferedReader (new FileReader(path));
        while((st = bfro.readLine()) != null){
            String[] arr = st.split(" ");
            int i1 = Integer.parseInt(arr[0]);
            int i2 = Integer.parseInt(arr[1]);
            Agent agent = network.getAgent(i1);
            agent.pair(i2, network.getAgent(i2));
        }
        
        int h = 700;
        network.initialDefector(h);
        //network.printState();
        network.collectMode();
        PrintWriter writer = new PrintWriter(new FileWriter("output.txt"));
        System.out.println("Game Start:");
        int maxIndex = network.findMaxK();
        int minIndex = network.findMinK();
        
        System.out.println("Agent " + maxIndex + " has the largest k with k = " + network.getAgent(maxIndex).getK());
        System.out.println("Agent " + minIndex + " has the smallest k with k = " + network.getAgent(minIndex).getK());
        System.out.println("Parameters:");
        System.out.println("b = " + b + ", h = " + h + ", T = " + T + ", m = " + m);
        
        writer.println("Agent " + maxIndex + " has the largest k with k = " + network.getAgent(maxIndex).getK());
        writer.println("Agent " + minIndex + " has the smallest k with k = " + network.getAgent(minIndex).getK());
        writer.println("Parameters:");
        writer.println("b = " + b + ", h = " + h + ", T = " + T + ", m = " + m);
        

        
        int counter = 0;
        while(!network.checkEnd()){
            network.playOneRound();
            network.AllLearnStrategy();
            network.copyToPreMode();
            network.collectMode();
            network.eliminate();
            counter++;
            System.out.println("Game Round " + counter);
            writer.println("Game Round " + counter);
            double activeAgentNum = (double)network.getActiveAgentNum();
            double agentNum = (double)network.getAgentNum();
            double eliminatedAgentNum = agentNum - activeAgentNum;
            System.out.println("There are " + network.getCNum() + " Coorperators");
            System.out.println("There are " + network.getDNum() + " Defectors");
            System.out.println("The percentage of eliminated agents is " + (eliminatedAgentNum / agentNum) * 100 + "%");
            writer.println("The percentage of eliminated agents is " + (eliminatedAgentNum / agentNum) * 100 + "%");
            //network.printState();
        }
        
        writer.close();
    }
}
