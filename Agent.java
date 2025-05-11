/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectpartii;

import java.util.*;

/**
 *
 * @author chenhenr
 */
public class Agent {
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
    //1 means active; 0 means dead
    private int state;

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
    
    public void addPairAgents(int i){
        pairAgents.add(i);
        unplayedAgents.add(i);
        pairNum++;
        k++;
    }
    
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
    
    
    public boolean checkPair (int i){
        return pairAgents.contains(i);
    }
    public Agent getSelf(){
        return this;
    }
    
    public TreeSet<Integer> getPairs(){
        return pairAgents;
    }
    
    public TreeSet<Integer> getUnplayed(){
        return unplayedAgents;
    }
    
    public void refreshUnplayed(){
        unplayedAgents = new TreeSet<>(pairAgents);
    }
    
    public void removePlayed(int i){
        unplayedAgents.remove(i);
    }
    

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
