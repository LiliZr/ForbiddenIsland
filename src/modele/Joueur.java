package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tools.Direction;
import tools.Spec;

public class Joueur {
	private Modele m;
	private Cellule pos;	
	private boolean[] actions, actionsSpec, actionsSpecUsed;
	private boolean alive;
	private List <Spec> cles, artefacts;
	
	public Joueur(Modele m, Cellule c) {
		this.m = m;
		this.pos = c;
		this.actions = new boolean[3]; 				//Les actions normales qu'un joueur a utilisées durant Le tour actuel
		this.actionsSpec = new boolean[2]; 			//Les actions spéciales qu'un joueur possède
		this.actionsSpecUsed = new boolean[2]; 		//Les actions spéciales que le joueur a utilisées
		this.alive = true;
		this.cles = new ArrayList<Spec>();
		this.artefacts = new ArrayList<Spec>();
	}

	/**
	 * Met l'action 'simple' i à vrai pour dire que le joueur a réalisé cette action durant le tour actuel.
	 * Action 0 = déplace un joueur
	 * Action 1 = Assèche une case 
	 * Action 2 = Récupère un artefact 
	 */
	public void realiseAction(int i) {
		this.actions[i] = true;
	}
	
	/**
	 * Renvoie vrai si l'action i a ete réalisée par le joueur. Faux, sinon
	 */
	public boolean aRealiseAction(int i) {
		return this.actions[i];
	}
	
	/**
	 * Met à jour la nouvelle position du joueur avec la direction donnée
	 */
	
	public void updatePos(Direction d) {
		Cellule posNew = pos;

		switch (d) {
		case Up:
			posNew = m.getCellule(pos.getX(), pos.getY()-1);
			break;
		case Down:
			posNew = m.getCellule(pos.getX(), pos.getY()+1);
			break;
		case Left:
			posNew = m.getCellule(pos.getX()-1, pos.getY());
			break;
		case Right:
			posNew = m.getCellule(pos.getX()+1, pos.getY());
			break;
		case None:
			break;
		}
		if ( posNew.estValide() && !posNew.isNotSafe() ) {
			pos = posNew;
			realiseAction(0);
		}
			
		
	}
	/**
	 * Renvoie vrai si joueur est vivant. Faux, sinon.
	 */
	public boolean isAlive() {
		return this.alive;
	}
	/**
	 * Tue un joueur. (Met son attribut alive à faux)
	 */
	public void kill() {
		this.alive =false;
	}


	/**
	 * Réinitialise les actions simples du joueur.
	 */
	public void reinitialiseActions() {
		this.actions = new boolean[3]; 
	}
	/**
	 * Renvoie la position du joueur.
	 */
	public Cellule getPos (){
		return this.pos;
	}
	
	/**
	 * Change la position du joueur à c.
	 */
	public void setPos (Cellule c){
		this.pos = c;
	}
	
	/**
	 * Recupere un artefact si le joueur possède la clé et est sur une zone d'artefact correspondant
	 */
	public void recupereArtefact() {
		if(!this.cles.isEmpty()) {
			for (Spec cle : cles) {
				if(this.pos.renvoieArtefact() == cle) {
					this.artefacts.add(cle);
					m.retireArtefact(cle.ordinal());
					this.actions[2] = true;
				}
			}
		}
	}
	
	/**
	 * Génère une clé aléatoire pour le joueur
	 */
	public void initialiseCle() {
		Random r = new Random();	    		
		int art = r.nextInt(Spec.values().length-2);
		if(this.cles.size()<4) {
			while (this.cles.contains( Spec.values()[art])) {
				art = r.nextInt(Spec.values().length-2);
			}
			this.cles.add( Spec.values()[art]);
		}
	}
	
	/**
	 * Initialise une action spéciale
	 * ActionSpec 0 :  bac à sable
	 * ActionSpec 1 :  Hélicoptère
	 */
	public void initialiseActionsSpc() {
		Random r = new Random();	    		
		int actSpci = r.nextInt(2);
		if (!this.actionsSpec[actSpci] )
			this.actionsSpec[actSpci] = true;
	}
	

	/**
	 * Renvoie vrai si le joueur possède l'action spéciale 'actionSpec' et ne l'a pas utilisée
	 * ActionSpec 0 :  bac à sable
	 * ActionSpec 1 :  Hélicoptère
	 */
	public boolean actionSpec(int actionSpec) {
		return this.actionsSpec[actionSpec] && !this.actionsSpecUsed[actionSpec];
	}
	/**
	 * On met à vrai l'indice correspondant à l' action speciale utilisée (Pour dire qu'on a utilisé cette action)
	 * ActionSpec 0 :  bac à sable
	 * ActionSpec 1 :  Hélicoptère
	 */
	public void useActionSpec(int actionSpec) {
		this.actionsSpecUsed[actionSpec] = true;
	}

	/**
	 * Renvoie vrai si on possède une action speciale quelconque et on ne l'a pas utilisée
	 */
	public boolean possedeUneActionSpc() {
		return ((this.actionsSpec[0] && !this.actionsSpecUsed[0])
				|| (this.actionsSpec[1] && !this.actionsSpecUsed[1]));
	}
	/**
	 * Renvoie les cles que possede le joueur
	 */
	public List<Spec> clesPossedees(){
		return this.cles;
	}
	
	/**
	 * Renvoie les artefacts que possède le joueur
	 */
	public List<Spec> artefactPossedes(){
		return this.artefacts;
	}
}
