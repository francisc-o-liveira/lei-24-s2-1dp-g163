package MATDISC.USs;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.*;
import java.util.ArrayList;
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

        while (option != 0) {
            option = askOptionShowOptions();
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
                    if (edges == null) {
                        System.out.println("No file has been loaded!");
                        break;
                    }
                    startTime = System.nanoTime();
                    try {
                        if (edges != null) {
                            sortArrayListPrimitivePerPrice(edges);
                        }else {
                            throw new FileNotFoundException();
                        }
                    }catch (FileNotFoundException e){
                        System.out.println("File Empty, not found any Edge on this file");
                    }
                    result = kruskalAlgorithm(edges);
                    endTime = System.nanoTime();
                    long executionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                    executionTimes.add((double) executionTime);
                    try {
                        createResultFile(result,executionTime);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //sizeInput.add((double) (edges != null ? edges.size() : 0));
                    ArrayList<Point> vertices=numberOfVertices(edges);
                    sizeInput.add((double)vertices.size());
                    break;
                case 3:

                    try{
                        createExecutionTimeFile(executionTimes,sizeInput);
                        plotGraphAndShow();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    break;
            }

        }
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

    public static int askOptionShowOptions() {
        Scanner scan = new Scanner(System.in);
        int option = -1;
        System.out.println("-----------------MENU KRUSKAL ALGORITHM---------------");
        System.out.printf("----Option 1 : Introduce FileName      ACTUAL FILENAME:  %s%n", fileName);
        System.out.println("----Option 2 : Kruskal Algorithm (Data Loaded)");
        System.out.println("----Option 3 : Plot the graph of Execution Time");
        while (option < 0 || option > 3) {
            if (option != -1) {
                System.out.println("WARNING : INTRODUCE A CORRECT NUMBER TO SELECT A OPTION ON MENU");
            }

            option = Integer.parseInt(scan.nextLine());
        }
        return option;
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

    public static ArrayList<Edge> kruskalAlgorithm(ArrayList<Edge> edges) {
        int na = 0;
        ArrayList<Point> vertices = numberOfVertices(edges);
        ArrayList<Edge> edgesSave = new ArrayList<>();
        Point[][] S = new Point[vertices.size()][vertices.size()];
        distributeInArrayPoints(S, vertices);

        int valueIndexLine1 = 0;
        int valueIndexLine2 = 0;
        int valueIndexColumn1 = 0;
        int valueIndexColumn2 = 0;
        for (Edge edgeX : edges) {
            Point x1 = edgeX.getP1();
            Point x2 = edgeX.getP2();
            for (int j = 0; j < S[0].length; j++) {
                for (int k = 0; k < S.length; k++) {
                    if (x1.equals(S[k][j])) {
                        valueIndexColumn1 = j;
                        valueIndexLine1 = k;
                    }
                    if (x2.equals(S[k][j])) {
                        valueIndexColumn2 = j;
                        valueIndexLine2 = k;
                    }
                }
            }
            if (valueIndexColumn1 != valueIndexColumn2) {
                na++;
                if (na == vertices.size()) {
                    break;
                }
                edgesSave.add(edgeX);
                int newIndexLine2 = findNull(valueIndexColumn1, S);
                for (int l = 0; l < S[valueIndexColumn2].length; l++) {
                    if (S[l][valueIndexColumn2] == null) {
                        break;
                    }
                    S[newIndexLine2][valueIndexColumn1] = S[l][valueIndexColumn2];
                    S[l][valueIndexColumn2] = null;
                    newIndexLine2++;
                }
            }
        }
        return edgesSave;
    }

    private static void distributeInArrayPoints(Point[][] s, ArrayList<Point> vertices) {
        int i = 0;
        for (Point vertice : vertices) {
            s[0][i] = vertice;
            i++;
        }
    }

    public static int findNull(int column, Point[][] S) {
        for (int i = 0; i < S[column].length; i++) {
            if (S[i][column] == null) {
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