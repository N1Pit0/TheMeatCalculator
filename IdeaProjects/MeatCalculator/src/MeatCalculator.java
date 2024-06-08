import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Main class
public class MeatCalculator {

    // Components
    private JTextField productNameField;
    private JTextField pricePerKgField;
    private JTextField weightField; // Text field for entering weight
    private JTextArea productListTextArea;
    private JLabel averagePriceLabel; // Label to display calculated price per kg
    private Sausage sausage;
    private int indexWeight; // Count how many products have their respective weights assigned

    // Constructor to set up the GUI components and layout
    public MeatCalculator() {
        // Create a new type of sausage
        sausage = new Sausage();

        // Create a new JFrame
        JFrame frame = new JFrame("Meat Calculator");

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels and text fields
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameField = new JTextField(15);

        JLabel pricePerKgLabel = new JLabel("Price per Kg:");
        pricePerKgField = new JTextField(10);

        // Create a button to add products
        JButton addButton = new JButton("Add");

        // Add action listener to the button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSausage();
                updateProductListDisplay();
            }
        });

        // Create a text area to display the list of products
        productListTextArea = new JTextArea(10, 30);
        productListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productListTextArea);

        // Label to display the average price per kg
        averagePriceLabel = new JLabel("Average Price per Kg: $0.00");

        // Create a panel to hold the input fields and button
        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Set the horizontal group
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(productNameLabel)
                                .addComponent(pricePerKgLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(productNameField)
                                .addComponent(pricePerKgField)
                                .addComponent(addButton))
                                .addComponent(deleteButton)
        );

        // Set the vertical group
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(productNameLabel)
                                .addComponent(productNameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(pricePerKgLabel)
                                .addComponent(pricePerKgField))
                        .addComponent(addButton)
                        .addComponent(deleteButton)
        );

        // Create a panel for the product list
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.add(new JLabel("Product List:"), BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for weight input and calculation button
        JPanel weightPanel = new JPanel();
        JLabel weightLabel = new JLabel("Weight:");
        weightField = new JTextField(10);
        JButton calculateButton = new JButton("Calculate");

        // Add action listener to the calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePricePerKg();
            }
        });

        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        weightPanel.add(calculateButton);
        weightPanel.add(averagePriceLabel);

        // Add panels to the frame
        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(listPanel, BorderLayout.CENTER);
        frame.add(weightPanel, BorderLayout.SOUTH);

        // Pack the frame to fit the preferred size of its components
        frame.pack();

        // Set the frame's location relative to null (center on screen)
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to add a product to the sausage
    private void addProduct() {
        String productName = productNameField.getText();
        String pricePerKgText = pricePerKgField.getText();
        boolean validInput = true; // Initialize to true. The flag

        // Check if the product name contains only alphabetic letters
        if (!productName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid product name (letters only).", "Error", JOptionPane.ERROR_MESSAGE);
            validInput = false;
        }

        // Check if the price per Kg is a valid number
        double pricePerKg = 0;
        try {
            pricePerKg = Double.parseDouble(pricePerKgText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number for price per Kg.", "Error", JOptionPane.ERROR_MESSAGE);
            validInput = false;
        }

        // If both inputs are valid, add the product to the list and update the display
        if (validInput) {
            Product product = new Product(pricePerKg, productName);
            sausage.addProduct(product);
            updateProductListDisplay();

            // Clear input fields after adding the product
            productNameField.setText("");
            pricePerKgField.setText("");
        }
    }

    // Method to update the product list display
    private void updateProductListDisplay() {
        StringBuilder productListText = new StringBuilder();
        for (Product product : sausage.getProductList()) {
            productListText.append(product.getProductName())
                    .append(" - $")
                    .append(product.getPricePerKg())
                    .append("/Kg\n");
        }
        productListTextArea.setText(productListText.toString());
    }

    private void deleteSausage() {
        sausage = new Sausage();
        indexWeight = 0;
        averagePriceLabel.setText("Average Price per Kg: $0.00");
    }

    private void calculatePricePerKg() {
        double weight = 0.0;
        List<Product> productList = sausage.getProductList();

        try {
            weight = Double.parseDouble(weightField.getText()); // Get weight from the weight field
            weightField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number for weight.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (indexWeight < productList.size()) {
            productList.get(indexWeight).setWeight(weight);
            indexWeight++;
        } else {
            JOptionPane.showMessageDialog(null, "There is not enough number of products in the list yet", "Error", JOptionPane.ERROR_MESSAGE);
        }

        double averagePrice = sausage.calculatePrice();
        sausage.setPrice(averagePrice);

        // Display calculated price per kg
        averagePriceLabel.setText(String.format("Average Price per Kg: $%.2f", sausage.getPrice()));
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Use the Event Dispatch Thread to construct the GUI
        SwingUtilities.invokeLater(MeatCalculator::new);
    }
}