package com.greentea.connectiontest2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Person>> call = api.getPeople();

        // select
        call.enqueue(new Callback<List<Person>>() {

            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {

                List<Person> people = response.body();

                String[] names = new String[people.size()];

                for (int i = 0; i < people.size(); i++) {

                    names[i] = people.get(i).getName();
                }

                listView.setAdapter(
                        new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                names
                        )
                );
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // select query

        editText = findViewById(R.id.text);
        final TextView textView = findViewById(R.id.view);

        button = findViewById(R.id.button1);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                String personId = editText.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                        .build();

                Api api = retrofit.create(Api.class);

                Call<Person> call = api.getPerson(personId);

                call.enqueue(new Callback<Person>() {

                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {

                        Person person = response.body();

                        String name = person.getAge();

                        textView.setText(name);
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                /////
                Person info = new Person("3","James","90");

                Call<Person> call1 = api.postPerson(info);

                call1.enqueue(new Callback<Person>(){

                    @Override
                    public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "successssss", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "failllllllllll", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {

                    }

                });
            }
        });

        // post

//        HashMap<String,String> input = new HashMap<>();
//        input.put("id", "3");
//        input.put("name", "James");
//        input.put("age", "90");

//        Person info = new Person("3","James","90");
//
////        Call<Person> call1 = api.postPerson(input);
//
//        Call<Person> call1 = api.postPerson(info);
//
//        call1.enqueue(new Callback<Person>(){
//
//            @Override
//            public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(getApplicationContext(), "successssss", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
//
//            }
//
//        });

    }
}