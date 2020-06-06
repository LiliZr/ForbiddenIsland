package vue;

import java.awt.BorderLayout;
import javax.swing.*;

import controleur.CtrlActJoueur;
import modele.Modele;

public class Vue  {

    private JFrame frame;
    private VueModele grilleGraphique;
    private VueCommandes commandes;
    private CtrlActJoueur c ;
    private VueEtatJoueur vueJoueur ;

    public Vue(Modele m) {
		frame = new JFrame();
		frame.setTitle("Ile interdite");
		frame.setLayout(new BorderLayout());
		
		/*Dimension de la fentre*/


		grilleGraphique = new VueModele(m);
		frame.add(grilleGraphique, BorderLayout.CENTER); 
		
		commandes = new VueCommandes(m);
		frame.add(commandes, BorderLayout.EAST);
		
		vueJoueur = new VueEtatJoueur(m);
		frame.add(vueJoueur, BorderLayout.WEST);
		
		c = new CtrlActJoueur(m);
		frame.addKeyListener(c);
		
		//non redimensionnable
		frame.setResizable(false);

		
		
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	
    }

}
