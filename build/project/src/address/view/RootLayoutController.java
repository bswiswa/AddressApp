package address.view;
import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import address.MainApp;
/*
 * controller for the root layout. 
 * root layout provides the basic layout of the application
 * this includes a menu bar and space where other JavaFX elements can be placed
 */
public class RootLayoutController {
	//reference main application
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	//create an empty address book
	@FXML
	private void handleNew() {
		mainApp.getPersonData().clear();
		mainApp.setPersonFilePath(null);
	}
	
	// opens a FileChooser to let user select an address book to load
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		//set extension filer
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//show open file dialog
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if(file != null) {
			mainApp.loadPersonDataFromFile(file);
		}
	}
	
	/* save file to the person file that is currently open
	* if there is no open file, show the "save as" dialog
	*/
	@FXML
	private void handleSave() {
		File personFile = mainApp.getPersonFilePath();
		if(personFile != null) {
			mainApp.savePersonDataToFile(personFile);
		}else {
			handleSaveAs();
		}
	}
	
	// open FileChooser to let user select file to save to
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		//set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
	
		//show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if(file != null) {
			//make sure it has correct extension
			if(!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.savePersonDataToFile(file);
		}
	}
	
	//opens the birthday statistics
	@FXML
	private void handleShowBirthdayStatistics() {
		mainApp.showBirthdayStatistics();
	}
	
	//opens an about dialog
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("AddressApp");
		alert.setHeaderText("About");
		alert.setContentText("Authors: Marco Jakob - http://code.makery.ch\nBatsirai Swiswa - http://www.github.com/bswiswa");
		
		alert.showAndWait();
	}
	
	//closes the application
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}
