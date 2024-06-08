import java.util.*;
import java.util.List;

public class Sausage {

    private double price;
    private List<Product> productList;
//    private List<Double> weightList;

    public Sausage() {
        this.productList = new ArrayList<>();
        this.price = 0.0;
//        this.weightList = new ArrayList<>();
    }
    public double calculatePrice(){

        double nominator = 0.0;
        double denominator = 0.0;
        for(Product product : this.productList){
            nominator += (product.getPricePerKg() * product.getWeight());
            denominator += product.getWeight();
        }

        return denominator != 0 ? nominator / denominator : 0.0;
    }

//    public void setWeight(){
//        for (int i = 0; i < this.weightList.size(); i++) {
//            productList.get(i).setWeight(this.weightList.get(i));
//        }
//    }

    public void addProduct(Product product){
        this.productList.add(product) ;
    }

    public double getPrice() {
        return price;
    }

    public List<Product> getProductList() {return productList;}

    public void setPrice(double price) {
        this.price = price;
    }
}
