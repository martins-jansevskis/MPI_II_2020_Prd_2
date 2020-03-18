package lv.martins.mpi_ii_2020_prd_2;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RepositoryActivity extends AppCompatActivity {

    ArrayList<String> mylist = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.covid_listview_item, mylist);

        ListView listView = (ListView) findViewById(R.id.covid_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,  long id) {
//                Intent intent = new Intent(RepositoryActivity.this, SendMessage.class);
//                String message = "abc";
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
                Toast.makeText(RepositoryActivity.this, "on item click", Toast.LENGTH_LONG).show();
            }
        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        String url ="https://newsapi.org/v2/top-headlines?q=covid-19&?country=lv&from="+currentDateandTime+"&sortBy=publishedAt&apiKey=7aded4da9fcd4dee808447686e76b561";

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray articles = response.getJSONArray("articles");
                        for (int i = 0; i < articles.length(); i++) {
                            JSONObject article = articles.getJSONObject(i);
                            mylist.add(article.getString("title") + "\n" + article.get("description"));
                        }
                    } catch (Exception e) {

                    }

                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Praktiskais", "error:" + error.toString());
                }
            });


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                finish();
//                Intent repoActivity = new Intent(this, MapsActivity.class);
//                startActivity(repoActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
