package MATDISC.US013;

public class Point {
    private double xA;

    private double xB;

    private double price;

    private static final double PRICE_PER_OMISSION=0;

    public Point(double xA, double xB, double price){
        this.xA=xA;
        this.xB=xB;
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
    public double getxA() {
        return xA;
    }
    public double getxB() {
        return xB;
    }
}
