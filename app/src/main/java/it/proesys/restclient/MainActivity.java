package it.proesys.restclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String REST_URL_JSON = "https://jsonplaceholder.typicode.com";
    private final String REST_URL_XML = "http://api2.mytrendin.com/";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textData);
        Button buttonGetDataJson = findViewById(R.id.buttonGetDataJson);
        buttonGetDataJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // -----------------------------
                // Chiamata API JSON
                // -----------------------------
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(REST_URL_JSON)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PostsAPI api = retrofit.create(PostsAPI.class);

                Call<JsonTestData>  call = api.getPost(1);
                call.enqueue(new Callback<JsonTestData>() {
                    @Override
                    public void onResponse(Call<JsonTestData> call, Response<JsonTestData> response) {
                        int statusCode = response.code();
                        JsonTestData post = response.body();
                        String title = post.getTitle();
                        textView.setText(title);
                    }

                    @Override
                    public void onFailure(Call<JsonTestData> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error retrieving data", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        Button buttonGetDataXml = findViewById(R.id.buttonGetDataXml);
        buttonGetDataXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // -----------------------------
                // Chiamata API XML
                // -----------------------------
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(REST_URL_XML)
                        .client(new OkHttpClient())
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .build();

                ProductAPI api = retrofit.create(ProductAPI.class);

                Call<XmlTestData>  call = api.getProducts();
                call.enqueue(new Callback<XmlTestData>() {
                    @Override
                    public void onResponse(Call<XmlTestData> call, Response<XmlTestData> response) {
                        int statusCode = response.code();
                        String productNames = "";
                        List<Product> listaProdotti = response.body().products;
                        for (Product pr:listaProdotti) {
                            productNames += pr.getName() + "\n";
                        }
                        textView.setText(productNames);
                    }

                    @Override
                    public void onFailure(Call<XmlTestData> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error retrieving data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}


