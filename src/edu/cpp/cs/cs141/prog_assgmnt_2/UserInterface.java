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

import java.util.Scanner;

import edu.cpp.cs.cs141.prog_assgmnt_2.Gun.GUN_TYPE;
import edu.cpp.cs.cs141.prog_assgmnt_2.ItemDrops.ITEM_DROPS;

public class UserInterface {
	
	/**
	 * The scanner we use to get all the responses from the Player
	 */
	private Scanner keyboard; 
	
	/**
	 * The game engine we use to interact with all the other classes and get the info we need
	 */
	private GameEngine engine;
	
	/**
	 * This is just the constructor for the UserInterface
	 */
	public UserInterface() {
		engine = new GameEngine();
		keyboard = new Scanner(System.in);
	}
	
	/**
	 * Starts the game off and send the player off on their adventure if they want to go 
	 */
	public void startGame() {
		System.out.println("Welcome to Escape the Dungeon! I hope you enjoy this game!");
		int option = mainMenu();
		
		switch(option) {
			case 1: 
				System.out.println("You mission is to get to take 10 steps without dying.");
				gameTime();
				break; 
			case 2: 
				break; 
			default: 
				System.out.println("That isn't an option, try again.\n");
				startGame();
				break; 
		}
	}
	
	 /**
	  *This is the main menu just asking the player if they want to start their adventure 
	  * @return the option the player picks. 1 Being they want to start 2 being the quit button
	  */
	public int mainMenu() {
		int option = 0; 
		
		System.out.println("Are you ready to start your adventure?\n" 
		+ "1: Begin adventure.\n" + "2: Quit before you even started like the loser you are.");
		option = keyboard.nextInt();
		keyboard.nextLine();
		return option; 
	}
	
	/**
	 * This is how we decided which gun the Player wants after they input into the console
	 * @param option This is the number that we received from the Player
	 * @return the gun type the player picked  
	 */
	public GUN_TYPE setGunType(int option) { 
		GUN_TYPE gunType = null; 
		switch(option){
			case 1: 
				gunType = GUN_TYPE.PISTOL;
				break; 
			case 2: 
				gunType = GUN_TYPE.RIFLE; 
				break; 
			case 3: 
				gunType = GUN_TYPE.SHOTGUN; 
				break;
			default: 
				gunType = null;
				break; 
		}
		return gunType; 
	}
	
	/**
	 * This is how the player picks what type of gun they want at the beginning of the game 
	 * @return the type of Gun they want so the we can tell the game engine what they want
	 */
	public GUN_TYPE pickGun() {
		int option = 0; 
		GUN_TYPE gunType = null; 
		System.out.println("What type of gun would you like?\n" 
		+ "1: Pistol\n"  + "2: Rifle\n" + "3: Shotgun");
		option = keyboard.nextInt(); 
		keyboard.nextLine();
		gunType = setGunType(option);
		if(gunType == null) {
			System.out.println("That isn't an option, try again.");
			gunType = pickGun(); 
		}
		return gunType; 
	}
	
	/**
	 * As soon as the player has decided they want to adventure and have picked their gun this is where they stay until the end of the game
	 * This is the main game loop
	 */
	public void gameTime() {
		GUN_TYPE gunType  = pickGun();
		engine.createGameInstance(gunType);
		while(!engine.gameOver()){
			System.out.println("Press Enter to move forward."  + "You have moved: "+ engine.getMovement() +  " steps!");
			keyboard.nextLine(); 
			engine.moveForward();
			if(engine.isThereAnEncounter()) {
				engine.createEnemy();
				while(!engine.encounterEnd()) {
					encounter();
				}
				if(engine.encounterWon()) {
					System.out.println("\tYou won this battle");
					ITEM_DROPS itemDrop = engine.computeItemDrop(); 
					engine.dropItem(itemDrop);
					itemDropMessage(itemDrop);
				}
			}
		}
		if(engine.gameWon()) {
			System.out.println("You made it to the end of the Dungeon. C0NGRABULAT10NS!");
			System.out.println("\n\n tbh it wasnt that hard.");	
		}else {
			System.out.println("\tGame Over. You lose!");
		}
	}
	
	
	/**
	 * This is the loop for when an encounter happens 
	 */
	public void encounter() {
		int option = 0; 
		System.out.println("You were attacked by the Agent with a " + engine.getEnemyGunType() + " What will you do?\n"
		+ "1: Shoot\n" + "2: Try to Escape");
		option = keyboard.nextInt(); 
		keyboard.nextLine(); 
		
		switch(option){
			case 1: 
				engine.encounter();
				statsMessage();
				if(engine.getPlayerAmmo() <= 0) {
					System.out.println("You are out of ammo, Your only option now is to retreat.");
				}
				break; 
			case 2: 
				if(engine.doYouRetreat()){
					System.out.println("You escaped the encounter but you had to take a step back");
					engine.retreatBackward();
				}else {
					System.out.println("You weren't able to retreat and the Agent attacked you.");
					engine.enemyAttacks();
					statsMessage();
				}
				break;
			default: 
				System.out.println("That isn't an option try again.");
				encounter();
				break; 
		}
	}
	
	/**
	 * This is just the message that tells the player different important statistics 
	 */
	public void statsMessage() {
		System.out.println("Your current health is: " + engine.getPlayerHitPoints() + " Your current ammo amount is: " + engine.getPlayerAmmo());
		System.out.println("Your enemy's health is: " + engine.getEnemyHitPoints());
	}
	
	/**
	 * This is what tells the player which item they received after a successful encounter 
	 * @param itemDrop which item the engine computed should drop this time
	 */
	public void itemDropMessage(ITEM_DROPS itemDrop) {
		switch(itemDrop) {
			case HEALTH_PACK: 
				System.out.println("You just recieved a health pack and gained 5 hit points back.");
				break; 
			case AMMO_MAG: 
				System.out.println("You just recieved a gun magazine and gained all your ammo back.");
				break; 
			default: 
				System.out.println("You didn't get a Item Drop at all. Sucks to be you. ");
		}
	}
}
