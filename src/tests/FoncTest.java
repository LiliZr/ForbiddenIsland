package tests;

import etats.EtatSubmerge;
import modele.Cellule;
import modele.Modele;

public class FoncTest {
	/**
	 * Renvoie un tableau 2D de bool correspondant à si une cellule a changé d'etat après un tour
	 */
	public static boolean[][] booleanTabCorr(Modele m ){
		boolean[][] t = new boolean[Modele.LARGEUR][Modele.HAUTEUR];
		for(int i=1; i<=Modele.LARGEUR; i++) {
		    for(int j=1; j<=Modele.HAUTEUR; j++) {
		    	Cellule c = m.getCellule(i, j);
		    	t[i-1][j-1] = c.modified;
		    }
		}
		return t;
	}
	
	/**Compte le nombre de cases inondables i.e sèches ou inondées
	 * @param m
	 * @return
	 */
	
	public static int countInondable(Modele m) {
		int cpt = 0;
		for(int i=1; i<=Modele.LARGEUR; i++) {
		    for(int j=1; j<=Modele.HAUTEUR; j++) {    	
		    	Cellule c = m.getCellule(i, j);
		    	if(!(c.etat instanceof EtatSubmerge)) {
		    		cpt++;
		    	}
		    }
		}
		return cpt;
	}
	/**
	 * compte le nombre  cases differentes entre les deux tableaux
	 * @param avant
	 * @param apres
	 */
    public static int countDiff(boolean[][] avant, boolean[][] apres) {
    	int cpt = 0;
		for(int i=0; i<avant.length; i++) {
		    for(int j=0; j<avant[0].length; j++) {

		    	if(avant[i][j]!=apres[i][j]) 
		    		cpt++;		
		    }		    
		}
		return cpt;
    }
}
