package MATDISC.US013;

public class Point {
    private double x;

    private double y;

    private double price;

    private static final double PRICE_PER_OMISSION=0;

    public Point(double x, double y, double price){
        this.x=x;
        this.y=y;
        if(price>0){
            this.price=price;
        }else {
            this.price=PRICE_PER_OMISSION;
            System.out.println("Preço por omissao introduzido");
        }
    }

    public void setPrice(double price) {
        if(price>0){
            this.price=price;
        }else {
            this.price=PRICE_PER_OMISSION;
            System.out.println("Preço por omissao introduzido");
        }
    }
    public double getPrice() {
        return price;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object other){
        if(this==other){
            return true;
        }
        if(other != null || this.getClass() != other.getClass()){
            return false;
        }
        Point otherPoint=(Point) other;
        return otherPoint.getX()==this.getX() && otherPoint.getY()==this.getY();
    }
}
