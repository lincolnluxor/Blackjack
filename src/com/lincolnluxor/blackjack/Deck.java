package com.lincolnluxor.blackjack;

import java.util.ArrayList;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private String[] suit = {"Clubs", "Spades", "Diamonds", "Hearts"};
	private String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
	
	public Deck() {
		createDeck();
	}
	
	private void createDeck() {
		for (int i = 0; i < suit.length; i++) {
			for (int j = 0; j < rank.length; j++) {
				Card card = new Card(suit[i], rank[j]);
				deck.add(card);
			}
		}
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
}
