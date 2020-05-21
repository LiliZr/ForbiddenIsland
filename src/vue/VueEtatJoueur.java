package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.Joueur;
import modele.Modele;
import tools.Observer;
import tools.Spec;

public class VueEtatJoueur extends JPanel implements Observer{
	private Modele m;
	protected static int taille = VueModele.TAILLE;
	private int largeur, hauteur;
	
	public VueEtatJoueur(Modele m) {
		//Liaison vers le modele
		this.m = m;
		m.addObserver(this);
		
		//Reglage size
		largeur = 240;
		hauteur = taille*Modele.HAUTEUR;
		Dimension dim = new Dimension(largeur, hauteur);				
		this.setPreferredSize(dim);

		//Titre du panel
		JLabel label = new JLabel("Etat des joueurs: ");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("default", Font.BOLD,16));
		this.add(label);

		//Charge les images du joueurs pour les afficher par la suite
		Images.chargeImageJoueurs();
	}
	
	 public void update() { 
	    	repaint(); 
	 }

	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(Images.background[0], 0, 0, largeur, hauteur, this);
		 Image img;
		 int espJoueur = 4*taille;
		
		for (int i=0; i<Modele.nbJoueurs; i++) {
			/*Affiche les joueurs*/
			Joueur joueur = m.getJoueur(i);
			
			if (joueur.isAlive())
				img = Images.joueurs[i];
			else 
				img = Images.joueurM;
			
			int posJ = i*espJoueur+taille*2;
			g.drawImage(img, 0, posJ, taille, taille, this);	
			
			/*Affiche les noms des joueurs*/
			g.setColor(Color.WHITE);
			g.setFont(new Font("default", Font.BOLD, 13));			
			g.drawString("Joueur"+(i+1), taille+2, posJ);
			
			
			g.setFont(new Font("default", Font.BOLD, 12));
			
			paintActionsSpec(g, joueur, taille+62, posJ) ;
			
			/*Affiche les cles en la possession du joueur*/
			int xCle = taille+5;   int yCle = posJ+15;
			g.drawString(" - Clés : ", xCle, yCle);			
			paintCles( g, joueur, xCle+10, yCle+15);
			
			/*Affiche les Artefacts en la possession du joueur*/
			int xArt = taille+5;   int yArt = posJ+45;
			g.drawString(" - Artefacts : ", xArt, yArt);
			paintArtefacts(g, joueur, xArt+10, yArt+15);


		}
	 }
	 
	 /**
	  * Parcourt toutes les clés du joueur et les affiche à la position x,y.
	  */
	 public void paintCles(Graphics g, Joueur joueur, int x, int y) {
			List<Spec> cles = joueur.clesPossedees();
			int esp = 40;
			int i = 0;
			for (Spec c : cles) {
				g.drawString(c.toString() +".", x+(esp*i), y);
				i++;
			}
	 }
	 
	 /**
	  * Parcourt tous les artefacts du joueur et les affiche à la position x, y.
	  */
	 public void paintArtefacts(Graphics g, Joueur joueur, int x, int y) {
			List<Spec> art = joueur.artefactPossedes();
			int esp = 40;
			int i = 0;
			for (Spec a : art) {
				g.drawString(a.toString() +".", x+(esp*i), y);
				i++;
			}
	 }
	 
	 
	 public void paintActionsSpec(Graphics g, Joueur joueur, int x, int y) {
		 int esp = 70;
		 int i = 0;
		 if (joueur.possedeUneActionSpc()) {
			 if (joueur.actionSpec(0)) {
				 g.drawString("Bac à Sable", x+ esp*i, y);
				 i++;
			 }
			 if (joueur.actionSpec(1)) {
				 g.drawString("Hélicoptère", x+ (esp*i), y);
			 }
			 
				 
		 }
	 }
	 
	 

}
