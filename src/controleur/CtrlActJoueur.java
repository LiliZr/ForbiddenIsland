package controleur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import modele.Modele;
import tools.Direction;

public class CtrlActJoueur  implements KeyListener{

    Modele m;
    private static int cpt;
    private int previousKey;
    public CtrlActJoueur(Modele m) { 
    	this.m = m;
    	CtrlActJoueur.cpt = 0;
    }

    
	public void keyPressed(KeyEvent e) {
    	Direction d = valeurDirection(e);
    	int numJoueur = joueurActif();
    	int key = e.getKeyCode();
    	
    	/**
    	 * Touches directionnelles pour se déplacer
    	 */
    	if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN ||
    		key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) 
    		m.actionJoueur(numJoueur, d, 0);
    	/**
    	 * Enter pour assecher
    	 */
    	if (key == KeyEvent.VK_ENTER ) {
    		m.actionJoueur(numJoueur, d, 1);
    	}
    	/**
    	 * Espace pour récuperer un artefact
    	 */
    	if (key == KeyEvent.VK_SPACE) {
    		   m.actionJoueur(numJoueur, d, 2);
    	}
    	
    	/**
    	 * A pour l'action spéciale Assèche
    	 */
    	if (key == KeyEvent.VK_A) {
    		m.actionSpecJoueur(numJoueur, 0, true);
    	}
    	/**
    	 * H pour l'action spéciale hélicoptère
    	 */
    	if (key == KeyEvent.VK_H) {
    		m.choixHelico(numJoueur);
    	}
    	
    	/**
    	 * Oui ou non pour emporter les autres joueurs 
    	 */
    	if (previousKey == KeyEvent.VK_H) {
    		if (key == KeyEvent.VK_Y) {
        		m.actionSpecJoueur(numJoueur, 1, true);
    		}
    		if (key == KeyEvent.VK_N) {
        		m.actionSpecJoueur(numJoueur, 1, false);
    		}
    	}

    	 
    	previousKey = e.getKeyCode();
    	

	}
	public Direction valeurDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			return Direction.Up;
		case KeyEvent.VK_DOWN:
			return Direction.Down;
		case KeyEvent.VK_LEFT:
			return Direction.Left;
		case KeyEvent.VK_RIGHT:
			return Direction.Right;
		default:
			return Direction.None;
		}
	}
    public static void incremente() {
    	cpt++;
    }
	public static int joueurActif() {
		return (cpt%Modele.nombreJoueurs());
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}



}
