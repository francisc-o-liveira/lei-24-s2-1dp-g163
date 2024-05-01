package pt.ipp.isep.dei.esoft.project.ui.console.vehicle;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterCheckUpController;
import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
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
        System.out.println("--- Register a Check Up of a Vehicle ---");
        requestData();
        submitsData();
    }

    private void submitsData() {
        Optional<Object> checkUpList = getController().addCheckUp(vehicleToCheckUp,dateOfCheckUp,currentKm);
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
    }

    private Date registerDateCheckUp() {
        String date;
        boolean showError = true;
        do{
            if(!showError){
                System.out.println("Formato de data inválido. Por favor, utilize DD/MM/AAAA. ");
            }
            System.out.print("Introduza a data do CheckUp (DD/MM/AAAA): ");
            date = scanner.nextLine();
            scanner.nextLine(); // Consume the newline
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
                    System.out.println("Quilometragem inválida. A quilometragem deve ser maior que o último valor registado e não negativa.");
                }
                System.out.print("Introduza a quilometragem atual: ");
                currentKm = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline
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
                System.out.println("Não foram encontrados veículos.");
            }

            boolean completed = false;
            int selectedPlate;
            do {
                System.out.println("Matrículas dos veículos disponíveis:");
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

