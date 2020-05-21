package etats;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import vue.Images;

public class EtatSubmerge extends Etat {
	

	static final String path = Images.DIRECTORY + "submerge.jpg";
	static Image image;

	public EtatSubmerge() {
		name = "Submerge";
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
		return EtatSubmerge.image;
	}
	@Override
	public Etat inonde() {
		return this;
	}


	@Override
	public Etat asseche() {
		return this;
	}

}
