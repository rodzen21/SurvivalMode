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
import com.oopclass.breadapp.models.Order;
import com.oopclass.breadapp.services.impl.OrderService;
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
public class OrderController implements Initializable {

    @FXML
    private Label orderId;

    @FXML
    private TextField productName;

    @FXML
    private TextField quantity;

    @FXML
    private DatePicker doo;
    
    @FXML
    private DatePicker dod;

    @FXML
    private Button reset;

    @FXML
    private Button backOrder;
    
    @FXML
    private Button saveOrder;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Long> colOrderId;

    @FXML
    private TableColumn<Order, String> colProductName;

    @FXML
    private TableColumn<Order, String> colQuantity;

    @FXML
    private TableColumn<Order, LocalDate> colDOO;
    
    @FXML
    private TableColumn<Order, LocalDate> colDOD;

    @FXML
    private TableColumn<Order, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private OrderService OrderService;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();

//    @FXML
//    private void exit(ActionEvent event) {
//        Platform.exit();
//    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void backOrder(ActionEvent event) throws IOException {
    	stageManager.switchScene(FxmlView.FRONTPAGE);
    }
    
    @FXML
    private void saveOrder(ActionEvent event) {

       // if (validate("Product Name", getProductName(), "([a-zA-Z]{3,30}\\s*)+")
          //      && validate("Quantity", getQuantity(), "([a-zA-Z]{3,30}\\s*)+")
           //     && emptyValidation("DOB", doo.getEditor().getText().isEmpty()))
            //    && emptyValidation("DOB", dod.getEditor().getText().isEmpty()))
            {

            if (orderId.getText() == null || "".equals(orderId.getText())) {
                if (true) {

                    Order order = new Order();
                    order.setProductName(getProductName());
                    order.setQuantity(getQuantity());
                    order.setDoo(getDoo());
                    order.setDod(getDod());

                    Order newOrder = OrderService.save(order);

                    saveAlert(newOrder);
                }

            } else {
                Order order = OrderService.find(Long.parseLong(orderId.getText()));
                order.setProductName(getProductName());
                order.setQuantity(getQuantity());
                order.setDoo(getDoo());
                order.setDod(getDod());
                Order updatedOrder = OrderService.update(order);
                updateAlert(updatedOrder);
            }

            clearFields();
            loadOrderDetails();
        }

    }

    @FXML
    private void deleteOrder(ActionEvent event) {
        List<Order> orders = orderTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            OrderService.deleteInBatch(orders);
        }

        loadOrderDetails();
    }

    private void clearFields() {
        orderId.setText(null);
        productName.clear();
        quantity.clear();
        doo.getEditor().clear();
        dod.getEditor().clear();
    }

    private void saveAlert(Order order) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The order " + order.getProductName() + " " + order.getQuantity() + " has been created and \n" + " id is " + order.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(Order order) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The order " + order.getProductName() + " " + order.getQuantity() + " has been updated.");
        alert.showAndWait();
    }


    public String getProductName() {
        return productName.getText();
    }

    public Integer getQuantity() {
        return Integer.parseInt( quantity.getText());
    }

    public LocalDate getDoo() {
        return doo.getValue();
    }

    public LocalDate getDod() {
        return dod.getValue();
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

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDOO.setCellValueFactory(new PropertyValueFactory<>("doo"));
        colDOD.setCellValueFactory(new PropertyValueFactory<>("dod"));
        colEdit.setCellFactory(cellFactory);
    }

    Callback<TableColumn<Order, Boolean>, TableCell<Order, Boolean>> cellFactory
            = new Callback<TableColumn<Order, Boolean>, TableCell<Order, Boolean>>() {
        @Override
        public TableCell<Order, Boolean> call(final TableColumn<Order, Boolean> param) {
            final TableCell<Order, Boolean> cell = new TableCell<Order, Boolean>() {
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
                            Order order = getTableView().getItems().get(getIndex());
                            updateOrder(order);
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

                private void updateOrder(Order order) {
                    orderId.setText(Long.toString(order.getId()));
                    productName.setText(order.getProductName());
                    quantity.setText (Integer.toString(order.getQuantity()));
                    doo.setValue(order.getDoo());
                    dod.setValue(order.getDod());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All users to observable list and update table
     */
    private void loadOrderDetails() {
        orderList.clear();
        orderList.addAll(OrderService.findAll());

        orderTable.setItems(orderList);
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

        orderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all users into table
        loadOrderDetails();
    }
}
