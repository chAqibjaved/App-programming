package com.aqib.mymedreminder.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.Calendar;

public class DialogUtils {


    // Callback interface to return the selected date
    public interface DatePickerCallback {
        void onDateSelected(int year, int month, int day);
    }



    // Interface to handle dialog response
    public interface DialogCallback {
        void onYesClicked();
        void onNoClicked();
    }
    // General method to show DatePicker with a callback
    public static void showDatePickerDialog(Context context, DatePickerCallback callback) {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Invoke the callback with selected date
                    callback.onDateSelected(selectedYear, selectedMonth + 1, selectedDay);
                }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    // Method to show a simple dialog with an OK button
    public static void showOkDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog and return to the activity
                        dialog.dismiss();
                    }
                })
                .setCancelable(false) // Prevent dismissing by tapping outside
                .show();
    }



    // Static method to show confirmation dialog
    public static void showConfirmationDialog(Context context, String message, final DialogCallback callback) {
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        // Set "Yes" button and handle user response
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Trigger callback for "Yes"
                if (callback != null) {
                    callback.onYesClicked();
                }
            }
        });

        // Set "No" button and handle user response
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Trigger callback for "No"
                if (callback != null) {
                    callback.onNoClicked();
                }
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        final android.app.AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }

}
