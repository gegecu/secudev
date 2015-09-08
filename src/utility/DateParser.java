package utility;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateParser {

	// outputs from materialize date picker for database format
	// defaults to current date if param format is invalid
	public static String parseDateForDatabase(String param_date){
		
		DateFormat input = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		try {
			date = input.parse(param_date);
			return output.format(date);
		} catch (Exception e) {
			return null;
		}
	
	}
	
	public static LocalDate parseStringToDate(String param_date){
		
		try {
			return LocalDate.parse(param_date);
		} catch (Exception e) {
			return null;
		}
		
	}
	
}
