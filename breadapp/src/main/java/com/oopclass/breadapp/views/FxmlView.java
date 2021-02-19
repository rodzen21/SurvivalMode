package com.oopclass.breadapp.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

    RECEIPT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("receipt.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Receipt.fxml";
        }
    },
    
    INVENTORY {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("inventory.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Inventory.fxml";
        }
    },
    
    ORDER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("order.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Order.fxml";
        }
    },
    
    FRONTPAGE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("frontpage.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Frontpage.fxml";
        }
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
