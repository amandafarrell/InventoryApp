package com.example.android.inventory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.inventory.data.InventoryDBHelper;
import com.example.android.inventory.data.InventoryContract.InventoryEntry;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** Database helper that will provide us access to the database */
    private InventoryDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new InventoryDBHelper(this);
        Log.e(LOG_TAG, "mDbHelped initialized " + mDbHelper.getDatabaseName());

        //Insert the dummy data
        long rowId = insertData();
        Log.e(LOG_TAG,"dummy data inserted at rowId " + rowId);

        //query the data
        Cursor cursor = queryData();

        //See what's inside the cursor
        int productNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int productQuantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);

        try {
            while (cursor.moveToNext()){
                Log.e(LOG_TAG, "product name and quantity: "
                        + cursor.getString(productNameColumnIndex) + ", "
                        + cursor.getInt(productQuantityColumnIndex));
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Insert dummy data
    private long insertData(){
        //Get the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and the product's attributes are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Hula hoop");
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, 200);
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, 0);
        values.put(InventoryEntry.COLUMN_SUPPLIER_NAME, "Superhooper");
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE, "714.299.8035");

        // Insert a new row for the product in the database, returning the ID of that new row.
        // The first argument for db.insert() is the inventory table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for the product.
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    //Cursor returns all columns for all entries in the inventory table
    private Cursor queryData(){
        /**
         * Query the database.
         * Always close the cursor when you're done reading from it.
         * This releases all its resources and makes it invalid.
         */

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryEntry.COLUMN_SUPPLIER_PHONE};

        //Performs a query on the inventory table
        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,  // The table to query
                projection,                 // The columns to return
                null,               // The columns for the WHERE clause
                null,           // The values for the WHERE clause
                null,               // Don't group the rows
                null,               // Don't filter by row groups
                null);              // The sort order

        return cursor;
    }
}