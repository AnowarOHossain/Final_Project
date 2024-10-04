package final_project;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField heightField;
    @FXML
    private TextField weightField;
    @FXML
    private Label bmiValueLabel;
    @FXML
    private Label bmiCategoryValueLabel;
    @FXML
    private Label healthTipsLabel;
    @FXML
    private TextField ageField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private ListView<String> bmiHistoryList;
    @FXML
    private TextField goalField;
    @FXML
    private Label goalResultLabel;

    private ObservableList<String> bmiHistory;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Others"));
        bmiHistory = FXCollections.observableArrayList();
        bmiHistoryList.setItems(bmiHistory); 
    }

    @FXML
    private void calculateBMI(ActionEvent event) {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            int age = Integer.parseInt(ageField.getText());
            String gender = genderComboBox.getValue();

            double bmi = weight / (height * height);

            bmiValueLabel.setText(String.format("%.2f", bmi));

            String category = getBMICategory(bmi);
            bmiCategoryValueLabel.setText(category);

            String historyEntry = "BMI: " + String.format("%.2f", bmi) + " (" + category + ")";
            bmiHistory.add(historyEntry);

            healthTipsLabel.setText(getHealthTips(bmi, age, gender));

        } catch (NumberFormatException e) {
            bmiValueLabel.setText("Invalid input");
            bmiCategoryValueLabel.setText("");
            healthTipsLabel.setText("");
        }
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

    private String getHealthTips(double bmi, int age, String gender) {
        List<String> tips = new ArrayList<>();

        if (bmi < 18.5) {
            tips.add("Consider a balanced diet rich in proteins and healthy fats.");
        } else if (bmi >= 25) {
            tips.add("Incorporate regular physical activity like walking or running.");
        } else {
            tips.add("Maintain a healthy lifestyle with a balanced diet and regular exercise.");
        }

        if (age > 40) {
            tips.add("Regular health checkups are recommended.");
        }

        if ("Female".equals(gender)) {
            tips.add("Consider calcium-rich foods to support bone health.");
        } else if ("Male".equals(gender)) {
            tips.add("Consider strength training to maintain muscle mass.");
        }

        return String.join(" ", tips);
    }

    @FXML
    private void setGoal(ActionEvent event) {
        try {
            double goalBMI = Double.parseDouble(goalField.getText());
            String goalMessage;

            if (goalBMI >= 18.5 && goalBMI <= 24.9) {
                goalMessage = "Great goal! Aim for a balanced and healthy lifestyle.";
            } else {
                goalMessage = "Set a realistic goal. Aim for a BMI between 18.5 and 24.9.";
            }

            goalResultLabel.setText(goalMessage);
        } catch (NumberFormatException e) {
            goalResultLabel.setText("Invalid input. Please enter a valid BMI goal.");
        }
    }
}
