package grocito.wingstud.groctiodriver.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDetailBean implements Serializable{


    /**
     * active : true
     * createdAt : 21/08/2019
     * modifiedAt : Wed Aug 21 13:55:14 IST 2019
     * address : B/136, Shivraj Niketan Colony, Vaishali Nagar, Jaipur, Rajasthan 302021, India
     * latitude : 26.91585119613057
     * longitude : 75.739521458745
     * paymentMode : CARD
     * deliveryDate : Wed Aug 21 13:54:36 IST 2019
     * deliveryDateTime : 0
     * userId : 46
     * rating : 0.0
     * driverId : 10
     * driverLat : 26.8562753
     * driverLong : 75.8060015999999
     * driverName : driver_two
     * driverImg : https://s3.ap-south-1.amazonaws.com/crypto-pro-bucket/1563792229162_notavailablegeneric.png
     * driverMobile : 12
     * orderId : 36
     * restaurantId : 2
     * orderTotal : 260.5
     * orderDiscount : 0.0
     * coinDiscount : 0.0
     * orderTax : 1.5
     * orderSavings : 0.0
     * orderStatus : Accepted
     * ratingUserToDriver : 0
     * ratingDriverToUser : 0
     * paymentStatus : Confirm
     * paymentOrderId : FMPAY00036
     * paymentOrderDetail : All Additional Payment Details will come here.
     * productList : [{"recordId":"67748ff9-0e28-450c-93ee-890d5f9880b1","active":true,"createdAt":"Wed Aug 21 13:55:14 IST 2019","modifiedAt":"Wed Aug 21 13:55:14 IST 2019","orderId":36,"productId":76,"productTitle":"Rasgulla","productQty":1,"productType":"VEG","productMrp":110,"productPrice":100,"productQtyType":"GRAM","productTotal":100,"productDiscount":10,"productTax":1.5,"productSavings":10,"ratingUserToDish":0},{"recordId":"67748ff9-0e28-450c-93ee-890d5f9880b1","active":true,"createdAt":"Wed Aug 21 13:55:14 IST 2019","modifiedAt":"Wed Aug 21 13:55:14 IST 2019","orderId":36,"productId":75,"productTitle":"Gulab Jamun","productQty":1,"productType":"VEG","productMrp":100,"productPrice":99,"productQtyType":"GRAM","productTotal":149,"productDiscount":1,"productTax":1.5,"productSavings":1,"ratingUserToDish":0}]
     * userDetails : {"apUserId":46,"referralCode":"FMREF46","coinBalance":0,"name":"Test","dob":1312960467338,"gender":"Male","phoneNumber":"1234567890","imageUrl":"https://s3-ap-southeast-1.amazonaws.com/sealogbuket/IMG_20190810_124445.jpg","phoneVerified":true,"email":"test@test.com","latitude":0,"longitude":0,"deviceToken":"123456","active":true,"latestRating":0,"avgSpend":0,"totalSpend":0}
     * restaurantAddress : B/136, Shiv niketan colony, near JMJ jewellers, vaishali nagar, jaipur
     * restaurantLat : 26.8857787
     * restaurantLong : 75.9795795
     * restaurantEmail : admin@admin.com
     * restaurantPhone : 9999999999
     * restaurantName : Admin Admin
     * deliveryCharge : 10.0
     * orderSubTotal : 249.0
     * redeemCoin : 0
     * day : 0
     */

    private boolean active;
    private String createdAt;
    private String modifiedAt;
    private String address;
    private double latitude;
    private double longitude;
    private String paymentMode;
    private String deliveryDate;
    private int deliveryDateTime;
    private int userId;
    private double rating;
    private int driverId;
    private double driverLat;
    private double driverLong;
    private String driverName;
    private String driverImg;
    private String driverMobile;
    private String orderId;
    private int restaurantId;
    private double orderTotal;
    private double orderDiscount;
    private double coinDiscount;
    private double orderTax;
    private double orderSavings;
    private String orderStatus;
    private int ratingUserToDriver;
    private int ratingDriverToUser;
    private String paymentStatus;
    private String paymentOrderId;
    private String paymentOrderDetail;
    private UserDetailsBean userDetails;
    private String restaurantAddress;
    private double restaurantLat;
    private double restaurantLong;
    private String restaurantEmail;
    private String restaurantPhone;
    private String restaurantName;
    private double deliveryCharge;
    private double orderSubTotal;
    private int redeemCoin;
    private int day;
    private ArrayList<ProductListBean> productList;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(int deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public double getDriverLat() {
        return driverLat;
    }

    public void setDriverLat(double driverLat) {
        this.driverLat = driverLat;
    }

    public double getDriverLong() {
        return driverLong;
    }

    public void setDriverLong(double driverLong) {
        this.driverLong = driverLong;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverImg() {
        return driverImg;
    }

    public void setDriverImg(String driverImg) {
        this.driverImg = driverImg;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public double getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(double orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public double getCoinDiscount() {
        return coinDiscount;
    }

    public void setCoinDiscount(double coinDiscount) {
        this.coinDiscount = coinDiscount;
    }

    public double getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(double orderTax) {
        this.orderTax = orderTax;
    }

    public double getOrderSavings() {
        return orderSavings;
    }

    public void setOrderSavings(double orderSavings) {
        this.orderSavings = orderSavings;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getRatingUserToDriver() {
        return ratingUserToDriver;
    }

    public void setRatingUserToDriver(int ratingUserToDriver) {
        this.ratingUserToDriver = ratingUserToDriver;
    }

    public int getRatingDriverToUser() {
        return ratingDriverToUser;
    }

    public void setRatingDriverToUser(int ratingDriverToUser) {
        this.ratingDriverToUser = ratingDriverToUser;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }

    public String getPaymentOrderDetail() {
        return paymentOrderDetail;
    }

    public void setPaymentOrderDetail(String paymentOrderDetail) {
        this.paymentOrderDetail = paymentOrderDetail;
    }

    public UserDetailsBean getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsBean userDetails) {
        this.userDetails = userDetails;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public double getRestaurantLat() {
        return restaurantLat;
    }

    public void setRestaurantLat(double restaurantLat) {
        this.restaurantLat = restaurantLat;
    }

    public double getRestaurantLong() {
        return restaurantLong;
    }

    public void setRestaurantLong(double restaurantLong) {
        this.restaurantLong = restaurantLong;
    }

    public String getRestaurantEmail() {
        return restaurantEmail;
    }

    public void setRestaurantEmail(String restaurantEmail) {
        this.restaurantEmail = restaurantEmail;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getOrderSubTotal() {
        return orderSubTotal;
    }

    public void setOrderSubTotal(double orderSubTotal) {
        this.orderSubTotal = orderSubTotal;
    }

    public int getRedeemCoin() {
        return redeemCoin;
    }

    public void setRedeemCoin(int redeemCoin) {
        this.redeemCoin = redeemCoin;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ArrayList<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class UserDetailsBean implements Serializable{
        /**
         * apUserId : 46
         * referralCode : FMREF46
         * coinBalance : 0
         * name : Test
         * dob : 1312960467338
         * gender : Male
         * phoneNumber : 1234567890
         * imageUrl : https://s3-ap-southeast-1.amazonaws.com/sealogbuket/IMG_20190810_124445.jpg
         * phoneVerified : true
         * email : test@test.com
         * latitude : 0.0
         * longitude : 0.0
         * deviceToken : 123456
         * active : true
         * latestRating : 0.0
         * avgSpend : 0.0
         * totalSpend : 0.0
         */

        private int apUserId;
        private String referralCode;
        private int coinBalance;
        private String name;
        private long dob;
        private String gender;
        private String phoneNumber;
        private String imageUrl;
        private boolean phoneVerified;
        private String email;
        private double latitude;
        private double longitude;
        private String deviceToken;
        private boolean active;
        private double latestRating;
        private double avgSpend;
        private double totalSpend;

        public int getApUserId() {
            return apUserId;
        }

        public void setApUserId(int apUserId) {
            this.apUserId = apUserId;
        }

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }

        public int getCoinBalance() {
            return coinBalance;
        }

        public void setCoinBalance(int coinBalance) {
            this.coinBalance = coinBalance;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getDob() {
            return dob;
        }

        public void setDob(long dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public boolean isPhoneVerified() {
            return phoneVerified;
        }

        public void setPhoneVerified(boolean phoneVerified) {
            this.phoneVerified = phoneVerified;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public double getLatestRating() {
            return latestRating;
        }

        public void setLatestRating(double latestRating) {
            this.latestRating = latestRating;
        }

        public double getAvgSpend() {
            return avgSpend;
        }

        public void setAvgSpend(double avgSpend) {
            this.avgSpend = avgSpend;
        }

        public double getTotalSpend() {
            return totalSpend;
        }

        public void setTotalSpend(double totalSpend) {
            this.totalSpend = totalSpend;
        }
    }

    public static class ProductListBean {
        /**
         * recordId : 67748ff9-0e28-450c-93ee-890d5f9880b1
         * active : true
         * createdAt : Wed Aug 21 13:55:14 IST 2019
         * modifiedAt : Wed Aug 21 13:55:14 IST 2019
         * orderId : 36
         * productId : 76
         * productTitle : Rasgulla
         * productQty : 1
         * productType : VEG
         * productMrp : 110.0
         * productPrice : 100.0
         * productQtyType : GRAM
         * productTotal : 100.0
         * productDiscount : 10.0
         * productTax : 1.5
         * productSavings : 10.0
         * ratingUserToDish : 0
         */

        private String recordId;
        private boolean active;
        private String createdAt;
        private String modifiedAt;
        private int orderId;
        private int productId;
        private String productTitle;
        private int productQty;
        private String productType;
        private double productMrp;
        private double productPrice;
        private String productQtyType;
        private double productTotal;
        private double productDiscount;
        private double productTax;
        private double productSavings;
        private int ratingUserToDish;

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getModifiedAt() {
            return modifiedAt;
        }

        public void setModifiedAt(String modifiedAt) {
            this.modifiedAt = modifiedAt;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public int getProductQty() {
            return productQty;
        }

        public void setProductQty(int productQty) {
            this.productQty = productQty;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public double getProductMrp() {
            return productMrp;
        }

        public void setProductMrp(double productMrp) {
            this.productMrp = productMrp;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductQtyType() {
            return productQtyType;
        }

        public void setProductQtyType(String productQtyType) {
            this.productQtyType = productQtyType;
        }

        public double getProductTotal() {
            return productTotal;
        }

        public void setProductTotal(double productTotal) {
            this.productTotal = productTotal;
        }

        public double getProductDiscount() {
            return productDiscount;
        }

        public void setProductDiscount(double productDiscount) {
            this.productDiscount = productDiscount;
        }

        public double getProductTax() {
            return productTax;
        }

        public void setProductTax(double productTax) {
            this.productTax = productTax;
        }

        public double getProductSavings() {
            return productSavings;
        }

        public void setProductSavings(double productSavings) {
            this.productSavings = productSavings;
        }

        public int getRatingUserToDish() {
            return ratingUserToDish;
        }

        public void setRatingUserToDish(int ratingUserToDish) {
            this.ratingUserToDish = ratingUserToDish;
        }
    }
}
