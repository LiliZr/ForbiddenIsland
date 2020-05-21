package vue;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import modele.Modele;

public class Images {
	public static final String DIRECTORY = "./images/";
	public static final String[] names = {"feu.png", "air.png", "eau.png", "terre.png", "heliport.png", "croix.png"};
	
	public static Image[]  joueurs;
	public static Image joueurM;
	
	public static Image[] objets;
	
	public static Image victoire, defaite;
	
	public static Image[] background;
	
	public static Image button;

	
	
	public Images(){
		chargeImageJoueurs () ;
	}
	public static void chargeImageJoueurs () {
		joueurs = new Image[Modele.nbJoueurs];
		objets = new Image[names.length];
		background = new Image[2];
		try {
			for (int i=0; i<Modele.nbJoueurs; i++) {

				joueurs[i]= ImageIO.read(new File(DIRECTORY+"j"+i+".png"));
			}
			joueurM = ImageIO.read(new File(DIRECTORY+"mort.png"));
			victoire = ImageIO.read(new File(DIRECTORY+"victory.jpg"));
			defaite = ImageIO.read(new File(DIRECTORY+"def.jpg"));
			for (int i=0; i<background.length; i++) {

				background[i]= ImageIO.read(new File(DIRECTORY+"back"+i+".jpg"));
			}
			
			
			for (int i=0; i<objets.length; i++) {
				objets[i] = ImageIO.read(new File(DIRECTORY+names[i]));
			}
			
			button = ImageIO.read(new File(DIRECTORY+"button.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
