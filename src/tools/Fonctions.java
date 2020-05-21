package tools;

import java.util.Random;

import modele.Cellule;

public class Fonctions {

	/**
	 * initialise  toutes les cases d'un tableau à -1
	 */
    public static void initialiseTabMoins1(int[][] tab) {
    	for(int i=0; i<tab.length; i++) {
    		for(int j=0; j<tab[0].length; j++) {
    			tab[i][j]=-1;        		
        	}
    	}
    }
    
    /**
     * affiche le tableau d'entiers
     */
    public static void affiche(int[][] tab) {
    	for(int i=0; i<tab.length; i++) {
    		for(int j=0; j<tab[0].length; j++) {
    			System.out.print(tab[i][j]+"\t");       		
        	}
    		System.out.print("\n"); 
    	}
    }
    /**
     * test si x, y appartient à tab
     */
    public static boolean  appartientTab(int[][] tab, int x, int y) {
    	for(int i=0; i<tab.length; i++) {
    		if (tab[i][0]==x && tab[i][1]==y)
    			return true;       	
    	}
    	return false;
    }
    
    
    /**
     * Renvoie les indices des cellules "safe" 
     */
	public static int[][] sontSafe(Cellule[][]  cellules, int minL, int minH, int maxL, int maxH){
		int [][] cells;
		int cpt = 0;
		for(int i=minL; i<=maxL; i++) {
		    for(int j=minH; j<=maxH; j++) {
				if (!cellules[i][j].isNotSafe() && cellules[i][j].estValide())
					cpt++;
			}
		}
		cells = new int[cpt][2];
		cpt = 0;
		for(int i=minL; i<=maxL; i++) {
		    for(int j=minH; j<=maxH; j++) {
				if (!cellules[i][j].isNotSafe() && cellules[i][j].estValide()) {
					cells[cpt][0] = i;	cells[cpt][1] = j;
					cpt++;
				}					
			}
		}
		
		
		return cells;
	}
	


	
	
	/**
	 * Choisi aléatoirement n coordonnées  parmi tab
	 */
	public static int[][] choisiNCases(int [][] tab, int n){
		int[][] coord = new int[n][2];
		
		int i = 0;
		while (i < n) {
    		Random r = new Random();	    		
    		int idx = r.nextInt(tab.length);
    		int x = tab[idx][0]; int y = tab[idx][1];
			if (!appartientTab(coord, x, y)) {
				coord[i][0] = x; coord[i][1] = y;
				i++;
			}
		}
		return coord;
	}
	
	/**
	 * renvoie les coordonées des cellules inondables
	 */
	public static int[][] sontInondables(Cellule[][]  cellules,  int minL, int minH, int maxL, int maxH){
		int [][] cells;
		int cpt = 0;
		for(int i=minL; i<maxL; i++) {
		    for(int j=minH; j<maxH; j++) {
				if (cellules[i][j].inondable() &&  cellules[i][j].estValide())
					cpt++;
			}
		}
		cells = new int[cpt][2];
		cpt = 0;
		for(int i=minL; i<maxL; i++) {
		    for(int j=minH; j<maxH; j++) {
				if (cellules[i][j].inondable() && cellules[i][j].estValide()) {
					cells[cpt][0] = i;    cells[cpt][1] = j;
					cpt++;
				}					
			}
		}
		
		
		return cells;
	}

}
