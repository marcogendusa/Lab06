package it.polito.tdp.meteo;

import java.util.*;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.CittaDAO;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	MeteoDAO meteoDao = new MeteoDAO();
	CittaDAO cittaDao = new CittaDAO();
	
	List<Citta> citta;

	public Model() {
		
		citta = new ArrayList<Citta>();
		for(String s: cittaDao.getAllCities()) {
			Citta c = new Citta(s);
			citta.add(c);
		}
		
	}

	public String getUmiditaMedia(int mese) {
		List<String> citta = cittaDao.getAllCities();
		List<String> medie = new LinkedList<>();
		
		for(String s: citta) {
			String st = ""+s+" "+meteoDao.getAvgRilevamentiLocalitaMese(mese, s);
			medie.add(st);
		}
		
		return medie.toString();
	}

	public String trovaSequenza(int mese) {
		
		recursive(0, new ArrayList<SimpleCity>());
		
		return "TODO!";
	}
	
	public void recursive(int step, List<SimpleCity> cittaVisitate) {
		
		if(step >= NUMERO_GIORNI_TOTALI) {
			
		}
		
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		
		for(SimpleCity sc: soluzioneCandidata) {
			for(Citta c: this.citta) {
				if(c.getNome().compareTo(sc.getNome())==0)
					score += sc.getCosto()*c.getCounter() + 100;
			}
			
		}
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}

}
