package inventorymanagementsystem.oop;

import java.util.ArrayList;
import java.util.Scanner;

public class MobileInventory {
    private static final ArrayList<Retailer> retailers = new ArrayList<>(); // -> empty array of retailers []
    private static final ArrayList<CheckIn> checkIns = new ArrayList<>(); // -> empty array of checkIns []
    private static final ArrayList<CheckOut> checkOuts = new ArrayList<>(); // -> empty array of checkOuts []

    //Starting point of app
    public static void main(String[] args) {
        displayMenuInfo();
    }

    private static void displayMenuInfo() {
        //Main Menu
       
        System.out.println("------------Mobile Inventory System---------------");
        
        System.out.println(" Choose a option from list 1 - 7:");
        System.out.println("1.Check In\n2.Check Out\n3.Add Retailer\n4.Display Check In\n5.Display Checkout\n6.Display Retailer\n7.Exit");

        //User input
        Scanner input = new Scanner(System.in);
        int chosenNo = input.nextInt();
        switch (chosenNo) {
            case 1: {
                //check in
                checkIn();
                break;
            }
            case 2: {
                //check out
                checkOut();
                break;
            }
            case 3: {
                addRetailer();
                break;
                //add retailer
            }
            case 4: {
                displayCheckIns();
                displayMenuInfo();
                break;
            }
            case 5: {
                displayCheckOuts();
                displayMenuInfo();
                break;
                //display check out
            }
            case 6: {
                displayRetailers();
                break;
            }
            case 7: {
                //exit out of the application
                System.exit(0);
                break;
            }
            default: {
                System.out.println("You didn't choose a number from the list");
                displayMenuInfo();
                break;
            }
        }
    }

    private static void displayCheckOuts() {
        System.out.println("---------------------------------");
        System.out.println("Brand | Model | Color | Quantity | Price");
        System.out.println("---------------------------------");
        for (int i = 0; checkOuts.size() >= i; i++) {
            Mobile mobile;
            mobile = checkOuts.get(i).mobile;
            System.out.println(mobile.getBrand() + " | " +mobile.getModelNumber() + " | " +
                    mobile.getColor() + " | " + mobile.getQuantity() + " | " + mobile.getPrice());
        }
    }

    private static void checkOut() {
        //check if any retailer is added
        // if isEmpty []
        if (retailers.isEmpty()) {
            System.out.println("No retailers has been added. Please add to checkout");
            displayMenuInfo();
        }
        //if the retailer is added [{}]
        else {
            displayCheckIns();

            Scanner input = new Scanner(System.in);

            System.out.println("Select model number to checkout:");
            String enteredModel = input.nextLine();

            System.out.println("Enter the quantity to checkout:");
            int enteredQuantity = input.nextInt();

            for (int i = 0; i < checkIns.size(); i++) {
                Mobile mobile = checkIns.get(i).mobile;
                if (mobile.getModelNumber().equals(enteredModel)) {
                    //the user has entered the correct model number that in on the array
                    try {
                        if (mobile.getQuantity() < enteredQuantity) {
                            throw new CheckoutNumberLimitException("Not that many items to checkout");
                        } else {
                            Mobile mobileToCheckout = new Mobile(
                                    mobile.getModelNumber(),
                                    mobile.getBrand(),
                                    mobile.getColor(),
                                    enteredQuantity,
                                    mobile.price
                            );
                            CheckOut checkOut = new CheckOut(mobileToCheckout);
                            checkOuts.add(checkOut);
                            System.out.println("Mobile checkout successful!");
                            displayMenuInfo();
                        }
                    } catch (CheckoutNumberLimitException e) {
                        System.out.println(e.getMessage());
                        displayMenuInfo();
                    }
                } else {
                    System.out.println("The model number is not the inventory");
                }
            }
        }
    }

    private static void displayCheckIns() {
        System.out.println("---------------------------------");
        System.out.println("Brand | Model || Color | Quantity | Price");
        System.out.println("---------------------------------");
        for (int i = 0; i < checkIns.size(); i++) {
            Mobile mobile = checkIns.get(i).mobile;
            System.out.println(mobile.getBrand() + " | " + mobile.getModelNumber() + " | " +
                    mobile.getColor() + " | " + mobile.getQuantity() + " | " + mobile.getPrice());
        }
    }

    private static void checkIn() {
        Scanner input = new Scanner(System.in);
         System.out.println("Enter phone brand");
        String brand = input.nextLine();
        
        System.out.println("Enter phone model:");
        String model = input.nextLine();

        System.out.println("Enter phone color:");
        String color = input.nextLine();

        System.out.println("Enter phone quantity");
        int quantity = input.nextInt();

        System.out.println("Enter phone price:");
        double price = input.nextDouble();

        //create new mobile object
        Mobile mobile = new Mobile(brand, model, color, quantity, price);

        //add mobile to checkins
        checkIns.add(new CheckIn(mobile));

        System.out.println("Mobile check in successful!");

        displayMenuInfo();
    }

    private static void displayRetailers() {
        System.out.println("---------------------------------");
        System.out.println("Retailer Name | Retailer Address");
        System.out.println("---------------------------------");
        for (int i = 0; i < retailers.size(); i++) {
            Retailer retailer = retailers.get(i);
            System.out.println(retailer.getFullName() + " | " + retailer.getAddress());
        }

        //display menu
        displayMenuInfo();
    }

    private static void addRetailer() {
        //scanner
        Scanner input = new Scanner(System.in);

        //ask for retailer name
        System.out.println("Enter retailer FullName");

        //user enter fullName
        String name = input.nextLine();

        System.out.println("Enter retailer address");
        String address = input.nextLine();

        // new retailer with given name and address
        Retailer newRetailer = new Retailer(name, address);

        //add that retailer to array
        retailers.add(newRetailer); // [Retailer(name, address)]

        System.out.println("New Retailer added!");

        //display Menu
        displayMenuInfo();
    }

    //Classes -> Retailer, Mobile, CheckIn, Checkout
    public static class Retailer {
        private String fullName, address;

        Retailer(String fullName, String address) {
            this.fullName = fullName;
            this.address = address;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class Mobile {
        private String brand, model, color;
        private int quantity;
        private double price;

        //constructor
        public Mobile(String brand, String model, String color, int quantity, double price) {
            this.brand = brand;
            this.model = model;
            this.color = color;
            this.quantity = quantity;
            this.price = price;
        }

        public String getModelNumber() {
            return model;
        }

        public void setModelNumber(String modelNumber) {
            this.model = modelNumber;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public static class CheckIn {
        Mobile mobile;

        public CheckIn(Mobile mobile) {
            this.mobile = mobile;
        }

        public Mobile getMobile() {
            return mobile;
        }

        public void setMobile(Mobile mobile) {
            this.mobile = mobile;
        }
    }

    public static class CheckOut {
        Mobile mobile;

        public CheckOut(Mobile mobile) {
            this.mobile = mobile;
        }

        public Mobile getMobile() {
            return mobile;
        }

        public void setMobile(Mobile mobile) {
            this.mobile = mobile;
        }

//        public Retailer getRetailer() {
//            return retailer;
//        }
//
//        public void setRetailer(Retailer retailer) {
//            this.retailer = retailer;
//        }
    }

    static class CheckoutNumberLimitException extends Exception {
        public CheckoutNumberLimitException(String message) {
            super(message);
        }
    }
}

// signature of function -> return type function name(arguments, parameters) { block of code }
//    void printSomething(){
//        System.out.println("I am a function that is called");
//    }
//
//    // return type  -> int
//    // function name -> add
//    // parameters -> a, b type int
//    // return add
//    int add(int a, int b){
//        return a + b;
//    }

//   // -> "AS102"
//        //arr = ["SS102", "CG", "Realme 10"]
//        //arr[0] -> SS102
//        //arr[1] -> CG
//        //arr[2] -> realme 10 
