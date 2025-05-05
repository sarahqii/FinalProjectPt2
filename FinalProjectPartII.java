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
        String path = "output.txt";
        BufferedReader bfro = new BufferedReader (new FileReader(path));
        String st;
        int N = 0;
        bfro = new BufferedReader (new FileReader(path));
        while((st = bfro.readLine()) != null){
          //  System.out.println(st);
            String[] arr = st.split(" ");
            int i1 = Integer.parseInt(arr[0]);
            if (i1 > N){N = i1;}
            int i2 = Integer.parseInt(arr[1]);
            if (i2 > N){N = i2;}
        }
        //System.out.println("\n\n\n");
        double m = 0.6;
        int T = 1;
        Network network = new Network(N, 1, m, T);
        bfro = new BufferedReader (new FileReader(path));
        while((st = bfro.readLine()) != null){
            String[] arr = st.split(" ");
            int i1 = Integer.parseInt(arr[0]);
            int i2 = Integer.parseInt(arr[1]);
            Agent agent = network.getAgent(i1);
            agent.pair(i2, network.getAgent(i2));
        }
        network.printAllPairs();
        System.out.println("\n\n\nGame Start:");
        
        int counter = 0;
        int h = 3;
        network.initialDefector(h);
        while(!network.checkEnd()){
            network.playOneRound();
            network.AllLearnStrategy();
            network.eliminate();
            counter++;
        }
        network.printAllPairs();
        System.out.println("\n\n\n");
        network.printResult();
        
        
    }
}
