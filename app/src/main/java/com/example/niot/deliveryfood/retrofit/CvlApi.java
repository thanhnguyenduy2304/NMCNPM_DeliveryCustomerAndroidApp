package com.example.niot.deliveryfood.retrofit;

import com.example.niot.deliveryfood.model.Bill;
import com.example.niot.deliveryfood.model.BillDetail;
import com.example.niot.deliveryfood.model.BillResponse;
import com.example.niot.deliveryfood.model.Cart;
import com.example.niot.deliveryfood.model.Comment;
import com.example.niot.deliveryfood.model.Food;
import com.example.niot.deliveryfood.model.PostResponse;
import com.example.niot.deliveryfood.model.Restaurant;
import com.example.niot.deliveryfood.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CvlApi {
    @GET("/login/nguoi_dung")
    Call<List<User>> loginUser(@Query("sdt") String phone, @Query("pass") String password);

    @GET("/signup/nguoi_dung")
    Call<List<User>> newUser(@QueryMap Map<String, String> info);

    @GET("/db/quan_an")
    Call<List<Restaurant>> getRestaurant();

    @GET("/mon_an")
    Call<List<Food>> getFoodsList(@Query("id_quan_an") int res_id);

    @GET("/hoa_don")
    Call<List<Bill>> getBillsList(@Query("id_nguoi_dung") int user_id);

    @GET("/hoa_don")
    Call<Bill> getBillById(@Query("id_hoa_don") int bill_id);

    @GET("/hoa_don_chua_xong")
    Call<List<Bill>> getCurrentBillsList(@Query("id_nguoi_dung") int user_id);

    @POST("/dat_mon")
    Call<BillResponse> postCart(@Body Cart cart);

    @GET("/chi_tiet_hoa_don")
    Call<List<BillDetail>> getBillDetailByBill(@Query("id_hoa_don") int id);

    @GET("/yeu_thich/check")
    Call<PostResponse> checkFav(@Query("id_nguoi_dung") int userId, @Query("id_quan_an") int resId);

    @GET("/yeu_thich/change")
    Call<PostResponse> changeFav(@Query("id_nguoi_dung") int userId, @Query("id_quan_an") int resId);

    @GET("/xem_danh_gia")
    Call<List<Comment>> getComments(@Query("id_quan_an") int resId);

    @POST("/danh_gia")
    Call<PostResponse> sendComment(@Body Comment comment);
}
