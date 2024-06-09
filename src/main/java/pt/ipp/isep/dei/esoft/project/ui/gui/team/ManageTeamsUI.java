package pt.ipp.isep.dei.esoft.project.ui.gui.team;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.teamSystem.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.core.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.core.application.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.ui.gui.login.LoginUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;

/**
 * UI Controller class for managing teams.
 */
public class ManageTeamsUI {

    /** Stage */
    public Stage stage= LoginUI.getMainStage();
    /** Controller to generate the team */
    public GenerateTeamController ctrl;
    /** Controller for authentication */
    public AuthenticationController ctrlAuth;
    /** Stage to view the details */
    public Stage stageToViewDetails = new Stage();

    @FXML
    public TableView<Team> tableViewTeams;
    @FXML
    public TableColumn<Team, String> colTeams;
    @FXML
    public TableColumn<Team, Void> colViewDetails;
    @FXML
    public Label labelRole;

    /** Variable for the team selected */
    private Team selectedTeam;

    /**
     * Constructs a new ManageTeamsUI instance.
     */
    public ManageTeamsUI(){
        ctrl=  GenerateTeamController.getInstance();
        ctrlAuth=  AuthenticationController.getInstance();
    }

    /**
     * Sets the role label based on the current user's role.
     */
    public void setLabel(){
        UserRoleDTO role = ctrlAuth.getAtualUserRole();
        if (role.getDescription().equals(RegisterController.ROLE_HRM)){
            labelRole.setText("HumanResourcesManager");
        } else if (role.getDescription().equals(RegisterController.ROLE_GSM)) {
            labelRole.setText("GreenSpaceManager");
            labelRole.setLayoutX(28.0);
            labelRole.setLayoutY(130.0);
        } else {
            labelRole.setText("Admin");
            labelRole.setLayoutX(69);
            labelRole.setLayoutY(130.0);
        }
    }

    /**
     * Handles the action event of adding a team.
     *
     * @throws IOException if an I/O exception occurs
     */
    @FXML
    public void btnAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/teams/Scene_SelectSkills.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        SelectSkillForTeamUI ui=fxmlLoader.getController();
        ui.setTableViewSkill();
    }

    /**
     * Handles the action event of removing a team.
     */
    @FXML
    public void btnRemove(){
        Team selectedTeam= tableViewTeams.getSelectionModel().getSelectedItem();

        if(selectedTeam != null){
            Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

            popUp.setHeaderText("Removing Team");
            popUp.setContentText("Do you want to remove this team?");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

            if (popUp.showAndWait().get() == ButtonType.OK) {
                tableViewTeams.getItems().remove(selectedTeam);
                ctrl.removeTeam(selectedTeam);
            }
        }
    }

    /**
     * Sets the table of teams.
     */
    public void setTableTeams(){
        setLabel();
        colTeams.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        colViewDetails.setCellFactory(new Callback<
                TableColumn<Team, Void>, TableCell<Team, Void>>() {
            @Override
            public TableCell<Team, Void> call(TableColumn<Team, Void> param) {
                return new TableCell<Team, Void>() {
                    private final javafx.scene.control.Button btn = new Button("View Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            selectedTeam = tableViewTeams.getItems().get(((TableCell) ((Button)event.getSource()).getParent()).getIndex());
                            try {
                                showMore(selectedTeam);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
        for(Team t : ctrl.getTeams()){
            tableViewTeams.getItems().add(t);
        }
    }

    /**
     * Shows more details about a selected team.
     *
     * @param team the selected team
     * @throws IOException if an I/O exception occurs
     */
    public void showMore(Team team) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/teams/Scene_ViewDetailsTeam.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        ViewDetailsTeamUI ui=fxmlLoader.getController();
        ui.showTeamSelected(selectedTeam);
        ui.setTableSkills();
        ui.setTableCollabs();
    }

    /**
     * Handles the action event of logging out.
     *
     * @param event the ActionEvent triggering the method
     * @throws IOException if an I/O exception occurs
     */
    @FXML
    public void doLogout(ActionEvent event) throws IOException {
        Alert popUp = new Alert(Alert.AlertType.CONFIRMATION);

        popUp.setHeaderText("Logging Out");
        popUp.setContentText("Do you wish to log out?");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) popUp.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (popUp.showAndWait().get() == ButtonType.OK) {
            ctrlAuth.doLogout();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneLogin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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
            } else if (role.getDescription().equals(RegisterController.ROLE_VFM)) {
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
     * Handles the action event of updating the teams.
     *
     * @param event the ActionEvent triggering the method
     * @throws IOException if an I/O exception occurs
     */
    @FXML
    public void btnUpdate(ActionEvent event) throws IOException{
        tableViewTeams.getItems().clear();
        setTableTeams();
    }
}

