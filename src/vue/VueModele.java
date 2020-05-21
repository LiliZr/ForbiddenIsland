package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.CtrlClicCase;
import etats.EtatInonde;
import etats.EtatSec;
import etats.EtatSubmerge;
import modele.Cellule;
import modele.Element;
import modele.Joueur;
import modele.Modele;
import tools.Observer;
import tools.Spec;

public class VueModele extends JPanel implements Observer{
    private Modele m;
    private CtrlClicCase z;
    protected final static int TAILLE = 25;
    private int largeur, hauteur;
    static JLabel label;

    public VueModele(Modele m) {
    	//chargeImgCases();
    	this.m = m;
    	largeur = TAILLE*Modele.LARGEUR;
    	hauteur = TAILLE*Modele.HAUTEUR;
		Dimension dim = new Dimension(largeur, hauteur);
		
		m.addObserver(this);
		this.setPreferredSize(dim);
		z = new CtrlClicCase(m);
		this.addMouseListener(z);
		
		label = new JLabel("Joueur n°1 à ton tour !");
		this.add(label);
		
		//Charge les images
		chargeImgCases();
		Images.chargeImageJoueurs();

    }

    

    public void update() { 
    	repaint(); 
    }

    public void paintComponent(Graphics graph) {
    	super.paintComponent(graph);
		if (!m.partieGagnee() && !m.partiePerdue()) {
			paintCells(graph);
			paintElements(graph);
			paintPlayers(graph);
    	}
		else {
			Image img = null;
		
			if (m.partieGagnee()) {			
				img = Images.victoire;
				graph.drawImage(img, 0, 0, TAILLE*Modele.LARGEUR, TAILLE*Modele.HAUTEUR, this);
			}else {
				img = Images.defaite;
				graph.drawImage(img, 0, 0, TAILLE*Modele.LARGEUR, TAILLE*Modele.HAUTEUR, this);
			}
		}

    }

    /**
     * initialise les images dans chacune des classes des etats
     */
    public void chargeImgCases() {
    	EtatSubmerge.chargeImage();
    	EtatSec.chargeImage();
    	EtatInonde.chargeImage();
    }
    
    public static void setLabel (String s) {
    	label.setText(s);
    }

    public void paintCells(Graphics graph) {
    	Image img = null;
		/*Associe chaque cellule à l'image qui lui correspond*/
		for(int i=1; i<=Modele.LARGEUR; i++) {
		    for(int j=1; j<=Modele.HAUTEUR; j++) {
		    	
		    	Cellule c = m.getCellule(i, j);
		    	img = c.etat.renvoieImage();
		    	
		    	int x = (i-1)*TAILLE;
		    	int y = (j-1)*TAILLE;
		    	graph.drawImage(img, x, y, TAILLE, TAILLE, this);
		    	
		    	if (c.selected) {
		    		graph.setColor(Color.BLACK);
		    		graph.drawRect(x, y, TAILLE-1, TAILLE-1);

		    		graph.setColor(Color.WHITE);
		    		graph.drawRect(x+1, y+1, TAILLE-3, TAILLE-3);
		    	}


		    }
		} 
    }
    public void paintPlayers(Graphics graph) {
    	Image img = null;
		/*Affiche les joueurs*/
		for (int i=0; i<Modele.nbJoueurs; i++) {
			Joueur joueur = m.getJoueur(i);
			
			int x = joueur.getPos().getX();
			int y = joueur.getPos().getY();
			
			if (joueur.isAlive())
				img = Images.joueurs[i];
			else 
				img = Images.joueurM;
			
			graph.drawImage(img, (x-1)*TAILLE, (y-1)*TAILLE, TAILLE, TAILLE, this);				
		}	
    	
    }

    
    public void paintElements(Graphics graph) {
    	Image img = null;
		for (int i=0; i<Spec.values().length-1; i++) {
			Element elem = m.getElem(i);
			
			int x = elem.getPos().getX();
			int y = elem.getPos().getY();

			if(elem.aEteRecup()) 
				img = Images.objets[Spec.values().length-1];
			else 
				img = Images.objets[elem.getPos().getId().ordinal()];
			
			graph.drawImage(img, (x-1)*TAILLE, (y-1)*TAILLE, TAILLE, TAILLE, this);				
		}	
    	
    }



}
