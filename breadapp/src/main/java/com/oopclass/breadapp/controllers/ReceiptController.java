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
import com.oopclass.breadapp.models.Receipt;
import com.oopclass.breadapp.services.impl.ReceiptService;
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
public class ReceiptController implements Initializable {

    @FXML
    private Label receiptId;

    @FXML
    private TextField productName;

    @FXML
    private TextField price;

    @FXML
    private DatePicker dop;

    @FXML
    private Button reset;
    
     @FXML
    private Button backReceipt;

    @FXML
    private Button saveReceipt;

    @FXML
    private TableView<Receipt> receiptTable;

    @FXML
    private TableColumn<Receipt, Long> colReceiptId;

    @FXML
    private TableColumn<Receipt, String> colProductName;

    @FXML
    private TableColumn<Receipt, String> colPrice;

    @FXML
    private TableColumn<Receipt, LocalDate> colDOP;
    
    @FXML
    private TableColumn<Receipt, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ReceiptService receiptService;

    private ObservableList<Receipt> receiptList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void backReceipt(ActionEvent event) throws IOException {
    	stageManager.switchScene(FxmlView.FRONTPAGE);
    }

    @FXML
    private void saveReceipt(ActionEvent event) {

       // if (validate("Product Name", getProductName(), "([a-zA-Z]{3,30}\\s*)+")
           //     && validate("Price", getPrice(), "([a-zA-Z]{3,30}\\s*)+")
            //
            //&& emptyValidation("DOP", dop.getEditor().getText().isEmpty())) {
            {
            if (receiptId.getText() == null || "".equals(receiptId.getText())) {
                if (true) {

                    Receipt receipt = new Receipt();
                    receipt.setProductName(getProductName());
                    receipt.setPrice(getPrice());
                    receipt.setDop(getDop());

                    Receipt newReceipt = receiptService.save(receipt);

                    saveAlert(newReceipt);
                }

            } else {
                Receipt receipt = receiptService.find(Long.parseLong(receiptId.getText()));
                receipt.setProductName(getProductName());
                receipt.setPrice(getPrice());
                receipt.setDop(getDop());
                Receipt updatedReceipt = receiptService.update(receipt);
                updateAlert(updatedReceipt);
            }

            clearFields();
            loadReceiptDetails();
        }

    }

    @FXML
    private void deleteReceipt(ActionEvent event) {
        List<Receipt> receipts = receiptTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            receiptService.deleteInBatch(receipts);
        }

        loadReceiptDetails();
    }

    private void clearFields() {
        receiptId.setText(null);
        productName.clear();
        price.clear();
        dop.getEditor().clear();
    }

    private void saveAlert(Receipt receipt) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Receipt saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The receipt " + receipt.getProductName() + " " + receipt.getPrice() + " has been created and \n" + receipt.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Receipt receipt) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Receipt updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The receipt " + receipt.getProductName() + " " + receipt.getPrice() + " has been updated.");
        alert.showAndWait();
    }

    
    

    public String getProductName() {
        return productName.getText();
    }

    public Integer getPrice() {
        return Integer.parseInt( price.getText());
    }

    public LocalDate getDop() {
        return dop.getValue();
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

        colReceiptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDOP.setCellValueFactory(new PropertyValueFactory<>("dop"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Receipt, Boolean>, TableCell<Receipt, Boolean>> cellFactory
            = new Callback<TableColumn<Receipt, Boolean>, TableCell<Receipt, Boolean>>() {
        @Override
        public TableCell<Receipt, Boolean> call(final TableColumn<Receipt, Boolean> param) {
            final TableCell<Receipt, Boolean> cell = new TableCell<Receipt, Boolean>() {
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
                            Receipt receipt = getTableView().getItems().get(getIndex());
                            updateReceipt(receipt);
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

                private void updateReceipt(Receipt receipt) {
                    receiptId.setText(Long.toString(receipt.getId()));
                    productName.setText(receipt.getProductName());
                    price.setText (Integer.toString(receipt.getPrice()));
                    dop.setValue(receipt.getDop());
                    
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadReceiptDetails() {
        receiptList.clear();
        receiptList.addAll(receiptService.findAll());

        receiptTable.setItems(receiptList);
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

        receiptTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadReceiptDetails();
    }
}
