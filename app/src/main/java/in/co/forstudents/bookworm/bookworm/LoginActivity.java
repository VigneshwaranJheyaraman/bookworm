package in.co.forstudents.bookworm.bookworm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
EditText loginpwd,mobile;
Button login,cancel,signup;
String[] listid,listphoneofuser,currentuserdb;
ArrayList<String> userlist,userdb;
Integer currentuserpos;
RelativeLayout relativeLayout;
FirebaseDatabase bwdb;
DatabaseReference bwref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        allIntent();
        initviews();//initialize all the layout elements
        listenerofbutton();

    }

    private void allIntent() {
        Intent fromsignup =  getIntent();
    }

    private void listenerofbutton() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<listphoneofuser.length;i++){
                    if(mobile.getText().toString().equals(listphoneofuser[i])){
                       currentuserpos =i;
                                         //      Toast.makeText(LoginActivity.this,"User is available",Toast.LENGTH_LONG).show();
                        //loginpwd.setText(listid[currentuserpos]);
                    }
                }
                if(ValidUserPassword()){
                    String username = "bwuid223:"+mobile.getText().toString();
                    Toast.makeText(LoginActivity.this, "WELCOME HOLA", Toast.LENGTH_LONG).show();
                    SharedPreferences pref = getSharedPreferences("LoginActPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edt = pref.edit();
                    edt.putBoolean("activity_executed", true);
                    edt.putString("useridal",username);
                    edt.commit();
                    Intent tobookdb = new Intent(LoginActivity.this,BookDatabaseActivity.class);
                    tobookdb.putExtra("useridfromlogin",username);
                    startActivity(tobookdb);
                    finish();
                }
             }
        });

        bwref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userlist.add(dataSnapshot.getKey());
                for(int i=0;i<userlist.size();i++) {
                    StringTokenizer tokenizer = new StringTokenizer(userlist.get(i), ":");
                    listid[i] = tokenizer.nextToken();
                    listphoneofuser[i] = tokenizer.nextToken();
                    Toast.makeText(LoginActivity.this, listphoneofuser[i], Toast.LENGTH_LONG).show();
                }
                for(DataSnapshot currentuser:dataSnapshot.getChildren()){
                    userdb.add(currentuser.getValue().toString());
                    //Toast.makeText(LoginActivity.this, currentuser.getValue().toString(), Toast.LENGTH_LONG).show();
                }
                }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile.setText("");
                loginpwd.setText("");
                //loginpwd.setText(listid[currentuserpos]);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this)
                        .setIcon(R.drawable.ic_signup)
                        .setTitle("Join the BOOK")
                        .setMessage("Aren't you a VERIFIED BW User")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent tosignup = new Intent(LoginActivity.this,SignupactivityActivity.class);
                                startActivity(tosignup);
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

    public boolean ValidUserPassword() {
        for(int i=0;i<userdb.size();i++){
            if(listphoneofuser[currentuserpos].equals(userdb.get(i))) {
                  currentuserdb[0] =  userdb.get(i);
                  currentuserdb[1] = userdb.get(i+1);
                  currentuserdb[2] = userdb.get(i+2);
            }
        }
        if(loginpwd.getText().toString().equals(currentuserdb[1])) {
            return true;
        }
        else
            return false;
    }

/*    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }*/
    private void initviews() {
        relativeLayout = (RelativeLayout) findViewById(R.id.loginlayout);
        relativeLayout.setBackgroundResource(R.drawable.bookwormloginscreen);
        loginpwd = (EditText) findViewById(R.id.loginpassword);
        mobile = (EditText) findViewById(R.id.loginmobile);
        loginpwd.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
        login = (Button) findViewById(R.id.loginbutton);
        cancel = (Button) findViewById(R.id.cancelbutton);
        signup = (Button) findViewById(R.id.signupbutton);
        userlist = new ArrayList<>();
        userdb = new ArrayList<>();
        listid = new String[1000000];
        listphoneofuser = new String[1000000];
        currentuserdb = new String[1000000];
        bwdb = FirebaseDatabase.getInstance();
        bwref = bwdb.getReference("usersofbw");
    }

}
