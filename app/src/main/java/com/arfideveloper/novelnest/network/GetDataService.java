package com.arfideveloper.novelnest.network;


import com.arfideveloper.novelnest.apimodels.AuthResponseModel;
import com.arfideveloper.novelnest.apimodels.CategoryResponseModel;
import com.arfideveloper.novelnest.apimodels.FavouritesResponseModel;
import com.arfideveloper.novelnest.apimodels.ForgotResponseModel;
import com.arfideveloper.novelnest.apimodels.HomeResponseModel;
import com.arfideveloper.novelnest.apimodels.InboxResponseModel;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.apimodels.NovelDetailResponseModel;
import com.arfideveloper.novelnest.apimodels.SearchResponseModel;
import com.arfideveloper.novelnest.apimodels.UserMessagesResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("signup")
    Call<AuthResponseModel> registerUser(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("password") String password);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("login")
    Call<AuthResponseModel> loginUser(@Field("email") String email,
                                      @Field("password") String password,
                                      @Field("token") String token)
                                        ;

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgotResponseModel> forgotPassword(@Field("email") String email);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("reset_password")
    Call<AuthResponseModel> resetPasswordUser(@Field("email") String email,
                                              @Field("password") String password,
                                              @Field("confirm_password") String confirm_password);


    @Headers({"Accept: application/json"})
    @Multipart
    @POST("edit_profile")
    Call<AuthResponseModel> updateUserProfileAndImage(@Part("user_id") RequestBody user_id,
                                                      @Part("name") RequestBody name,
                                                      @Part("email") RequestBody email,
                                                      @Part("bio") RequestBody bio,
                                                      @Part("gender") RequestBody gender,
                                                      @Part("date_of_birth") RequestBody date_of_birth,
                                                      @Part MultipartBody.Part image
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("edit_profile")
    Call<AuthResponseModel> updateUserProfileInfo(@Field("user_id") String user_id,
                                                  @Field("name") String name,
                                                  @Field("email") String email,
                                                  @Field("bio") String bio,
                                                  @Field("gender") String gender,
                                                  @Field("date_of_birth") String date_of_birth);


    @GET("home_screen/{user_id}")
    Call<HomeResponseModel> getHomeScreen(@Path("user_id") String user_id);

    @GET("home_screen/{user_id}")
    Call<HomeResponseModel> getHomeScreenWithPagination(@Path("user_id") String user_id, @Query("page") String page);

    @GET("favourite_novel/{user_id}/{novel_id}")
    Call<MainResponseModel> likeNovel(@Path("user_id") String user_id,
                                      @Path("novel_id") String novel_id);

    @GET("favourite_novel/{user_id}/{novel_id}")
    Call<MainResponseModel> likeNovelSearch(@Path("user_id") String user_id,
                                            @Path("novel_id") String novel_id);

    @GET("get_favourite_novels/{user_id}")
    Call<FavouritesResponseModel> favouriteNovels(@Path("user_id") String user_id);

    @GET("get_messanger/{user_id}")
    Call<InboxResponseModel> getInboxList(@Path("user_id") String user_id);

    @GET("get_messages/{user_id}/{novel_id}")
    Call<UserMessagesResponseModel> getUserMessages(@Path("user_id") String user_id,
                                                    @Path("novel_id") String novel_id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("send_message")
    Call<MainResponseModel> sendMessage(
            @Field("user_id") String user_id,
            @Field("chat_group_id") String chat_group_id,
            @Field("text") String text);

    @GET("logout/{user_id}")
    Call<MainResponseModel> logoutUser(@Path("user_id") String user_id);

    @GET("logout/{user_id}")
    Call<MainResponseModel> changePasswordLogOut(@Path("user_id") String user_id);

    @GET("delete_account/{user_id}")
    Call<MainResponseModel> deleteAccount(@Path("user_id") String user_id);

    @GET("category_novels/{user_id}/{category_id}")
    Call<CategoryResponseModel> categoryNovelsList(@Path("user_id") String user_id,
                                                   @Path("category_id") String category_id);

    @GET("category_novels/{user_id}/{category_id}")
    Call<CategoryResponseModel> categoryNovelsListWithPagination(@Path("user_id") String user_id, @Path("category_id") String category_id, @Query("page") String page);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("explore")
    Call<SearchResponseModel> searchNovel(
            @Field("user_id") String user_id,
            @Field("search_term") String search_term);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("explore")
    Call<SearchResponseModel> exploreNovelList(
            @Field("user_id") String user_id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("change_password")
    Call<MainResponseModel> changePasswordFor(
            @Field("user_id") String user_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password);

    @GET("join_chat_group/{user_id}/{novel_id}")
    Call<MainResponseModel> joinChatGroup(@Path("user_id") String user_id,
                                          @Path("novel_id") String novel_id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("contact_us")
    Call<MainResponseModel> forContactUs(
            @Field("email") String email,
            @Field("subject") String subject,
            @Field("message") String message,
            @Field("name") String name);


    @GET("novel_details/{user_id}/{novel_id}")
    Call<NovelDetailResponseModel> getNovelDetails(@Path("user_id") String user_id,
                                                   @Path("novel_id") String novel_id);


    @Headers({"Accept: application/json"})
    @Multipart
    @POST("become_author")
    Call<MainResponseModel> forBecomeAuthor(
            @Part("user_id") RequestBody user_id,
            @Part("pen_name") RequestBody pen_name,
            @Part("portfolio_url") RequestBody portfolio_url,
            @Part("personal_email") RequestBody personal_email,
            @Part MultipartBody.Part verification_image,
            @Part MultipartBody.Part cnic_front_image,
            @Part MultipartBody.Part cnic_back_image);



    @Multipart
    @POST("add-novel")
    Call<MainResponseModel> addNovelText(
            @Part("author_id") RequestBody author_id,
            @Part("category_id") RequestBody category_id,
            @Part("novel_title") RequestBody novel_title,
            @Part("novel_type") RequestBody novel_type,
            @Part("novel_text") RequestBody novel_text,
            @Part("language") RequestBody language,
            @Part MultipartBody.Part novel_banner
    );


    @Multipart
    @POST("add-novel")
    Call<MainResponseModel> addNovelForPdf(
            @Part("author_id") RequestBody author_id,
            @Part("category_id") RequestBody category_id,
            @Part("novel_title") RequestBody novel_title,
            @Part("novel_type") RequestBody novel_type,
            @Part("language") RequestBody language,
            @Part MultipartBody.Part novel_file,
            @Part MultipartBody.Part novel_banner
    );


}
