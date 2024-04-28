package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.vehicle.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;
import pt.ipp.isep.dei.esoft.project.utilities.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * Classe responsável pela gestão do registo de manutenções dos veículos.
 * Oferece métodos para validar datas de manutenção e quilometragem, bem como
 * adicionar manutenção ao veículo.
 */
public class RegisterCheckUpController {
    private final VehicleRepository vehicleRepository;
    /**
     * Construtor para o controller de registo de manutenção.
     * Inicializa a referência ao repositório global de dados.
     */
    public RegisterCheckUpController() {
        this.vehicleRepository = Repositories.getInstance().getVehicleRepository();
    }


    /**
     * Obtém os dados necessários para iniciar o processo de registo de manutenção.
     *
     * @return Lista de matrículas dos veículos disponíveis para manutenção.
     */
    public List<String> getDataNeededToRegisterCheckUp() {
        return vehicleRepository.getVehiclePlateList();
    }

    /**
     * Valida a data de manutenção introduzida, verificando se não é uma data
     * futura.
     *
     * @param date Data de manutenção a ser validada.
     * @return Verdadeiro se a data for válida, falso se for inválida ou mal
     *         formatada.
     */
    public boolean validateCheckUpDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate CheckUpDate = LocalDate.parse(date, formatter);
            return !CheckUpDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Valida a quilometragem introduzida, assegurando que é superior à última
     * quilometragem registada e não negativa.
     *
     * @param kms   Quilometragem atual do veículo.
     * @param plate Matrícula do veículo.
     * @return Verdadeiro se a quilometragem for válida, falso se for inferior à
     *         última registada ou negativa.
     */

    /**
     * Este método é usado para adicionar um check-up a um veículo.
     *
     * @param vehicle     O vehicle o ao qual o check-up será adicionado.
     * @param date       A data em que o check-up foi realizado.
     * @param currentKms A quilometragem atual do veículo no momento do check-up.
     * @return Uma lista de strings contendo os detalhes do check-up se a operação
     * for bem-sucedida,
     * ou null se a operação falhar.
     */
    public Optional<Object> addCheckUp(Vehicle vehicle, Date date, int currentKms) {
        if (vehicleRepository.addCheckUp(vehicle, date, currentKms)) {
            return Optional.of(vehicleRepository.getCheckUpDetailsList(vehicle));
        } else {
            return Optional.empty();
        }
    }

    public Vehicle getVehicleByIndex(int selectedPlate) {
        return vehicleRepository.getVehicleByIndex(selectedPlate);
    }
}
