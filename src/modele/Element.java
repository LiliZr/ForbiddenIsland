package modele;

import tools.Spec;

public class Element {
	private static int cpt;
	private Spec id;
	private Modele m;
	private Cellule pos;
	private boolean recup;
	
	public Element (Modele m, Cellule pos) {
		this.m = m;
    	this.id = Spec.values()[cpt] ;
    	this.pos = pos;
        cpt++;
	}
	
	/**
	 * Renvoie l'id
	 */
	public Spec getId() {
		return this.id;
	}
	
	/**
	 * Récupere l'element : met recup à true
	 */
	public void recuperer() {
		this.recup = true;
	}
	/**
	 * Renvoie vrai si l'element a ete récup. Faux, sinon
	 */
	public boolean aEteRecup() {
		return this.recup;
	}

	/**
	 * Renvoie la position de l'element
	 */
	public Cellule getPos() {
		return this.pos;
	}
}
