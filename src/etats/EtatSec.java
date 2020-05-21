package etats;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import vue.Images;

public class EtatSec extends Etat{
	static final String path = Images.DIRECTORY + "sable.jpg";
	static Image image; 
	
	public EtatSec() {
		name = "Sec";
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
		return EtatSec.image;
	}
	
	@Override
	public Etat inonde() {
		Etat newEtat = new EtatInonde();
		return newEtat;
	}
	@Override
	public Etat asseche() {
		return this;
	}
	

}
