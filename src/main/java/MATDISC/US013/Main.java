package MATDISC.US013;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Point p1=new Point(1,5,2.0);
        Point p2=new Point(1,2,4.0);
        Point p3=new Point(5,2,1.0);
        Point p4=new Point(5,6,2.0);
        Point p5=new Point(2,3,2.0);
        Point p6=new Point(6,3,3.0);
        Point p7=new Point(6,7,2.0);
        Point p8=new Point(7,3,2.0);
        Point p9=new Point(3,4,4.0);
        Point p10=new Point(7,4,3.0);
        Point p11=new Point(5,8,1.0);
        Point p12=new Point(8,6,3.0);
        Point p13=new Point(8,9,2.0);
        Point p14=new Point(9,7,1.0);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        points.add(p8);
        points.add(p9);
        points.add(p10);
        points.add(p11);
        points.add(p12);
        points.add(p13);
        points.add(p14);
        double price=minimumCostAlgorithm(points);
        System.out.printf("Cost: %f", price);//tem de dar 14

    }

    public static void readFromFile(String fileName){
        Scanner scanFile = new Scanner(fileName);
        String[] line;
        ArrayList <Point> points = new ArrayList<>();
        while(scanFile.hasNextLine()){
            line = scanFile.nextLine().split(";");
            points.add(new Point(Double.parseDouble(line[0]),Double.parseDouble(line[1]),Double.parseDouble(line[2])));
        }
    }

    // START US013
    public static void sortArrayListPrimitivePerPrice(ArrayList<Point> points){
        Point savePoint;
        for (int i = 1; i < points.size()-1; i++) {
            savePoint = points.get(i);
            for (int j = i+1; j < points.size(); j++) {
                if(points.get(j).getPrice()<points.get(i).getPrice()){
                    savePoint=points.get(i);
                    points.set(i, points.get(j));
                    points.set(j,savePoint);
                }
            }
        }
    }

    public static int numberOfVertices(ArrayList<Point> points){
        ArrayList<Double> vertices = new ArrayList<>();
        for(Point x : points){
            boolean valueX = true;
            boolean valueY = true;
            for(Double y : vertices){
                if(x.getX()== y){
                    valueX=false;
                }
                if(x.getY()== y){
                    valueY=false;
                }
            }
            if(valueX){
                vertices.add(x.getX());
            }
            if (valueY){
                vertices.add(x.getY());
            }
        }
        return vertices.size();
    }

    public static double minimumCostAlgorithm(ArrayList<Point> points){
        double cost=0;
        int na=0;
        int naFalse = numberOfVertices(points);

        double[][] S = new double[points.size()][naFalse];




        ArrayList<Point> pointsMinimumCost=new ArrayList<>();
        sortArrayListPrimitivePerPrice(points);
        ArrayList<Double> temps=new ArrayList<>();
        temps.add(points.get(0).getX());
        for(int i=0; i<points.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (na == naFalse) { //algoritmo para determinar nª de vértices
                    break;
                }
                if (points.get(i).getX() != temps.get(j) && points.get(i).getY() != temps.get(j)) {
                    temps.add(points.get(i).getX());
                    //algo para n permitir a repetição de arestas
                    pointsMinimumCost.add(points.get(i));
                    cost += points.get(i).getPrice();
                    na++;

                }
            }
        }
        for(Point p: pointsMinimumCost){
            System.out.print(p+"\n");
        }

        return cost;
    }
}