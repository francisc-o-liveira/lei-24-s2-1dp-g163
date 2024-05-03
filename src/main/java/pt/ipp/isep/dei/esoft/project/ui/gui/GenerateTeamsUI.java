package pt.ipp.isep.dei.esoft.project.ui.gui;

import javafx.beans.property.SimpleBooleanProperty;
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
import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.collaborator.Skill;
import pt.ipp.isep.dei.esoft.project.domain.team.Team;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateTeamsUI {

    public Stage stage= new Stage();
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

    private List<Skill> skillsSelectedForTeam;
    private List<Integer> numberCollabsPerSkill;

    public GenerateTeamsUI(){
        ctrlAuth = new AuthenticationController();
        ctrl = new GenerateTeamController();
        skillsSelectedForTeam= new ArrayList<>();
        numberCollabsPerSkill= new ArrayList<>();
    }
    public void setTableViewTeam(){
        Skill skill1=new Skill("skill");
        Skill skill2= new Skill("skil");

        colSkills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        colSelect.setCellValueFactory(cellData -> cellData.getValue().selectedSkill());
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


        skillsToChoose.add(skill1);
        skillsToChoose.add(skill2);

        tableViewTeam.setItems(skillsToChoose);
    }

    public void getSkillsAndCollabs(){
        skillsSelectedForTeam.clear();
        for (Skill s : skillsToChoose) {
            if (s.selectedSkill().get()==true) {
                skillsSelectedForTeam.add(s);
                numberCollabsPerSkill.add(s.numberCollabsPerSkillProperty().get());
            }
        }
    }

    @FXML
    public void btnGenerateTeam(){
        int maxTeamSize=Integer.parseInt(maximumTeamSize.getText());
        int minTeamSize=Integer.parseInt(minimumTeamSize.getText());
        String teamName = null;
        getSkillsAndCollabs();
        try{
            ctrl.generateTeam(maxTeamSize, minTeamSize, skillsSelectedForTeam, numberCollabsPerSkill,teamName);
        } catch (RuntimeException e){
            popUpOfVerifications(Alert.AlertType.ERROR, "").show();
        }
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


    private Alert popUpOfVerifications(Alert.AlertType alertType, String messages) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle("ERROR");
        alerta.setHeaderText("Invalid Data");
        alerta.setContentText(messages);

        return alerta;
    }
}
