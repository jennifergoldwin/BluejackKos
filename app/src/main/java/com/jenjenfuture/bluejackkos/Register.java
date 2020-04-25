package com.jenjenfuture.bluejackkos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Register extends AppCompatActivity {

    private TextView tvsignin;

    private EditText dof;
    private EditText phoneNum;
    private EditText username;
    private EditText password;
    private EditText confirmpass;

    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;

    private CheckBox checkBox;

    private Button register;

    private Calendar calendar;
    private SimpleDateFormat dateFormatter;

    private DBUser dbUser;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        dbUser = DBUser.getInstance(this);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        tvsignin = findViewById(R.id.signintxt);

        dof = findViewById(R.id.dof);
        phoneNum = findViewById(R.id.phonenum);
        username = findViewById(R.id.username);
        password = findViewById(R.id.pswrd);
        confirmpass = findViewById(R.id.confrimpass);


        register = findViewById(R.id.registerbtn);
        checkBox = findViewById(R.id.checkterm);

        gender = findViewById(R.id.radiogroup);
        female = findViewById(R.id.genderfemale);
        male = findViewById(R.id.gendermale);

        dof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,month,dayOfMonth);
                        dof.setText(dateFormatter.format(newDate.getTime()));
                        dof.setError(null);
                    }
                },year,month,day);

                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();


            }
        });
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();

            }
        });
    }

    private void checkValidate() {

        boolean isValid = true;

        if(isEmpty(dof)){
            dof.setError("This input must be filled");
            isValid = false;
        }


        if (gender.getCheckedRadioButtonId()==-1){
            female.setError("Select gender");
            isValid = false;
        }
        else{
            female.setError(null);
        }

        if (!checkBox.isChecked())
        {
            checkBox.setError("Must checked");
            isValid = false;
        }
        else
        {
            checkBox.setError(null);
        }

        if (!username.getText().toString().matches("^(?=.*\\d)(?=.*[a-zA-Z]).{3,25}$")) {
            username.setError("Username must between 3 and 25 characters,1 digit and alphabetic");
            isValid = false;

        }
        else{
            username.setError(null);
        }

        String pass="";
        if (!password.getText().toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$") ) {
            password.setError("Password must contain at least 1 lowercase letter, 1 uppercase letter,1 digit and more than 6 character");
            isValid = false;
        }
        else {
            password.setError(null);
            pass = password.getText().toString();
        }

        boolean flag = true;
        int len = phoneNum.getText().toString().length();
        if (len>6){
            for (int i =0 ; i<len ; i++){
                if (!(phoneNum.getText().toString().charAt(i)>='0' && phoneNum.getText().toString().charAt(i)<='9')){
                    flag = false;
                }
            }
            if (flag){
                phoneNum.setError(null);
            }
            else{
                phoneNum.setError("Phone number must contains digit only");
                isValid = false;
            }

        }
        else {
            phoneNum.setError("Phone number must between 10 and 12 character");
            isValid = false;

        }

        if (!confirmpass.getText().toString().matches(pass)){
            confirmpass.setError("Must same with password");
            isValid = false;
        }
        else {
            confirmpass.setError(null);
        }

        if (isValid){
            String cekUser = username.getText().toString().trim();

            boolean validUser= true;

            if (dbUser.checkUserValid(cekUser)) {
                username.setError("Username already exists");
                validUser = false;
            }

            if(validUser){

                String userId = "";

                int sz = dbUser.getAllUser().size();

                if (sz<10){
                    userId = "US00" + sz;
                }
                else if (sz>=10 && sz<=99){
                    userId = "US0" + sz;
                }
                else{
                    userId = "US" + sz;
                }

                Log.d("123123",userId);

                String us = username.getText().toString();
                String passwrd =  password.getText().toString();
                String phone = phoneNum.getText().toString();
                String birth = dof.getText().toString();

                int genId = gender.getCheckedRadioButtonId();
                RadioButton sexgender = findViewById(genId);
                String sex = sexgender.getText().toString();

                User user = new User();

                user.setUserId(userId);
                user.setUserName(us);
                user.setPassword(passwrd);
                user.setPhoneNum(phone);
                user.setDof(birth);
                user.setGender(sex);

                dbUser.insertUser(user);

                Toast toast = Toast.makeText(getApplicationContext(),"Register Successful",Toast.LENGTH_SHORT);
                toast.show();
                this.finish();
            }
        }

    }


    boolean isEmpty(EditText text){
        String checktext = text.getText().toString();
        return TextUtils.isEmpty(checktext);
    }
}
