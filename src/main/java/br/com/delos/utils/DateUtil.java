package br.com.delos.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtil {
	
	private static final String DIA_MES_ANO_SEM_BARRAS = "ddMMyyyy";
	private static final int MILI_SEG_POR_DIA = 86400000;
	public static final String HORA = "hh";
	public static final String HORA24 = "HH";
	public static final String MINUTO = "mm";
	public static final String SEGUNDO = "ss";
	public static final String MES = "MM";
	public static final String DIA = "dd";
	public static final String ANO = "yyyy";
	public static final String DIA_MES_ANO = "dd/MM/yyyy";
	public static final String HORA_MINUTO = "HH:mm";
	public static final String TIMESTAMP = "dd/MM/yyyy HH:mm";
	public static final String HORA_MINUTO_SEGUNDO = "HH:mm:ss";
	public static final String DATA_CACHE = "yyyy-MM-dd";
	public static final String DATA_HORA_CACHE = "yyyy-MM-dd HH:mm:ss";
	public static final String SEGUNDA = "Segunda";
	public static final String TERCA = "Terça";
	public static final String QUARTA = "Quarta";
	public static final String QUINTA = "Quinta";
	public static final String SEXTA = "Sexta";
	public static final String SABADO = "Sábado";
	public static final String DOMINGO = "Domingo";
	public static final String[] MESES_DO_ANO = { "Janeiro", "Fevereiro",
			"Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
			"Outubro", "Novembro", "Dezembro" };
	public static final String[] DIAS_DA_SEMANA = { "Domingo", "Segunda",
			"Terça", "Quarta", "Quinta", "Sexta", "Sábado" };

	private static Calendar CALENDAR = Calendar.getInstance();

	public static String getDataPorExtenso(java.util.Date data) {
		return getDia(data) + " de " + MESES_DO_ANO[(getMes(data) - 1)]
				+ " de " + getAno(data);
	}

	public static String getDataAtualPorExtenso() {
		return getDataPorExtenso(getDataAtual());
	}

	public static java.util.Date getDataAtual() {
		return getData(getTimestampEmMilis());
	}

	public static Time getHoraAtual() {
		return getHora(getTimestampEmMilis());
	}

	public static Timestamp getTimestampAtual() {
		return getTimestamp(getTimestampEmMilis());
	}

	public static Timestamp getTimestamp(long timestampEmMilis) {
		return new Timestamp(timestampEmMilis);
	}

	public static long getTimestampEmMilis() {
		return System.currentTimeMillis();
	}

	public static java.util.Date getData(String dataEmFormatoTexto,
			String mascaraDaData) throws ParseException {
		if (dataEmFormatoTexto != null) {
			SimpleDateFormat formatador = new SimpleDateFormat(mascaraDaData);
			java.util.Date parse = formatador.parse(dataEmFormatoTexto);
			long horaEmMilis = parse.getTime();
			return new java.util.Date(horaEmMilis);
		}
		return null;
	}

	public static long getDataEmMilis(String dataEmFormatoTexto,
			String mascaraDaData) throws ParseException {
		if (dataEmFormatoTexto != null) {
			SimpleDateFormat formatador = new SimpleDateFormat(mascaraDaData);
			java.util.Date parse = formatador.parse(dataEmFormatoTexto);
			return parse.getTime();
		}
		throw new RuntimeException("Parâmetros inválidos para montagem da data");
	}

	public static java.util.Date getData(long dataEmMilisegundos) {
		return new java.util.Date(dataEmMilisegundos);
	}

	public static Time getHora(long horaEmMilisegundos) {
		return new Time(horaEmMilisegundos);
	}

	public static String getDataFormatada(java.util.Date data, String formato) {
		try {
			SimpleDateFormat formatador = new SimpleDateFormat(formato);
			return formatador.format(data);
		} catch (NullPointerException e) {
		}
		return "";
	}

	public static String getDataFormatada(long dataEmMilisegundos,
			String formato) {
		return getDataFormatada(getData(dataEmMilisegundos), formato);
	}

	public static String getHoraFormatada(Time time, String formato) {
		SimpleDateFormat formatador = new SimpleDateFormat(formato);
		return formatador.format(time);
	}

	public static String getHoraAtualFormatada() {
		return getHoraFormatada(getHoraAtual(), "HH:mm");
	}

	public static java.util.Date somaDias(java.util.Date data, int dias) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.add(6, dias);
		return new java.util.Date(calendario.getTimeInMillis());
	}

	public static java.util.Date somaHoras(java.util.Date data, int horas) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.add(10, horas);
		return new java.util.Date(calendario.getTimeInMillis());
	}

	public static java.util.Date somaMeses(java.util.Date data, int meses) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.add(2, meses);
		return new java.util.Date(calendario.getTimeInMillis());
	}

	public static java.util.Date somaAnos(java.util.Date data, int anos) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.add(1, anos);
		return new java.util.Date(calendario.getTimeInMillis());
	}

	public static long diferencaEmMilis(java.util.Date dataFinal,
			java.util.Date dataInicial) {
		return (dataFinal.getTime() - dataInicial.getTime());
	}

	public static long diferencaEmDias(java.util.Date dataFinal,
			java.util.Date dataInicial) {
		return (diferencaEmMilis(dataFinal, dataInicial) / 86400000L);
	}

	public static long diferencaEmAnos(java.util.Date dataFinal,
			java.util.Date dataInicial) {
		return (diferencaEmDias(dataFinal, dataInicial) / 365L);
	}

	public static int getAno(java.util.Date data) {
		return Integer.parseInt(getDataFormatada(data, "yyyy"));
	}

	public static int getAnoAtual() {
		return getAno(getDataAtual());
	}

	public static int getMes(java.util.Date data) {
		return Integer.parseInt(getDataFormatada(data, "MM"));
	}

	public static int getMesAtual() {
		return getMes(getDataAtual());
	}

	public static String getMesPorExtenso(java.util.Date data) {
		return MESES_DO_ANO[(getMes(data) - 1)];
	}

	public static String getMesAtualPorExtenso() {
		return MESES_DO_ANO[(getMes(getDataAtual()) - 1)];
	}

	public static int getDia(java.util.Date data) {
		return Integer.parseInt(getDataFormatada(data, "dd"));
	}

	public static int getDiaAtual() {
		return getDia(getDataAtual());
	}

	public static String getDataAtualFormatada() {
		return getDataFormatada(getDataAtual(), "dd/MM/yyyy");
	}

	public static boolean isDataValida(String data) {
		return isDataValida(data, "dd/MM/yyyy");
	}

	public static boolean isHoraValida(String hora) {
		return isDataValida(hora, "HH:mm");
	}

	public static boolean isDataValida(String data, String formato) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			sdf.setLenient(false);
			sdf.parse(data);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static int getDiaDaSemana(java.util.Date data) {
		GregorianCalendar calendario = new GregorianCalendar(
				TimeZone.getDefault());

		calendario.setTime(data);
		return (calendario.get(7) - 1);
	}

	public static boolean isFinalDeSemana(java.util.Date data) {
		int dia = getDiaDaSemana(data);
		return ((dia == 0) || (dia == 6));
	}

	public static boolean isDiaDeSemana(java.util.Date data) {
		return (!(isFinalDeSemana(data)));
	}

	public static String getDiaDaSemanaFormatado(java.util.Date data) {
		return DIAS_DA_SEMANA[getDiaDaSemana(data)];
	}

	public static String getTimestampFormatado(Timestamp data, String formato) {
		return getDataUtilFormatada(data, formato);
	}

	private static String getDataUtilFormatada(java.util.Date data,
			String formato) {
		try {
			SimpleDateFormat formatador = new SimpleDateFormat(formato);
			return formatador.format(data);
		} catch (Exception e) {
		}
		return "";
	}

	public static String getDataFormatada(java.sql.Date data) {
		return getDataUtilFormatada(data, "dd/MM/yyyy");
	}

	public static String getDataFormatada(java.util.Date data) {
		return getDataUtilFormatada(data, "dd/MM/yyyy");
	}

	public static java.util.Date getData(String string) {
		try {
			return getData(string, "dd/MM/yyyy");
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getNomeDoDiaDaSemana(Timestamp hora) {
		int diaDaSemana = getDiaDaSemana(getData(hora.getTime()));
		return DIAS_DA_SEMANA[diaDaSemana];
	}

	public static String getNomeDoDiaDaSemana(java.util.Date dia) {
		int diaDaSemana = getDiaDaSemana(dia);
		return DIAS_DA_SEMANA[diaDaSemana];
	}

	public static int getUltimoDiaDoMes(int mes, int ano) {
		int[] mesesCom30 = { 4, 6, 9, 11 };
		if (Arrays.binarySearch(mesesCom30, mes) > -1) {
			return 30;
		}
		if (mes == 2) {
			if (ano % 4 == 0) {
				return 29;
			}
			return 28;
		}
		return 31;
	}

	public static boolean isAnoBissexto(int ano) {
		return ((ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0)));
	}

	public static boolean isAnoBissexto(java.util.Date data) {
		return isAnoBissexto(getAno(data));
	}

	public static java.util.Date getDataUtilFormatada(String data) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");

			return getData(getDataUtilFormatada(formato.parse(data),
					"dd/MM/yyyy"));
		} catch (Exception e) {
			return null;
		}
	}

	public static Timestamp getTimestamp(java.util.Date data, int hora,
			int minuto, int segundo) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.set(10, hora);
		calendario.set(12, minuto);
		calendario.set(13, segundo);
		calendario.getTime();
		return new Timestamp(calendario.getTime().getTime());
	}

	public static Time somaMinutos(Time data, int minutos) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.add(12, minutos);
		return new Time(calendario.getTimeInMillis());
	}

	public static String formatarDataPorExtenso(java.util.Date data) {
		Locale objLocale = new Locale("pt", "BR");
		Calendar cal = null;
		cal = new GregorianCalendar();
		cal.setTime(data);
		String mes = new java.text.DateFormatSymbols(objLocale).getMonths()[cal
				.get(2)];
		String dia = (cal.get(5) < 10) ? "0" + cal.get(5) : String.valueOf(cal
				.get(5));
		return dia + " de " + mes + " de " + cal.get(1);
	}

	public static java.util.Date getData(java.util.Date data, int hora,
			int minuto, int segundo, int milissegundo) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);
		calendario.set(11, hora);
		calendario.set(12, minuto);
		calendario.set(13, segundo);
		calendario.set(14, milissegundo);
		calendario.getTime();
		return getData(calendario.getTime().getTime());
	}

	public static java.util.Date getUltimoDiaMes(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int lastDate = calendar.getActualMaximum(5);
		calendar.set(5, lastDate);

		return calendar.getTime();
	}

	public static java.util.Date getPrimeiroDiaMes(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int lastDate = calendar.getActualMinimum(5);
		calendar.set(5, lastDate);

		return calendar.getTime();
	}

	public static java.util.Date endOfDay(java.util.Date date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTime(date);
			calendar.set(11, 23);
			calendar.set(14, 999);
			calendar.set(13, 59);
			calendar.set(12, 59);
			return calendar.getTime();
		}
	}

	public static java.util.Date startOfHour(java.util.Date date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTime(date);
			calendar.set(12, 0);
			calendar.set(13, 0);
			calendar.set(14, 0);
			return calendar.getTime();
		}
	}

	public static java.util.Date endOfHour(java.util.Date date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTime(date);
			calendar.set(12, 59);
			calendar.set(13, 59);
			calendar.set(14, 999);
			return calendar.getTime();
		}
	}

	public static java.util.Date startOfDay(java.util.Date date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTime(date);
			calendar.set(11, 0);
			calendar.set(14, 0);
			calendar.set(13, 0);
			calendar.set(12, 0);
			return calendar.getTime();
		}
	}

	public static long startOfDayInMillis(long date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.set(11, 0);
			calendar.set(14, 0);
			calendar.set(13, 0);
			calendar.set(12, 0);
			return calendar.getTimeInMillis();
		}
	}

	public static long endOfDayInMillis(long date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.set(11, 23);
			calendar.set(14, 999);
			calendar.set(13, 59);
			calendar.set(12, 59);
			return calendar.getTimeInMillis();
		}
	}

	public static java.util.Date nextDay(java.util.Date date) {
		return new java.util.Date(addDays(date.getTime(), 1));
	}

	public static long addDays(long time, int amount) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(time);
			calendar.add(5, amount);
			return calendar.getTimeInMillis();
		}
	}

	public static long nextDay(long date) {
		return addDays(date, 1);
	}

	public static long nextWeek(long date) {
		return addDays(date, 7);
	}

	public static int getDaysDiff(long t1, long t2, boolean checkOverflow) {
		if (t1 > t2) {
			long tmp = t1;
			t1 = t2;
			t2 = tmp;
		}
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(t1);
			int delta = 0;
			while (calendar.getTimeInMillis() < t2) {
				calendar.add(5, 1);
				++delta;
			}
			if ((checkOverflow) && (calendar.getTimeInMillis() > t2)) {
				--delta;
			}
			return delta;
		}
	}

	public static int getDaysDiff(long t1, long t2) {
		return getDaysDiff(t1, t2, true);
	}

	public static boolean isFirstOfYear(long date) {
		boolean ret = false;
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			int currentYear = calendar.get(1);

			calendar.add(5, -1);
			int yesterdayYear = calendar.get(1);
			ret = currentYear != yesterdayYear;
		}
		return ret;
	}

	public static boolean isFirstOfMonth(long date) {
		boolean ret = false;
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			int currentMonth = calendar.get(2);

			calendar.add(5, -1);
			int yesterdayMonth = calendar.get(2);
			ret = currentMonth != yesterdayMonth;
		}
		return ret;
	}

	public static long previousDay(long date) {
		return addDays(date, -1);
	}

	public static long previousWeek(long date) {
		return addDays(date, -7);
	}

	public static long getPreviousDay(long date, int startOfWeek) {
		return getDay(date, startOfWeek, -1);
	}

	public static long getNextDay(long date, int startOfWeek) {
		return getDay(date, startOfWeek, 1);
	}

	private static long getDay(long date, int startOfWeek, int increment) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			int day = calendar.get(7);
			while (day != startOfWeek) {
				calendar.add(5, increment);
				day = calendar.get(7);
			}
			return startOfDayInMillis(calendar.getTimeInMillis());
		}
	}

	public static long getPreviousMonth(long date) {
		return incrementMonth(date, -1);
	}

	public static long getNextMonth(long date) {
		return incrementMonth(date, 1);
	}

	private static long incrementMonth(long date, int increment) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			calendar.add(2, increment);
			return calendar.getTimeInMillis();
		}
	}

	public static long getStartOfMonth(long date) {
		return getMonth(date, -1);
	}

	public static long getEndOfMonth(long date) {
		return getMonth(date, 1);
	}

	private static long getMonth(long date, int increment) {
		Calendar calendar = CALENDAR;
		long result;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			if (increment == -1) {
				calendar.set(5, 1);
				result = startOfDayInMillis(calendar.getTimeInMillis());
			} else {
				calendar.add(2, 1);
				calendar.set(5, 1);
				calendar.set(11, 0);
				calendar.set(14, 0);
				calendar.set(13, 0);
				calendar.set(12, 0);
				calendar.add(14, -1);
				result = calendar.getTimeInMillis();
			}
		}
		return result;
	}

	public static int getDayOfWeek(long date) {
		Calendar calendar = CALENDAR;
		synchronized (calendar) {
			calendar.setTimeInMillis(date);
			return calendar.get(7);
		}
	}

	public static String distanciaDoTempoEmPalavras(java.util.Date dataInicial,
			java.util.Date dataFinal, boolean incluirSegundos) {
		String retorno = "";
		Long distanciaMilisegundos = Long.valueOf(diferencaEmMilis(dataFinal,
				dataInicial));
		Long distanciaSegundos = Long
				.valueOf(distanciaMilisegundos.longValue() / 1000L);
		Long distanciaMinutos = Long
				.valueOf(distanciaSegundos.longValue() / 60L);

		if (existeEntreRange(distanciaMinutos, Integer.valueOf(0),
				Integer.valueOf(1))) {
			if (incluirSegundos) {
				if (existeEntreRange(distanciaSegundos, Integer.valueOf(0),
						Integer.valueOf(4)))
					retorno = menosXSegundos(5);
				else if (existeEntreRange(distanciaSegundos,
						Integer.valueOf(5), Integer.valueOf(9)))
					retorno = menosXSegundos(10);
				else if (existeEntreRange(distanciaSegundos,
						Integer.valueOf(10), Integer.valueOf(19)))
					retorno = menosXSegundos(20);
				else if (existeEntreRange(distanciaSegundos,
						Integer.valueOf(20), Integer.valueOf(39)))
					retorno = meioMinuto();
				else if (existeEntreRange(distanciaSegundos,
						Integer.valueOf(40), Integer.valueOf(60))) {
					retorno = menosXMinutos(1);
				}

			} else if (distanciaMinutos.longValue() == 0L)
				retorno = menosXMinutos(1);
			else {
				retorno = menosXMinutos(distanciaMinutos.intValue());
			}

		} else if (existeEntreRange(distanciaMinutos, Integer.valueOf(2),
				Integer.valueOf(44))) {
			retorno = menosXMinutos(distanciaMinutos.intValue());
		} else if (existeEntreRange(distanciaMinutos, Integer.valueOf(45),
				Integer.valueOf(89))) {
			retorno = cercaXHoras(1);
		} else if (existeEntreRange(distanciaMinutos, Integer.valueOf(90),
				Integer.valueOf(1439))) {
			retorno = cercaXHoras(distanciaMinutos.intValue() / 60);
		} else if (existeEntreRange(distanciaMinutos, Integer.valueOf(1440),
				Integer.valueOf(2529))) {
			retorno = cercaXDias(1);
		} else if (existeEntreRange(distanciaMinutos, Integer.valueOf(2530),
				Integer.valueOf(43199))) {
			retorno = cercaXDias(distanciaMinutos.intValue() / 1440);
		} else if (existeEntreRange(distanciaMinutos, Integer.valueOf(43200),
				Integer.valueOf(86399))) {
			retorno = cercaXMeses(1);
		} else if (existeEntreRange(distanciaMinutos, Integer.valueOf(86400),
				Integer.valueOf(525599))) {
			retorno = cercaXMeses(distanciaMinutos.intValue() / 43200);
		} else {
			Long distanciaAnos = Long
					.valueOf(distanciaMinutos.longValue() / 525600L);
			Long minuteOffSetForLeapYear = Long.valueOf(distanciaAnos
					.longValue() / 4L * 1440L);
			Long remainder = Long
					.valueOf((distanciaMinutos.longValue() - minuteOffSetForLeapYear
							.longValue()) % 525600L);

			if (remainder.intValue() < 13400)
				retorno = cercaXAnos(distanciaAnos.intValue());
			else if (remainder.intValue() < 394200)
				retorno = maisXAnos(distanciaAnos.intValue());
			else {
				retorno = quaseXAnos(distanciaAnos.intValue() + 1);
			}
		}

		return retorno;
	}

	private static boolean existeEntreRange(Long valor, Integer inicio,
			Integer fim) {
		return ((valor.intValue() <= fim.intValue()) && (valor.intValue() >= inicio
				.intValue()));
	}

	public static java.util.Date avancarDataEmDias(java.util.Date data,
			Integer numeroDias) {
		return avancarData(data, Integer.valueOf(5), numeroDias);
	}

	public static java.util.Date avancarDataEmMeses(java.util.Date data,
			Integer numeroMeses) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		cal.add(2, numeroMeses.intValue());

		return cal.getTime();
	}

	public static java.util.Date retrocederDataEmMeses(java.util.Date data,
			Integer numeroMeses) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		cal.add(2, -numeroMeses.intValue());

		return cal.getTime();
	}

	public static boolean maisRecente(java.util.Date date1, java.util.Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		return cal1.after(cal2);
	}

	public static java.util.Date limparTempo(java.util.Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		cal.set(11, cal.getActualMinimum(11));
		cal.set(12, cal.getActualMinimum(12));
		cal.set(13, cal.getActualMinimum(13));
		cal.set(14, cal.getActualMinimum(14));

		return cal.getTime();
	}

	public static java.util.Date minusDays(java.util.Date data, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		cal.add(6, -1);

		return cal.getTime();
	}

	private static java.util.Date avancarData(java.util.Date data,
			Integer campo, Integer quantidade) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		cal.add(campo.intValue(), quantidade.intValue());

		return cal.getTime();
	}

	private static String menosXSegundos(int count) {
		return "menos de " + count + "segundos";
	}

	private static String minutos(int count) {
		return count + " minutos";
	}

	private static String meioMinuto() {
		return "menos de meio minuto";
	}

	private static String menosXMinutos(int count) {
		return "menos de " + count + " minutos";
	}

	private static String cercaXHoras(int count) {
		return "cerca de " + count + " horas";
	}

	private static String cercaXDias(int count) {
		return "cerca de " + count + " dias";
	}

	private static String cercaXMeses(int count) {
		return "cerca de " + count + " mêses";
	}

	private static String cercaXAnos(int count) {
		return "cerca de " + count + " anos";
	}

	private static String maisXAnos(int count) {
		return "mais de " + count + " anos";
	}

	private static String quaseXAnos(int count) {
		return "quase " + count + " anos";
	}
	
	public static List<Integer> getListaAnos(Integer anoInicial){  
	    Calendar dataFinal=CALENDAR;  
	    Integer anoAtual = dataFinal.get(Calendar.YEAR);  
	    List<Integer> anos = new ArrayList<Integer>();  
	      
	    for(Integer ano=anoInicial; ano<=anoAtual;ano++){  
	        anos.add(ano);  
	    }  
	      
	    return anos;  
	} 
}
