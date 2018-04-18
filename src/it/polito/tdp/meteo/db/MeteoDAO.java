package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;
import java.util.Calendar;
import java.util.List;

import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		List<Rilevamento> rilevamenti = this.getAllRilevamenti();
		List<Rilevamento> l = new LinkedList<>();
		
		for(Rilevamento r: rilevamenti) {
			Calendar c = this.toCalendar(r.getData());
			if(r.getLocalita().compareTo(localita) == 0 && c.get(Calendar.MONTH)+1 == mese)
				l.add(r);
		}

		return l;
	}
	
	public Calendar toCalendar(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c;
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {
		
		List<Rilevamento> rilevamenti = this.getAllRilevamentiLocalitaMese(mese, localita);
		double somma = 0;
		
		for(Rilevamento r: rilevamenti) {
			somma += r.getUmidita();
		}
		
		return somma/rilevamenti.size();
	}

}
