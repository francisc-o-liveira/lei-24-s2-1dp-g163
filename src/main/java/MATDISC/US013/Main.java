package MATDISC.US013;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static String fileName;

    public static final String FILENAME_PER_OMISSION="..NONE..";

    public static void main(String[] args) {
        long startTime = 0;
        long endTime = 0;




        String filename = FILENAME_PER_OMISSION;
        int option=-1;
        ArrayList<Edge> edges = null;
        ArrayList<Edge> result;
        while(option!=0){
            option=askOptionShowOptions();
            switch (option){
                case 1:
                    filename=askFileName();
                    edges = readFromFile(filename);
                    sortArrayListPrimitivePerPrice(edges);
                case 2:
                    if(edges==null){
                        break;
                    }
                    startTime = System.nanoTime();
                    result=kruskalAlgorithm(edges);
                    double price=0;
                    for(Edge r : result){
                        System.out.print(r+"\n");
                        price += r.getPrice();
                    }
                    System.out.printf("Cost: %f", price);
                    endTime = System.nanoTime();
                case 0:
                    break;
            }

        }
        // Stop measuring execution time


        // Calculate the execution time in milliseconds
        long executionTime = (endTime - startTime) / 1000000;
        System.out.println("Counting the time of Execution Kruskal takes "
                + executionTime + "ms");
    }

    public static String askFileName() {
        String filename;
        Scanner readLineFile = new Scanner(System.in);
        File test;
        do{
            System.out.println("Introduce the FileName to take data.(input: nameFile.csv)");
            filename=readLineFile.nextLine();
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
        while(option<0 || option>2){
            if(option!=-1){
                System.out.println("WARNING : INTRODUCE A CORRECT NUMBER TO SELECT A OPTION ON MEN");
            }

            option = Integer.parseInt(scan.nextLine());
        }
        return option;
    }

    public static ArrayList<Edge> readFromFile(String fileName) {
        Scanner scanFile = new Scanner(fileName);
        String[] line;
        ArrayList<Edge> edges = new ArrayList<>();
        while (scanFile.hasNextLine()) {
            line = scanFile.nextLine().split(";");
            edges.add(new Edge(new Point(Double.parseDouble(line[0])), new Point(Double.parseDouble(line[1])), Double.parseDouble(line[2])));
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