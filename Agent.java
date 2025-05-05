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
    private PriorityQueue<Integer> pairAgents;
    private PriorityQueue<Integer> unplayedAgents;
    private int agentNum;
    private double score;
    private int k;
    //0: coordinator and 1: defector
    private int mode;
    private int b;
    private double m;

    public Agent(int index, int pairNum, int agentNum, int b, double m){
        this.index = index;
        this.pairNum = pairNum;
        this.pairAgents = new PriorityQueue<>();
        this.unplayedAgents = new PriorityQueue<>();
        this.agentNum = agentNum;
        this.score = 0;
        this.k = pairNum;
        this.mode = 0;
        this.b = b;
        this.m = m;
    }
    
    public void pair(int i, Agent agenti){
        if(index == i){
            return;
        }
        if(agenti.checkPair(index)){
            pairNum++;
            pairAgents.add(i);
            unplayedAgents.add(i);
        }
        else{
            pairNum++;
            pairAgents.add(i);
            unplayedAgents.add(i);
            agenti.getPairs().add(index);
            agenti.getUnplayed().add(index);
        }
    }
    
    public void play(int i, Agent agenti){
        int modei = agenti.getMode();
        if(mode == 0 && modei == 0){
            addScore(1/k);
            agenti.addScore(1/(agenti.getK()));
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
        else if(mode == 0 && modei == 0){
            addScore(0);
            agenti.addScore(0);
            removePlayed(i);
            agenti.removePlayed(index);
        }
    }
    
    public void playAll(Network network){
        while (!unplayedAgents.isEmpty()) {
        int i = unplayedAgents.poll(); 
        Agent agenti = network.getAgent(i); 
        play(i, agenti); 
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
    
    public PriorityQueue<Integer> getPairs(){
        return pairAgents;
    }
    
    public PriorityQueue<Integer> getUnplayed(){
        return unplayedAgents;
    }
    
    public void refreshUnplayed(){
        unplayedAgents = new PriorityQueue<>(pairAgents);
    }
    
    public void removePlayed(int i){
        unplayedAgents.remove(i);
    }
    

    public int getIndex(){
        return index;
    }
    
    public void addScore(double score){
        this.score = score;
    }
    
    public int getMode(){
        return mode;
    }
    
    public int getK(){
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
   
    
    
}
