package modele;

import java.util.Random;

import controleur.CtrlActJoueur;
import etats.EtatInonde;
import etats.EtatSubmerge;
import tools.Direction;
import tools.Fonctions;
import tools.Observable;
import tools.Spec;

public class Modele extends Observable{
	
    public static final int HAUTEUR=20, LARGEUR=20;
    public static final int nbJoueurs = 4;
    private Cellule[][] cellules;
    private Joueur[] joueurs;
    private Element[] elements;
    private int xM,  yM;


    public Modele() {
		cellules = new Cellule[LARGEUR+2][HAUTEUR+2];
		for(int i=0; i<LARGEUR+2; i++) {
		    for(int j=0; j<HAUTEUR+2; j++) {
			cellules[i][j] = new Cellule(this,i, j);
		    }
		}
		initCells();
		initJoueurs();   
		initElements();
	}

    

    /**
     * Initialisation aléatoire des cellules
     */
    public void initCells() {
		for(int i=1; i<=LARGEUR; i++) {
		    for(int j=1; j<=HAUTEUR; j++) {
		    	double proba = Math.random(); 
				if (proba < 0.05) {
				    cellules[i][j].initNewEtat(new EtatSubmerge());
				}else {
					if(proba <0.20) 
						cellules[i][j].initNewEtat(new EtatInonde());				
				}
		    }
		}

		
		
    }
    /**
     * Initialisation aléatoire des Positions de joueurs
     */
    public void initJoueurs() {
		joueurs = new Joueur[nbJoueurs];
		int[][] cellSeches = Fonctions.sontInondables( cellules, 1, 1, LARGEUR+1, HAUTEUR+1);
		int[][] cellSecAlea = Fonctions.choisiNCases(cellSeches, joueurs.length);
		for (int i = 0; i<joueurs.length; i++) {
			joueurs[i] = new Joueur(this, cellules[cellSecAlea[i][0]][cellSecAlea[i][1]]);

		}
    }
    
    /**
     * Initialise les artefacts et l'héliport
     */
    public void initElements() {
    	elements = new Element[Spec.values().length-1];
    	
    	int[][] cases = Fonctions.sontInondables( cellules, 1, 1, LARGEUR+1, HAUTEUR+1);
		int[][] casesAlea = Fonctions.choisiNCases(cases, Spec.values().length-1);
		
		for(int i = 0; i<elements.length; i++) {
			elements[i] = new Element(this, cellules[casesAlea[i][0]][casesAlea[i][1]]);
			cellules[casesAlea[i][0]][casesAlea[i][1]].setId(elements[i].getId());
		}
    	
    }

    
    /**
     * Réalise un tour en inondant 3cases et vérifiant si des joueurs sont morts
     */
    public void realiseUnTour() {
    	inonde3Cases();
    	metAJourJoueurs();		//Verifie si des joueurs sont morts
    	notifyObservers();
    }


    /**
     * Inonde 3 cases aléatoires qui ne viennent pas d'etre inondées
     * et qui ne sont pas submergées
     */
    public void inonde3Cases() {
    	int cpt = 0;   	
    	int[][] coordonnees = new int[3][2];	//Un tableau pour garder les cordonnées deja tirées
    	boolean dejaInondee;   	
    	int[][] inondables = Fonctions.sontInondables(cellules, 1, 1, LARGEUR+1, HAUTEUR+1);
    	
    	while (cpt!=3  && inondables.length>=3) {		//Tant qu'on a pas inonder 3 cases  et tant qu'il en reste plus de trois  		
    		Random r = new Random();	    		
    		int idx = r.nextInt(inondables.length);	  
    		
    		int i = inondables[idx][0];  int j = inondables[idx][1];
    		
    		dejaInondee = Fonctions.appartientTab(coordonnees, i, j);	//Vrai si on a déja inondé cette case, faux sinon

	    		if (!dejaInondee) {
	    			cellules[i][j].inonde();	
	    			coordonnees[cpt][0] = i; coordonnees[cpt][1] = j; 
	    			cpt++;
	    		}	    		
    	}
    }

    /**
     * Realise l'action 'action' du joueur numero 'num'
     * @param num : int, numéro du joueur
     * @param direction : Direction
     * @param action : int
	 * Action 0 = déplace un joueur
	 * Action 1 = Assèche une case 
	 * Action 2 = Récupère un artefact 
     */
    
    public void actionJoueur(int num, Direction direction, int action) {
    	//Si le joueur est vivant et n'a toujours pas réalisé l'action pour le tour actuel
    	
    	if (joueurs[num].isAlive() && ! joueurs[num].aRealiseAction(action) ) {
	    	switch (action) {
	    	case 0:	    		
	    		deplaceJoueur(num, direction, action);	    		    		
	    		break;
	    	case 1: 
	    		assecheCellule(xM, yM, num, action);
	        	desSelectionneTout();
	    		break;   
	    	case 2:
	    		recupereArtefact(num);
	    		break;
	    		
	    	}	    		
	    	notifyObservers();
    	}
    	
    }
    

    /**
     * Deplace un joueur numero 'num'
     * @param num : int, numéro du joueur
     * @param direction : Direction
     * @param action : int, action de 0 à 2 
     */
    public void deplaceJoueur(int num, Direction direction, int action) {
		joueurs[num].updatePos(direction);   	
    }
    
    /**
     * Assèche la cellule i j
     * @param i : int 
     * @param j : int
     * @param num : int, numéro du joueur
     * @param action : int, numéro de l'action
     */
    public void assecheCellule(int i, int j, int num, int action) {
    	if (estSelectionnablePourAsseche(cellules[i][j]) ) {
			cellules[i][j].asseche();
			joueurs[num].realiseAction(action); 
			selctionneCel(i, j);
    	}
    }
    
    /**
     * Retire l'artefact i de la grille.
     */
    public void retireArtefact(int i) {
    	elements[i].recuperer();
    }
	/**
	 * Renvoie vrai si la cellule est selectionnable : si la cellule est voisine du joueur, quelle 
	 * est inondée et que le joueur n'a toujours pas réalisé l'action d'assècher
	 */
	public boolean estSelectionnablePourAsseche(Cellule c) {
		int i = CtrlActJoueur.joueurActif();
		return c.estInondee() && joueurs[i].getPos().estVoisine(c) && ! joueurs[i].aRealiseAction(1);
	}
	
	
    /**
     * le joueur num recupere un artefact
     * @param num : indice joueur
     */
    public void recupereArtefact(int num) {
    	joueurs[num].recupereArtefact();   	
    }
    
    /**
     * Réalise l'action spécilae action pour le joueur num
     * @param num int : numéro du joueur
     * @param action int : numéro de l'action 
     */
    public void actionSpecJoueur(int num, int action, boolean teleporteAll) {
    	if (joueurs[num].isAlive() && joueurs[num].actionSpec(action) ){
	    	switch (action) {
	    	case 0:	   
	    		bacASable(num, action);
	    		desSelectionneTout(); 
	    		break;
	    	case 1: 
	    		if (!teleporteAll)
	    			teleporteJoueur(num);
	    		else {
	    	    	int x = joueurs[num].getPos().getX();	
	    	    	int y = joueurs[num].getPos().getY();
	    	    	for (int i=0; i< nbJoueurs; i++) {
	    	    		if (joueurs[i].getPos().getX()==x &&joueurs[i].getPos().getY()==y  ) {
	    	    			teleporteJoueur(i);
	    	    		}
	    	    	}
	    		}
    			joueurs[num].useActionSpec(action);
	    		desSelectionneTout();        	
	    		break;   
	    	}    		
	    	notifyObservers();
    	}
    	
    }
    
    /**
     * Réalise l'action spéciale bac à sable pour le joueur num : assèche la céllule sur laquelle on a cliqué
     * @param num int : num du joueur
     * @param action : num de l'action ici 0
     */
    public void bacASable(int num, int action) {
		if (estSelectionnable(cellules[xM][yM])) {   	
			cellules[xM][yM].asseche();
			joueurs[num].useActionSpec(action);			
		}   	
    }
    
    /**
     * Réalise l'action spéciale Hélicoptère pour le joueur num 
     * @param num int : num du joueur
     */
    public void teleporteJoueur(int num) {
		if (estSelectionnable(cellules[xM][yM])) {  
			joueurs[num].setPos(cellules[xM][yM]);
		}
    	
    }


    /**
     * Si il existe un joueur sur la meme case que num on affiche si le joueur veut le déplacer avec lui ou non
     * Sinon on déplace diréctement le joueur
     */
    public void choixHelico(int num) {
    	int x = joueurs[num].getPos().getX();	
    	int y = joueurs[num].getPos().getY();
    	boolean affiche = false;
    	for (int i=0; i< nbJoueurs; i++) {
    		if (i!= num && joueurs[num].actionSpec(1) && joueurs[i].getPos().getX()==x &&joueurs[i].getPos().getY()==y  ) {
    			affiche = true;
    		}
    	}
    	if (affiche)
    		javax.swing.JOptionPane.showMessageDialog(null,"Voulez-vous emmener avec vous les autres joueurs ?\n Appuyer sur (Y) pour 'oui' ou (N) pour 'non'.");
    	else 
    		actionSpecJoueur(num, 1, false);
    }
	/**
	 * Renvoie vrai si la cellule c est selectionnable 
	 */
	public boolean estSelectionnable(Cellule c) {
		int i = CtrlActJoueur.joueurActif();
		return c.estValide() && joueurs[i].possedeUneActionSpc() && !(c.etat instanceof EtatSubmerge) ;
		
	}
    
    /**
     * Recherche une clé pour le joueur num
     * @param num : int
     */
    public void rechercheCle(int num) {
    	if (joueurs[num].isAlive()) {
	    	double proba = Math.random(); 
			if (proba < 0.10) {
				joueurs[num].initialiseActionsSpc();
			} 
			else {
				if (proba < 0.30) {
					joueurs[num].initialiseCle();
				} 
				
				else{
					if (proba < 0.50) {
					joueurs[num].getPos().inonde();
					}
				}
			}
			
			metAJourJoueurs();
			notifyObservers();
    	}
    }
   /**
     * Vérifie si les joueurs sont sur une zone submergée, 
     * si c'est le cas on déplace le joueur vers une zone adjacente 
     * non submergée si elle existe sinonle joueur meurt
     */
    public void metAJourJoueurs() {
    	for (int i=0; i< nbJoueurs; i++) {    		
    		if (joueurs[i].getPos().etat instanceof EtatSubmerge)
    			if(!existeZoneSafe(joueurs[i])) {    			
	    			joueurs[i].kill();
    		}
    	}
    }
    
    /**
     * Cherche si il existe parmi les cases voisines du joueur une case non submergée
     * dans ce cas là y déplace le joueur et renvoie vrai sinon renvoie faux
     */
    public boolean existeZoneSafe(Joueur joueur){
    	Cellule c = joueur.getPos();
    	int[][] cellSafe = Fonctions.sontSafe(cellules, c.getX()-1, c.getY()-1, c.getX()+1, c.getY()+1);
    	if (cellSafe.length != 0) {
    		int[][] cellAlea = Fonctions.choisiNCases(cellSafe, 1);
    		joueur.setPos(cellules[cellAlea[0][0]][cellAlea[0][1]]);
    		return true;
    	}
 
    	return false;
    }

    
    /**
     * Incrémente le compteur pour voir quel est le prochain joueur à partir du joueur num
     */
    public void prochainJoueur(int num) {
 		
		//On incrémente tant qu'il y'a des joueurs morts
		while(! joueurs[(num)%nbJoueurs].isAlive() && ! tousMorts()) {
    		CtrlActJoueur.incremente();
    		num++;   
		}
		//Passe au prochain joueur
		CtrlActJoueur.incremente();

	}

    /**Renvoie vrai si tous les joueurs sont morts sinon faux
     */
	public boolean tousMorts() {
		for(int i=0; i< nbJoueurs; i++) {
			if (joueurs[i].isAlive()) 
				return false;
		}
		return true;
	
	}
	    
 
    
    /**
     * Retourne le joueur i
     * @param i : int
     * 
     */
    public Joueur  getJoueur(int i) {
    	return joueurs[i];
    }
    
    
    /**
     * renvoie la cellule x y
     * @param x : int
     * @param y : int
     */
    public Cellule getCellule(int x, int y) {
    	return cellules[x][y];
    }
    /**
     * Renvoie l'element i 
     */
    public Element getElem(int i) {
    	return elements[i];
    }

    /**
     * Enregistre les coordonnées de la case sur laquelle on a cliqué avec la souris.
     */
    
    public void initialiseCoordonnees(int x, int y) {
    	this.xM = x;
    	this.yM = y;
    }
    /**
     * Renvoie le nombre de joueurs
     * @return
     */
	public static int nombreJoueurs() {
		return nbJoueurs;
	}
	/**
	 * Réinitialise les actions du joueur i 
	 */
	public void reinitialiseActionsJ(int i) {
		joueurs[i].reinitialiseActions();
	}
	
	/**
	 * Selectionne ou desselectionne une cellule 
	 */
	public void selctionneCel(int i, int j) {
		
		cellules[i][j].selectionne();
		notifyObservers();
	}
	

	
	
	/**
	 * Renvoie vrai si tous les artefacts ont été recupérés : 
	 * si les cases qui contenaient des artefacts sont maintenant None.
	 */
	public boolean tousRecuperes() {
		for(int i=0; i< elements.length-1; i++) {
			if (!elements[i].aEteRecup())
				return false;
		}
		return true;
	}
	
	/**
	 * Renvoie vrai si tous les joueurs sont sur la case qui contient l'Héliport
	 */
	public boolean tousSurHeliport() {
		for (int i=0; i< nbJoueurs; i++) {
			if (joueurs[i].getPos().getId() != Spec.Heliport)
				return false;
		}
		return true;
	}

	/**
	 * Renvoie vrai si tous les joueurs sont sur l'héliport et tous les artefacts ont été récup.
	 */
	public boolean partieGagnee() {
		return tousSurHeliport() && tousRecuperes();
	}
	
	/**
	 * Renvoie vrai si un joueur est mort
	 */
	public boolean unJoueurMort() {
		for (int i=0; i< nbJoueurs; i++) {
			if (!joueurs[i].isAlive())
				return true;
		}
		return false;
	}
	
	/**
	 * Renvoie vrai si un element (un artefact non récupéré ou l'héliport) a été submergé
	 */
	public boolean unElemSubmerge() {
		for (int i=0; i<elements.length; i++) {
			//Si c'est un héliport et a ete submergé 
			if (elements[i].getId() == Spec.Heliport  && elements[i].getPos().isNotSafe())
				return true;
			//Si l'artefact n'a pas ete recuperé et que sa case est submergée
			if( elements[i].getId() != Spec.Heliport && !elements[i].aEteRecup() && elements[i].getPos().isNotSafe())
				return true;
		}
		return false;

	}
	/**
	 * Renvoie vrai si la partie est perdue faux sinon
	 */
	
	public boolean partiePerdue() {
		return unJoueurMort() || unElemSubmerge();
	}
	
	/**
	 * Desselectionne toutes les cellules.
	 */
	public void desSelectionneTout() {
		for(int i=1; i<=LARGEUR; i++) {
		    for(int j=1; j<=HAUTEUR; j++) {
		    	cellules[i][j].desSelectionne();
		    }
		}
	}

}