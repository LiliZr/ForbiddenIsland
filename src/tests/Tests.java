package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.Cellule;
import modele.Joueur;
import modele.Modele;

public class Tests {

	@Test
	public void testEtats() {
		Modele m = new Modele();
		Cellule c = new Cellule(m, 1, 1);
		c.inonde();
		assertEquals(c.etat.toString(), "Inonde");
		c.asseche();
		assertEquals(c.etat.toString(), "Sec");
		c.inonde();
		c.inonde();
		assertEquals(c.etat.toString(), "Submerge");
	}

	@Test
	public void test3CasesDiffInondees() {
		Modele m = new Modele();
		int i=-1;
		int c = FoncTest.countInondable(m);
		while(c>3) {
			boolean[][] avant = FoncTest.booleanTabCorr(m);
			m.realiseUnTour();
			boolean[][] apres = FoncTest.booleanTabCorr(m);
			i = FoncTest.countDiff(avant, apres);
			System.out.println(i);
			c = FoncTest.countInondable(m);
			assertEquals(i, 3);
		}
	}

}
