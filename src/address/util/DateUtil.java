package address.util;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/* Helper functions for handling dates
 * 
 */
public class DateUtil {
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	
	/* Returns the given date as a well formatted String
	 * using the above DATE_PATTERN
	 */
	public static String format(LocalDate date) {
		if(date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}
	/* converts String to the  desired format
	 * returns null if failed
	 */
	public static LocalDate parse(String dateString) {
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		}catch(DateTimeParseException e) {
			return null;
		}
	}
	
	/* Checks if String is a valid date
	 */
	public static boolean validDate(String dateString) {
		return DateUtil.parse(dateString) != null;
	}
	
	
	
	
}
