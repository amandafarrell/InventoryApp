package com.example.android.inventory.data;

import android.provider.BaseColumns;

public class InventoryContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InventoryContract() {}

    /**
     * Inner class that defines constant values for the inventory database table.
     * Each entry in the table represents a single product.
     */
    public static final class InventoryEntry implements BaseColumns {

        /** Name of database table for inventory */
        public final static String TABLE_NAME = "inventory";

        /**
         * Unique ID number for the product (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the product.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME ="product_name";

        /**
         * Price of the product.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_PRICE = "product_price";

        /**
         * Weight of the product.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PRODUCT_WEIGHT = "product_weight";

        /**
         * Name of the supplier.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME ="supplier_name";

        /**
         * Phone number of the supplier.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";
    }
}
