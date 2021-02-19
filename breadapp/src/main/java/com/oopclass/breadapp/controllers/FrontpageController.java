
package com.oopclass.breadapp.controllers;


import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.views.FxmlView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

/**
 * FXML Controller class
 *
 * @author Arnel Alvarez
 */
@Controller
public class FrontpageController implements Initializable {

    @FXML
    private Button openOrder;
    
    @FXML
    private Button openReceipt;
    
     @FXML
    private Button openInventory;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    
    @FXML
    private void openOrder(ActionEvent event) throws IOException {
    	stageManager.switchScene(FxmlView.ORDER);
    }
    
    @FXML
    private void openReceipt(ActionEvent event) throws IOException {
    	stageManager.switchScene(FxmlView.RECEIPT);
    }
    
    @FXML
    private void openInventory(ActionEvent event) throws IOException {
    	stageManager.switchScene(FxmlView.INVENTORY);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
