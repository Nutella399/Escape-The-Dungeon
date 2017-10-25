/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * The mini-game Escape the Dungeon
 *
 * @author RuthNuttall
 */
package edu.cpp.cs.cs141.prog_assgmnt_2;

import java.util.Random;

import edu.cpp.cs.cs141.prog_assgmnt_2.ActiveAgents.AGENT_TYPE;
import edu.cpp.cs.cs141.prog_assgmnt_2.Gun.GUN_TYPE;
import edu.cpp.cs.cs141.prog_assgmnt_2.ItemDrops.ITEM_DROPS;

public class GameEngine {
	
	/**
	 * The Player 
	 */
	private ActiveAgents player;
	
	/**
	 * The Players gun
	 */
	private Gun playerGun; 
	
	/**
	 * The ENEMY
	 */
	private ActiveAgents enemy; 
	
	/**
	 * A random number generator so we can have all those nice random statistics
	 */
	private Random rand = new Random(); 
	
	/**
	 * This helps us decided whether you actually retreat when you attempt to
	 */
	private boolean playerRetreats; 
	
	/**
	 *Constructor for the GameEngine so we can get methods from here 
	 */
	public GameEngine() {
		 
	}

	/**
	 * So this is how we being everything by creating the Player and assigning them the gun they wanted 
	 * @param gunType The gun they picked and wants so badly
	 */
	public void createGameInstance(GUN_TYPE gunType) {
		playerGun = new Gun(gunType); 
		player = new ActiveAgents(playerGun, AGENT_TYPE.PLAYER);
	}
	
	/**
	 * This is how we figure out what type of gun they enemy is going to have cause they don't have a choice about it
	 * @return The gun they are force to attack innocent players with I mean all they want to do is walk around a dungeon in peace
	 */
	private Gun computeEnemyGun() {
		Gun enemyGun = null; 
		int chance = rand.nextInt(100);
		
		if(chance >= 50) {
			enemyGun = new Gun(GUN_TYPE.PISTOL); 
		}else if(chance >= 35){
			enemyGun = new Gun(GUN_TYPE.RIFLE); 
		}else {
			enemyGun = new Gun(GUN_TYPE.SHOTGUN); 
		}
		return enemyGun;
	}
	
	/**
	 * This is where we figure out what kinda loot you receive after those meanies attack you 
	 * @return the loot you receive I mean its lame loot but it helps atleast
	 */
	public ITEM_DROPS computeItemDrop() {
		ITEM_DROPS itemDrop = null;
		int chance = rand.nextInt(100);
		
		if(chance >= 30) {
			itemDrop = ITEM_DROPS.AMMO_MAG; 
		}else {
			itemDrop = ITEM_DROPS.HEALTH_PACK; 
		}
		return itemDrop;  
	}
	
	/**
	 * This is how we decide whether one of those jerks come and attack you or not
	 * @return the boolean telling you whether you can walk in peace or not
	 */
	public boolean isThereAnEncounter() {
		int chance = rand.nextInt(100); 
		boolean encounter = false; 
		if(chance <= 30) {
			encounter = true; 
		}else {
			encounter = false; 
		}
		return encounter; 
	}
	
	/**
	 * This is where we create an enemy every time we need a new one Its like a cloning machine
	 * So of course this is a world without people stopping science from progressing wildly out of control
	 */
	public void createEnemy() {
		enemy = new ActiveAgents(computeEnemyGun(), AGENT_TYPE.ENEMY);	
	}
		
	/**
	 * This is where we let you both attack each other and hurt each other if you can
	 */
	public void encounter() {
		enemy.applyDamage(player.shootWeapon()); 
		player.applyDamage(enemy.shootWeapon());	
	}
	
	/**
	 * So this is when you are trying to retreat but the enemy attacks you because you were unsuccessful
	 */
	public void enemyAttacks() {
		player.applyDamage(enemy.shootWeapon()); 
	}
	
	/**
	 * This is where we decided whether you are done being attacked by the doofuses yet
	 * @return boolean whether the encounter is continuing or not
	 */
	public boolean encounterEnd() {
		boolean finished = false; 
		if(player.getHitPoints() <= 0 || enemy.getHitPoints() <= 0 || playerRetreats) {
			finished = true;
			playerRetreats = false;
		}
		return finished; 
	}
	
	/**
	 * This moves the player forward so they can progress through the dungeon
	 */
	public void moveForward() {
		player.moveForward(); 
	}
	
	/**
	 * This moves the player backward so that when they retreat we can take them back a step
	 */
	public void retreatBackward() {
		player.retreatBackward(); 
	}
	
	/**
	 * This is how we can tell the UI where they player is at that moment
	 * @return The number of steps 
	 */
	public int getMovement() {
		return player.getMovement(); 			
	}
	
	/**
	 * This tells the UI how much ammo the Player has left
	 * @return The number of rounds they have left
	 */
	public int getPlayerAmmo() {
		return playerGun.getAmmo();
	}
	
	/**
	 * This tells the UI what type of gun the enemy has so we can tell the player
	 * @return the type of Gun they enemy has 
	 */
	public GUN_TYPE getEnemyGunType() {
		return enemy.getGunType(); 
	}
	
	/**
	 * This is how we decide who much longer the game has
	 * @return boolean whether the game is over or not
	 */
	public boolean gameOver() {
		boolean gameOver = false; 
		if(getMovement() >= 10 || player.getHitPoints() <= 0){
			gameOver = true; 
		}
		return gameOver; 
	}
	
	/**
	 * How we decide whether the player won the game or not
	 * @return boolean whether the player won or not
	 */
	public boolean gameWon() {
		boolean gameWon = false; 
		if(getMovement() == 10 && !(getPlayerHitPoints() <= 0)) {
			gameWon = true; 
		}else {
			gameWon = false; 
		}
		return gameWon; 
	}
	
	/**
	 * Whether the player won the encounter not not 
	 * @return boolean saying if the player won or the enemy did
	 */
	public boolean encounterWon() {
		boolean encounterWon = false; 
		if(getEnemyHitPoints() <= 0 && getPlayerHitPoints() >= 0) {
			encounterWon = true; 
		}else {
			encounterWon = false; 
		}
		return encounterWon; 
	}

	/**
	 * This is how we tell the UI what HP the Player is at 
	 * @return the int by which the HP of the player is associated with
	 */
	public int getPlayerHitPoints() {
		int hitPoints = player.getHitPoints(); 
		return hitPoints;
	}
	
	/**
	 * THis is how we tell the UI what HP the enemy is at  
	 * @return the int that the enemy's HP is at 
	 */
	public int getEnemyHitPoints(){
		int hitPoints = enemy.getHitPoints(); 
		return hitPoints;
	}

	/**
	 * This is how we computer whether you can retreat the encounter or not
	 * @return boolean whether you can retreat or not
	 */
	public boolean doYouRetreat() {
		int chance = rand.nextInt(100);
		if(chance <= 10) {
			playerRetreats = true; 
		}
		return playerRetreats;
	}
	
	/**
	 * This is we give the player the health if they get a health pack and the ammo if they got a mag
	 * @param itemDrops which item they received
	 */
	public void dropItem(ITEM_DROPS itemDrops) {
		switch(itemDrops){
	 	case HEALTH_PACK:
	 		player.addHealth(); 
	 		break; 
	 	case AMMO_MAG:
	 		playerGun.addAmmo(); 
	 		break;
	 	default: 
	 		break; 
		}
	}
}
