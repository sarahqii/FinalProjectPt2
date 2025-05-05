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
public class Network {
    private Agent[] agents;
    private int agentNum;
    private int activeAgentNum;
    private int activeAgentNumPrevious;
    private int gridRow;
    private int b;
    private double m;
    private int T;
    
    public Network(int agentNum, int b, double m, int T){
        this.agents = new Agent[agentNum + 1];
        for(int i = 1; i <= agentNum; i++){
            agents[i] = new Agent(i, 0, agentNum, b, m);
        }
        this.agentNum = agentNum;
        activeAgentNum = agentNum;
        activeAgentNumPrevious = agentNum;
        gridRow = 0;
        this.b = b;
        this.m = m;
        this.T = T;
    }
    
    public void initialDefector(int h){
        Agent agent = getAgent(h);
        agent.switchMode();
    }
    
    public Agent getAgent(int i) {
        return agents[i];
    }
    
    public void playOneRound(){
        for (int i = 1; i <= agentNum; i++) {
        Agent agent = agents[i];
        agent.refreshUnplayed();
        agent.addScore(0); 
        }
        for (int i = 1; i <= agentNum; i++) {
            Agent agent = agents[i];
            agent.playAll(this);
        }
    }
    
    public void AllLearnStrategy(){
        for (int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            agent.learnStrategy(this);
        }
    }
    
    public void eliminate(){
        activeAgentNumPrevious = activeAgentNum;
        for (int i = 1; i <= agentNum; i++) {
            Agent agent = agents[i];
            if (agent.getScore() < T/agent.getPairNum()) {
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
    
    public boolean checkEnd(){
        if(activeAgentNum == 0){
            return true;
        }
        else if(activeAgentNum == activeAgentNumPrevious){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    public void printAllPairs(){
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            PriorityQueue<Integer> pairs = agent.getPairs();
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
    
    
    public void printResult(){
        for(int i = 1; i <= agentNum; i++){
            Agent agent = agents[i];
            if(agent.getPairNum() != 0){
                System.out.println(agent.getIndex());
            }
        }
    
    }
}
