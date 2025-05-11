/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectpartii;

import java.util.*;
/* Defines a single agent participating in pairwise networks.
Each agent can switch strategies (cooperator or defector), track scores, learn from neighbors, and manage its own interaction network. */

public class Agent {
    // Agent properties
    private int index;
    private int pairNum;
    private TreeSet<Integer> pairAgents;
    private TreeSet<Integer> unplayedAgents;
    private int agentNum;
    private double score;
    private double k;
    //0: coordinator and 1: defector
    private int mode;
    private double b;
    private double m;
    // 1 means active; 0 means dead
    private int state;
    // Constructor to initialize an agent
    public Agent(int index, int pairNum, int agentNum, double b, double m){
        this.index = index;
        this.pairNum = pairNum;
        this.pairAgents = new TreeSet<>();
        this.unplayedAgents = new TreeSet<>();
        this.agentNum = agentNum;
        this.score = 0;
        this.k = pairNum;
        this.mode = 0;
        this.b = b;
        this.m = m;
        state = 1;
    }
    // Establishes a pairing with another agent, ensuring no duplicate connections
    public void pair(int i, Agent agenti){
        if(index == i){
            return;
        }
        else if(checkPair(i)){
            return;
        }
        else if(agenti.checkPair(index)){
            addPairAgents(i);
        }
        else{
            addPairAgents(i);
            agenti.addPairAgents(index);
        }
    }
    // Adds a paired agent to this agent's lists
    public void addPairAgents(int i){
        pairAgents.add(i);
        unplayedAgents.add(i);
        pairNum++;
        k++;
    }
    // Executes the payoff calculation when this agent interacts with another
    public void play(int i, Agent agenti){
        int modei = agenti.getMode();
        
        if(mode == 0 && modei == 0){
            addScore(1.0/k);
            agenti.addScore(1.0/(agenti.getK()));
            removePlayed(i);
            agenti.removePlayed(index);
        }
        else if(mode == 0 && modei == 1){
            addScore(0);
            agenti.addScore(b/(agenti.getK()));
            removePlayed(i);
            agenti.removePlayed(index);
        }
        else if(mode == 1 && modei == 0){
            addScore(b/k);
            agenti.addScore(0);
            removePlayed(i);
            agenti.removePlayed(index);
        }
        else if(mode == 1 && modei == 1){
            addScore(0);
            agenti.addScore(0);
            removePlayed(i);
            agenti.removePlayed(index);
        }
    }
    // Plays with all unplayed agents in the current round
    public void playAll(Network network){
        while (!unplayedAgents.isEmpty()) {
        int i = unplayedAgents.pollFirst(); 
        Agent agenti = network.getAgent(i); 
        /*
        System.out.println("I'm Agent " + index +", and I'm playing with Agent " + i);
        System.out.print("I'm ");
        if(getMode() == 0){
                System.out.print("(C)");
            }
            else{
                System.out.print("(D)");
            }
        System.out.print(", and he is ");
        if(agenti.getMode() == 0){
                System.out.print("(C)\n");
            }
            else{
                System.out.print("(D)\n");
            }
        */
        play(i, agenti);
        //System.out.println("My score now is" + score);
    }
        
    
    }
    // Attempts to learn the strategy from the highest scoring neighbor
    public void learnStrategy(Network network){
        Iterator<Integer> it = pairAgents.iterator();
        double maxScore = score;
        int maxScoreIndex = 0;
        while(it.hasNext()){
            int i = it.next();
            Agent agent = network.getAgent(i);
            if(maxScore < agent.getScore()){
                maxScore = agent.getScore();
                maxScoreIndex = i;
            }
        }
        if(maxScoreIndex != 0){
            Random ran = new Random();
            double ranN = ran.nextInt(11)/10.0;
            if(ranN <= m){
                mode = network.getAgent(maxScoreIndex).getMode();
            }
        }
    }
    
    // Utility to check if already paired with agent i
    public boolean checkPair (int i){
        return pairAgents.contains(i);
    }
    // Returns the current agent instance
    public Agent getSelf(){
        return this;
    }
    // Returns the set of paired agent IDs
    public TreeSet<Integer> getPairs(){
        return pairAgents;
    }
    // Returns the set of unplayed agent IDs
    public TreeSet<Integer> getUnplayed(){
        return unplayedAgents;
    }
    // Resets unplayed agents to all paired agents
    public void refreshUnplayed(){
        unplayedAgents = new TreeSet<>(pairAgents);
    }
    // Removes an agent from the unplayed list after playing
    public void removePlayed(int i){
        unplayedAgents.remove(i);
    }

    // Accessor and mutator methods for various properties
    public int getIndex(){
        return index;
    }
    
    public void addScore(double newS){
        score = score + newS;
    }
    
    public void setScore(double newS){
        score = newS;
    }
    
    public int getMode(){
        return mode;
    }
    
    public double getK(){
        return k;
    }
    
    public int getPairNum(){
        return pairNum;
    }
    
    public void cleanPaired(){
        pairAgents.clear();
        unplayedAgents.clear();
        pairNum = 0;
    }
    
    public void switchMode(){
        if(mode == 0){mode = 1;}
        else{mode = 0;}
    }
    
    public void eliminate(int i){
        pairAgents.remove(i);
        unplayedAgents.remove(i);
        pairNum--;
    }
    
    public double getScore(){
        return score;
    }
   
    public int getState(){
        return state;
    }
    
    public void setState(int i){
        state = i;
    }
    
    
}
