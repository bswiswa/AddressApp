package address;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import address.model.Person;
import address.view.PersonEditDialogController;
import address.view.PersonOverviewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainApp extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
	//create an obsevable list of Persons
	private ObservableList<Person> personData = FXCollections.observableArrayList();
	
	//constructor
	public MainApp() {
		//Add some sample data
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
		//add application icon
		this.primaryStage.getIcons().add(new Image("file:resources/images/address_book.png"));
		
		initRootLayout();
		showPersonOverview();
	}

	public void initRootLayout() {
		try {
			// load root layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// show the scene containing the root layout
			Scene scene = new Scene(rootLayout, 400, 200);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//shows the person overview inside the root layout
	public void showPersonOverview() {
		try {
			// load root layout from fxml file
			//load person overview
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
	
	//returns main stage
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public ObservableList<Person> getPersonData(){
		return personData;
	}
	
	/* opens a dialog to edit details of a selected person
	 * If user clicks OK, changes are saved into person object and
	 * true is returned
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
	        //set the icon of the dialog page
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
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
