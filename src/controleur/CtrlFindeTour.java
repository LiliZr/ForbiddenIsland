package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modele.Modele;
import vue.VueCommandes;
import vue.VueModele;

public class CtrlFindeTour implements ActionListener {

    Modele m;
    
    public CtrlFindeTour(Modele m) { 
    	this.m = m; 
    }
    
    /**
     * Avance lorsqu'on produit un evenement
     */
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	int joueurActuel = CtrlActJoueur.joueurActif();
    	
    	if (!m.partieGagnee() && !m.partiePerdue()) {
	   	 
			if(source == VueCommandes.getBoutonAvance()){	//Si c'est ce bouton alors c'est fin de tour
			
				//inonde 3 cases
				m.realiseUnTour();
				
				//Reinitialise les actions du joueur actuel
				m.reinitialiseActionsJ(joueurActuel);
				
				//incrémente jusqu'à arriver au prochain joueur non mort (on cherche quel est le prochain joueur)
				m.prochainJoueur(joueurActuel+1);
				
				//Lance la recherche de clé
				m.rechercheCle(joueurActuel);		
				
				//Actualise le label
				joueurActuel = CtrlActJoueur.joueurActif()+1;
				VueModele.setLabel("Joueur n°"+ joueurActuel +" à ton tour !");
				
				//On desselectionne toutes les cellules 			
				m.desSelectionneTout();
			}
		} else {
			VueModele.setLabel(""); //Vide le label en cas de partie finie gagnante ou perdante
		}
    }


}