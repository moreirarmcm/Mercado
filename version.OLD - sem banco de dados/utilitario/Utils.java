package utilitario;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
	static NumberFormat nf = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")) ); 
	
	public static String DateParaString(Date data) {
		return Utils.sdf.format(data);
	}
	
	public static String doubleParaString (Double valor) {
		return Utils.nf.format(valor);
	}
	
	public static Double StringParaDouble(String s) {
		try {
			return (Double)Utils.nf.parse(s);
		}catch(ParseException e) {
			return null;
		}
	}
	
	public static void Pausar (int segundos) {
		try {
			TimeUnit.SECONDS.sleep(segundos);
		}catch (InterruptedException e) {
			System.out.println("Não foi possível pausar por " + segundos);
		}
	}
}
