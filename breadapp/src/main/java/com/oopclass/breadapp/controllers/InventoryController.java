package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.models.Inventory;
import com.oopclass.breadapp.models.Receipt;
import com.oopclass.breadapp.services.impl.InventoryService;
import com.oopclass.breadapp.views.FxmlView;
import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class InventoryController implements Initializable {

    @FXML
    private Label inventoryId;

    @FXML
    private TextField itemName;

    @FXML
    private TextField quantity;

    @FXML
    private Button reset;
    
    @FXML
    private Button backInventory;

    @FXML
    private Button  saveInventory;

    @FXML
    private TableView<Inventory> inventoryTable;

    @FXML
    private TableColumn<Inventory, Long> colInventoryId;

    @FXML
    private TableColumn<Inventory, String> colItemName;

    @FXML
    private TableColumn<Inventory, String> colQuantity;
    
    @FXML
    private TableColumn<Inventory, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private InventoryService InventoryService;

    private ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void backInventory(ActionEvent event) throws IOException {
    	stageManager.switchScene(FxmlView.FRONTPAGE);
    }

    @FXML
    private void saveInventory(ActionEvent event) {

       // if (validate("Product Name", getProductName(), "([a-zA-Z]{3,30}\\s*)+")
           //     && validate("Price", getPrice(), "([a-zA-Z]{3,30}\\s*)+")
            //
            //&& emptyValidation("DOP", dop.getEditor().getText().isEmpty())) {
            {
            if (inventoryId.getText() == null || "".equals(inventoryId.getText())) {
                if (true) {

                    Inventory inventory = new Inventory();
                    inventory.setItemName(getItemName());
                    inventory.setQuantity(getQuantity());
                    
                    Inventory newInventory = InventoryService.save(inventory);

                    saveAlert(newInventory);
                }

            } else {
                Inventory inventory = InventoryService.find(Long.parseLong(inventoryId.getText()));
                inventory.setItemName(getItemName());
                inventory.setQuantity(getQuantity());
                Inventory updatedInventory = InventoryService.update(inventory);
                updateAlert(updatedInventory);
            }

            clearFields();
            loadInventoryDetails();
        }

    }

    @FXML
    private void deleteInventory(ActionEvent event) {
        List<Inventory> inventorys = inventoryTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            InventoryService.deleteInBatch(inventorys);
        }

        loadInventoryDetails();
    }

    private void clearFields() {
        inventoryId.setText(null);
        itemName.clear();
        quantity.clear();
    }

    private void saveAlert(Inventory inventory) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Inventory saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The inventory " + inventory.getItemName() + " " + inventory.getQuantity() + " has been created and \n" + inventory.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Inventory inventory) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Inventory updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The inventory " + inventory.getItemName() + " " + inventory.getQuantity() + " has been updated.");
        alert.showAndWait();
    }

    
    

    public String getItemName() {
        return itemName.getText();
    }

    public Integer getQuantity() {
        return Integer.parseInt( quantity.getText());
    }

    /*
	 *  Set All userTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Inventory, Boolean>, TableCell<Inventory, Boolean>> cellFactory
            = new Callback<TableColumn<Inventory, Boolean>, TableCell<Inventory, Boolean>>() {
        @Override
        public TableCell<Inventory, Boolean> call(final TableColumn<Inventory, Boolean> param) {
            final TableCell<Inventory, Boolean> cell = new TableCell<Inventory, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            Inventory inventory = getTableView().getItems().get(getIndex());
                            updateInventory(inventory);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateInventory(Inventory inventory) {
                    inventoryId.setText(Long.toString(inventory.getId()));
                    itemName.setText(inventory.getItemName());
                    quantity.setText (Integer.toString(inventory.getQuantity()));
                    
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadInventoryDetails() {
        inventoryList.clear();
        inventoryList.addAll(InventoryService.findAll());

        inventoryTable.setItems(inventoryList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inventoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadInventoryDetails();
    }
}
