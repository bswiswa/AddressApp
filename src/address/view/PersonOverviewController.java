package address.view;
//must be in the same folder as PersonOverview.fxml otherwise SceneBuilder won't find it
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import address.MainApp;
import address.model.Person;

public class PersonOverviewController {
	/*we need to have instance variables that give us access to the table and labels in the view
	* the fields and some methods have a special @FXML annotation which gives the fxml file access to private fields and methods
	*/
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label birthdayLabel;
	
	//reference to main application
	private MainApp mainApp;
	
	//constructor. Called before initialize()
	public PersonOverviewController() {
	}
	
	//automatically called after fxml file has been loaded to initialize the controller class.
	@FXML
	private void initialize() {
		//initialize the person table with the two columns
		firstNameColumn.setCellValueFactory(cellData-> cellData.getValue().getFirstName());
		lastNameColumn.setCellValueFactory(cellData-> cellData.getValue().getLastName());
	}

	//is called by main application to give a reference back to itself
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		//add observable list data to the table
		personTable.setItems(mainApp.getPersonData());
	}
}
