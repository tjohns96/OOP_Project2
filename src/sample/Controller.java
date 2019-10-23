package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private ComboBox typeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeComboBox.getItems().addAll(
                "Audio", "Visual", "AudioMobile", "VisualMobile");
    }
}
