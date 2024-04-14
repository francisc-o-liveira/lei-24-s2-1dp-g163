package MATDISC.US013;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Point p1 = new Point(1.0, 0.0);
        Point p2 = new Point(2.0, 0.0);
        Point p3 = new Point(3.0, 0.0);
        Point p4 = new Point(4.0, 0.0);
        Point p5 = new Point(5.0, 0.0);
        Point p6 = new Point(6.0, 0.0);
        Point p7 = new Point(7.0, 0.0);
        Point p8 = new Point(8.0, 0.0);
        Point p9 = new Point(9.0, 0.0);



        Edge edge1 = new Edge(p1, p5, 2.0);
        Edge edge2 = new Edge(p1, p2, 4.0);
        Edge edge3 = new Edge(p5, p2, 1.0);
        Edge edge4 = new Edge(p5, p6, 2.0);
        Edge edge5 = new Edge(p2, p3, 2.0);
        Edge edge6 = new Edge(p6, p3, 3.0);
        Edge edge7 = new Edge(p3, p7, 2.0);
        Edge edge8 = new Edge(p6, p7, 2.0);
        Edge edge9 = new Edge(p3, p4, 4.0);
        Edge edge10= new Edge(p7,p4,3.0);
        Edge edge11=new Edge(p5,p8,1.0);
        Edge edge12=new Edge(p8,p6,3.0);
        Edge edge13=new Edge(p8,p9,2.0);
        Edge edge14= new Edge(p9,p7,1.0);


        ArrayList<Edge> edges = new ArrayList<>();

        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);
        edges.add(edge5);
        edges.add(edge6);
        edges.add(edge7);
        edges.add(edge8);
        edges.add(edge9);
        edges.add(edge10);
        edges.add(edge11);
        edges.add(edge12);
        edges.add(edge13);
        edges.add(edge14);

        sortArrayListPrimitivePerPrice(edges);

        ArrayList<Edge> result=kruskalAlgorithm(edges);
        double price=0;
        for(Edge r : result){
            System.out.print(r+"\n");
            price += r.getPrice();
        }
        System.out.printf("Cost: %f", price);

    }

    /*public static void readFromFile(String fileName) {
        Scanner scanFile = new Scanner(fileName);
        String[] line;
        ArrayList<Point> points = new ArrayList<>();
        while (scanFile.hasNextLine()) {
            line = scanFile.nextLine().split(";");
            points.add(new Point(Double.parseDouble(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2])));
        }
    }*/

    // START US013
    public static void sortArrayListPrimitivePerPrice(ArrayList<Edge> edges) {
        Edge saveEdge;
        for (int i = 1; i <edges.size() - 1; i++) {
            saveEdge = edges.get(i);
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
        double cost = 0;
        int na = 0;
        ArrayList<Point> vertices = numberOfVertices(edges);
        ArrayList<Edge> edgesSave = new ArrayList<>();
        Point[][] S = new Point[vertices.size()][vertices.size()];
        int i=0;
        for(Point vertice: vertices){
            S[0][i]= vertice;
            i++;
        }
        int valueIndexLine1 = 0;
        int valueIndexLine2 = 0;
        int valueIndexColumn1=0;
        int valueIndexColumn2=0;
        for(Edge edgeX : edges){
            Point x1 = edgeX.getP1();
            Point x2 = edgeX.getP2();
            for (int j = 0; i < S[0].length; i++){
                for(int k = 0; i < S.length; i++){
                    if(S[k][j] == null){
                        break;
                    }
                    if (x1.equals(S[k][j])){
                        valueIndexLine1=j;
                        valueIndexColumn1=k;
                    }
                    if (x2.equals(S[k][j])){
                        valueIndexLine2=j;
                        valueIndexColumn2=k;
                    }
                }
            }
            if(valueIndexLine1!=valueIndexLine2){
                S[valueIndexLine1++][valueIndexColumn1]=S[valueIndexLine2][valueIndexColumn2];
                S[valueIndexLine2][valueIndexColumn2]=null;
                int newIndexLine2=findNull(valueIndexColumn1, S);
                for(int l=0; l<S[valueIndexColumn2].length; l++){
                    if(S[l][valueIndexColumn2]==null){
                        break;
                    }
                    S[newIndexLine2][valueIndexColumn1]=S[l][valueIndexColumn2];
                    S[l][valueIndexColumn2]=null;
                    newIndexLine2++;
                }
                edgesSave.add(edgeX);
            }
        }
        return edgesSave;
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