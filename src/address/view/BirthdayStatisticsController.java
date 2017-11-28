package address.view;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import address.model.Person;
/* controller for the birthday statistics
 * 
 */
public class BirthdayStatisticsController {
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private CategoryAxis xAxis;
	
	private ObservableList<String> monthNames = FXCollections.observableArrayList();
	
	/* initialize controller class. Called after fxml file
	 * has loaded
	 */
	@FXML
	private void initialize() {
		//get an array with English month names
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		//convert array to list and add to the observable list of month names
		monthNames.addAll(Arrays.asList(months));
		
		//assign month names as categories for horizontal axis
		xAxis.setCategories(monthNames);
	}
	
	/* set persons to show statistics for
	 */
	public void setPersonData(List<Person> persons) {
		//count number of people with birthday in a specific month
		int[] monthCounter = new int[12];
		for(Person p: persons) {
			int month = p.getBirthday().getMonthValue() - 1;
			monthCounter[month]++;
		}
		
		XYChart.Series<String, Integer> series = new XYChart.Series();
		//create a XYChart.Data object for each month and add it to series
		for(int i = 0; i < monthCounter.length; i++) {
			series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
		}
		
		barChart.getData().add(series);
	}
}
