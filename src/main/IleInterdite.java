package main;

import java.awt.EventQueue;

import modele.Modele;
import vue.Vue;

public class IleInterdite {

    /**
     * Programme principal
     */

    public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
	                Modele m = new Modele();
	                Vue vue = new Vue(m);
	                
		    });
		
    }

}
