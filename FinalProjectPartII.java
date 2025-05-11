/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalprojectpartii;
import java.io.*;
import java.util.*;
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
        
        System.out.println("Welcome to the Agent-Agent Interactions");
        System.out.println("Please select an option from 1 to 17: ");
        Scanner in = new Scanner(System.in);
        int opt = in.nextInt();
        while(opt < 1 || opt > 17){
            System.out.println("Invalid option! Choose again: ");
            opt = in.nextInt();
        }
        Network network;
        double b, m, T;
        int h;
        switch(opt){
            case 1:
                b = 2;
                h = 1;
                T = 1;
                m = 0.05;
                network = createNtw("option1.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 2:
                b = 2;
                h = 500;
                T = 1;
                m = 0.1;
                network = createNtw("option2.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 3:
                b = 2;
                h = 500;
                T = 1;
                m = 0.9;
                network = createNtw("option3.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 4:
                b = 2;
                h = 500;
                T = 1;
                m = 0.6;
                network = createNtw("option4.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 5:
                b = 2;
                h = 500;
                T = 1;
                m = 0.9;
                network = createNtw("option5.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 6:
                b = 2;
                h = 700;
                T = 1;
                m = 0.3;
                network = createNtw("option6.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 7:
                b = 2;
                h = 700;
                T = 2;
                m = 0.3;
                network = createNtw("option7.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 8:
                b = 2;
                h = 700;
                T = 2;
                m = 0.3;
                network = createNtw("option7.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
            case 9:
                b = 2;
                h = 500;
                T = 0.8;
                m = 0.1;
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 10:
                b = 2;
                h = 500;
                T = 2;
                m = 0.1;
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 11:
                b = 2;
                h = 500;
                T = 3;
                m = 0.1;
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 12:
                b = 2;
                h = 500;
                T = 0.8;
                m = 0.1;
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 13:
                b = 3;
                h = 500;
                T = 1.6;
                m = 0.5;
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 14:
                b = 1;
                h = 500;
                T = 3;
                m = 0.9;
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 15:
                b = 1;
                h = 500;
                T = 1;
                m = 0.9;
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 16:
                System.out.println("please input b: ");
                b = in.nextDouble();
                System.out.println("please input h in the range of [1, 999]: ");
                h = in.nextInt();
                while(h < 1 || h > 999){
                    System.out.println("Invalid input! Try again:");
                    h = in.nextInt();
                }
                System.out.println("please input T: ");
                T = in.nextDouble();
                T *= 4;
                System.out.println("please input m in the range of [0, 1]: ");
                m = in.nextDouble();
                while(m < 0 || m > 1){
                    System.out.println("Invalid input! Try again:");
                    m = in.nextDouble();
                }
                network = createNtw("option9-16.txt", b, m, T);
                runGame2D4N(network, b, m, T, h);
                break;
            case 17:
                System.out.println("please input b: ");
                b = in.nextDouble();
                System.out.println("please input h in the range of [1, 1000]: ");
                h = in.nextInt();
                while(h < 1 || h > 1000){
                    System.out.println("Invalid input! Try again:");
                    h = in.nextInt();
                }
                System.out.println("please input T: ");
                T = in.nextDouble();
                System.out.println("please input m in the range of [0, 1]: ");
                m = in.nextDouble();
                while(m < 0 || m > 1){
                    System.out.println("Invalid input! Try again:");
                    m = in.nextDouble();
                }

                network = createNtw("option7.txt", b, m, T);
                runGameRan(network, b, m, T, h);
                break;
        }



    }
    
    
    public static Network createNtw(String filename, double b, double m, double T) throws Exception{
        String st;
        int N = 0;
        BufferedReader bfro = new BufferedReader (new FileReader(filename));
        while((st = bfro.readLine()) != null){
            String[] arr = st.split(" ");
            int i1 = Integer.parseInt(arr[0]);
            if (i1 > N){N = i1;}
            int i2 = Integer.parseInt(arr[1]);
            if (i2 > N){N = i2;}
        }
        Network network = new Network(N, b, m, T);
        bfro = new BufferedReader (new FileReader(filename));
        while((st = bfro.readLine()) != null){
            String[] arr = st.split(" ");
            int i1 = Integer.parseInt(arr[0]);
            int i2 = Integer.parseInt(arr[1]);
            if(i2 == -1){
                continue;
            }
            Agent agent = network.getAgent(i1);
            agent.pair(i2, network.getAgent(i2));
        }
        return network;
    } 
    
    public static void runGame(Network network, double b, double m, double T, int h) throws Exception{
        network.initialDefector(h);
        //network.printState();
        network.collectMode();
        PrintWriter writer = new PrintWriter(new FileWriter("output.txt"));
        
        
        System.out.println("Game Start:");
        int maxIndex = network.findMaxK();
        int minIndex = network.findMinK();
        System.out.println("The inital number of Agents is " + network.getAgentNum());
        System.out.println("Agent " + maxIndex + " has the largest k with k = " + network.getAgent(maxIndex).getK());
        System.out.println("Agent " + minIndex + " has the smallest k with k = " + network.getAgent(minIndex).getK());
        
        writer.println("Agent " + maxIndex + " has the largest k with k = " + network.getAgent(maxIndex).getK());
        writer.println("Agent " + minIndex + " has the smallest k with k = " + network.getAgent(minIndex).getK());
        
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
            //System.out.println("There are " + network.getCNum() + " Coorperators");
            //System.out.println("There are " + network.getDNum() + " Defectors");
            System.out.println("The percentage of eliminated agents is " + (eliminatedAgentNum / agentNum) * 100 + "%");
            writer.println("The percentage of eliminated agents is " + (eliminatedAgentNum / agentNum) * 100 + "%");
            //network.printState();
        }
        writer.close();
        
    }
    
    public static void runGame2D4N(Network network, double b, double m, double T, int h)throws Exception{
        System.out.println("Parameters:");
        System.out.println("b = " + b + ", h = " + h + ", T = " + T/4 + ", m = " + m);
        runGame(network, b, m, T, h);
    }
    
    public static void runGameRan(Network network, double b, double m, double T, int h)throws Exception{
        System.out.println("Parameters:");
        System.out.println("b = " + b + ", h = " + h + ", T = " + T + ", m = " + m);
        runGame(network, b, m, T, h);
    }
}
