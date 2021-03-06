package Screens;

import DataModel.Patient;
import FileUtils.PatientFile;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane; 
import javafx.scene.image.*;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.geometry.Pos; 
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Register extends Application  {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException{
    	
    	Label heading = new Label("Register Your Details");
    	heading.setStyle("-fx-font: normal bold 20px 'arial' ");
    	
    	TextField id = new TextField();
    	id.setPromptText("Patient ID");
    	TextField name = new TextField();
    	name.setPromptText("Name");
    	PasswordField password = new PasswordField();
    	password.setPromptText("Password");
    	PasswordField confirmPassword = new PasswordField();
    	confirmPassword.setPromptText("Confirm Password");
    	TextField phone = new TextField();
    	phone.setPromptText("Phone");
    	TextField address = new TextField();
    	address.setPromptText("Address");
    	
    	Label gen = new Label("Gender: ");
    	ToggleGroup gender = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        male.setToggleGroup(gender);
        RadioButton female = new RadioButton("Female");
        female.setToggleGroup(gender);
        
        ChoiceBox<String> state = new ChoiceBox<String>();
        state.getItems().addAll("Karnataka", "Maharashtra", "West Bengal", "Uttar Pradesh", "Kerala");
    	
        FileInputStream inputstream1 = new FileInputStream("Images/file.png"); 
        Image file_img = new Image(inputstream1);
        ImageView file_imv = new ImageView(file_img);
        file_imv.setFitHeight(200); 
        file_imv.setFitWidth(150); 
        
        Button confirm = new Button("Confirm");
        confirm.setStyle("-fx-background-color: #278fcc; -fx-text-fill: white;");
        
        GridPane gp = new GridPane();
        gp.setMinSize(700, 600);
        gp.setStyle("-fx-background-color: LIGHTBLUE;");
        
        GridPane gp1 = new GridPane();
        gp1.setMinSize(300, 600);
        gp1.setAlignment(Pos.CENTER);
        gp1.setHgap(7);
        gp1.setVgap(25);
        gp1.setStyle("-fx-background-color: WHITE;");
        
        GridPane gp2 = new GridPane();
        gp2.setMinSize(400, 600);
        gp2.setAlignment(Pos.CENTER);
        gp2.setStyle("-fx-background-color: LIGHTBLUE;");
        gp2.add(file_imv, 0, 0);
        
        GridPane gp3 = new GridPane();
        gp3.setAlignment(Pos.CENTER);
        gp3.setHgap(7);
        gp3.add(gen, 0, 0);
        gp3.add(male, 1, 0);
        gp3.add(female, 2, 0);
        
        gp.add(gp1, 0, 0);
        gp.add(gp2, 1, 0);        
        
        gp1.add(heading, 0, 0);
        gp1.add(id, 0, 2);
        gp1.add(name, 0, 3);
        gp1.add(gp3, 0, 4);
        gp1.add(password, 0, 5);
        gp1.add(confirmPassword, 0, 6);
        gp1.add(phone, 0, 7);
        gp1.add(address, 0, 8);
        gp1.add(state, 0, 9);
        gp1.add(confirm, 0, 11);
        
        
        Scene scene = new Scene(gp, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("file:Images/icon.png"));
        primaryStage.setTitle("Register");
        primaryStage.setAlwaysOnTop(true);
		gp.requestFocus();
		
		confirm.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent arg0) {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Invalid Input");
				a.initOwner(primaryStage);

            	//Validate
				if (id.getText().isEmpty()) {
					a.setContentText("Enter Patient ID");
					a.showAndWait();
				} else if (name.getText().isEmpty()) {
					a.setContentText("Enter Name");
					a.show();
				} else if (gender.getSelectedToggle() == null) {
					a.setContentText("Select Gender");
					a.show();
				} else if (password.getText().isEmpty()) {
					a.setContentText("Enter Password");
					a.show();
				} else if (confirmPassword.getText().isEmpty() || !confirmPassword.getText().equals(password.getText())) {
					a.setContentText("Enter Password");
					a.show();
				} else if (!confirmPassword.getText().equals(password.getText())) {
					a.setContentText("Enter correct Password");
					a.show();
				} else if (phone.getText().isEmpty()) {
					a.setContentText("Enter Phone");
					a.show();
				} else if (phone.getText().length() != 10 || !phone.getText().chars().allMatch(Character::isDigit)) {
					a.setContentText("Enter valid Phone");
					a.show();
				} else if (address.getText().isEmpty()) {
					a.setContentText("Enter Address");
					a.show();
				} else if (state.getValue() == null) {
					a.setContentText("Select State");
					a.show();
				} else {
					RadioButton rb = (RadioButton)gender.getSelectedToggle();
					Patient p = new Patient(id.getText(),name.getText(),phone.getText(),rb.getText(),address.getText(),state.getValue());
					new PatientFile().addPatient(p,password.getText());
					try{
						Login login = new Login();
						login.start(primaryStage);
					} catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
				}
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
