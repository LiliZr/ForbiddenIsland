package tools;

import java.util.Random;

import modele.Cellule;

public class Fonctions {

	/**
	 * Initialise  toutes les cases d'un tableau à -1
	 * @param tab int[][]
	 */
    public static void initialiseTabMoins1(int[][] tab) {
    	for(int i=0; i<tab.length; i++) {
    		for(int j=0; j<tab[0].length; j++) {
    			tab[i][j]=-1;        		
        	}
    	}
    }
    
    /**
     * Affiche le tableau d'entiers
     * @param tab int[][]
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
     * Test si x, y appartient à tab
     * @param tab int[][] tableau qu'on parcourt.
     * @param x int
     * @param y int
     * @return
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
     * @param cellules. Cellule[][] : tableau de céllules qu'on va parcourir
	 * @param minL int : minimum largeur du tableau 
	 * @param minH int : minimum hauteur du tableau 
	 * @param maxL int : max largeur du tableau
	 * @param maxH int : max hauteur du tableau
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
	 * @param tab int[][]
	 * @param n  int
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
	 * Renvoie les coordonées des cellules inondables
	 * @param cellules. Cellule[][] : tableau de céllules qu'on va parcourir
	 * @param minL int : minimum largeur du tableau 
	 * @param minH int : minimum hauteur du tableau 
	 * @param maxL int : max largeur du tableau
	 * @param maxH int : max hauteur du tableau
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
