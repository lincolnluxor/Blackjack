package com.lincolnluxor.blackjack;

import java.util.ArrayList;

public class Player {
	ArrayList<Card> hand = new ArrayList<Card>();
	boolean inGame = true;
	
	public Player() {
		if (getHandWorth() > 21) {
			inGame = false;
		}
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public int getHandWorth() {
		int totalWorth = 0;
		int i = 0;
		for (Card c : hand) {
			totalWorth += c.getCardWorth(totalWorth);
			if (c.getCardRank() == "A") {
				i++;
			}
		}
		if (i > 0) {
			if (totalWorth < 12) {
				totalWorth += 10;
			}
		}
		return totalWorth;
	}

	public void printHand() {
		System.out.println("PLAYER:");
		for (Card c : hand) {
			System.out.println(c.getCardRank() + " " + c.getCardSuit());
		}
	}
}
