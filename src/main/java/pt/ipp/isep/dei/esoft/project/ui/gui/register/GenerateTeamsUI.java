package pt.ipp.isep.dei.esoft.project.ui.gui.register;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import pt.ipp.isep.dei.esoft.project.application.controller.teamSystem.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenerateTeamsUI {

    public Stage stage= new Stage();
    public Stage stageClose;
    public GenerateTeamController ctrl;
    ObservableList<Skill> skillsToChoose= FXCollections.observableArrayList();

    public AuthenticationController ctrlAuth;

    @FXML
    public TableView<Skill> tableViewTeam;
    @FXML
    public TableColumn<Skill, Boolean> colSelect;
    @FXML
    public TableColumn<Skill, Skill> colSkills;
    @FXML
    public TableColumn<Skill, Integer> colNumberCollabs;
    @FXML
    public TextField maximumTeamSize;
    @FXML
    public TextField minimumTeamSize;
    @FXML
    public TextField nameForTeam;

    private List<Skill> skillsSelectedForTeam;
    private List<Integer> numberCollabsPerSkill;

    public void stageToCloseGenerate(Stage stage){
        stageClose=stage;
    }
    public GenerateTeamsUI(){
        ctrlAuth = new AuthenticationController();
        ctrl = new GenerateTeamController();
        skillsSelectedForTeam= new ArrayList<>();
        numberCollabsPerSkill= new ArrayList<>();
        skillsToChoose.addAll(ctrl.getSkillList());
    }
    public void setTableViewTeam(){

        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        colSelect.setCellValueFactory(cellData -> cellData.getValue().selectedSkillForTeam());
        colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));

        colNumberCollabs.setCellValueFactory(new PropertyValueFactory<>("numberCollabsPerSkill"));
        colNumberCollabs.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colNumberCollabs.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Skill, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Skill, Integer> event) {
                Skill skill=event.getRowValue();
                skill.setNumberCollabsPerSkill(event.getNewValue());
            }
        });

        tableViewTeam.setItems(skillsToChoose);
    }

    public void getSkillsAndCollabs() throws IOException{
        skillsSelectedForTeam.clear();
        for (Skill s : skillsToChoose) {
            if(ctrl.isSkillSelected(s)){
                if(s.numberCollabsPerSkillProperty() == null){
                    throw new IOException("Please fill all the necessary fields");
                }else if (s.numberCollabsPerSkillProperty().get() != 0) {
                    skillsSelectedForTeam.add(s);
                    numberCollabsPerSkill.add(s.numberCollabsPerSkillProperty().get());
                }
            }
        }
    }

    @FXML
    public void btnGenerateTeam(){
        int maxTeamSize=Integer.parseInt(maximumTeamSize.getText());
        int minTeamSize=Integer.parseInt(minimumTeamSize.getText());
        String teamName = nameForTeam.getText();
        try{
            getSkillsAndCollabs();
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
                        stageClose.close();
                    }
                }
            } catch (RuntimeException e){
                popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } catch (IOException e){
            popUpOfVerifications(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader ;
        try {
            UserRoleDTO role = ctrlAuth.getAtualUserRole();
            if (role.getDescription().equals(AuthenticationController.ROLE_HRM)){
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_HRM.fxml"));
            } else if (role.getDescription().equals(AuthenticationController.ROLE_HRM)) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_VFM.fxml"));
            }else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menus/SceneMenu_GSM.fxml"));
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (ArrayIndexOutOfBoundsException e){
            popUpOfVerifications(Alert.AlertType.WARNING,"PLEASE RESTART THIS APPLICATION").show();
        }
    }


    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
