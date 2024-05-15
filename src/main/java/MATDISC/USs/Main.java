package MATDISC.USs;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import java.io.IOException;



public class Main {
    public static String fileName;

    public static final String pathName = "src/main/java/MATDISC/USs/";

    public static final String FILENAME_PER_OMISSION = "..NONE..";

    public static void main(String[] args) {
        long startTime;
        long endTime;
        String filename = FILENAME_PER_OMISSION;
        int option = -1;
        ArrayList<Edge> edges = null;
        ArrayList<Edge> result;
        ArrayList<Double> executionTimes = new ArrayList<>();
        ArrayList<Double> sizeInput = new ArrayList<>();
        long executionTime;
        ArrayList<Point> vertices;
        while (option != 0) {
            option = askOptionShowOptions(edges);
            switch (option) {
                case 1:
                    filename = askFileName();
                    try {
                        edges = readFromFile(filename);
                        createInputFile(edges);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    startTime = System.nanoTime();
                    try {
                        if (edges != null) {
                            sortArrayListPrimitivePerPrice(edges);
                        }else {
                            throw new FileNotFoundException("No data has been loaded!");
                        }
                    }catch (FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    result = kruskalAlgorithm(edges);
                    endTime = System.nanoTime();
                    executionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                    executionTimes.add((double) executionTime);
                    try {
                        createResultFile(result,executionTime);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    vertices = numberOfVertices(edges);
                    sizeInput.add((double)edges.size());
                    break;
                case 3:
                    try{
                        createExecutionTimeFile(executionTimes,sizeInput);
                        plotGraphAndShow();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    try{
                        Path folder = askFolderName();
                        File[] files=folder.toFile().listFiles();
                        sortFiles(files);
                        for (final File fileEntry : files) {
                            if (fileEntry.isDirectory()) {
                                System.out.println("Error not possible to make multiple folders");
                                break;
                            } else {
                                try {
                                    String[] parts = String.valueOf(fileEntry).split("/");
                                    filename= parts[parts.length-1];

                                    edges = readFromFile(filename);
                                    createInputFile(edges);

                                    startTime = System.nanoTime();
                                    try {
                                        if (edges != null) {
                                            sortArrayListPrimitivePerPrice(edges);
                                        }else {
                                            throw new FileNotFoundException("No data has been loaded!");
                                        }
                                    }catch (FileNotFoundException e){
                                        System.out.println(e.getMessage());
                                    }
                                    result = kruskalAlgorithm(edges);
                                    endTime = System.nanoTime();
                                    executionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                                    executionTimes.add((double) executionTime);
                                    try {
                                        createResultFile(result,executionTime);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    vertices = numberOfVertices(edges);
                                    sizeInput.add((double)vertices.size());
                                }catch (IOException e){
                                    System.out.printf("---- Not Complete ---- Error");
                                }
                            }
                        }
                        createExecutionTimeFile(executionTimes,sizeInput);
                        plotGraphAndShow();
                    }catch (IOException e){
                        System.out.println("Error Ploting Graph of Execution Time.");
                    }
                case 0:
                    break;
            }

        }
    }

    private static void sortFiles(File[] files){
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                int number1 = extractNumber(file1);
                int number2 = extractNumber(file2);
                return Integer.compare(number1, number2);
            }
            private int extractNumber(File file) {
                String fileName = file.getName();
                int index = fileName.indexOf('_');
                String numberString = fileName.substring(index + 1, fileName.indexOf('.'));
                return Integer.parseInt(numberString);
            }
        });
    }


    private static Path askFolderName() {
        Scanner scanner = new Scanner(System.in);
        Path folderPath = null;

        try {
            while (true) {
                System.out.print("Please enter a path to the folder: ");
                String folderName = scanner.nextLine();
                folderPath = Paths.get(folderName);

                if (Files.isDirectory(folderPath)) {
                    break;
                } else {
                    System.out.println("The entered path is not a directory. Please try again.");
                }
            }
        } finally {
            scanner.close();
        }

        return folderPath;
    }

    public static void plotGraphAndShow() throws IOException{
        String command = "C:\\Program Files\\gnuplot\\bin\\gnuplot script_plotGraph.gp";
        Runtime rt = Runtime.getRuntime();
        Process prcs = rt.exec(command);
    }

    public static String askFileName() {
        String filename = pathName;
        Scanner readLineFile = new Scanner(System.in);
        File test;
        do {
            System.out.println("Introduce the FileName to take data.(input: nameFile.csv)");
            filename += readLineFile.nextLine();
            test = new File(filename);

        } while (!test.canExecute());
        return filename;
    }

    public static int askOptionShowOptions(ArrayList<Edge> edges) {
        Scanner scan = new Scanner(System.in);
        int option = -1;
        System.out.println("-----------------MENU KRUSKAL ALGORITHM---------------");
        System.out.printf("----Option 1 : Introduce FileName      ACTUAL FILENAME:  %s%n", fileName);
        if (edges==null){
            System.out.println("----Option 2 : Kruskal Algorithm (No Data Loaded)");
        }else{
            System.out.println("----Option 2 : Kruskal Algorithm (Data Loaded)");
        }
        System.out.println("----Option 3 : Plot the graph of Execution Time");
        System.out.println("----Option 4 : Read Folder (opt3/4 included)");
        while (option < 0 || option > 4) {
            if (option != -1) {
                System.out.println("WARNING : INTRODUCE A CORRECT NUMBER TO SELECT A OPTION ON MENU");
            }

            option = Integer.parseInt(scan.nextLine());
        }
        return option;
    }


    // VERTICES == POINTS
    public static ArrayList<Point> numberOfVertices(ArrayList<Edge> edges) {
        ArrayList<Point> vertices = new ArrayList<>();
        boolean value1;
        boolean value2;
        for (Edge edgeX : edges) {
            value1 = false;
            value2 = false;
            Point x1 = edgeX.getP1();
            Point x2 = edgeX.getP2();
            for (Point pointX : vertices) {
                if (x1.equals(pointX)) {
                    value1 = true;
                }
                if (x2.equals(pointX)) {
                    value2 = true;
                }
            }
            if (!value1) {
                vertices.add(x1);
            }
            if (!value2) {
                vertices.add(x2);
            }
        }
        return vertices;
    }

    public static ArrayList<Edge> readFromFile(String fileName) throws FileNotFoundException {
        Scanner scanFile = new Scanner(new File(fileName));
        String[] line;
        ArrayList<Edge> edges = new ArrayList<>();
        while (scanFile.hasNextLine()) {
            line = scanFile.nextLine().split(";");
            edges.add(new Edge(new Point(line[0]), new Point(line[1]), Integer.parseInt(line[2])));
        }
        return edges;
    }

    // START US013
    public static void sortArrayListPrimitivePerPrice(ArrayList<Edge> edges) {
        Edge saveEdge;
        for (int i = 0; i < edges.size() - 1; i++) {
            for (int j = i + 1; j < edges.size(); j++) {
                if (edges.get(j).getPrice() < edges.get(i).getPrice()) {
                    saveEdge = edges.get(i);
                    edges.set(i, edges.get(j));
                    edges.set(j, saveEdge);
                }
            }
        }
    }

    // VERTICES == POINTS
    private static ArrayList<Point> getVertices(ArrayList<Edge> edges) {
        ArrayList<Point> vertices = new ArrayList<>();
        for (Edge edge : edges) {
            if (!vertices.contains(edge.getP1())) {
                vertices.add(edge.getP1());
            }
            if (!vertices.contains(edge.getP2())) {
                vertices.add(edge.getP2());
            }
        }
        return vertices;
    }

    private static int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    private static void union(int[] parent, int[] rank, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public static ArrayList<Edge> kruskalAlgorithm(ArrayList<Edge> edges) {
        ArrayList<Edge> result = new ArrayList<>();

        // Get all vertices
        ArrayList<Point> vertices = getVertices(edges);
        int V = vertices.size();
        // Initialize Union-Find structures
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        // Helper to find index of a vertex in vertices list


        // Kruskal's algorithm

        for (Edge edge : edges) {
            int x = findVertexIndex(edge.getP1(), vertices);
            int y = findVertexIndex(edge.getP2(), vertices);

            int xRoot = find(parent, x);
            int yRoot = find(parent, y);

            if (xRoot != yRoot) {
                result.add(edge);
                union(parent, rank, xRoot, yRoot);
            }
        }
        return result;
    }


    private static int findVertexIndex(Point p, ArrayList<Point> vertices) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(p)) {
                return i;
            }
        }
        return -1;
    }

    public static void createExecutionTimeFile(ArrayList<Double> executionTimes, ArrayList<Double> sizeInput) throws IOException{
        FileWriter exectimesFile= new FileWriter("execution_times.csv");
        for(int i=0; i<executionTimes.size(); i++){
            String line=String.format("%.2f; %.2f%n", sizeInput.get(i), executionTimes.get(i));
            exectimesFile.write(line);
        }
        exectimesFile.close();
    }

    public static void createResultFile(ArrayList<Edge> edges, long execTime) throws IOException {
        FileWriter fileDot= new FileWriter("graph.dot");
        FileWriter result=new FileWriter("result.csv");
        int price = 0;
        String line="graph US13 {\n" ;
        fileDot.write(line);
        for(int i=0; i<edges.size(); i++){
            String string=String.format("%s -- %s [label=\"%d\"];\n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            fileDot.write(string);
            String line1=String.format("%s;%s;%d%n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            price+=edges.get(i).getPrice();
            result.write(line1);
        }
        String line2="}";
        fileDot.write(line2);
        String line3=String.format("Cost: %s%n", price);
        String line4=String.format("Number of edges: %d%n", edges.size());
        String line5=String.format("Time of execution: %d%n ms", execTime);
        result.write(line3);
        result.write(line4);
        result.write(line5);
        fileDot.close();
        result.close();
        try{
            String resultingFile="result_graph.png";
            createGraph("graph.dot", resultingFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void createInputFile(ArrayList<Edge> edges) throws IOException {
        FileWriter fileDot= new FileWriter("graphinput.dot");
        FileWriter result=new FileWriter("result.csv");
        int price = 0;
        String line="graph US13 {\n" ;
        fileDot.write(line);
        for(int i=0; i<edges.size(); i++){
            String string=String.format("%s -- %s [label=\"%d\"];\n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            fileDot.write(string);
            String line1=String.format("%s;%s;%d%n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            price+=edges.get(i).getPrice();
            result.write(line1);
        }
        String line2="}";
        fileDot.write(line2);
        String line3=String.format("Cost: %s", price);
        result.write(line3);
        fileDot.close();
        result.close();
        try{
            createInputGraph("graphinput.dot");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void createGraph(String fileName, String resultingFile) throws IOException{
        String command = "C:\\Program Files\\Graphviz\\bin\\dot.exe -Tpng " + fileName + " -o " +resultingFile;
        Runtime rt = Runtime.getRuntime();
        Process prcs = rt.exec(command);
    }

    public static void createInputGraph(String fileName) throws IOException{
        String command = "C:\\Program Files\\Graphviz\\bin\\dot.exe -Tpng " + fileName + " -o input_graph.png";
        Runtime rt = Runtime.getRuntime();
        Process prcs = rt.exec(command);
    }
}