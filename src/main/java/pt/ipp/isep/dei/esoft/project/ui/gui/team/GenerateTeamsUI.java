package pt.ipp.isep.dei.esoft.project.ui.gui.team;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.teamSystem.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UI Controller class for generating teams.
 */
public class GenerateTeamsUI {

    public Stage stage= new Stage();
    public GenerateTeamController ctrl;

    public AuthenticationController ctrlAuth;

    @FXML
    public TextField maximumTeamSize;
    @FXML
    public TextField minimumTeamSize;
    @FXML
    public TextField nameForTeam;
    @FXML
    private VBox skillsContainer;

    private List<Skill> skillsSelectedForTeam;
    private List<Integer> numberCollabsPerSkill;
    private List<TextField> skillTextFields;

    /**
     * Constructs a new GenerateTeamsUI instance.
     */
    public GenerateTeamsUI(){
        ctrlAuth = AuthenticationController.getInstance();
        ctrl = GenerateTeamController.getInstance();
        skillsSelectedForTeam= new ArrayList<>();
        numberCollabsPerSkill= new ArrayList<>();
        skillTextFields=new ArrayList<>();
    }

    /**
     * Sets the skills selected for the team.
     *
     * @param skills the list of skills selected for the team
     */
    public void setSkillsSelectedForTeam(List<Skill> skills){
        skillsSelectedForTeam=skills;
        createSkillInputFields();
    }

    // Other methods

    private void createSkillInputFields() {
        skillsContainer.getChildren().clear();

        for (Skill skill : skillsSelectedForTeam) {
            Label label = new Label(skill.getSkillName() + ":");
            TextField textField = new TextField();
            textField.setPromptText("Enter value for " + skill.getSkillName());

            skillsContainer.getChildren().addAll(label, textField);
            skillTextFields.add(textField);
        }
    }

    /**
     * Handles the action event of generating a team.
     */
    @FXML
    public void btnGenerateTeam(){
        int maxTeamSize=Integer.parseInt(maximumTeamSize.getText());
        int minTeamSize=Integer.parseInt(minimumTeamSize.getText());
        String teamName = nameForTeam.getText();
        for (TextField textField : skillTextFields) {
            numberCollabsPerSkill.add(Integer.parseInt(textField.getText()));
        }

        try{
            minimumTeamSize.clear();
            maximumTeamSize.clear();
            nameForTeam.clear();
            for (TextField textField : skillTextFields) {
                textField.clear();
            }
            try{
                Optional<Team> teamCreated=ctrl.generateTeam(minTeamSize, maxTeamSize, skillsSelectedForTeam, numberCollabsPerSkill,teamName);
                if (teamCreated.isPresent()) {
                    Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);
                    popUp.setHeaderText("Team Created!");
                    StringBuilder contentText = new StringBuilder("Collaborators:\n");
                    for (Collaborator c : teamCreated.get().getTeamList()) {
                        contentText.append(String.format("%s%n", c.getName()));
                    }
                    popUp.setContentText(contentText.toString() + "\nDo you wish to add this team?");
                    ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
                    ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

                    Optional<ButtonType> result = popUp.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        ctrl.saveTeam(teamCreated.get());
                        stage.close();
                    }
                }
            } catch (RuntimeException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } catch (Exception e){
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    /**
     * Handles the action event of going back to the previous menu.
     *
     * @param event the ActionEvent triggering the method
     * @throws IOException if an I/O exception occurs
     */
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(RegisterController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(RegisterController.ROLE_HRM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_Admin.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }

    /**
     * Generates a pop-up alert for displaying verification messages.
     *
     * @param alertType the type of the alert
     * @param messages the messages to be displayed in the alert
     * @return the pop-up alert
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);
        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);
        return alerta;
    }
}
