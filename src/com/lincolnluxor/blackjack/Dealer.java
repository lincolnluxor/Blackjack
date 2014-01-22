package com.lincolnluxor.blackjack;

import java.util.ArrayList;

public class Dealer {
	ArrayList<Card> hand = new ArrayList<Card>();
	boolean inGame = true;
	
	public Dealer() {
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
		System.out.println("DEALER:");
		for (Card c : hand) {
			System.out.println(c.getCardRank() + " " + c.getCardSuit());
		}
	}
}
