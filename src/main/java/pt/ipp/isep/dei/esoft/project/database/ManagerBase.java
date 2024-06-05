package pt.ipp.isep.dei.esoft.project.database;

import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;
import pt.ipp.isep.dei.esoft.project.utilities.AppendableObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerBase {
    private static ManagerBase instance;
    private Organization Rep;


    public static ManagerBase getInstance() {
        if (instance == null) {
            synchronized (ManagerBase.class) {
                instance = new ManagerBase();
            }
        }
        return instance;
    }

    public ManagerBase(){
    }

    public Organization getRep() {
        if (Rep == null) {
            synchronized (Organization.class) {
                Rep = Repositories.getInstance().getOrganizationRepository();
            }
        }
        return Rep;
    }

    public void removeFromManagerDataBase(Manager manager) {
        List<Manager> managers = new ArrayList<>();
        Manager managerLoaded;
        try {
            FileInputStream file = new FileInputStream(MainApp.getManagerDataBaseFile());
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                try {
                    managerLoaded = (Manager) in.readObject();
                    if (!managerLoaded.equals(manager)) {
                        managers.add(managerLoaded);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            in.close();
            file.close();

            FileOutputStream fileOut = new FileOutputStream(MainApp.getManagerDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Manager managerSave : managers) {
                out.writeObject(managerSave);
            }
            out.close();
            fileOut.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFromManagerInDataBase(Manager manager){
        try {
            FileOutputStream file = new FileOutputStream(MainApp.getManagerDataBaseFile());
            ObjectOutputStream out;
            // If the file already has content, we need to use the AppendableObjectOutputStream
            if (file.getChannel().size() > 0) {
                out = new AppendableObjectOutputStream(file);
            } else {
                out = new ObjectOutputStream(file);
            }
            out.writeObject(manager);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromManagerDataBase(){
        Manager manager;
        try {
            FileInputStream file = new FileInputStream(MainApp.getManagerDataBaseFile());
            if (file.getChannel().size() > 0){
                ObjectInputStream in = new ObjectInputStream(file);
                while (true) {
                    try {
                        manager = (Manager) in.readObject();
                        loadInSystem(manager);
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
            }
            file.close();
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(Manager manager) throws CloneNotSupportedException {
        getRep().loadManager(manager);
    }
}
