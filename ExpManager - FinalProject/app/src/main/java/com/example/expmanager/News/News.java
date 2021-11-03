package com.example.expmanager.News;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.expmanager.Adapter.RecyclerViewAdapter;
import com.example.expmanager.DataBaseHandler.MyDBHandler;
import com.example.expmanager.News.Model.Articles;
import com.example.expmanager.News.Model.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.expmanager.R;

public class News extends Fragment {
    RecyclerView recyclerView;
   // final String API_KEY = "eb1f2906286a42fd8d5df2a80b4b38ae";
   final String API_KEY = "dbcbf20d672b4ad49da82aa8976d7a3c"; //Prachi
   //final String API_KEY = "";
    Adapter adapter;
    List<Articles>  articles = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_news, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        String country = getCountry();
        retrieveJson(country,API_KEY);


        //recycler view connect
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news);
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        String country = getCountry();
//        retrieveJson(country,API_KEY);
//    }

    public void retrieveJson(String country, String apiKey){
        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(getContext(), articles);
                    recyclerView.setAdapter(adapter);


                    String country = getCountry();

                    retrieveJson(country, API_KEY);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                //Toast.makeText(get, t.getLocalizedMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}