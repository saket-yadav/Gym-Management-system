public class PremiumMember extends GymMember {
    // Attributes
    private final double premiumCharge;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    // Constructor
    public PremiumMember(int id, String name, String location, String phone,
                        String email, String gender, String DOB, String membershipStartDate,
                        String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.premiumCharge = 50000;
        this.personalTrainer = personalTrainer;
        this.isFullPayment = false;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }

    // Accessor methods
    public double getPremiumCharge() {
        return premiumCharge;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public boolean isFullPayment() {
        return isFullPayment;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
        @Override
    public void markAttendance() {
        attendance++;
        loyaltyPoints += 5;
    }

    // Method to pay due amount
    public String payDueAmount(double payment) {
        if (isFullPayment) {
            return "Payment already completed. No further payments required.";
        }

        if (payment <= 0) {
            return "Invalid payment amount. Amount must be positive.";
        }

        if (paidAmount + payment > premiumCharge) {
            return "Payment exceeds the premium charge. Maximum acceptable amount: " + (premiumCharge - paidAmount);
        }

        paidAmount += payment;
        
        // Check if payment is now complete
        if (paidAmount == premiumCharge) {
            isFullPayment = true;
            calculateDiscount(); // Calculate discount if payment is complete
            return "Payment completed successfully. Thank you for your full payment!";
        } else {
            double remainingAmount = premiumCharge - paidAmount;
            return "Payment of " + payment + " received. Remaining amount: " + remainingAmount;
        }
    }

    // Method to calculate discount
    public void calculateDiscount() {
        if (isFullPayment) {
            discountAmount = premiumCharge * 0.10;
            System.out.println("Discount calculated: " + discountAmount + " (10% of premium charge)");
        } else {
            discountAmount = 0;
            System.out.println("No discount available. Complete your payment to get 10% discount.");
        }
    }

    // Method to revert premium member
    public void revertPremiumMember() {
        super.resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.paidAmount = 0;
        this.discountAmount = 0;
    }

    // Display method
    @Override
    public void display() {
        super.display();
        System.out.println("Premium Charge: " + premiumCharge);
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Full Payment Status: " + (isFullPayment ? "Completed" : "Pending"));
        
        double remainingAmount = premiumCharge - paidAmount;
        System.out.println("Remaining Amount to Pay: " + remainingAmount);
        
        if (isFullPayment) {
            System.out.println("Discount Amount: " + discountAmount);
        }
    }
}