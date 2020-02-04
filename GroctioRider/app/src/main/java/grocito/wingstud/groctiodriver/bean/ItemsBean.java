package grocito.wingstud.groctiodriver.bean;

public class ItemsBean {

    private String itemName;
    private int qty;
    private String price;
    private boolean isVeg;

    public ItemsBean(String itemName, int qty, String price, boolean isVeg){
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.isVeg = isVeg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }
}
