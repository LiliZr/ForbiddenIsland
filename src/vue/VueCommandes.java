package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Modele;
import controleur.Controleur;

public class VueCommandes extends JPanel   {
	
    private Modele m;
    private static JButton boutonAvance;
    private Controleur ctrl;
    protected static int taille = VueModele.TAILLE;
    private int largeur, hauteur;
    
    public VueCommandes(Modele m) {
		this.m = m;
		
		//Reglage size
		largeur = 240;
		hauteur = taille*Modele.HAUTEUR;
		Dimension dim = new Dimension(largeur, hauteur);				
		this.setPreferredSize(dim);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel label1 = new JLabel("<html> Bienvenue sur l'île interdite ! <br/></html>");
		JLabel label2 = new JLabel("<html> Choisir une action : <br/>"
				+ " - Se déplacer. (Touches directionnelles) <br/>"
				+ " - Assecher une zone. (Sélectionner d'abord la zone   avec la souris puis appuyer sur entrer) <br/>"
				+ " - Récuperer un artefact. (Se placer sur l'artefact et appuyer sur espace)  <br/><br/> "
				+ "Pour les actions spéciales :<br/>"
				+ " - Bac à sable : sélectionner la cellule puis appuyer sur A.<br/>"
				+ " - Hélicoptère : sélectionner la cellule puis appuyer sur H.</html>:");
		//label.setVerticalAlignment(JLabel.TOP);
		
		label1.setForeground(Color.BLACK);
		label1.setFont(new Font("default", Font.BOLD,16));
		label2.setForeground(Color.WHITE);
		label2.setFont(new Font("default", Font.BOLD,12));


		this.add(label1);
		this.add(label2);
		
		this.add(Box.createVerticalStrut(100));
	
		boutonAvance = new JButton("");
		boutonAvance.setIcon(new ImageIcon(Images.button));
		this.add(boutonAvance);
		
		ctrl = new Controleur(m);
		boutonAvance.addActionListener(ctrl);
		
		boutonAvance.setFocusable(false) ;	//Eneleve le focus du bouton !

    }
    public static JButton getBoutonAvance() {
    	return boutonAvance;
    }

    
    public void paintComponent(Graphics graph) {
    	super.paintComponent(graph);
    	graph.drawImage(Images.background[1], 0, 0, largeur, hauteur, this);
    }

    

}