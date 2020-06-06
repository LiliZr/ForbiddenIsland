package modele;

import etats.Etat;
import etats.EtatInonde;
import etats.EtatSec;
import etats.EtatSubmerge;
import tools.Spec;

public class Cellule { 
	protected Modele m;
	protected int x, y;
    public Etat etat;
    protected Spec id;
    public boolean modified;
    public boolean selected;
    
    public Cellule(Modele m, int x, int y) {
        this.m = m;
        this.etat = new EtatSec();
        this.x = x; this.y = y;
        this.id = Spec.None;
    }

    /**
     * Inonde la cellule.
     */
	public void inonde() {
		this.modified = !this.modified;
		etat = etat.inonde();
	}
	/**
	 * Initialise la cellule à l'etat e
	 * @param e
	 */
	public void initNewEtat(Etat e) {
		this.etat = e;
		
	}
	/**
	 * Assèche la cellule.
	 */
	public void asseche() {
		this.modified = !this.modified;
		etat = etat.asseche();
	}

	/**
	 * Selectionne une cellule.
	 */
	public void selectionne() {
		this.selected = true;
	}
	
	/**
	 * Desselectionne la cellule.
	 */
	public void desSelectionne() {
		this.selected = false;
	}

	

	/**
	 * Retourne si la cellule est inondable.
	 * 
	 */
	public boolean inondable() {
		return !(this.etat instanceof EtatSubmerge); 
	}
	/**
	 * Retourne si la cellule est seche
	 * 
	 */
	public boolean sec() {
		return (this.etat instanceof EtatSec); 
	}
	
	/**
	 * Retourne si la cellule est inondée
	 * @return
	 */
	public boolean estInondee() {
		return (this.etat instanceof EtatInonde); 
	}
	
	/**
     * Verifie si la cellule est submergée, retourne vrai si c'est le cas sinon faux
     */
    public boolean isNotSafe() {
    	return (this.etat instanceof EtatSubmerge);   	
    }
    
    /**
     * Retourne la coordonnée x
     */
	public int getX() {
		return this.x;
	}
    /**
     * Retourne la coordonnée y
     */
	public int getY() {
		return this.y;
	}
	/**
	 * Retourne vrai si la cellule c est voisine
	 * @param c
	 * @return
	 */
	public boolean estVoisine(Cellule c) {
		return (c.x >= this.x-1 && c.x <=this.x+1 && c.y >=this.y-1 && c.y <=this.y+1);
	}
    /**
     * Vérifie si la cellule, ne dépasse pas la grille.
     * Sert à vérifier les déplacements des joueurs
     * retourne vrai si c'est bon sinon faux
     */
    public boolean estValide() {
    	return ( x>=1 && x<=Modele.LARGEUR && y>=1 && y<= Modele.HAUTEUR);
    }
    
    
    /**
     * Retourne vrai si la cellule contient un artefact (si son id n'est pas du type enum 'None'
     * Pour les cellules (zones normales l'id est toujours None)
     */
    public boolean contientArtefact() {
    	return false;
    }
    
    /**
     * Renvoie l'artefact que la cellule contient
     */
    public Spec renvoieArtefact() {
    	return this.id;
    }

    
    
    /**
     * Renvoie l'id qui est de type enum (artefact ou non)
     * @return
     */
    public Spec getId() {
    	return this.id;
    }
    /**
     * Renvoie vrai si cette cellule est selctionnée, faux sinon
     * @return
     */
    public boolean estSelectionnee() {
    	return this.selected;
    }
    
    /**
     * Met l'identifiant de la cellule à 'e'
     * @param e : Spec
     */
    public void setId(Spec e) {
    	this.id = e;
    }

   
}    