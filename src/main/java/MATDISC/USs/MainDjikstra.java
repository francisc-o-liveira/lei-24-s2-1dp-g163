package MATDISC.USs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;


public class MainDjikstra {
    public static String fileName;

    public static final String pathName = "src/main/java/MATDISC/USs/evaluationFolder/sprintC/";

    public static final String FILENAME_PER_OMISSION = "..NONE..";
    private static int counterForAPS=0;

    public static void main(String[] args) {
        String filenameMatrix = FILENAME_PER_OMISSION;
        String filenamePointsName = FILENAME_PER_OMISSION;
        int option = -1;
        ArrayList<Edge> edges = null;
        ArrayList<Edge> result;
        Point start;
        Scanner sc = new Scanner(System.in);
        while (option != 0) {
            option = askOptionShowOptions(edges);
            switch (option) {
                case 1:
                    filenameMatrix = askFileNameMatrix();
                    filenamePointsName= askFileNamePoints();
                    try {
                        if(filenameMatrix.contains("17")){
                            edges = readEdgesFromFileUS17(filenameMatrix,filenamePointsName);
                        }
                        if(filenameMatrix.contains("18")){
                            edges = readEdgesFromFileUS18(filenameMatrix,filenamePointsName);
                        }
                        createInputFile(edges);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    start=new Point("AP");
                    System.out.println("To which point do you wish to calculate the path?");
                    Point end=new Point(sc.next());
                    result = dijkstraAlgorithmUS17(edges, start, end);
                    try {
                        createResultFile(result);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    ArrayList<Point> startingPoints = new ArrayList<>();
                    for (int i = 0; i < counterForAPS; i++) {
                        int numberForAP=i+1;
                        startingPoints.add(new Point("AP"+numberForAP));
                    }
                    ArrayList<Point> vertices=numberOfVertices(edges);
                    System.out.print("What is the starting point: ");
                    start = new Point(sc.next());
                    sc.close();
                    startingPoints.remove(start);
                    ArrayList<Edge> edgesToUse=removingEdges(edges,startingPoints);
                    result = dijkstraAlgorithmUS18(edgesToUse, start, vertices);
                    try {
                        createResultFile(result);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 0:
                    break;
            }

        }
    }

    private static ArrayList<Edge> removingEdges(ArrayList<Edge> edges, ArrayList<Point> startingPoints) {
        ArrayList<Edge> edgeList=new ArrayList<>();
        for (Edge e : edges) {
            boolean containsStartingPoint = false;
            for (Point p : startingPoints) {
                if (e.getP1().equals(p) || e.getP2().equals(p)) {
                    containsStartingPoint = true;
                    break;
                }
            }
            if (!containsStartingPoint) {
                edgeList.add(e);
            }
        }
        return edgeList;
    }


    private static ArrayList<Edge> readEdgesFromFileUS17(String matrixFilePath, String pointsFilePath) throws FileNotFoundException, FileNotFoundException {
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Point> points = new ArrayList<>();
        Scanner scPoints = new Scanner(new File(pointsFilePath));
        if (scPoints.hasNextLine()) {
            String line = scPoints.nextLine();
            String[] pointNames = line.split(";");
            for (String pointName : pointNames) {
                points.add(new Point(pointName));
            }
        }
        scPoints.close();
        Scanner scMatrix = new Scanner(new File(matrixFilePath));
        int row = 0;
        while (scMatrix.hasNextLine()) {
            String line=scMatrix.nextLine();
            if (line.startsWith("\uFEFF")) {
                line = line.substring(1);
            }
            String[] weights = line.split(";");
            for (int col = 0; col < weights.length; col++){
                int weight = Integer.parseInt(weights[col]);
                if (weight > 0) {
                    edges.add(new Edge(points.get(row), points.get(col), weight));
                }
            }
            row++;
        }
        scMatrix.close();

        return edges;
    }

    private static ArrayList<Edge> readEdgesFromFileUS18(String matrixFilePath, String pointsFilePath) throws FileNotFoundException, FileNotFoundException {
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Point> points = new ArrayList<>();
        Scanner scPoints = new Scanner(new File(pointsFilePath));
        if (scPoints.hasNextLine()) {
            String line = scPoints.nextLine();
            String[] pointNames = line.split(";");
            for (String pointName : pointNames) {
                points.add(new Point(pointName));
                if(pointName.startsWith("AP")){
                    counterForAPS++;
                }
            }
        }
        scPoints.close();
        Scanner scMatrix = new Scanner(new File(matrixFilePath));
        int row = 0;
        while (scMatrix.hasNextLine()) {
            String line=scMatrix.nextLine();
            if (line.startsWith("\uFEFF")) {
                line = line.substring(1);
            }
            String[] weights = line.split(";");
            for (int col = 0; col < weights.length; col++){
                int weight = Integer.parseInt(weights[col]);
                if (weight > 0) {
                    edges.add(new Edge(points.get(row), points.get(col), weight));
                }
            }
            row++;
        }
        scMatrix.close();

        return edges;
    }

    //procura o índice com o menor custo
    private static int indexOfMin(ArrayList<Integer> costsOfVertices, ArrayList<Boolean> verticesVisited, int numVertices) {
        int min = Integer.MAX_VALUE;
        int indexOfMinimum = -1;
        for (int j = 0; j < numVertices; j++) {
            if (!verticesVisited.get(j) && costsOfVertices.get(j) < min) {
                min = costsOfVertices.get(j);
                indexOfMinimum = j;
            }
        }
        return indexOfMinimum;
    }


    public static ArrayList<Edge> dijkstraAlgorithmUS17(ArrayList<Edge> edges, Point startingPoint, Point endPoint){
        ArrayList<Edge> resultPath = new ArrayList<>();

        ArrayList<Point> vertices = numberOfVertices(edges);
        int numVertices = vertices.size();

        //lista dos custos
        ArrayList<Integer> costsOfVertices = new ArrayList<>(Collections.nCopies(numVertices, Integer.MAX_VALUE));
        //lista para saber se o vértice já foi usado
        ArrayList<Boolean> verticesUsed = new ArrayList<>(Collections.nCopies(numVertices, false));
        //lista do caminho usado
        ArrayList<Point> pathVertices = new ArrayList<>(Collections.nCopies(numVertices, null));

        int indexOfStartingPoint = vertices.indexOf(startingPoint);
        int indexOfEndPoint = vertices.indexOf(endPoint);

        costsOfVertices.set(indexOfStartingPoint, 0);


        for (int i = 0; i < numVertices - 1; i++) {
            //dos vértices com os quais o vértice escolhido tem ligação, o index do que tem menor custo
            int indexOfMinimum = indexOfMin(costsOfVertices, verticesUsed, numVertices);
            //o algoritmo pára quando o de menor custo for o endPoint ou quando todos os vértices já foram usados
            if (indexOfMinimum==-1 || indexOfMinimum == indexOfEndPoint) {
                break;
            }

            Point chosenVertice = vertices.get(indexOfMinimum);
            verticesUsed.set(indexOfMinimum, true);

            //atualizar os custos dos caminhos (no caso de serem menores)
            for (Edge edge : edges) {
                if (edge.getP1().equals(chosenVertice)) {
                    int indexOfNext = vertices.indexOf(edge.getP2());
                    if (!verticesUsed.get(indexOfNext)){
                        int cost=costsOfVertices.get(indexOfMinimum)+edge.getPrice();
                        if (cost<costsOfVertices.get(indexOfNext)) {
                            costsOfVertices.set(indexOfNext,cost);
                            pathVertices.set(indexOfNext,chosenVertice);
                        }
                    }
                }
            }
        }

        // construir o caminho resultante
        Point currentPoint=endPoint;
        while (currentPoint != null && !currentPoint.equals(startingPoint)) {
            Point beforePoint=pathVertices.get(vertices.indexOf(currentPoint));
            if (beforePoint == null) {
                break;
            }
            int cost = costsOfVertices.get(vertices.indexOf(currentPoint))-costsOfVertices.get(vertices.indexOf(beforePoint));
            resultPath.add(new Edge(beforePoint, currentPoint, cost));
            currentPoint=beforePoint;
        }

        return resultPath;
    }

    public static ArrayList<Edge> dijkstraAlgorithmUS18(ArrayList<Edge> edges, Point startingPoint, ArrayList<Point> vertices) {
        ArrayList<Edge> resultPath = new ArrayList<>();

        int numVertices = vertices.size();

        // List of costs
        ArrayList<Integer> costsOfVertices = new ArrayList<>(Collections.nCopies(numVertices, Integer.MAX_VALUE));
        // List to check if the vertex has been used
        ArrayList<Boolean> verticesUsed = new ArrayList<>(Collections.nCopies(numVertices, false));
        // List of the path used
        ArrayList<Point> pathVertices = new ArrayList<>(Collections.nCopies(numVertices, null));

        int indexOfStartingPoint = vertices.indexOf(startingPoint);


        costsOfVertices.set(indexOfStartingPoint, 0);

        for (int i = 0; i < numVertices - 1; i++) {
            // Index of the vertex with the minimum cost among the unused vertices
            int indexOfMinimum = indexOfMin(costsOfVertices, verticesUsed, numVertices);
            // The algorithm stops when the endPoint is the vertex with the minimum cost or all vertices have been used
            if (indexOfMinimum == -1) {
                break;
            }
            Point chosenVertex = vertices.get(indexOfMinimum);
            verticesUsed.set(indexOfMinimum, true);
            // Update the costs of the paths (if they are smaller)
            for (Edge edge : edges) {
                if (edge.getP1().equals(chosenVertex)) {
                    int indexOfNext = vertices.indexOf(edge.getP2());
                    if (!verticesUsed.get(indexOfNext)) {
                        int cost = costsOfVertices.get(indexOfMinimum) + edge.getPrice();
                        if (cost < costsOfVertices.get(indexOfNext)) {
                            costsOfVertices.set(indexOfNext, cost);
                            pathVertices.set(indexOfNext, chosenVertex);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < numVertices; i++) {
            if (pathVertices.get(i) != null) {
                resultPath.add(new Edge(pathVertices.get(i), vertices.get(i), costsOfVertices.get(i)));
            }
        }

        return resultPath;
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

    public static String askFileNameMatrix() {
        String filename = pathName;
        Scanner readLineFile = new Scanner(System.in);
        File test;
        do {
            System.out.println("Introduce the matrix to take data.(input: nameFile.csv)");
            filename += readLineFile.nextLine();
            test = new File(filename);

        } while (!test.canExecute());
        return filename;
    }

    public static String askFileNamePoints() {
        String filename = pathName;
        Scanner readLineFile = new Scanner(System.in);
        File test;
        do {
            System.out.println("Introduce the points' names to take data.(input: nameFile.csv)");
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
        if (edges == null) {
            System.out.println("----Option 2 : Djikstra Algorithm US17 (No Data Loaded)");
        } else {
            System.out.println("----Option 2 : Djikstra Algorithm US17 (Data Loaded)");
        }
        if (edges == null) {
            System.out.println("----Option 3 : Djikstra Algorithm US18 (No Data Loaded)");
        } else {
            System.out.println("----Option 3 : Djikstra Algorithm US18 (Data Loaded)");
        }
        while (option < 0 || option > 3) {
            if (option != -1) {
                System.out.println("WARNING : INTRODUCE A CORRECT NUMBER TO SELECT A OPTION ON MENU");
            }

            option = Integer.parseInt(scan.nextLine());
        }
        return option;
    }

    public static void createResultFile(ArrayList<Edge> edges) throws IOException {
        FileWriter fileDot = new FileWriter("graph.dot");
        FileWriter result = new FileWriter("result.csv");
        int price = 0;
        String line = "graph US13 {\n";
        fileDot.write(line);
        for (int i = 0; i < edges.size(); i++) {
            String string = String.format("%s -- %s [label=\"%d\"];\n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            fileDot.write(string);
            String line1 = String.format("%s;%s;%d%n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            price += edges.get(i).getPrice();
            result.write(line1);
        }
        String line2 = "}";
        fileDot.write(line2);
        String line3 = String.format("Cost: %s%n", price);
        String line4 = String.format("Number of edges: %d%n", edges.size());
        result.write(line3);
        result.write(line4);
        fileDot.close();
        result.close();
        try {
            String resultingFile = "result_graph.png";
            createGraph("graph.dot", resultingFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createInputFile(ArrayList<Edge> edges) throws IOException {
        FileWriter fileDot = new FileWriter("graphinput.dot");
        FileWriter result = new FileWriter("result.csv");
        int price = 0;
        String line = "graph US13 {\n";
        fileDot.write(line);
        for (int i = 0; i < edges.size(); i++) {
            String string = String.format("%s -- %s [label=\"%d\"];\n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            fileDot.write(string);
            String line1 = String.format("%s;%s;%d%n", edges.get(i).getP1(), edges.get(i).getP2(), edges.get(i).getPrice());
            price += edges.get(i).getPrice();
            result.write(line1);
        }
        String line2 = "}";
        fileDot.write(line2);
        String line3 = String.format("Cost: %s", price);
        result.write(line3);
        fileDot.close();
        result.close();
        try {
            createInputGraph("graphinput.dot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createGraph(String fileName, String resultingFile) throws IOException {
        String command = "C:\\Program Files\\Graphviz\\bin\\dot.exe -Tpng " + fileName + " -o " + resultingFile;
        Runtime rt = Runtime.getRuntime();
        Process prcs = rt.exec(command);
    }

    public static void createInputGraph(String fileName) throws IOException {
        String command = "C:\\Program Files\\Graphviz\\bin\\dot.exe -Tpng " + fileName + " -o input_graph.png";
        Runtime rt = Runtime.getRuntime();
        Process prcs = rt.exec(command);
    }
}