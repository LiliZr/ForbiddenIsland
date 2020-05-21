package etats;

import java.awt.Image;

public abstract class Etat {
	String name;

	public String toString() {
		return name;
	}
	
	/**
	 * Renvoie l'image associée à l'etat
	 */
	public abstract Image renvoieImage(); 
	
	/**
	 * Renvoie l'etat après inondation
	 */
	public abstract Etat inonde();
	
	/**
	 * Renvoie l'etat après asseche
	 */
	public abstract Etat asseche();

}

