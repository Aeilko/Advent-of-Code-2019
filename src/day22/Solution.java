package day22;

import java.util.Arrays;

public class Solution {

	public static final String[] INPUT = {"deal into new stack","deal with increment 25","cut -5919","deal with increment 56","deal into new stack","deal with increment 20","deal into new stack","deal with increment 53","cut 3262","deal with increment 63","cut 3298","deal into new stack","cut -4753","deal with increment 57","deal into new stack","cut 9882","deal with increment 42","deal into new stack","deal with increment 40","cut 2630","deal with increment 32","cut 1393","deal with increment 74","cut 2724","deal with increment 23","cut -3747","deal into new stack","cut 864","deal with increment 61","deal into new stack","cut -4200","deal with increment 72","cut -7634","deal with increment 32","deal into new stack","cut 6793","deal with increment 38","cut 7167","deal with increment 10","cut -9724","deal into new stack","cut 6047","deal with increment 37","cut 7947","deal with increment 63","deal into new stack","deal with increment 9","cut -9399","deal with increment 26","cut 1154","deal with increment 74","deal into new stack","cut 3670","deal with increment 45","cut 3109","deal with increment 64","cut -7956","deal with increment 39","deal into new stack","deal with increment 61","cut -9763","deal with increment 20","cut 4580","deal with increment 30","deal into new stack","deal with increment 62","deal into new stack","cut -997","deal with increment 54","cut -1085","deal into new stack","cut -9264","deal into new stack","deal with increment 11","cut 6041","deal with increment 9","deal into new stack","cut 5795","deal with increment 26","cut 5980","deal with increment 38","cut 1962","deal with increment 25","cut -565","deal with increment 45","cut 9490","deal with increment 21","cut -3936","deal with increment 64","deal into new stack","cut -7067","deal with increment 75","cut -3975","deal with increment 29","deal into new stack","cut -7770","deal into new stack","deal with increment 12","cut 8647","deal with increment 49"};

	public static void main(String[] args){
		/*long[] def = {0,1,2,3,4,5,6,7,8,9};
		SpaceDeck deck = new SpaceDeck(def);
		System.out.println(deck);
		deck.newStack();
		System.out.println(deck);
		deck = new SpaceDeck(def);
		deck.cutCards(-4);
		System.out.println(deck);
		deck = new SpaceDeck(def);
		deck.dealWithIncrement(3);
		System.out.println(deck);*/

		SpaceDeck sp = new SpaceDeck();
		for(String s: INPUT){
			if(s.equals("deal into new stack"))
				sp.newStack();
			else{
				String[] sa = s.split(" ");
				if(sa[0].equals("cut"))
					sp.cutCards(Integer.parseInt(sa[1]));
				else
					sp.dealWithIncrement(Integer.parseInt(sa[3]));
			}
		}

		long result = sp.findCard(2019);

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result + "\n");
	}
}

class SpaceDeck{

	public static final int DEFAULT_SIZE = 10007;

	// The current state of the deck
	private long[] deck;

	// The size of our current deck.
	private int deckSize;

	public SpaceDeck(){
		this.deck = new long[DEFAULT_SIZE];
		this.deckSize = DEFAULT_SIZE;
		for(int i = 0; i < DEFAULT_SIZE; i++){
			deck[i] = i;
		}
	}

	public SpaceDeck(long[] d){
		this.deck = d.clone();
		this.deckSize = d.length;
	}

	public void newStack(){
		// Reverse deck
		for(int i = 0; i < this.deckSize/2; i++){
			long tmp = this.deck[i];
			this.deck[i] = this.deck[this.deckSize-1-i];
			this.deck[deckSize-1-i] = tmp;
		}
	}

	public void cutCards(int n){
		// Handle negative values
		n = n%this.deckSize;
		while(n < 0)
			n += this.deckSize;

		// Save the first n values
		long[] tmp = new long[n];
		for(int i = 0; i < n; i++){
			tmp[i] = this.deck[i];
		}

		// Move the rest of the deck n places forward
		for(int i = n; i < this.deckSize; i++){
			this.deck[i-n] = this.deck[i];
		}

		// Add the first n values to the end
		int start = this.deckSize-n;
		for(int i = 0; i < tmp.length; i++){
			this.deck[start+i] = tmp[i];
		}
	}

	public void dealWithIncrement(int n){
		// This is a bad implementation, it should be possible to do this in place like the rest
		// Handle negative values
		n = n%this.deckSize;
		while(n < 0)
			n += this.deckSize;

		long[] newDeck = new long[this.deckSize];
		for(int i = 0; i < this.deckSize; i++){
			int index = (i*n)%this.deckSize;
			newDeck[index] = this.deck[i];
		}

		this.deck = newDeck;
	}

	public long getCard(int index){
		return this.deck[index];
	}

	public long findCard(long card){
		for(int i = 0; i < this.deckSize; i++)
			if(this.deck[i] == card)
				return i;

		return -1;
	}

	@Override
	public String toString() {
		String result = "";
		for(long l: this.deck){
			result += " " + l;
		}
		return result.substring(1);
	}
}
