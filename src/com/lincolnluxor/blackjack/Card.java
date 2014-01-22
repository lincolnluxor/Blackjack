package com.lincolnluxor.blackjack;

public class Card {
	String suit;
	String rank;
	int worth;
	
	public Card(String suit2, String rank2) {
		suit = suit2;
		rank = rank2;
	}
	
	public String getCardSuit() {
		return suit;
	}
	
	public String getCardRank() {
		return rank;
	}
	
	public int getCardWorth(int totalWorth) {
		if (rank == "T" || rank == "J" || rank == "Q" || rank == "K") {
			worth = 10;
		}
		else if (rank == "A") {
			worth = 1;
			/*
			if (totalWorth <= 10) { //huge issues with this
				worth = 11;
			}
			else {
				worth = 1;
			}
			*/
		}
		else {
			worth = Integer.parseInt(rank);
		}
		return worth;
	}
}
