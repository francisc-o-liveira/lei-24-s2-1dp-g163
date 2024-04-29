package MATDISC.USs;

public class Point {
    private String x;
    private static final double PRICE_PER_OMISSION=0;

    public Point(String x){
        this.x=x;
    }

    public String getX() {
        return x;
    }

    @Override
    public boolean equals(Object other){
        if(other == null || this.getClass() != other.getClass()){
            return false;
        }
        Point otherPoint = (Point) other;
        return otherPoint.getX().equals(this.getX());
    }

    @Override
    public String toString(){
        return String.format("%s", x);
    }
}
