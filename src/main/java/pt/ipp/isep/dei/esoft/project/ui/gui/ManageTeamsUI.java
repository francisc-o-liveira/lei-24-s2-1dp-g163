package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.DocType;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.JobCategory;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.ipp.isep.dei.esoft.project.utilities.Date;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageTeamsUI {

    public Stage stage= LoginUI.getMainStage();
    public GenerateTeamController ctrl;
    public AuthenticationController ctrlAuth;
    public Stage stageToViewDetails = new Stage();

    @FXML
    public TableView<Team> tableViewTeams;

    @FXML
    public TableColumn<Team, String> colTeams;
    @FXML
    public TableColumn<Team, Void> colViewDetails;

    private Team selectedTeam;

    public ManageTeamsUI(){
        ctrl= new GenerateTeamController();
        ctrlAuth= new AuthenticationController();
    }

    @FXML
    public void btnAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_GenerateTeams.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        GenerateTeamsUI ctrlForGenerate= fxmlLoader.getController();
        ctrlForGenerate.setTableViewTeam();
    }

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

    public void setTableTeams(){
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
        List<Team> teamList= new ArrayList<>();
        List<Skill> skillsSelected1 = new ArrayList<>();
// Assuming you have some skills already initialized
// Add skills to the list
        skillsSelected1.add(new Skill("JavaProgramming"));
        skillsSelected1.add(new Skill("DatabaseManagement"));

        Team team1 = new Team(5, 3, skillsSelected1, "team1");

// Assuming you have some collaborators already initialized
// Add collaborators to the team
        Collaborator collaborator1 = new Collaborator("John Doe", new Date(1990, 5, 15), new Date(2020, 3, 10), "123 Main St", "12345", "New York", "+3511234567890", "john.doe@example.com", DocType.Type.CitizenCard, 123456789, new JobCategory("SoftwareEngineer"));
        Collaborator collaborator2 = new Collaborator("Jane Smith", new Date(1985, 8, 25), new Date(2018, 7, 20), "456 Oak St", "54321", "Los Angeles", "+351987654321", "jane.smith@example.com",DocType.Type.CitizenCard, 987654321, new JobCategory("MarketingSpecialist"));

        team1.addCollaborator(collaborator1);
        team1.addCollaborator(collaborator2);
        List<Skill> skillsSelected2 = new ArrayList<>();
// Assuming you have some skills already initialized
// Add skills to the list
        skillsSelected2.add(new Skill("ProjectManagement"));
        skillsSelected2.add(new Skill("Communication"));

        Team team2 = new Team(4, 2, skillsSelected2, "team2");

// Assuming you have some collaborators already initialized
// Add collaborators to the team
        Collaborator collaborator3 = new Collaborator("Alice Johnson", new Date(1988, 10, 30), new Date(2019, 6, 25), "789 Elm St", "67890", "Chicago", "+351987654321", "alice.johnson@example.com",DocType.Type.CitizenCard, 987654321, new JobCategory("ProjectManager"));
        Collaborator collaborator4 = new Collaborator("Bob Williams", new Date(1992, 4, 12), new Date(2021, 2, 18), "321 Pine St", "54321", "Boston", "+3511234567890", "bob.williams@example.com", DocType.Type.CitizenCard, 123456789, new JobCategory("BusinessAnalyst"));

        team2.addCollaborator(collaborator3);
        team2.addCollaborator(collaborator4);
        teamList.add(team1);
        teamList.add(team2);
        for(Team t : teamList){
            tableViewTeams.getItems().add(t);
        }
    }

    public void showMore(Team team) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Scene_ViewDetailsTeam.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageToViewDetails.setScene(scene);
        stageToViewDetails.show();
        ViewDetailsTeamUI ui=fxmlLoader.getController();
        ui.showTeamSelected(selectedTeam);
        ui.setTableSkills();
        ui.setTableCollabs();
    }

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

    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(AuthenticationController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(AuthenticationController.ROLE_HRM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SceneMenu_GSM.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }

}
