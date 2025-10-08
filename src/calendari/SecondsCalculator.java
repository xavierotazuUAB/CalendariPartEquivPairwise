package calendari;

public class SecondsCalculator
{
	public long SegonsFins(int dia, int mes, int any, int hora, int minut, int segon)
	{
		if(hora <0 || hora>23)
		{
			throw new IllegalArgumentException("Hora fora de rang");
		}

		if(minut <0 || minut>59)
		{
			throw new IllegalArgumentException("Minut fora de rang");
		}
		
		if(segon <0 || segon>59)
		{
			throw new IllegalArgumentException("Segon fora de rang");
		}

		if(mes <1 || mes>12)
		{
			throw new IllegalArgumentException("Mes fora de rang");
		}

		if(dia<1 || dia>DiesDelMes(mes,any))
		{
			throw new IllegalArgumentException("Dia fora de rang");
		}

		if(any<1980)
		{
			throw new IllegalArgumentException("Any fora de rang");
		}

		long nSegonsTotal = 0;
		long nSegonsAny = 0;
		
		// Saltem anys sencers
		for (int iAny = 1980; iAny<any; ++iAny)
		{
			int nDiesAny = 365;

			if(IsAnyTraspas(iAny))
				nDiesAny = 366;

			nSegonsAny = nDiesAny*24*60*60;
			nSegonsTotal += nSegonsAny;
		}

		// Saltem mesos sencers
		for (int iMes = 1; iMes<mes; ++iMes)
		{
			int nDiesMes = DiesDelMes(iMes, any);
			int nSegonsMes = nDiesMes*24*60*60;

			nSegonsTotal += nSegonsMes;
		}

		// Saltem dies, hores, minuts i segons
		nSegonsTotal += (dia-1)*24*60*60 + hora*60*60 + minut*60 + segon;


		return nSegonsTotal;
	}
	
	private boolean IsAnyTraspas(int any)
	{
		boolean bTraspas = false;
		
		// Multiple de 4
		if(any%4==0)
		{
			bTraspas = true;
			
			// Multiple de 100
			if(any%100==0)
			{
				bTraspas = false;
				
				// Multiple de 400
				if(any%400==0)
				{
					bTraspas = true;
				}
			}
		}
		
		return bTraspas;
	}
	
	private int DiesDelMes(int mes, int any)
	{
		if(mes<1 || mes>12)
			return -1;

		int n_dies = 0;
		int dies[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		if(mes!=2)
			n_dies = dies[mes-1];
		else
		{
			if(IsAnyTraspas(any))
				n_dies = 29;
			else
				n_dies = 28;
		}
		
		return n_dies;
	}

	
	// Mètodes publics per poder fer testing
	
	public int getDiesDelMes(int mes, int any) {return DiesDelMes(mes,any);}
	public boolean getIsAnyTraspas(int any) {return IsAnyTraspas(any);}
}
