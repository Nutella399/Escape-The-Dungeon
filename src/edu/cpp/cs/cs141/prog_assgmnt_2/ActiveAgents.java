/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * The mini-game Escape the Dungeon
 *
 * @RuthNuttall
 */
package edu.cpp.cs.cs141.prog_assgmnt_2;

import edu.cpp.cs.cs141.prog_assgmnt_2.Gun.GUN_TYPE;

public class ActiveAgents {
	
	/**
	 * This is where we create the different types of agents so we can use them elsewhere 
	 */
	public static enum AGENT_TYPE {PLAYER, ENEMY};

	/**
	 * This is where we create a general Gun object 
	 */
	private Gun gun; 

	/**
	 * This is where we create the general hitPoints a Agent receives 
	 */
	private int hitPoints;
	
	/**
	 * This is where we create the movement so we can keep track of the players Movements
	 */
	private int movement; 
		
	/**
	 * General constructor for the ActiveAgents 
	 */
	public ActiveAgents() {
		
	}
	
	/**
	 * Constructor that we actually use  
	 * @param gun The gun that the active agent uses
	 * @param agentType what type of agent they are 
	 */
	public ActiveAgents(Gun gun, AGENT_TYPE agentType) {
		this.gun = gun;
		hitPoints = computeHitPoints(agentType);
		movement = 0; 
	}

	/**
	 * Figures out how much hp they have depending one what type of agent they are
	 * @param agentType what type of agent they are 
	 * @return how much health they get to start with
	 */
	private int computeHitPoints(AGENT_TYPE agentType) {
		int hitPoints = 0; 
		switch(agentType){
			case ENEMY: 
				hitPoints = 5; 
				break; 
			case PLAYER: 
				hitPoints = 20; 
			break;
		}
		return hitPoints;
	}
	
	/**
	 * This is where the agent shoots their weapons
	 * @return how much damage they do when they shoot the weapon
	 */
	public int shootWeapon() {
		return gun.shoot(); 
	}
	
	/**
	 * Applies the damage done by the gun to the agent that is being shot
	 * @param damage The amount of damage being a applied
	 * @return the new hit points of the agent
	 */
	public int applyDamage(int damage) {
		if(hitPoints > 0) {
			hitPoints -= damage; 
		} else {
			hitPoints = 0; 
		}
		return hitPoints; 
	}
	
	/**
	 * This just helps to let the UI know how much health the agent has
	 * @return the hit points the agent has
	 */
	public int getHitPoints() {
		return hitPoints; 
	}
	
	/**
	 * This just helps us tell the UI what type of Gun the agent has
	 * @return What type of gun the agent has
	 */
	public GUN_TYPE getGunType() {
		return gun.getGunType();
	}
	
	/**
	 * This lets us add health when we receive the health pack
	 * @return the new amount of health after we add the health from the health pack
	 */
	public int addHealth() {
		hitPoints+= 5; 
		return hitPoints; 
	}
	
	/**
	 * Moves the agent forward in the dungeon
	 */
	public void moveForward() {
		movement ++;  
	}
	
	/**
	 * Moves the agent backward in the dungeon
	 */
	public void retreatBackward() {
		movement--;  
	}
	
	/**
	 * Gets the amount the distance the agent has travels. Not the displacement but the distance!
	 * @return The distance
	 */
	public int getMovement() {
		return movement; 
	}
}