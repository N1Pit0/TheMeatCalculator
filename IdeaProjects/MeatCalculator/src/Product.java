public class Product {
    private double weight;
    private double pricePerKg;

    private String productName;
    public Product(double pricePerKg, String productName){
        this.pricePerKg = pricePerKg;
        this.productName = productName;
        this.weight = 0.0;
    }

    public double getPricePerKg(){
        return this.pricePerKg;
    }

    public void setPricePerKg(double pricePerKg){
        this.pricePerKg = pricePerKg;
    }

    public double getWeight(){
        return this.weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public String getProductName(){
        return this.productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }


}
