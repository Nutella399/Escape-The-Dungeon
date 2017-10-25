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

import java.util.Random;

public class Gun {
	
	/**
	 * This is where we create the different types of Guns
	 */
	public static enum GUN_TYPE {PISTOL, SHOTGUN, RIFLE}; 
	
	/**
	 * This is how we keep track of the gun's ammo 
	 */
	private int ammo; 
	
	/**
	 * This is how we keep track of the gun's accuracy
	 */
	private int accuracy; 

	/**
	 * This is how we remember the gun's maxAmmo
	 */
	private int maxAmmo; 

	/**
	 * This is how we keep track of what type of gun we are talking about
	 */
	private GUN_TYPE gunType; 

	/**
	 * This is the amount of damage a Gun can do 
	 */
	private int gunDamage; 
	
	/**
	 * General constructor for a gun
	 */
	public Gun() {
		
	}
	
	/**
	 * This is the constructor we actually use for the gun class
	 * @param gunType This lets us know what type of gun we are talking about and want to create
	 */
	public Gun(GUN_TYPE gunType) {
		this.gunType = gunType;
		accuracy = computeAccuracy(gunType); 
		maxAmmo = computerMaxAmmo(gunType); 
		gunDamage = computeDamage(gunType);
		ammo = maxAmmo; 
	}
	
	/**
	 * This is how we figure out the max ammo for the gun they have 
	 * @param gunType what type of gun we are talking about
	 * @return the max amount of ammo we can hold
	 */
	private int computerMaxAmmo(GUN_TYPE gunType) {
		int maxAmmo = 0; 
		switch(gunType) {
			case PISTOL:
				maxAmmo = 15; 
				break; 
			case RIFLE: 
				maxAmmo = 10; 
				break; 
			case SHOTGUN: 
				maxAmmo = 5;
				break; 
		}
		return maxAmmo;
	}
	
	/**
	 * This is how we figure out the accuracy of the gun they have
	 * @param gunType what type of gun they have
	 * @return the accuracy of the gun
	 */
	private int computeAccuracy(GUN_TYPE gunType) {
		int accuracy = 0; 
		switch(gunType) {
			case PISTOL:
				accuracy = 75; 
				break; 
			case RIFLE: 
				accuracy = 65; 
				break; 
			case SHOTGUN: 
				accuracy = 40;
				break; 
		}
		return accuracy; 
	}
	
	/**
	 * This is how we figure out how much damage the gun will be able to do 
	 * @param gunType what kind of gun is being talked about
	 * @return the amount of damage said gun can do
	 */
	private int computeDamage(GUN_TYPE gunType) {
		int damage = 0;
		switch(gunType) {
			case PISTOL:
				damage = 1; 
				break; 
			case RIFLE: 
				damage = 2; 
				break; 
			case SHOTGUN: 
				damage = 5;
				break; 
		}
		return damage;
	}
	
	/**
	 * This is where we do the actual shooting of the gun But you can only shoot if you are accurate enough and still have ammo
	 * @return the damage you did if you were able to shoot
	 */
	public int shoot() {
		int damage = 0; 
		Random random = new Random();
		int chance = random.nextInt(100);
		if(chance < accuracy && ammo > 0) {
			damage = gunDamage; 
		}
		if(ammo > 0 ){
			ammo--; 
		}
		return damage; 
	}
	
	/**
	 * Refills the gun if the player receives a mag
	 * @return the new amount of ammo the player has which will be the max amount the gun can hold
	 */
	public int addAmmo() {
		ammo = maxAmmo; 
		return ammo; 
	}
	
	/**
	 * This helps let the UI know how much ammo the agent has
	 * @return the amount of ammo the agent has
	 */
	public int getAmmo() {
		return ammo; 
	}

	/**
	 * This helps the UI know what type of gun the agent has
	 * @return the type of gun the agent has
	 */
	public GUN_TYPE getGunType() {
		return gunType;
	}
}
