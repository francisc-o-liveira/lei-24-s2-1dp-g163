package MATDISC.US013;


public class Edge {
    private Point p1;

    private Point p2;

    private int price;

    private static final int PRICE_PER_OMISSION=0;

    public Edge(Point p1, Point p2, int price){
        this.p1=p1;
        this.p2=p2;
        if(price>0){
            this.price=price;
        }else {
            this.price=PRICE_PER_OMISSION;
            System.out.println("Preço por omissao introduzido");
        }
    }

    public void setPrice(int price) {
        if(price>0){
            this.price=price;
        }else {
            this.price=PRICE_PER_OMISSION;
            System.out.println("Preço por omissao introduzido");
        }
    }
    public int getPrice() {
        return price;
    }
    public Point getP1() {
        return p1;
    }
    public Point getP2() {
        return p2;
    }

    @Override
    public String toString(){
        return String.format("Edge: %s to %s Cost: %d", p1, p2, price);
    }
}
