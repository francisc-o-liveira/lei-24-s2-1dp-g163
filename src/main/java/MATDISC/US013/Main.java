package MATDISC.US013;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.knowm.xchart.*;



public class Main {


    public static String fileName;

    public static final String pathName = "src/main/java/MATDISC/US013/";

    public static final String FILENAME_PER_OMISSION="..NONE..";

    public static void main(String[] args) {
        long startTime = 0;
        long endTime = 0;
        String filename = FILENAME_PER_OMISSION;
        int option=-1;
        ArrayList<Edge> edges = null;
        ArrayList<Edge> result;
        ArrayList<Double> executionTimes = new ArrayList<Double>();

        ArrayList<Double> sizeInput = new ArrayList<Double>();


        while(option!=0){
            option=askOptionShowOptions();
            switch (option){
                case 1:
                    filename=askFileName();


                    try {
                    edges = readFromFile(filename);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    sortArrayListPrimitivePerPrice(edges);
                    break;
                case 2:
                    if(edges==null){
                        System.out.println("Nenhum arquivo carregado ainda!");
                        break;
                    }
                    startTime = System.nanoTime();
                    result=kruskalAlgorithm(edges);
                    int price=0;
                    for(Edge r : result){
                        System.out.print(r+"\n");
                        price += r.getPrice();
                    }
                    System.out.printf("Cost: %d%n", price);
                    endTime = System.nanoTime();
                    long executionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                    executionTimes.add((double) executionTime);
                    sizeInput.add((double) (edges != null ? edges.size() : 0));
                    break;
                case 3:
                    plotGraphAndShow(executionTimes,sizeInput);
                    break;
                case 0:
                    break;
            }

        }

        // Calculate the execution time in milliseconds


    }

    private static void plotGraphAndShow(ArrayList<Double> executionTimes, ArrayList<Double> sizeInput) {
        double[] xData = new double[sizeInput.size()];
        double[] yData = new double[getTheHighest(executionTimes)];
        for (int i = 1; i < executionTimes.size(); i++) {
            xData[i] = sizeInput.get(i);
            yData[i] = executionTimes.get(i);
        }

        if(yData.length!=0 && xData.length!=0){
        XYChart chart = QuickChart.getChart("Tempo de Execução", "Execução", "Tempo (ms)", "Execução", xData, yData);

        // Estilização do gráfico
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setCursorColor(Color.BLUE);
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setChartFontColor(Color.BLACK);

        new SwingWrapper<>(chart).displayChart();}
    }

    private static int getTheHighest(ArrayList<Double> executionTimes) {
        double highestValue = 0;
        int indexValue = 0;
        for(int i=0; i < executionTimes.size(); i++){
            if(executionTimes.get(i) > highestValue){
                highestValue=executionTimes.get(i);
                indexValue = i;
            }
        }
        return indexValue;
    }

    public static String askFileName() {
        String filename=pathName;
        Scanner readLineFile = new Scanner(System.in);
        File test;
        do{
            System.out.println("Introduce the FileName to take data.(input: nameFile.csv)");
            filename+=readLineFile.nextLine();
            test = new File(filename);

        }while(!test.canExecute());
        return filename;
    }

    public static int askOptionShowOptions() {
        Scanner scan = new Scanner(System.in);
        int option=-1;
        System.out.println("-----------------MENU KRUSKAL ALGORITHM---------------");
        System.out.printf("----Option 1 : Introduce FileName      ACTUAL FILENAME:  %s%n", fileName);
        System.out.println("----Option 2 : Kruskal Algorithm (Data Loaded)");
        System.out.println("----Option 3 : Plot the graph of Execution Time");
        while(option<0 || option>3){
            if(option!=-1){
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
        for (int i = 0; i <edges.size() - 1; i++) {
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
        distributeInArrayPoints(S,vertices);

        int valueIndexLine1 = 0;
        int valueIndexLine2 = 0;
        int valueIndexColumn1=0;
        int valueIndexColumn2=0;
        for(Edge edgeX : edges){
            Point x1 = edgeX.getP1();
            Point x2 = edgeX.getP2();
            for (int j = 0; j < S[0].length; j++){
                for(int k = 0; k < S.length; k++){
                    if (x1.equals(S[k][j])){
                        valueIndexColumn1=j;
                        valueIndexLine1=k;
                    }
                    if (x2.equals(S[k][j])){
                        valueIndexColumn2=j;
                        valueIndexLine2=k;
                    }
                }
            }
            if(valueIndexColumn1!=valueIndexColumn2){
                na++;
                if(na==vertices.size()){
                    break;
                }
                edgesSave.add(edgeX);
                int newIndexLine2=findNull(valueIndexColumn1, S);
                for(int l=0; l<S[valueIndexColumn2].length; l++){
                    if(S[l][valueIndexColumn2]==null){
                        break;
                    }
                    S[newIndexLine2][valueIndexColumn1]=S[l][valueIndexColumn2];
                    S[l][valueIndexColumn2]=null;
                    newIndexLine2++;
                }
            }
        }
        return edgesSave;
    }

    private static void distributeInArrayPoints(Point[][] s, ArrayList<Point> vertices) {
        int i=0;
        for(Point vertice: vertices){
            s[0][i]= vertice;
            i++;
        }
    }

    public static int findNull(int column, Point[][] S){
        for(int i=0; i<S[column].length; i++){
            if(S[i][column]==null){
                return i;
            }
        }
        return -1;
    }
}