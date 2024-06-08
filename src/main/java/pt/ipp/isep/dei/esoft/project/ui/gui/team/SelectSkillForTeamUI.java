package pt.ipp.isep.dei.esoft.project.ui.gui.team;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.teamSystem.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.collaborator.Skill;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UI Controller for selecting skills for a team.
 */
public class SelectSkillForTeamUI {

    /** The stage */
    public Stage stage = new Stage();

    /** Controller for generating teams */
    public GenerateTeamController ctrl;

    /** Observable list for the TableView */
    private ObservableList<Skill> skillsToChoose = FXCollections.observableArrayList();

    /**Authentication Controller */
    public AuthenticationController ctrlAuth;

    @FXML
    public TableView<Skill> tableViewSkills;
    @FXML
    public TableColumn<Skill, Boolean> colSelect;
    @FXML
    public TableColumn<Skill, Skill> colSkills;

    /** List of skills selected to generate a team */
    private List<Skill> skillsSelectedForTeam;

    /**
     * Constructor for SelectSkillForTeamUI.
     * Initializes controllers and loads skills to choose from.
     */
    public SelectSkillForTeamUI() {
        ctrlAuth = AuthenticationController.getInstance();
        ctrl = GenerateTeamController.getInstance();
        skillsSelectedForTeam = new ArrayList<>();
        skillsToChoose.addAll(ctrl.getSkillList());
    }

    /**
     * Sets up the table view for displaying skills.
     */
    public void setTableViewSkill(){
        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        colSelect.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(false));
        colSelect.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Skill, Boolean> call(TableColumn<Skill, Boolean> param) {
                return new TableCell<>() {
                    private final CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setGraphic(null);
                            return;
                        }
                        setGraphic(checkBox);
                        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                            Skill skill = (Skill) getTableRow().getItem();
                            if (isNowSelected) {
                                skillsSelectedForTeam.add(skill);
                            }
                        });
                    }
                };
            }
        });
        tableViewSkills.setItems(skillsToChoose);
    }

    /**
     * Handles button click event to generate a team.
     *
     * @throws IOException if an error occurs during loading the UI
     */
    @FXML
    public void btnGenerateTeam() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/teams/Scene_GenerateTeam.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        GenerateTeamsUI ui=fxmlLoader.getController();
        ui.setSkillsSelectedForTeam(skillsSelectedForTeam);
    }

    /**
     * Handles going back to the previous scene.
     *
     * @param event the ActionEvent triggering the method
     * @throws IOException if an error occurs during loading the UI
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
     * Creates a pop-up alert for displaying error messages.
     *
     * @param alertType the type of alert
     * @param messages  the message to display
     * @return the Alert object
     */
    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
