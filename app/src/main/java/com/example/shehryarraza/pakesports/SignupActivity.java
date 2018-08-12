package com.example.shehryarraza.pakesports;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    private Button button2;
    private EditText editText;
    private EditText DOB;
    private EditText state;
    private EditText password;
    private EditText confirm_password;
    private SearchableSpinner spinner;
    EditText textview_countries;
    boolean flag = false;        // Here

    private String[] countries_list={"Afghanistan","Albania","Algeria","Andorra","Angola","Anguilla","Antigua & Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan",
                                     "Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia & Herzegovina","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi",
                                     "Cambodia","Cameroon","Cape Verde","Cayman Islands","Chad","Chile","China","Colombia","Congo","Cook Islands","Costa Rica","Cote D Ivoire","Croatia","Cruise Ship","Cuba","Cyprus","Czech Republic",
                                     "Denmark","Djibouti","Dominica","Dominican Republic",
                                     "Ecuador","Egypt","El Salvador","Equatorial Guinea","Estonia","Ethiopia",
                                     "Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","French West Indies",
                                     "Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guernsey","Guinea","Guinea Bissau","Guyana",
                                     "Haiti","Honduras","Hong Kong","Hungary",
                                     "Iceland","India","Indonesia","Iran","Iraq","Ireland","Isle of Man","Israel","Italy",
                                     "Jamaica","Japan","Jersey","Jordan",
                                     "Kazakhstan","Kenya","Kuwait","Kyrgyz Republic",
                                     "Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg",
                                     "Macau","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Mauritania","Mauritius","Mexico","Moldova","Monaco","Mongolia","Montenegro","Montserrat","Morocco","Mozambique",
                                     "Namibia","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman",
                                     "Pakistan","Palestine","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Puerto Rico",
                                     "Qatar",
                                     "Reunion","Romania","Russia","Rwanda",
                                     "Saint Pierre & Miquelon","Samoa","San Marino","Satellite","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","South Africa","South Korea","Spain","Sri Lanka","St Kitts & Nevis","St Lucia","St Vincent","St. Lucia","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria",
                                     "Taiwan","Tajikistan","Tanzania","Thailand","Timor L'Este","Togo","Tonga","Trinidad & Tobago","Tunisia","Turkey","Turkmenistan","Turks & Caicos",
                                     "Uganda","Ukraine","United Arab Emirates","United Kingdom","Uruguay","Uzbekistan",
                                     "Venezuela","Vietnam","Virgin Islands (US)",
                                     "Yemen",
                                     "Zambia","Zimbabwe",""};


    final int listsize = countries_list.length - 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_signup);

        editText =  findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirmpassword);
        DOB = findViewById(R.id.DOB);
        spinner = findViewById(R.id.spinner);
        textview_countries= findViewById(R.id.txtview_countries);
        textview_countries.setInputType(InputType.TYPE_NULL);



        final CustomAdapter<String> spinner_countries = new CustomAdapter<String>(SignupActivity.this,android.R.layout.simple_spinner_item, countries_list){
            @Override
            public int getCount() {
                return(listsize); // Truncate the list
            }
        };

        spinner.setTitle("Select Country");
        spinner.setAdapter(spinner_countries);
        spinner.setSelection(listsize); // Hidden item to appear in the spinner



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hovno = spinner.getSelectedItem().toString();
                textview_countries.setText(hovno);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        final DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        DOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub

                Calendar mindate = Calendar.getInstance();

                Calendar maxdate = Calendar.getInstance();

                mindate.set(1970, 0, 1);//Year,Mounth -1,Day
                maxdate.set(2017, 11, 31);//Year,Mounth -1,Day

                datePickerDialog.getDatePicker().setMinDate(mindate.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(maxdate.getTimeInMillis());


                datePickerDialog.show();




            }
        });



    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, MainActivity.class));

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        DOB.setText(df.format(myCalendar.getTime()));
    }


}
