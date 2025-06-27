import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GymGUI {
    private ArrayList<GymMember> members = new ArrayList<>();
    private JFrame frame;
    private JTextField idField, nameField, locationField, phoneField, emailField, dobField, 
                      membershipDateField, referralField, paidAmountField, removalReasonField, 
                      trainerField, regularPriceField, premiumChargeField, discountField;
    private JRadioButton maleRadio, femaleRadio, otherRadio;
    private JComboBox<String> planComboBox;
    private ButtonGroup genderGroup;

    public GymGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Gym Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components to input panel
        inputPanel.add(new JLabel("Member ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        inputPanel.add(locationField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);
        inputPanel.add(genderPanel);

        inputPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobField = new JTextField();
        inputPanel.add(dobField);

        inputPanel.add(new JLabel("Membership Start Date (YYYY-MM-DD):"));
        membershipDateField = new JTextField();
        inputPanel.add(membershipDateField);

        inputPanel.add(new JLabel("Referral Source (Regular Only):"));
        referralField = new JTextField();
        inputPanel.add(referralField);

        inputPanel.add(new JLabel("Personal Trainer (Premium Only):"));
        trainerField = new JTextField();
        inputPanel.add(trainerField);

        inputPanel.add(new JLabel("Plan (Regular Only):"));
        planComboBox = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});
        inputPanel.add(planComboBox);

        inputPanel.add(new JLabel("Paid Amount (Premium Only):"));
        paidAmountField = new JTextField();
        inputPanel.add(paidAmountField);

        inputPanel.add(new JLabel("Removal Reason:"));
        removalReasonField = new JTextField();
        inputPanel.add(removalReasonField);

        inputPanel.add(new JLabel("Regular Plan Price:"));
        regularPriceField = new JTextField("6500");
        regularPriceField.setEditable(false);
        inputPanel.add(regularPriceField);

        inputPanel.add(new JLabel("Premium Charge:"));
        premiumChargeField = new JTextField("50000");
        premiumChargeField.setEditable(false);
        inputPanel.add(premiumChargeField);

        inputPanel.add(new JLabel("Discount Amount:"));
        discountField = new JTextField("0");
        discountField.setEditable(false);
        inputPanel.add(discountField);

        // Create button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 0, 5, 5));
        
        JButton addRegularBtn = new JButton("Add Regular Member");
        addRegularBtn.addActionListener(e -> addRegularMember());
        buttonPanel.add(addRegularBtn);

        JButton addPremiumBtn = new JButton("Add Premium Member");
        addPremiumBtn.addActionListener(e -> addPremiumMember());
        buttonPanel.add(addPremiumBtn);

        JButton activateBtn = new JButton("Activate Membership");
        activateBtn.addActionListener(e -> activateMembership());
        buttonPanel.add(activateBtn);

        JButton deactivateBtn = new JButton("Deactivate Membership");
        deactivateBtn.addActionListener(e -> deactivateMembership());
        buttonPanel.add(deactivateBtn);

        JButton markAttendanceBtn = new JButton("Mark Attendance");
        markAttendanceBtn.addActionListener(e -> markAttendance());
        buttonPanel.add(markAttendanceBtn);

        JButton upgradePlanBtn = new JButton("Upgrade Plan");
        upgradePlanBtn.addActionListener(e -> upgradePlan());
        buttonPanel.add(upgradePlanBtn);

        JButton calculateDiscountBtn = new JButton("Calculate Discount");
        calculateDiscountBtn.addActionListener(e -> calculateDiscount());
        buttonPanel.add(calculateDiscountBtn);

        JButton revertRegularBtn = new JButton("Revert Regular Member");
        revertRegularBtn.addActionListener(e -> revertRegularMember());
        buttonPanel.add(revertRegularBtn);

        JButton revertPremiumBtn = new JButton("Revert Premium Member");
        revertPremiumBtn.addActionListener(e -> revertPremiumMember());
        buttonPanel.add(revertPremiumBtn);

        JButton payDueBtn = new JButton("Pay Due Amount");
        payDueBtn.addActionListener(e -> payDueAmount());
        buttonPanel.add(payDueBtn);

        JButton displayBtn = new JButton("Display All Members");
        displayBtn.addActionListener(e -> displayMembers());
        buttonPanel.add(displayBtn);

        JButton clearBtn = new JButton("Clear Fields");
        clearBtn.addActionListener(e -> clearFields());
        buttonPanel.add(clearBtn);

        JButton saveBtn = new JButton("Save to File");
        saveBtn.addActionListener(e -> saveToFile());
        buttonPanel.add(saveBtn);

        JButton readBtn = new JButton("Read from File");
        readBtn.addActionListener(e -> readFromFile());
        buttonPanel.add(readBtn);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addRegularMember() {
        try {
            int id = Integer.parseInt(idField.getText());
            
            // Check for duplicate ID
            if (isDuplicateId(id)) {
                JOptionPane.showMessageDialog(frame, "Member ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String name = nameField.getText();
            String location = locationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            
            String gender = "";
            if (maleRadio.isSelected()) gender = "Male";
            else if (femaleRadio.isSelected()) gender = "Female";
            else if (otherRadio.isSelected()) gender = "Other";
            
            String dob = dobField.getText();
            String membershipDate = membershipDateField.getText();
            String referralSource = referralField.getText();
            
            // Validate required fields
            if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || email.isEmpty() || 
                gender.isEmpty() || dob.isEmpty() || membershipDate.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            RegularMember member = new RegularMember(id, name, location, phone, email, gender, 
                                                  dob, membershipDate, referralSource);
            members.add(member);
            
            JOptionPane.showMessageDialog(frame, "Regular Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPremiumMember() {
        try {
            int id = Integer.parseInt(idField.getText());
            
            // Check for duplicate ID
            if (isDuplicateId(id)) {
                JOptionPane.showMessageDialog(frame, "Member ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String name = nameField.getText();
            String location = locationField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            
            String gender = "";
            if (maleRadio.isSelected()) gender = "Male";
            else if (femaleRadio.isSelected()) gender = "Female";
            else if (otherRadio.isSelected()) gender = "Other";
            
            String dob = dobField.getText();
            String membershipDate = membershipDateField.getText();
            String personalTrainer = trainerField.getText();
            
            // Validate required fields
            if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || email.isEmpty() || 
                gender.isEmpty() || dob.isEmpty() || membershipDate.isEmpty() || personalTrainer.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            PremiumMember member = new PremiumMember(id, name, location, phone, email, gender, 
                                                    dob, membershipDate, personalTrainer);
            members.add(member);
            
            JOptionPane.showMessageDialog(frame, "Premium Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isDuplicateId(int id) {
        for (GymMember member : members) {
            if (member.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private void activateMembership() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMemberById(id);
            
            if (member != null) {
                member.activateMembership();
                JOptionPane.showMessageDialog(frame, "Membership activated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deactivateMembership() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMemberById(id);
            
            if (member != null) {
                member.deactivateMembership();
                JOptionPane.showMessageDialog(frame, "Membership deactivated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markAttendance() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMemberById(id);
            
            if (member != null) {
                if (member.isActiveStatus()) {
                    member.markAttendance();
                    JOptionPane.showMessageDialog(frame, "Attendance marked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Member is not active! Cannot mark attendance.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void upgradePlan() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMemberById(id);
            
            if (member != null && member instanceof RegularMember) {
                if (member.isActiveStatus()) {
                    String selectedPlan = (String) planComboBox.getSelectedItem();
                    RegularMember regularMember = (RegularMember) member;
                    String result = regularMember.upgradePlan(selectedPlan);
                    JOptionPane.showMessageDialog(frame, result, "Plan Upgrade", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Member is not active! Cannot upgrade plan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found or not a Regular Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateDiscount() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMemberById(id);
            
            if (member != null && member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                premiumMember.calculateDiscount();
                discountField.setText(String.valueOf(premiumMember.getDiscountAmount()));
                JOptionPane.showMessageDialog(frame, "Discount calculated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found or not a Premium Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void revertRegularMember() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMemberById(id);
            String removalReason = removalReasonField.getText();
            
            if (member != null && member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;
                regularMember.revertRegularMember(removalReason);
                JOptionPane.showMessageDialog(frame, "Regular Member reverted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found or not a Regular Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void revertPremiumMember() {
        try {
            int id = Integer.parseInt(idField.getText());
            GymMember member = findMemberById(id);
            
            if (member != null && member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                premiumMember.revertPremiumMember();
                JOptionPane.showMessageDialog(frame, "Premium Member reverted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found or not a Premium Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void payDueAmount() {
        try {
            int id = Integer.parseInt(idField.getText());
            double amount = Double.parseDouble(paidAmountField.getText());
            GymMember member = findMemberById(id);
            
            if (member != null && member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                String result = premiumMember.payDueAmount(amount);
                JOptionPane.showMessageDialog(frame, result, "Payment", JOptionPane.INFORMATION_MESSAGE);
                paidAmountField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Member not found or not a Premium Member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid amount format! Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private GymMember findMemberById(int id) {
        for (GymMember member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    private void displayMembers() {
        JFrame displayFrame = new JFrame("All Members");
        displayFrame.setSize(1200, 600);
        
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        // Header
        String header = String.format("%-5s %-15s %-15s %-15s %-25s %-10s %-15s %-10s %-10s %-10s %-15s %-15s %-15s %-15s %-15s\n",
            "ID", "Name", "Type", "Location", "Email", "Gender", "Membership Date", "Plan", "Price", "Attendance", "Active", "Trainer", "Paid", "Discount", "Loyalty");
        
        textArea.append(header);
        textArea.append("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        for (GymMember member : members) {
            if (member instanceof RegularMember) {
                RegularMember rm = (RegularMember) member;
                String line = String.format("%-5d %-15s %-15s %-15s %-25s %-10s %-15s %-10s %-10.2f %-10d %-15s %-15s %-15s %-15s %-15.2f\n",
                    rm.getId(), 
                    rm.getName(), 
                    "Regular",
                    rm.getLocation(),
                    rm.getEmail(),
                    rm.getGender(),
                    rm.getMembershipStartDate(),
                    rm.getPlan(),
                    rm.getPrice(),
                    rm.getAttendance(),
                    rm.isActiveStatus() ? "Active" : "Inactive",
                    "N/A",
                    "N/A",
                    "N/A",
                    rm.getLoyaltyPoints());
                textArea.append(line);
            } else if (member instanceof PremiumMember) {
                PremiumMember pm = (PremiumMember) member;
                String line = String.format("%-5d %-15s %-15s %-15s %-25s %-10s %-15s %-10s %-10.2f %-10d %-15s %-15s %-15.2f %-15.2f %-15.2f\n",
                    pm.getId(), 
                    pm.getName(), 
                    "Premium",
                    pm.getLocation(),
                    pm.getEmail(),
                    pm.getGender(),
                    pm.getMembershipStartDate(),
                    "Premium",
                    pm.getPremiumCharge(),
                    pm.getAttendance(),
                    pm.isActiveStatus() ? "Active" : "Inactive",
                    pm.getPersonalTrainer(),
                    pm.getPaidAmount(),
                    pm.getDiscountAmount(),
                    pm.getLoyaltyPoints());
                textArea.append(line);
            }
        }
        
        displayFrame.add(scrollPane);
        displayFrame.setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");
        genderGroup.clearSelection();
        dobField.setText("");
        membershipDateField.setText("");
        referralField.setText("");
        trainerField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");
        planComboBox.setSelectedIndex(0);
        discountField.setText("0");
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("MemberDetails.txt"))) {
            // Write header
            writer.println(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s",
                "ID", "Name", "Location", "Phone", "Email", "Membership Start Date", "Plan", "Price", "Attendance", 
                "Loyalty Points", "Active Status", "Full Payment", "Discount Amount", "Net Amount Paid"));
            
            // Write member data
            for (GymMember member : members) {
                if (member instanceof RegularMember) {
                    RegularMember rm = (RegularMember) member;
                    writer.println(String.format("%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15.2f %-10s %-15s %-15s %-15s",
                        rm.getId(), rm.getName(), rm.getLocation(), rm.getPhone(), rm.getEmail(),
                        rm.getMembershipStartDate(), rm.getPlan(), rm.getPrice(), rm.getAttendance(),
                        rm.getLoyaltyPoints(), rm.isActiveStatus() ? "Active" : "Inactive",
                        "N/A", "N/A", "N/A"));
                } else if (member instanceof PremiumMember) {
                    PremiumMember pm = (PremiumMember) member;
                    writer.println(String.format("%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15.2f %-10s %-15s %-15.2f %-15.2f",
                        pm.getId(), pm.getName(), pm.getLocation(), pm.getPhone(), pm.getEmail(),
                        pm.getMembershipStartDate(), "Premium", pm.getPremiumCharge(), pm.getAttendance(),
                        pm.getLoyaltyPoints(), pm.isActiveStatus() ? "Active" : "Inactive",
                        pm.isFullPayment() ? "Yes" : "No", pm.getDiscountAmount(), pm.getPaidAmount()));
                }
            }
            
            JOptionPane.showMessageDialog(frame, "Member details saved to file successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"))) {
            JFrame readFrame = new JFrame("Member Details from File");
            readFrame.setSize(1200, 600);
            
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            
            readFrame.add(scrollPane);
            readFrame.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymGUI());
    }
}