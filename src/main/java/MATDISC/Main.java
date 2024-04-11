package MATDISC;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {



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

    public static void sortArrayListPrimitivePerPrice(ArrayList<Point> points){
        Point savePoint;
        for (int i = 0; i < points.size(); i++) {
            savePoint = points.get(i);
            for (int j = i+1; j < points.size(); j++) {
                if(points.get(j).getPrice()<savePoint.getPrice()){
                    points.remove(savePoint);
                    points.add(i,points.get(j));
                    points.remove(points.get(j));
                    points.add(j,savePoint);
                }
            }
        }
    }
}
