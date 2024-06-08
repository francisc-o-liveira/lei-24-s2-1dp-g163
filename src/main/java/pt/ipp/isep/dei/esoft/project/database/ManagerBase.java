package pt.ipp.isep.dei.esoft.project.database;

import pt.ipp.isep.dei.esoft.project.domain.employee.Manager;
import pt.ipp.isep.dei.esoft.project.repository.Organization;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.gui.MainApp;

import java.io.*;
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

    public void removeFromManagerDataBase(Manager manager,List<Manager> managers) {

        try {
            FileOutputStream fileOut = new FileOutputStream(MainApp.getManagerDataBaseFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
           if (!managers.contains(manager)){
               out.writeObject(managers);
           }
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFromManagerInDataBase(List<Manager> managers){
        try {
            FileOutputStream file = new FileOutputStream(MainApp.getManagerDataBaseFile());
            ObjectOutputStream out;
            // If the file already has content, we need to use the AppendableObjectOutputStream
            out = new ObjectOutputStream(file);
            out.writeObject(managers);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromManagerDataBase() throws IOException {
        List<Manager> managers;
        File file = new File(MainApp.getManagerDataBaseFile());
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Collaborator database file did not exist and has been created. Starting with an empty list.");
                } else {
                    throw new IOException("Collaborator database file does not exist and could not be created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("An error occurred while trying to create the Collaborator database file.", e);
            }
        }
        try {
            FileInputStream fileIS = new FileInputStream(MainApp.getManagerDataBaseFile());
            if (fileIS.getChannel().size() > 0){
                ObjectInputStream in = new ObjectInputStream(fileIS);
                while (true) {
                    try {
                        managers = (List<Manager>) in.readObject();
                        if (managers != null){
                            for (Manager manager : managers) {
                                loadInSystem(manager);
                            }
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
            }
            fileIS.close();
        } catch (ClassNotFoundException | IOException | CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInSystem(Manager manager) throws CloneNotSupportedException {
        getRep().loadManager(manager);
    }
}
