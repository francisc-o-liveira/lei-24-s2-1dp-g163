package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;
import pt.ipp.isep.dei.esoft.project.ui.console.*;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.console.vehicle.CheckUpListUI;
import pt.ipp.isep.dei.esoft.project.ui.console.vehicle.RegisterCheckUpUI;
import pt.ipp.isep.dei.esoft.project.ui.console.vehicle.RegisterVehicleUI;
import pt.ipp.isep.dei.esoft.project.ui.console.vehicle.ShowVehicleListUI;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageVehiclesUI implements Runnable{
    private RegisterController ctrl;
    public ManageVehiclesUI(){
    }
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Show Vehicle List", new ShowVehicleListUI()));
        options.add(new MenuItem("Remove Vehicle", new ShowTextUI("Implementing.......")));
        options.add(new MenuItem("Add Vehicle", new RegisterVehicleUI()));
        options.add(new MenuItem("Show Check Up List", new CheckUpListUI()));
        options.add(new MenuItem("Register a CheckUp", new RegisterCheckUpUI()));
        options.add(new MenuItem("Register update Kilometers", new ShowTextUI("Implementing.......")));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- Manage Vehicles -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }

    /*private Runnable getUserMainUI() {
        ctrl = new RegisterController();
        List<String> roles = this.ctrl.getRolesToSelect();
        if ((roles == null) || (roles.isEmpty())) {
            System.out.println("No role assigned to user.");
        } else {
            UserRoleDTO role = selectsRole(roles);
            if (!Objects.isNull(role)) {
                if(role.equals(ctrl.ROLE_HRM)){
                    return new HRManagerUI();
                }
                if (role.equals(ctrl.ROLE_GSM)){
                    return new AdminUI();
                }
            } else {
                System.out.println("No role selected.");
            }
        }
        return null;
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
        }
    }*/
}
