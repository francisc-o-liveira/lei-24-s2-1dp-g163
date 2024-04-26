package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.Optional;
import java.util.Scanner;

public class RegisterVehicleUI {
    private String brand;
    private String model;
    private String plate;
    private Vehicle.Type type;
    private int tare;
    private double grossWeight;
    private int currentKM;
    private Date registerDate;
    private Date acquisitionDate;
    private int checkupFrequency;


    /**Controller*/
    public RegisterVehicleController ctrl;
    public RegisterVehicleUI() {
        ctrl= new RegisterVehicleController();
    }

    private RegisterVehicleController getController() {
        return ctrl;
    }

    public void run(){
        System.out.print("--------- Register a Vehicle ---------\n");
        ctrl.getVehicleRepository();
        type=displayAndSelectVehicleType();
        requestData();
        submitData();
    }

    private void submitData() {
        Optional<Vehicle> vehicle = getController().registerVehicle();
        if (vehicle.isPresent()) {
            System.out.println("\nCollaborator successfully created!");
        } else {
            System.out.println("\nCollaborator not created!");
        }
    }

    private void requestData() {
        this.brand=registerBrand();
        this.model=registerModel();
        this.acquisitionDate=registerAcquisitionDate();
        this.registerDate=registerRegistDate();
        this.currentKM=registerCurrentKM();
        this.checkupFrequency=registerFrequency();
        this.grossWeight=registerGrossWeigth();
        this.tare=registerTare();
        this.plate=registerPlate();  // Validation go to the Domain Vehicle
    }

    private String registerPlate() {
        Scanner scan = new Scanner(System.in);
        String plate = null;
        boolean validPlate=false;
        while (!validPlate){
            System.out.print("Plate of the Vehicle: ");
            plate = scan.nextLine();
            String[] plateFormat = plate.split("-");
            if(plateFormat.length==3 && acquisitionDate.getYear()>1900){
                throw new IllegalArgumentException("The introduced Plate is incorrect.");
            } else {
                validPlate=true;
            }
        }
        return plate;
    }

    private int registerTare() {
        Scanner scan = new Scanner(System.in);
        int tare = 0;
        boolean validTare=false;
        while (!validTare){
            System.out.print("Tare of the Vehicle: ");
            tare = scan.nextInt();
            if(tare>0 && tare<=7000){
                throw new IllegalArgumentException("The introduced Tare is incorrect.");
            } else {
                validTare=true;
            }
        }
        return tare;
    }

    private double registerGrossWeigth() {
        Scanner scan = new Scanner(System.in);
        int grossWeight = 0;
        boolean validKm=false;
        while (!validKm){
            System.out.print("Gross-Weight of the Vehicle: ");
            grossWeight = scan.nextInt();
            if(grossWeight>0 && grossWeight<=7000){
                throw new IllegalArgumentException("The introduced Gross-Weight is incorrect.");
            } else {
                validKm=true;
            }
        }
        return grossWeight;
    }

    private int registerFrequency() {
        Scanner scan = new Scanner(System.in);
        int frequencyKm = 0;
        boolean validKm=false;
        while (!validKm){
            System.out.print("Maintenance Check-Up Kilometers: ");
            frequencyKm = scan.nextInt();
            if(frequencyKm>1000 && frequencyKm<=50000){
                throw new IllegalArgumentException("The introduced Frequency for Check-Up is incorrect.");
            } else {
                validKm=true;
            }
        }
        return frequencyKm;
    }

    private int registerCurrentKM() {
        Scanner scan = new Scanner(System.in);
        int currentKM = 0;
        boolean validKm=false;
        while (!validKm){
            System.out.print("Current Kilometers of the Vehicle: ");
            currentKM = scan.nextInt();
           if(currentKM>0 && currentKM<1500000){
                throw new IllegalArgumentException("The introduced model is incorrect.");
            } else {
                validKm=true;
            }
        }
        return currentKM;
    }

    private Date registerRegistDate() {
        Scanner scan = new Scanner(System.in);
        String date;
        Date registerDate = null;
        boolean validDate=false;
        while (!validDate){
            System.out.print("Register Date of Vehicle: ");
            date = scan.nextLine();
            String[] dateFormat = date.split("-");
            registerDate=new Date(Integer.parseInt(dateFormat[0]),Integer.parseInt(dateFormat[1]),Integer.parseInt(dateFormat[2]));
            if(dateFormat.length==3 && acquisitionDate.getYear()>1900){
                throw new IllegalArgumentException("The introduced date is incorrect.");
            } else {
                validDate=true;
            }
        }
        return registerDate;
    }

    private Date registerAcquisitionDate() {
        Scanner scan = new Scanner(System.in);
        String date;
        Date acquisitionDate = null;
        boolean validDate=false;
        while (!validDate){
            System.out.print("Acquisition Date of the Vehicle: ");
            date = scan.nextLine();
            String[] dateFormat = date.split("-");
            acquisitionDate=new Date(Integer.parseInt(dateFormat[0]),Integer.parseInt(dateFormat[1]),Integer.parseInt(dateFormat[2]));
            if(dateFormat.length==3 && acquisitionDate.getYear()>1900){
                throw new IllegalArgumentException("The introduced date is incorrect.");
            } else {
                validDate=true;
            }
        }
        return acquisitionDate;
    }

    public String registerModel(){
        Scanner scan = new Scanner(System.in);
        String model = null;
        boolean validModel=false;
        while(!validModel){
            System.out.print("Model of Vehicle: ");
            model = scan.next();
            if(!verifyModel(model)){
                throw new IllegalArgumentException("The introduced model is incorrect.");
            } else {
                validModel=true;
            }
        }
        return model;
    }

    private boolean verifyModel(String model) {
        return model.split("").length<20;
    }

    public String registerBrand(){
        Scanner scan = new Scanner(System.in);
        String brand = null;
        boolean validBrand=false;
        while(!validBrand){
            System.out.print("Brand of Vehicle: ");
            brand = scan.next();
            if(!verifyBrand(brand)){
                throw new IllegalArgumentException("The introduced name is incorrect.");
            } else {
                validBrand=true;
            }
        }
        return brand;
    }

    private boolean verifyBrand(String brand) {
        return brand.split("").length<20;
    }

    private Vehicle.Type displayAndSelectVehicleType() {
        Vehicle.Type[] types = ctrl.getVehicleTypeValues();

        int indexOfType = -1;
        Scanner input = new Scanner(System.in);
        while (indexOfType < 1 || indexOfType > types.length) {
            displayTypeOptions(types);
            System.out.print("Select a task category: ");
            indexOfType = input.nextInt();
        }
        return types[indexOfType-1];
    }

    private void displayTypeOptions(Vehicle.Type[] types) {
        for (int i=0;i<types.length;i++) {
            System.out.println("  " + i+1 + " - " + types[i]);
        }
    }
}
