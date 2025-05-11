/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectpartii;

import java.util.*;

// Class representing a network of agents for simulation
public class Network {
    // Network state and parameters
    private Agent[] agents;
    private int agentNum;
    private int activeAgentNum;
    private int activeAgentNumPrevious;
    private int[] curModes;
    private int[] preModes;
    private int gridRow;
    private double b;
    private double m;
    private double T;
    
    // Constructor initializes the agent network with given parameters
    public Network(int agentNum, double b, double m, double T){
        this.agents = new Agent[agentNum + 1];
        for(int i = 1; i <= agentNum; i++){
            agents[i] = new Agent(i, 0, agentNum, b, m);
        }
        this.agentNum = agentNum;
        activeAgentNum = agentNum;
        activeAgentNumPrevious = 0;
        gridRow = 0;
        this.b = b;
        this.m = m;
        this.T = T;
        curModes = new int[agentNum + 1];
        preModes = new int[agentNum + 1];
    }
    
    // Marks agent h as a defector to start the simulation
    public void initialDefector(int h){
        Agent agent = getAgent(h);
        agent.switchMode();
    }

    // Returns the agent instance at index i
    public Agent getAgent(int i) {
        return agents[i];
    }

    // Runs a single round of interactions for all active agents
    public void playOneRound(){
        for (int i = 1; i <= agentNum; i++) {
        Agent agent = agents[i];
        if(agent.getState() == 0){
            continue;
        }
        agent.refreshUnplayed();
        agent.setScore(0); 
        }
        for (int i = 1; i <= agentNum; i++) {
            Agent agent = agents[i];
            if(agent.getState() == 0){
            continue;
            }
            agent.playAll(this);
        }
    }

    // All agents update their strategy by learning from neighbors
    public void AllLearnStrategy(){
        for (int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            agent.learnStrategy(this);
        }
    }

    // Finds the agent with the highest degree (most connections)
    public int findMaxK(){
        double max = -1;
        int maxIndex = -1;
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            if(agent.getK() > max){
                max = agent.getK();
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // Counts the number of cooperators
    public int getCNum(){
        int counter = 0;
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            if(agent.getMode() == 0){
                counter++;
            }
        }
        return counter;
    }

    // Counts the number of defectors
    public int getDNum(){
        int counter = 0;
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            if(agent.getMode() == 1){
                counter++;
            }
        }
        return counter;
    }

    // Finds the agent with the lowest degree (fewest connections)
    public int findMinK(){
        double min = 9999999;
        int minIndex = -1;
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            if(agent.getK() < min){
                min = agent.getK();
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    // Eliminates agents that fail to meet the score threshold
    public void eliminate(){
        activeAgentNumPrevious = activeAgentNum;
        for (int i = 1; i <= agentNum; i++) {
            Agent agent = agents[i];
            if(agent.getState() == 0){
                continue;
            }
            if (agent.getScore() < T/agent.getK()) {
                agent.setState(0);
                agent.cleanPaired();
                for(int j = 1; j <= agentNum; j++){
                    if(j == i){
                        continue;
                    }
                    agents[j].eliminate(i);
                }
                activeAgentNum--;
            }
        }
    }

    // Checks whether the simulation should end
    public boolean checkEnd(){
        //System.out.println("Now active Agent Num is: "+activeAgentNum);
        //System.out.println("activeAgentNum: " + activeAgentNum);
        //System.out.println("activeAgentNumPrevious: " + activeAgentNumPrevious);
        if(activeAgentNum == 0){
            return true;
        }
        
        else if(checkStable()){
            steadyPrint();
            //System.out.println(Arrays.toString(curModes));
            //System.out.println(Arrays.toString(preModes));
            return true;
        }

        else{
            return false;
        }
    }
    
    // Prints all existing agent pairs
    public void printAllPairs(){
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            TreeSet<Integer> pairs = agent.getPairs();
            if(pairs.isEmpty()){
                System.out.println(agent.getIndex() + " -1");
            }
            else{
                for(int pairedIndex: pairs){
                    int index = agent.getIndex();
                    if(index < pairedIndex){
                        System.out.println(index + " " + pairedIndex);
                    }
                }
            }
        }
    }
    
    // Prints all agents that still have at least one pair
    public void printResult(){
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            int pairNum = agent.getPairNum();
            if(pairNum != 0){
                System.out.println(agent.getIndex());
            }
        }
    
    }

    // Records current agent strategies
    public void collectMode(){
        for (int i = 1; i <= agentNum; i++) {
        curModes[i] = agents[i].getMode();
        }
    }

    // Copies current strategies to previous state buffer
    public void copyToPreMode(){
        System.arraycopy(curModes, 0, preModes, 0, agentNum+1);
    }

    // Checks if agent strategies have stabilized
    public boolean checkStable(){
        return Arrays.equals(preModes, curModes);
    }

    // Prints detailed state information for debugging
    public void printState(){
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            TreeSet<Integer> pairs = agent.getPairs();
            System.out.print(i);
            if(agent.getMode() == 0){
                System.out.print("(C)");
            }
            else{
                System.out.print("(D)");
            }
            System.out.print("(K: "+agent.getK()+", Score: " + agent.getScore() +"): ");
            for(int pairedIndex: pairs){
                System.out.print(pairedIndex+" ");
            }
            System.out.println("");
        }
    }

    // Returns number of active agents
    public int getActiveAgentNum(){
        return activeAgentNum;
    }

    // Returns total number of agents
    public int getAgentNum(){
        return agentNum;
    }
    
    // Prints the stabilized network in the format
    public void steadyPrint(){
        System.out.println("*Vertices " + activeAgentNum);
        int counter = 1;
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            if(!agent.getPairs().isEmpty()){
                System.out.println(counter + " \"" + agent.getIndex() + "\"");
                counter++;
            }
        }
        int edgeCounter = 0;
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            TreeSet<Integer> pairs = agent.getPairs();
            if(!pairs.isEmpty()){
                for(int pairedIndex: pairs){
                    int index = agent.getIndex();
                    if(index < pairedIndex){
                        //System.out.println(index + " " + pairedIndex);
                        edgeCounter++;
                    }
                }
            }
        }
        System.out.println("*Edges " + edgeCounter);
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            TreeSet<Integer> pairs = agent.getPairs();
            if(!pairs.isEmpty()){
                for(int pairedIndex: pairs){
                    int index = agent.getIndex();
                    if(index < pairedIndex){
                        System.out.println(index + " " + pairedIndex);
                    }
                }
            }
        }
    
    }
}
