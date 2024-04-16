package MATDISC.US013;

public class Point {
    private double x;
    private static final double PRICE_PER_OMISSION=0;

    public Point(double x){
        this.x=x;
    }
    public double getX() {
        return x;
    }

    @Override
    public boolean equals(Object other){
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        Point otherPoint = (Point) other;
        return otherPoint.getX()==this.getX();
    }

    @Override
    public String toString(){
        return String.format("%.2f", x);
    }
}
