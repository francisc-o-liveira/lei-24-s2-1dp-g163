package pt.ipp.isep.dei.esoft.project.ui.console.vehicle;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.Optional;
import java.util.Scanner;

public class RegisterVehicleUI  implements Runnable{
    private String brand;
    private String model;
    private String plate;
    private Vehicle.Type type;
    private int tare;
    private double grossWeight;
    private double currentKM;
    private Date registerDate;
    private Date acquisitionDate;
    private double checkupFrequency;
    private double lastCheckUpKm;
    private Date lastCheckUpDate;


    /**Controller*/
    public RegisterVehicleController ctrl;
    public RegisterVehicleUI() {
        ctrl= new RegisterVehicleController();
    }

    private RegisterVehicleController getController() {
        return ctrl;
    }

    public void run(){
        try {
            System.out.print("--------- Register a Vehicle ---------\n");
            ctrl.getVehicleRepository();
            type=displayAndSelectVehicleType();
            requestData();
            submitData();
        }catch (IllegalArgumentException | CloneNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    private void submitData() throws CloneNotSupportedException {
        Optional<Vehicle> vehicle = getController().registerVehicle(brand,model,acquisitionDate,registerDate,currentKM,checkupFrequency,grossWeight,tare,plate,type,lastCheckUpDate,lastCheckUpKm);
        if (vehicle.isPresent()) {
            System.out.println("\nVehicle successfully created!");
        } else {
            System.out.println("\nVehicle not created!");
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
        if (currentKM>10000){
            this.lastCheckUpKm=registerLastCheckUpKm();
            this.lastCheckUpDate=registerLastCheckDate();
        }else {
            defaultCheckUp();
        }
    }

    private Date registerLastCheckDate() {
        Date lastCheckUpDate = null;
        String lastCheckUpDateString;
        boolean validLastCheck=false;
        while (!validLastCheck){
            lastCheckUpDateString= Utils.readLineFromConsole("Introduce last check date: DD/MM/YYYY");
            String[]dateConstructor = lastCheckUpDateString.split("/");
            lastCheckUpDate=new Date(Integer.parseInt(dateConstructor[2]),Integer.parseInt(dateConstructor[1]),Integer.parseInt(dateConstructor[0]));
            if (lastCheckUpDate.compareTo(registerDate)>0){
                validLastCheck=true;
            }else {
                System.out.println("\nInvalid Last Check Up Km!");
            }
        }
        return lastCheckUpDate;
    }

    private void defaultCheckUp() {
        this.lastCheckUpDate=this.registerDate;
        this.lastCheckUpKm=0;
    }

    private double registerLastCheckUpKm() {
        double lastCheckKM = 0;
        boolean validLastCheck=false;
        while (!validLastCheck){
            lastCheckKM= Utils.readDoubleFromConsole("Introduce Last Check Up Km: ");
            if (lastCheckKM<currentKM && lastCheckKM>0){
                validLastCheck=true;
            }else {
                System.out.println("\nInvalid Last Check Up Km!");
            }
        }
        return lastCheckKM;
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
        double grossWeight = 0;
        boolean validGross=false;
        while (!validGross){
            System.out.print("Gross-Weight of the Vehicle: ");
            grossWeight = scan.nextDouble();
            if(grossWeight>0 && grossWeight<=7000){
                throw new IllegalArgumentException("The introduced Gross-Weight is incorrect.");
            } else {
                validGross=true;
            }
        }
        return grossWeight;
    }

    private double registerFrequency() {
        Scanner scan = new Scanner(System.in);
        double frequencyKm = 0;
        boolean validKm=false;
        while (!validKm){
            System.out.print("Maintenance Check-Up Kilometers: ");
            frequencyKm = scan.nextDouble();
            if(frequencyKm>1000 && frequencyKm<=50000){
                throw new IllegalArgumentException("The introduced Frequency for Check-Up is incorrect.");
            } else {
                validKm=true;
            }
        }
        return frequencyKm;
    }

    private double registerCurrentKM() {
        Scanner scan = new Scanner(System.in);
        double currentKM = 0;
        boolean validKm=false;
        while (!validKm){
            System.out.print("Current Kilometers of the Vehicle: ");
            currentKM = scan.nextInt();
           if(currentKM<0 || currentKM>1500000){
                throw new IllegalArgumentException("The introduced data of current kilometers is incorrect.");
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
        boolean validDate = false;
        while (!validDate){
            System.out.print("Register Date of Vehicle: ");
            date = scan.nextLine();
            String[] dateFormat = date.split("-");
            registerDate=new Date(Integer.parseInt(dateFormat[0]),Integer.parseInt(dateFormat[1]),Integer.parseInt(dateFormat[2]));
            if(dateFormat.length==3 && acquisitionDate.getYear()>1900){
                throw new IllegalArgumentException("The introduced register date is incorrect.");
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
                throw new IllegalArgumentException("The introduced acquisition date is incorrect.");
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
                throw new IllegalArgumentException("The introduced brand is incorrect.");
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
            System.out.print("Select a vehicle type: ");
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
