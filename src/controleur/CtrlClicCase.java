package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import modele.Modele;

public class CtrlClicCase implements MouseListener{
	Modele m;
	
	public CtrlClicCase(Modele m) {
		this.m = m;
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int i = (arg0.getX()/25)+1;
		int j = (arg0.getY()/25)+1;
		
		//On Recupere les coordonnées de la cellule sur la quelle on a cliqué pour les initialiser dans modèle
		if (m.estSelectionnable(m.getCellule(i, j))){
			m.desSelectionneTout();
			m.initialiseCoordonnees(i, j);
			m.selctionneCel(i, j);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
