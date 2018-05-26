package producer_consumer.demo;

public class Goods
{
    public final String name;
    public final int price;
    public final int id;

    public Goods(String name, int price, int id)
    {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }

    public static void main(String[] args)
    {
        Goods goods = new Goods("goods", 50, 1);
        System.out.println(goods);
    }
}
