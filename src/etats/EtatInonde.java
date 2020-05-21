package etats;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import vue.Images;

public class EtatInonde extends Etat {
	static final String path = Images.DIRECTORY + "inonde.jpg";
	static Image image;
	
	public EtatInonde() {
		name = "Inonde";		
	}

	/**
	 * Charge l'image de cet etat
	 */
	public static void chargeImage() {
			try {
				image = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}			
	}
	
	@Override
	public Image renvoieImage() {
		return EtatInonde.image;
	}
	
	@Override
	public Etat inonde() {
		Etat newEtat = new EtatSubmerge();
		return newEtat;
	}

	@Override
	public Etat asseche() {
		Etat newEtat = new EtatSec();
		return newEtat;
	}

}
