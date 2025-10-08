package calendari;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecondsCalculatorTest
{
	SecondsCalculator calc;

	void ValorsIncorrectesException(int dia, int mes, int any, int hora, int minut, int segon)
	{
		try
		{
			calc.SegonsFins(dia, mes, any, hora, minut, segon);
			assertTrue(false);
		}
		catch (Exception e){}

	}
	
	@BeforeEach
	void setUp() throws Exception
	{
		// Creació de l'objecte calc per poder cridar-lo des dels mètodes de prova
		calc = new SecondsCalculator();
	}

	@Test
	void testDiesDelMes()
	{
		// Cas més fàcil: anys no multiples de 4. 
		assertEquals(calc.getDiesDelMes(1,2001), 31);
		assertEquals(calc.getDiesDelMes(2,2001), 28);
		assertEquals(calc.getDiesDelMes(3,2001), 31);
		assertEquals(calc.getDiesDelMes(4,2001), 30);
		assertEquals(calc.getDiesDelMes(5,2001), 31);
		assertEquals(calc.getDiesDelMes(6,2001), 30);
		assertEquals(calc.getDiesDelMes(7,2001), 31);
		assertEquals(calc.getDiesDelMes(8,2001), 31);
		assertEquals(calc.getDiesDelMes(9,2001), 30);
		assertEquals(calc.getDiesDelMes(10,2001), 31);
		assertEquals(calc.getDiesDelMes(11,2001), 30);
		assertEquals(calc.getDiesDelMes(12,2001), 31);
		
		// Anys de traspas multiples de 4
		assertEquals(calc.getDiesDelMes(2,1980), 29);
		assertEquals(calc.getDiesDelMes(2,1984), 29);
		assertEquals(calc.getDiesDelMes(1,1980), 31);
		assertEquals(calc.getDiesDelMes(4,1980), 30);

		// Anys de traspas multiples de 100
		assertEquals(calc.getDiesDelMes(2,1900), 28);
		assertEquals(calc.getDiesDelMes(1,1900), 31);
		assertEquals(calc.getDiesDelMes(4,1900), 30);
		
		// Anys de traspas multiples de 400
		assertEquals(calc.getDiesDelMes(2,2000), 29);
		assertEquals(calc.getDiesDelMes(1,2000), 31);
		assertEquals(calc.getDiesDelMes(4,2000), 30);

		// Valors fora de rang
		assertEquals(calc.getDiesDelMes(0,2000), -1);
		assertEquals(calc.getDiesDelMes(13,2000), -1);
	
	}

	@Test
	void testIsAnyTraspas()
	{
		// Casos més simples, basats simplement en si el mes és múltiple de 4 o no
		assertTrue(calc.getIsAnyTraspas(1980));
		assertFalse(calc.getIsAnyTraspas(1981));
		assertFalse(calc.getIsAnyTraspas(1982));
		assertFalse(calc.getIsAnyTraspas(1983));
		assertTrue(calc.getIsAnyTraspas(1984));

		// Multiples de 100, però no de 400
		assertFalse(calc.getIsAnyTraspas(1900));
		assertFalse(calc.getIsAnyTraspas(2100));
		assertFalse(calc.getIsAnyTraspas(2200));

		// Multiples de 400
		assertTrue(calc.getIsAnyTraspas(1600));
		assertTrue(calc.getIsAnyTraspas(2000));
		assertTrue(calc.getIsAnyTraspas(2400));
	}

	@Test
	void testSegonsFins()
	{
		long lSegonsPerMinut = 60;
		long lSegonsPerHora = 60 * lSegonsPerMinut;
		long lSegonsPerDia = 24 * lSegonsPerHora;

			// Salts d'anys sencers
		
		// Comprovem que els factors anteriors ens permeten calcular correctament els segons que hi ha en un any de traspas
		assertEquals(366*lSegonsPerDia,31622400);

		// Saltem just un any, que en aquest cas és un any de traspàs (doncs comencem l'any 1980)
		assertEquals(calc.SegonsFins(1,1,1981,0,0,0),366*lSegonsPerDia);
		// Saltem dos anys, que en aquest cas és un any de traspàs i un que no ho és
		assertEquals(calc.SegonsFins(1,1,1982,0,0,0),(366+365)*lSegonsPerDia);

			// Salts de anys i mesos sencers

		// Saltem multiples d'un mes
		assertEquals(calc.SegonsFins(1,2,1980,0,0,0),31*lSegonsPerDia);
		assertEquals(calc.SegonsFins(1,3,1980,0,0,0),(31+29)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(1,4,1980,0,0,0),(31+29+31)*lSegonsPerDia);

		// Saltem multiples d'un any i d'un mes
		assertEquals(calc.SegonsFins(1,2,1981,0,0,0),(366+31)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(1,3,1981,0,0,0),(366+31+28)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(1,2,1982,0,0,0),(366+365+31)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(1,3,1982,0,0,0),(366+365+31+28)*lSegonsPerDia);

			// Saltem dies
		
		// Copiem algun dels casos anteriors i afegim dies
		assertEquals(calc.SegonsFins(2,3,1982,0,0,0),(366+365+31+28+1)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(3,3,1982,0,0,0),(366+365+31+28+2)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(31,3,1982,0,0,0),(366+365+31+28+30)*lSegonsPerDia);

			// Saltem hores
		
		// Copiem algun dels casos anteriors i afegim dies
		assertEquals(calc.SegonsFins(31,3,1982,1,0,0),(366+365+31+28+30)*lSegonsPerDia + 1*lSegonsPerHora);
		assertEquals(calc.SegonsFins(31,3,1982,2,0,0),(366+365+31+28+30)*lSegonsPerDia + 2*lSegonsPerHora);

			// Saltem minuts
		
		// Copiem algun dels casos anteriors i afegim minuts
		assertEquals(calc.SegonsFins(31,3,1982,2,2,0),(366+365+31+28+30)*lSegonsPerDia + 2*lSegonsPerHora + 2*lSegonsPerMinut);

			// Saltem segons
		
		// Copiem algun dels casos anteriors i afegim segons
		assertEquals(calc.SegonsFins(31,3,1982,2,2,2),(366+365+31+28+30)*lSegonsPerDia + 2*lSegonsPerHora + 2*lSegonsPerMinut+ 2);
	

			// Control valors paràmetres d'entrada

		// Segons fora de rang
		ValorsIncorrectesException(1,1,1981,0,0,-1);
		ValorsIncorrectesException(1,1,1981,0,0,60);

		// Minuts fora de rang
		ValorsIncorrectesException(1,1,1981,0,-1,0);
		ValorsIncorrectesException(1,1,1981,0,60,0);

		// Hores fora de rang
		ValorsIncorrectesException(1,1,1981,-1,0,0);
		ValorsIncorrectesException(1,1,1981,24,0,0);
	
		// Mes fora de rang
		ValorsIncorrectesException(1,0,1981,0,0,0);
		ValorsIncorrectesException(1,13,1981,0,0,0);

		// Dies fora de rang
		// Mes de 31 dies
		ValorsIncorrectesException(0,1,1981,0,0,0);
		ValorsIncorrectesException(32,1,1981,0,0,0);
		
		// Mes de 30 dies
		ValorsIncorrectesException(0,4,1981,0,0,0);
		ValorsIncorrectesException(31,4,1981,0,0,0);
		
		// Mes de febrer anys de no traspas
		ValorsIncorrectesException(0,2,1981,0,0,0);
		ValorsIncorrectesException(29,2,1981,0,0,0);

		// Mes de febrer anys de traspas multiple de 4
		ValorsIncorrectesException(0,2,1984,0,0,0);
		ValorsIncorrectesException(30,2,1984,0,0,0);

		// Mes de febrer anys de traspas multiple de 100
		ValorsIncorrectesException(0,2,2100,0,0,0);
		ValorsIncorrectesException(29,2,2100,0,0,0);

		// Mes de febrer anys de traspas multiple de 400
		ValorsIncorrectesException(0,2,2100,0,0,0);
		ValorsIncorrectesException(30,2,2000,0,0,0);
		
		
			// PAIRWISE TESTING
			// Hem vist a classe que fer TOTS els casos de test utilitzant particions equivalents
			// i valors limit i frontera genera un nombre de casos de prova massa elevat. Per tant,
			// decidim fer pairwise testing d'alguns paràmetres. Només en fem pels paràmetres
			// dia, mes i any, utilizant els valors de l'exercici que es proposa:
		
			// dia: 28, 29, 30, 31, 32
			// mes: 2, 3, 4
			// any: 2001, 2004, 1900, 2000
		
		long ndies_fins_2000 = 366*5+365*15;
		long ndies_fins_2001 = 366*6+365*15;
		long ndies_fins_2004 = 366*6+365*18;

		long ndies_cada_20_anys = 366*5+365*15;
		long ndies_fins_2100 = ndies_cada_20_anys*6;
		
		assertEquals(calc.SegonsFins(28,3,2004,0,0,0),(ndies_fins_2004+31+29+27)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(28,4,2100,0,0,0),(ndies_fins_2100+31+28+31+27)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(28,2,2000,0,0,0),(ndies_fins_2000+31+27)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(28,3,2001,0,0,0),(ndies_fins_2001+31+28+27)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(29,3,2100,0,0,0),(ndies_fins_2100+31+28+28)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(29,4,2000,0,0,0),(ndies_fins_2000+31+29+31+28)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(29,3,2001,0,0,0),(ndies_fins_2001+31+28+28)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(29,2,2004,0,0,0),(ndies_fins_2004+31+28)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(30,4,2001,0,0,0),(ndies_fins_2001+31+28+31+29)*lSegonsPerDia);
		assertEquals(calc.SegonsFins(30,3,2004,0,0,0),(ndies_fins_2004+31+29+29)*lSegonsPerDia);
		ValorsIncorrectesException(30,2,2100,0,0,0);
		assertEquals(calc.SegonsFins(30,3,2000,0,0,0),(ndies_fins_2000+31+29+29)*lSegonsPerDia);

		ValorsIncorrectesException(31,2,2004,0,0,0);
		assertEquals(calc.SegonsFins(31,3,2100,0,0,0),(ndies_fins_2100+31+28+30)*lSegonsPerDia);
		ValorsIncorrectesException(31,2,2000,0,0,0);
		ValorsIncorrectesException(31,4,2001,0,0,0);
		
		ValorsIncorrectesException(32,3,2000,0,0,0);
		ValorsIncorrectesException(32,2,2001,0,0,0);
		ValorsIncorrectesException(32,3,2001,0,0,0);
		ValorsIncorrectesException(32,4,2004,0,0,0);
		ValorsIncorrectesException(32,2,1900,0,0,0);

	}


}
