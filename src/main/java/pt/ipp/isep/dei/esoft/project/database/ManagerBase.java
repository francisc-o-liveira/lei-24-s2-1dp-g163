package pt.ipp.isep.dei.esoft.project.database;

import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

import java.io.*;
import java.util.List;

/**
 * Singleton class that manages the persistence and retrieval of Manager objects in the database.
 */
public class ManagerBase {

    private static ManagerBase instance;
    private Organization rep;

    /**
     * Gets the singleton instance of ManagerBase.
     *
     * @return the singleton instance of ManagerBase
     */
    public static ManagerBase getInstance() {
        if (instance == null) {
            synchronized (ManagerBase.class) {
                if (instance == null) {
                    instance = new ManagerBase();
                }
            }
        }
        return instance;
    }

    /**
     * Private constructor to prevent direct instantiation.
     */
    private ManagerBase() {
    }

    /**
     * Gets the organization repository.
     *
     * @return the organization repository
     */
    public Organization getRep() {
        if (rep == null) {
            synchronized (Organization.class) {
                if (rep == null) {
                    rep = Repositories.getInstance().getOrganizationRepository();
                }
            }
        }
        return rep;
    }

    /**
     * Removes a manager from the database.
     *
     * @param manager the manager to be removed
     * @param managers the list of managers
     */
    public void removeFromManagerDataBase(Manager manager, List<Manager> managers) {
        try (FileOutputStream fileOut = new FileOutputStream(MainApp.getManagerDataBaseFile());
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            if (!managers.contains(manager)) {
                out.writeObject(managers);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves a list of managers to the database.
     *
     * @param managers the list of managers to be saved
     */
    public void saveFromManagerInDataBase(List<Manager> managers) {
        try (FileOutputStream file = new FileOutputStream(MainApp.getManagerDataBaseFile());
             ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(managers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads managers from the database.
     */
    public void loadFromManagerDataBase() {
        try (FileInputStream file = new FileInputStream(MainApp.getManagerDataBaseFile())) {
            if (file.getChannel().size() > 0) {
                try (ObjectInputStream in = new ObjectInputStream(file)) {
                    List<Manager> managers;
                    while (true) {
                        try {
                            managers = (List<Manager>) in.readObject();
                            if (managers != null) {
                                for (Manager manager : managers) {
                                    loadInSystem(manager);
                                }
                            }
                        } catch (EOFException e) {
                            break;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a manager into the system.
     *
     * @param manager the manager to be loaded
     * @throws CloneNotSupportedException if cloning is not supported
     */
    private void loadInSystem(Manager manager) throws CloneNotSupportedException {
        getRep().loadManager(manager);
    }
}
