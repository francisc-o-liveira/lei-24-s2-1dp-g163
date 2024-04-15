package MATDISC.US013;

public class Point {
    private double x;

    private double y;


    private static final double PRICE_PER_OMISSION=0;

    public Point(double x, double y){
        this.x=x;
        this.y=y;

    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object other){
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        Point otherPoint = (Point) other;
        return otherPoint.getX()==this.getX() && otherPoint.getY()==this.getY();
    }

    @Override
    public String toString(){
        return String.format("%.2f, %.2f", x,y);
    }
}
