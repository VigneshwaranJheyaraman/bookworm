package in.co.forstudents.bookworm.bookworm;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import in.co.forstudents.bookworm.bookworm.Adapter.BooksAdpater;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookDatabaseActivity extends AppCompatActivity {
    private static final String TAG = BookDatabaseActivity.class.getSimpleName();
    private List<MainDB> movies;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private BooksAdpater adapter;
    public static String server_url = "http://bb280e3f.ngrok.io";
    ArrayList<String> currentuserdb;
    String usernamefromsignup;
    FirebaseDatabase bwdb;
    DatabaseReference bwref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_database);
        Intent fromsingup = getIntent();
        Intent fromlogin = getIntent();
        setactionbarasnew();
        if(SplashActivity.SP_VALUE== false){
            usernamefromsignup = fromsingup.getStringExtra("userid");
        String username = fromsingup.getStringExtra("username");
        String welcomemessage = "Welcome to world of BOOKS "+username;
        Toast.makeText(this,welcomemessage,Toast.LENGTH_LONG).show();}
        else if(SplashActivity.SP_VALUE == true){
            usernamefromsignup = getIntent().getStringExtra("userid");
            String welcomemessage = "Welcome to world of BOOKS "+usernamefromsignup;
            Toast.makeText(this,welcomemessage,Toast.LENGTH_LONG).show();
        }
        else if(fromlogin.hasExtra("useridfromlogin")){
            usernamefromsignup = fromlogin.getStringExtra("useridfromlogin");
            String welcomemessage = "Welcome to world of BOOKS "+usernamefromsignup;
            Toast.makeText(this,welcomemessage,Toast.LENGTH_LONG).show();
        }
        else if(getIntent().hasExtra("useridfromsplashal")){
            usernamefromsignup = getIntent().getStringExtra("useridfromsplashal");
            String welcomemessage = "Welcome to world of BOOKS "+usernamefromsignup;
            Toast.makeText(this,welcomemessage,Toast.LENGTH_LONG).show();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        movies = new ArrayList<>();
        displayalldetailsofcu();
        getMoviesFromDB(0);
        gridLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayout);

        adapter = new BooksAdpater(this, movies);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (gridLayout.findLastCompletelyVisibleItemPosition() == movies.size() - 1) {
                    try{
                        getMoviesFromDB(movies.get(movies.size() - 1).getId());
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.i("EXCEPTION_DURING",e.getMessage());
                    }
                }
            }
        });
    }

    private void setactionbarasnew() {
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.actionbar_custom_view_home);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.useraccount:
                Intent to_user_profile = new Intent(this,UserProfileActivity.class);
                to_user_profile.putStringArrayListExtra("currentuser",currentuserdb);
                startActivity(to_user_profile);
                break;
            default:
                break;
        }
        return true;
    }

    public void displayalldetailsofcu(){
        bwdb = FirebaseDatabase.getInstance();
        bwref = bwdb.getReference("usersofbw");
        currentuserdb = new ArrayList<>();
        bwref.child(usernamefromsignup).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    currentuserdb.add(ds.getValue().toString());
                //  Toast.makeText(BookDatabaseActivity.this,ds.getValue().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       }

    private void getMoviesFromDB(int id) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... movieIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(server_url+"/bookworm/books.php?id=" + movieIds[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        MainDB movie = new MainDB(object.getInt("book_id"), object.getString("book_name"),
                                object.getString("book_image").replaceAll("\\\\",""), object.getString("book_file").replaceAll("\\\\",""));
                        //Toast.makeText(BookDatabaseActivity.this,"DB updated",Toast.LENGTH_LONG).show();
                        BookDatabaseActivity.this.movies.add(movie);
                    }


                } catch (Exception e) {
                    Log.i("Exception",e.getMessage());
                    Toast.makeText(BookDatabaseActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }
}
