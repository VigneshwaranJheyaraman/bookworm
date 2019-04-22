package in.co.forstudents.bookworm.bookworm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupactivityActivity extends AppCompatActivity {
EditText newusername,newuserpass,newuserrepass,newusermobile;
Button singup,cancel,backtologin;
RelativeLayout signuplayout;
FirebaseDatabase bwdb;
TextView warning;
DatabaseReference bwref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        getdifferentIntent();
        initviews();
        buttonlisteners();
    }

    private void getdifferentIntent() {
        Intent fromlogin = getIntent();
        Intent fromsplash = getIntent();

    }

    private void buttonlisteners() {
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "bwuid223"+":"+newusermobile.getText().toString();
                if (isValidPassword(newuserpass.getText().toString().trim())) {
                        if(newuserpass.getText().toString().equals(newuserrepass.getText().toString())){
                    bwref.child(username).child("username").setValue(newusername.getText().toString());
                    bwref.child(username).child("password").setValue(newuserpass.getText().toString());
                            bwref.child("bwuid223"+":"+newusermobile.getText().toString()).child("mobile").setValue(newusermobile.getText().toString());
                            Toast.makeText(SignupactivityActivity.this,"Welcome to BOOKWORM",Toast.LENGTH_LONG).show();
                            SharedPreferences pref = getSharedPreferences("SignupActPREF", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edt = pref.edit();
                            edt.putBoolean("activity_executed", true);
                            edt.putString("useridas",username);
                            edt.commit();
                            Intent tobookdb = new Intent(SignupactivityActivity.this,BookDatabaseActivity.class);
                        tobookdb.putExtra("userid",username);
                        tobookdb.putExtra("username",newusername.getText().toString());
                        startActivity(tobookdb);
                        finish();
                        }
                    else
                        {
                            warning.setText("Passwords don't match with each other");
                        }
                } else {
                    Toast.makeText(SignupactivityActivity.this, "Invalid Password, It must include atleast ONE CAPITAL LETTER,SPECIAL CHARACTER and DIGIT", Toast.LENGTH_LONG).show();
            }
        }});
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(SignupactivityActivity.this)
                        .setIcon(R.drawable.ic_close)
                        .setTitle("CANCEL")
                        .setMessage("Do you want to cancel??")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(SignupactivityActivity.this)
                        .setIcon(R.drawable.ic_loginback)
                        .setTitle("Login")
                        .setMessage("Are you an existing user?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent backtologinpage = new Intent(SignupactivityActivity.this,LoginActivity.class);
                                startActivity(backtologinpage);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void initviews() {
        newusername = (EditText) findViewById(R.id.signupname);
        newuserpass = (EditText) findViewById(R.id.signuppwd);
        newuserrepass = (EditText) findViewById(R.id.singuprepwd);
        newuserpass.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newuserrepass.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newusermobile = (EditText) findViewById(R.id.signupmobile);
        singup = (Button) findViewById(R.id.signupbutton);
        cancel = (Button) findViewById(R.id.cancelsignup);
        backtologin = (Button) findViewById(R.id.backtologin);
        warning = (TextView) findViewById(R.id.pwdincorrect);
        bwdb = FirebaseDatabase.getInstance();
        bwref = bwdb.getReference("usersofbw");
        signuplayout = (RelativeLayout) findViewById(R.id.signuplayou);
    }
}


