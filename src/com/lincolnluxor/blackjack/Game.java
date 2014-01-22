package com.lincolnluxor.blackjack;

import java.util.Random;
import java.util.Scanner;

public class Game {
	private String[] options = {"hit", "stay", "fold"};
	private Scanner input = new Scanner(System.in);
	private boolean activePlayer = false;
	private boolean activeDealer = false;
	private boolean activeTurn = false;
	private boolean goodOptionInput = false;
	private boolean activeGame = true;
	private String inputString;
	private int inputInteger = 0;
	private Random random = new Random();

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		while (activeGame) {
			Deck deck = new Deck();
			Player player = new Player();
			Dealer dealer = new Dealer();
			dealCards(player, dealer, deck);
			playPlayer(player, deck);
			playDealer(dealer, player, deck);
			concludeGame(dealer, player);
			getRestartInput();
		}
	}
	
	private void dealCards(Player player, Dealer dealer, Deck deck) {
		while (player.getHand().size() < 2 && dealer.getHand().size() < 2) {
			int rand = random.nextInt(deck.getDeck().size());
			player.hand.add(deck.getDeck().get(rand));
			deck.getDeck().remove(rand);
			int rand2 = random.nextInt(deck.getDeck().size());
			dealer.hand.add(deck.getDeck().get(rand2));
			deck.getDeck().remove(rand2);
		}
		player.printHand();
		System.out.println("DEALER:");
		System.out.println(dealer.hand.get(0).getCardRank() + " " + dealer.hand.get(0).getCardSuit());
		System.out.println("X Xxxxx");
	}
	
	private Boolean getRestartInput() {
		System.out.println("Enter \"r\" to play again or \"q\" to quit");
		inputString = input.nextLine();
		if (inputString.length() > 1) {
			System.out.println("Sorry I didn't understand that response");
			System.out.println("Enter \"r\" to play again or \"q\" to quit");
			inputString = input.nextLine();
		}
		if (inputString == "r" || inputString == "R") {
			activeGame = true;
		}
		else if (inputString == "q" || inputString == "Q") {
			activeGame = false;
		}
		else {
			System.out.println("Sorry I didn't understand that response");
			activeGame = false;
		}
		return activeGame;
	}
	
	private int getOptionInput(Player player) {
		while (!goodOptionInput) {
			int numOptions;
			if (player.hand.size() == 2) {
				numOptions = options.length;
			}
			else {
				numOptions = options.length - 1;
			}
			for (int i = 0; i < numOptions; i++) {
				System.out.println("Enter " + (i+1) + " to " + options[i]);
			}
			inputString = input.nextLine();
			try {
				inputInteger = Integer.parseInt(inputString);
				if (inputInteger <= numOptions && inputInteger > 0) {
					goodOptionInput = true;
				}
			}
			catch (Exception e) {
				System.out.println("Sorry, I didn't understand your choice of: " + inputString);
			}
		}
		return inputInteger;
	}
	
	private void dealPlayerCard(Player player, Deck deck) {
		int rand = random.nextInt(deck.getDeck().size());
		player.hand.add(deck.getDeck().get(rand));
		deck.getDeck().remove(rand);
		player.printHand();
	}
	
	private void dealDealerCard(Dealer dealer, Deck deck) {
		int rand = random.nextInt(deck.getDeck().size());
		dealer.hand.add(deck.getDeck().get(rand));
		deck.getDeck().remove(rand);
		dealer.printHand();
	}
	
	private void performAction(Player player, Deck deck) {
		if (player.hand.size() == 2) {
			switch (inputInteger) {
			case 1 : dealPlayerCard(player, deck); //hit
					 goodOptionInput = false;
					 break;
			case 2 : activeTurn = false; //stay
					 activePlayer = false; 
					 break;
			case 3 : activeTurn = false; //fold
					 activePlayer = false; 
			  		 player.inGame = false;
			  		 break;
			default: activeTurn = false;
					 activePlayer = false;
			  		 break;
			}
		}
		else { //fold is not an option if you have already bought in and played any action.
			switch (inputInteger) {
			case 1 : dealPlayerCard(player, deck); //hit
					goodOptionInput = false;
					 break;
			case 2 : activeTurn = false; 
					 activePlayer = false; //stay
					 break;
			default: activeTurn = false;
					 activePlayer = false;
			  		 break;
			}
		}
	}
	
	private void playPlayer(Player player, Deck deck) {
		activePlayer = true;
		if (player.inGame && player.hand.size() == 2 && (
				(player.getHand().get(0).getCardRank() == "J"  && (player.getHand().get(0).getCardSuit() == "Clubs" || player.getHand().get(0).getCardSuit() == "Spades") && (player.getHand().get(1).getCardRank() == "A")) || 
				(player.getHand().get(1).getCardRank() == "J"  && (player.getHand().get(1).getCardSuit() == "Clubs" || player.getHand().get(1).getCardSuit() == "Spades") && (player.getHand().get(0).getCardRank() == "A")) 
		)) {
			System.out.print("You have a BLACKJACK!!! ");
		}
		while (activePlayer) {
			activeTurn = true;
			while (activeTurn) {
				if (player.getHandWorth() > 21) {
					System.out.println(player.getHandWorth() + " - Sorry you busted");
					player.inGame = false;
					activePlayer = false;
					activeTurn = false;
					activeDealer = false;
				}
				else {
					getOptionInput(player);
					performAction(player, deck);
					activeDealer = true;
				}
			}
		}
	}
	
	private void playDealer(Dealer dealer, Player player, Deck deck) {
		while (activeDealer) {
			activeTurn = true;
			while (activeTurn) {
				if (dealer.getHandWorth() > player.getHandWorth()) {
					activeDealer = false;
				}
				else {
					while (dealer.getHandWorth() < 17 || dealer.getHandWorth() < player.getHandWorth()) {
						System.out.println(dealer.getHandWorth() + " - Dealer hits");
						dealDealerCard(dealer, deck);
					}
				}
				activeTurn = false;
			}
			if (dealer.getHandWorth() > 21) {
				System.out.println(dealer.getHandWorth() + " - Dealer busted");
				dealer.inGame = false;
				activeDealer = false;
				activeTurn = false;
			}
			else {
				System.out.println(dealer.getHandWorth() + " - Dealer stays");
				//dealer.printHand();
				activeDealer = false;
			}
		}
	}
	
	private void concludeGame(Dealer dealer, Player player) {
		if (player.inGame && !dealer.inGame) {
			System.out.println("You win!");
		}
		else if (!player.inGame && dealer.inGame) {
			System.out.println("Dealer wins.");
		}
		else {
			if (player.getHandWorth() > dealer.getHandWorth()) {
				System.out.println("You win!");
			}
			else if (dealer.getHandWorth() > player.getHandWorth()) {
				
				System.out.println("Dealer wins.");
			}
			else {
				System.out.println("Push");
			}
		}
	}
}
