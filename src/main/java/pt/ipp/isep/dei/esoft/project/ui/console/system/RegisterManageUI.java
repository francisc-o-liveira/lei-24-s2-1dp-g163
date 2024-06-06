package pt.ipp.isep.dei.esoft.project.ui.console.system;

import pt.ipp.isep.dei.esoft.project.application.controller.OrganizationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.RegisterController;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RegisterManageUI implements Runnable{

    private AuthenticationController ctrlUser;
    private RegisterController ctrlRegis;
    private OrganizationController ctrlOrg;
    private String managerName;
    private String managerPassword;
    private String managerEmail;
    private String userRole;
    private String managerPhone;

    public RegisterManageUI() {
        ctrlUser =  AuthenticationController.getInstance();
        ctrlOrg = OrganizationController.getInstance();
        ctrlRegis =  RegisterController.getInstance();
    }
    @Override
    public void run() {
        try {
            System.out.print("--------- Register a Manager ---------\n");
            requestData();
            submitsData();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }

    private void submitsData() {
        if (ctrlOrg.addEmployee(managerName,userRole,managerPhone,managerEmail)){
            ctrlUser.addUserWithRole(managerName,managerEmail,managerPassword,userRole);
        }
    }

    private void requestData() {
        managerName=registerName();
        managerEmail=registerEmail();
        managerPassword=registerPassword();
        managerPhone=registerPhone();
        userRole=selectUserRole();
    }

    private String registerPhone() {
        Scanner scan = new Scanner(System.in);
        String phone = "";
        boolean validPhone=false;
        while(!validPhone){
            System.out.print("Enter a phone Number address: ");
            phone = scan.next();
            if(!verifyPhone(phone)){
                throw new IllegalArgumentException("The introduced phoneNumber is not correct.");
            } else {
                validPhone=true;
            }
        }
        return phone;
    }

    private boolean verifyPhone(String phone) {
        String regexPattern = "^\\\\d{10}$";
        return Pattern.compile(regexPattern).matcher(phone).matches();
    }

    private String registerPassword() {
        Scanner scan = new Scanner(System.in);
        String password = "";
        boolean validPass=false;
        while(!validPass){
            System.out.print("Enter a password for Manager: ");
            password = scan.next();
            if(!verifyPass(password,scan)){
                throw new IllegalArgumentException("The introduced password is not correct.");
            } else {
                validPass=true;
            }
        }
        return password;
    }

    private boolean verifyPass(String password, Scanner scan) {
        String passwordRepeat;
        System.out.print("Repeat the Password: ");
        passwordRepeat = scan.next();
        if (password.equals(passwordRepeat)){
            return true;
        }
        return false;
    }

    private String registerEmail() {
        Scanner scan = new Scanner(System.in);
        String email = "";
        boolean validEmail=false;
        while(!validEmail){
            System.out.print("Enter a email address: ");
            email = scan.next();
            if(!verifyEmail(email)){
                throw new IllegalArgumentException("The introduced email is not correct.");
            } else {
                validEmail=true;
            }
        }
        return email;
    }

    private boolean verifyEmail(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    private String selectUserRole(){
        List<String>roles=ctrlRegis.getRolesToSelect();
        Scanner scan = new Scanner(System.in);
        int docIDNumber;
        System.out.print("Select one of the following Roles for the Manager on the System: \n");
        for(int i = 0; i < roles.size(); i++){
            System.out.printf("%d --- %s%n", i+1,roles.get(i));
        }
        int option = -1;
        while(option<1 || option>3){
            option=scan.nextInt();
        }
        return roles.get(option-1);

    }

    private String registerName() {
        Scanner scan=new Scanner(System.in);
        String name = "";
        boolean validName=false;
        while(!validName){
            System.out.print("Name of Manager: ");
            name = scan.next();
            if(!verifyName(name)){
                throw new IllegalArgumentException("The introduced name is incorrect.");
            } else {
                validName=true;
            }
        }
        return name;
    }

    private boolean verifyName(String name) {
        String[]arrayNeedSize=name.split(" ");
        return arrayNeedSize.length<=6;
    }
}
