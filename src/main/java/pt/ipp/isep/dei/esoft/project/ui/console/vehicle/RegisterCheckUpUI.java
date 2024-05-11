package pt.ipp.isep.dei.esoft.project.ui.console.vehicle;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCheckUpController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Classe responsável pela interacção com o utilizador para o registo de
 * verificações técnicas (CheckUps) dos veículos.
 */
public class RegisterCheckUpUI implements Runnable {
    private RegisterCheckUpController ctrl;
    private Scanner scanner;
    private Vehicle vehicleToCheckUp;
    private Date dateOfCheckUp;
    private double mainetanceKm;

    private double currentKm;

    public RegisterCheckUpUI() {
        this.ctrl = new RegisterCheckUpController();
        this.scanner = new Scanner(System.in);
    }

    public RegisterCheckUpController getController(){
        return ctrl;
    }

    /**
     * Inicia o processo interativo de registo de um novo CheckUp.
     */
    public void run() {
        try {
            System.out.println("--- Register a Check Up of a Vehicle ---");
            requestData();
            submitsData();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void submitsData() {
        Optional<Object> checkUpList = getController().addCheckUp(vehicleToCheckUp,dateOfCheckUp,currentKm,mainetanceKm);
        if (checkUpList.isPresent()) {
            System.out.println("\nCheck Up successfully registed!");
        } else {
            System.out.println("\nCheck Up Not Registed!");
        }
    }

    /**
     * Processa o registo de CheckUp, obtendo dados necessários e gerindo a
     * interação com o utilizador.
     */
    private void requestData() {
        vehicleToCheckUp = displayPlatesListAndSelectOne();
        dateOfCheckUp = registerDateCheckUp();
        currentKm = registerCurrentKm();
        mainetanceKm=askIfWantNewFrequency();
    }

    private double askIfWantNewFrequency() {
        double mainetanceKm;
        int option=-1;
        do {
            System.out.println("You want the same or want a new frequency check up km?");
            System.out.println("1-- New Frequency of Check Up");
            System.out.println("2-- Same Frequency of Check Up");
            option= Utils.readIntegerFromConsole("Select option:");
        }while (option<1 || option>2);
        if(option==2){
            do{
                mainetanceKm=Utils.readIntegerFromConsole("Enter new frequency check up km: ");
            }while(mainetanceKm<5000 || mainetanceKm>100000);
            return mainetanceKm;
        }else{
            return 0;
        }
    }

    private Date registerDateCheckUp() {
        String date;
        boolean showError = true;
        do{
            if(!showError){
                System.out.println("Incorrect Date Format. ");
            }
            date = Utils.readLineFromConsole("Introduce the Check Up Date (DD/MM/AAAA): ");
            showError=false;
        }while(verifyCheckUpDateFormat(date));
        String[]dateFormat = date.split("/");
        return new Date(Integer.parseInt(dateFormat[2]), Integer.parseInt(dateFormat[1]), Integer.parseInt(dateFormat[0]));
    }

        public double registerCurrentKm () {
            int attempts=0;
            double currentKm;
            do {
                if(attempts<0){
                    System.out.println("Invalid Current Kms.");
                }
                currentKm = Utils.readDoubleFromConsole("Introduce the Current Kms: ");
                attempts--;
            } while (!verifyKilometersFormat(currentKm));
            return currentKm;
           }

        /**
         * Apresenta a lista de matrículas dos veículos e solicita dados ao utilizador
         * para o registo do CheckUp.
         *
         * @return
         */
        private Vehicle displayPlatesListAndSelectOne () {
            List<String> vehiclePlates = this.ctrl.getDataNeededToRegisterCheckUp();
            if (vehiclePlates.isEmpty()) {
                System.out.println("No Vehicles were found.");
            }

            boolean completed = false;
            int selectedPlate;
            do {
                System.out.println("Vehicles to Register a Check Up:");
                int i = 0;
                for (String plate : vehiclePlates) {
                    i++;
                    System.out.printf("---%d --- %s", i, plate);
                }

                System.out.print("Introduce the option of the plate for the CheckUp Vehicle: ");
                selectedPlate = scanner.nextInt();

            } while (selectedPlate < 0 || selectedPlate > vehiclePlates.size());
            return ctrl.getVehicleByIndex(selectedPlate);
        }

        /**
         * Verifica se o formato da data do CheckUp está correto.
         *
         * @param date A data do CheckUp como String.
         * @return Verdadeiro se o formato estiver correto; falso se não estiver.
         */
        private boolean verifyCheckUpDateFormat (String date){
            return date.matches("\\d{2}/\\d{2}/\\d{4}");
        }

        /**
         * Verifica se o formato da quilometragem está correto.
         *
         * @param currentKms A quilometragem como String.
         * @return Verdadeiro se o formato estiver correto; falso se não estiver.
         */
        private boolean verifyKilometersFormat ( double currentKms){
            return currentKms > 0;
        }
    }

