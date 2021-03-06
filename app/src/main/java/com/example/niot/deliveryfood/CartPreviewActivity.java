package com.example.niot.deliveryfood;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niot.deliveryfood.Adapter.FoodSimpleViewAdapter;
import com.example.niot.deliveryfood.model.BillResponse;
import com.example.niot.deliveryfood.model.Cart;
import com.example.niot.deliveryfood.model.Food;
import com.example.niot.deliveryfood.retrofit.CvlApi;
import com.example.niot.deliveryfood.retrofit.RetrofitObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartPreviewActivity extends AppCompatActivity{

    FoodSimpleViewAdapter adapter;
    Cart cart;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_preview);
        String json = getIntent().getStringExtra("cartJson");
        cart = new Gson().fromJson(json, Cart.class);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.cart_preview_recycler_view);
        adapter = new FoodSimpleViewAdapter(cart);
        recyclerView.setAdapter(adapter);
        EditText addr = findViewById(R.id.cart_preview_user_address);
        addr.setText(cart.getAddress());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    public void confirmCart(View view) {
        getUserAddress();
        Retrofit retrofit = RetrofitObject.getInstance();
        retrofit.create(CvlApi.class).postCart(cart).enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                if(response.body() != null){
                    BillResponse billResponse = response.body();
                    Intent i = new Intent(CartPreviewActivity.this, BillDetailActivity.class);
                    i.putExtra("bill", billResponse.getHoadon());
                    CartPreviewActivity.this.startActivityForResult(i, 2812);
                    Log.e("LOG BILL:", billResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<BillResponse> call, Throwable t) {
                Toast.makeText(CartPreviewActivity.this, "Failed3!", Toast.LENGTH_SHORT).show();

                if (t instanceof IOException) {
                    Toast.makeText(CartPreviewActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    //Toast.makeText(SignUpActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CartPreviewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2812){
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private void getUserAddress() {
        cart.setAddress(((EditText)findViewById(R.id.cart_preview_user_address)).getText().toString());
    }
}
