package address;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import address.model.Person;
import address.model.PersonListWrapper;
import address.view.BirthdayStatisticsController;
import address.view.PersonEditDialogController;
import address.view.PersonOverviewController;
import address.view.RootLayoutController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;

	// create an obsevable list of Persons
	private ObservableList<Person> personData = FXCollections.observableArrayList();

	// constructor
	public MainApp() {
		// Add some sample data
		personData.add(new Person("Hans", "Muster"));
		personData.add(new Person("Ruth", "Mueller"));
		personData.add(new Person("Heinz", "Kurz"));
		personData.add(new Person("Cornelia", "Meier"));
		personData.add(new Person("Werner", "Meyer"));
		personData.add(new Person("Lydia", "Kunz"));
		personData.add(new Person("Anna", "Best"));
		personData.add(new Person("Stefan", "Meier"));
		personData.add(new Person("Martin", "Mueller"));
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		// add application icon
		this.primaryStage.getIcons().add(new Image("file:resources/images/address_book.png"));

		initRootLayout();
		showPersonOverview();
	}

	/* initializes root layout and tries to load 
	 * last opened person file
	 */
	public void initRootLayout() {
		try {
			// load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			//give the RootLayoutController access to the main app
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//try to load last opened person file
		File file = getPersonFilePath();
		if(file != null) {
			loadPersonDataFromFile(file);
		}
	}

	// shows the person overview inside the root layout
	public void showPersonOverview() {
		try {
			// load root layout from fxml file
			// load person overview
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// set personOverview into center of rootLayout
			rootLayout.setCenter(personOverview);
			// give the controller access to the main app
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// returns main stage
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ObservableList<Person> getPersonData() {
		return personData;
	}

	/*
	 * opens a dialog to edit details of a selected person If user clicks OK,
	 * changes are saved into person object and true is returned
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			// set the icon of the dialog page
			dialogStage.getIcons().add(new Image("file:resources/images/address_book.png"));

			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * returns the person XML file that was last opened this file would have been
	 * stored in the Preferences class which allows us to store and persist simple
	 * application state on a system
	 */
	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/*
	 * set file path of Preferences to the currently loaded file
	 */
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// update stage title
			primaryStage.setTitle("AddressApp - " + file.getName());
		} else {
			prefs.remove("filePath");

			// update stage title
			primaryStage.setTitle("AddressApp");
		}
	}

	// Make MainApp responsible for reading and writing the person data since it has
	// personData

	/* load person data from file and replace current person data */
	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// read XML from file and unmarshall (return original specific object)
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

			personData.clear();
			personData.addAll(wrapper.getPersons());

			// save file path to the registry
			setPersonFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	/*
	 * save current person data in specified file
	 */
	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// wrap person data.
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(personData);

			// marshal and save XML to file.
			m.marshal(wrapper, file);

			// save file path to the registry.
			setPersonFilePath(file);
		} catch (Exception e) { 
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}
	}

	/*showing birthday statistics
	 * 
	 */
	public void showBirthdayStatistics() {
		try {
			//load fxml file and create its stage
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Birthday Statistics");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			//set persons in controller
			BirthdayStatisticsController controller = loader.getController();
			controller.setPersonData(personData);
			
			dialogStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

}
