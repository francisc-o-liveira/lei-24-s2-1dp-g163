package MATDISC.US013;


public class Edge {
    private Point p1;

    private Point p2;

    private double price;

    private static final double PRICE_PER_OMISSION=0;

    public Edge(Point p1, Point p2, double price){
        this.p1=p1;
        this.p2=p2;
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
    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p2;
    }
}
