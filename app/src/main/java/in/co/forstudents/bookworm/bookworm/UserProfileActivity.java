package in.co.forstudents.bookworm.bookworm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.Inet4Address;
import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {
    TextView currentusermobile,currentuserpass,currentusername;
    EditText url_of_server;
    Button set_server_url;
    ArrayList<String> currentuserdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initviews();
       displayuserinfo();
       url_of_server.setText(BookDatabaseActivity.server_url);
       set_server_url.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               BookDatabaseActivity.server_url = url_of_server.getText().toString();
           }
       });
    }

    private void displayuserinfo() {
        Intent frombookdb = getIntent();
        currentuserdb = frombookdb.getStringArrayListExtra("currentuser");
        currentusermobile.setText(currentuserdb.get(0));
        currentuserpass.setText(currentuserdb.get(1));
        currentusername.setText(currentuserdb.get(2));
    }

    private void initviews() {
        currentusermobile = (TextView) findViewById(R.id.usermobile);
        currentuserpass = (TextView) findViewById(R.id.userpass);
        currentusername = (TextView) findViewById(R.id.username);
        currentuserdb = new ArrayList<>();
        url_of_server = (EditText) findViewById(R.id.urlforserver);
        set_server_url = (Button) findViewById(R.id.setserverurl);
    }
}
