package address.view;
//must be in the same folder as PersonOverview.fxml otherwise SceneBuilder won't find it
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import address.MainApp;
import address.model.Person;
import address.util.DateUtil;

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
		//clear person details
		showPersonDetails(null);
		//Listen for selection changes and show the person details when changed
		personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal)-> showPersonDetails(newVal));
	
	}

	//is called by main application to give a reference back to itself
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		//add observable list data to the table
		personTable.setItems(mainApp.getPersonData());
	}
	
	public void showPersonDetails(Person person) {
		if(person != null) {
			// Fill the labels with info from person object
			firstNameLabel.setText(person.getFirstName().get());
			lastNameLabel.setText(person.getLastName().get());
			streetLabel.setText(person.getStreet().get());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode().get()));
			cityLabel.setText(person.getCity().get());
			birthdayLabel.setText(DateUtil.format(person.getBirthday().get()));
		}else {
			//person is null, remove all text
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
		}
	}
	
	//delete
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		}else {
			//Nothing selected
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table");
			alert.showAndWait();
		}
		
	}
}
