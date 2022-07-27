package com.arfideveloper.novelnest.activities.auth;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.ProfileImageActivity;
import com.arfideveloper.novelnest.apimodels.AuthResponseModel;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.apimodels.UserDataModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.FileUtils;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;


    RelativeLayout layout_backBtn, input_Gender;
    TextInputLayout input_FullName, input_Email, input_Bio, input_Date_Of_Birth;
    Button btn_saveChanges, btn_deleteAccount;
    CircleImageView user_ProfileImage;
    RelativeLayout layout_changeImage;
    EditText edit_bio, edit_dateOfBirth;
    MaterialSpinner genderSpinner;
    final Calendar myCalendar= Calendar.getInstance();

    String userId, name, email, bio, gender, date_of_birth, profile_image;
    String picturePath = "";
    int SELECT_PICTURE = 500;

    public static final int PERMISSION_CODE = 1001;
    final int MAX_IMAGES = 1;
    private final List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        context = EditProfileActivity.this;

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);


        // Get Data From Utilities shared Preferences

        userId = String.valueOf(Utilities.getInt(context, "user_id"));
        name = Utilities.getString(context, "name");
        email = Utilities.getString(context, "email");
        bio = Utilities.getString(context, "bio");
        gender = Utilities.getString(context, "gender");
        date_of_birth = Utilities.getString(context, "date_of_birth");
        profile_image = Utilities.getString(context, "profile_image");

        layout_backBtn = findViewById(R.id.layout_backBtn);
        input_FullName = findViewById(R.id.input_FullName);
        input_Email = findViewById(R.id.input_Email);
        input_Bio = findViewById(R.id.input_Bio);
        input_Gender = findViewById(R.id.input_Gender);
        input_Date_Of_Birth = findViewById(R.id.input_Date_Of_Birth);
        btn_saveChanges = findViewById(R.id.btn_saveChanges);
        user_ProfileImage = findViewById(R.id.user_ProfileImage);
        layout_changeImage = findViewById(R.id.layout_changeImage);
        edit_bio = findViewById(R.id.edit_bio);
        edit_dateOfBirth = findViewById(R.id.edit_dateOfBirth);
        genderSpinner = findViewById(R.id.genderSpinner);
        btn_deleteAccount = findViewById(R.id.btn_deleteAccount);


        btn_deleteAccount.setOnClickListener(v -> {
            doYouWantToDeleteAccount();
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        user_ProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, ProfileImageActivity.class);
            intent.putExtra("imageUrl", profile_image);
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        genderSpinner.setItems("Male", "Female", "Rather Not Say");

        genderSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> Toast.makeText(EditProfileActivity.this, "Clicked" + item , Toast.LENGTH_SHORT).show());


        DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };
        edit_dateOfBirth.setOnClickListener(view -> {
            new DatePickerDialog(EditProfileActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        layout_backBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        Objects.requireNonNull(input_FullName.getEditText()).setText(name);
        Objects.requireNonNull(input_Email.getEditText()).setText(email);
        Objects.requireNonNull(input_Bio.getEditText()).setText(bio);
        Objects.requireNonNull(input_Date_Of_Birth.getEditText()).setText(date_of_birth);
        Glide.with(context).load(profile_image).error(R.drawable.default_place_holder).placeholder(R.drawable.default_place_holder).into(user_ProfileImage);



        layout_changeImage.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //Permission not granted request it
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup for run time permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    openMultipleImagesGallery();
                }
            } else {
                openMultipleImagesGallery();
            }
        });


        btn_saveChanges.setOnClickListener(v -> {
            String fullName = Objects.requireNonNull(input_FullName.getEditText()).getText().toString();
            String bio = Objects.requireNonNull(input_Bio.getEditText()).getText().toString();
            String date_of_birth = Objects.requireNonNull(input_Date_Of_Birth.getEditText()).getText().toString();
            shadow_View.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);

            if (!picturePath.isEmpty()) {
                // Call the updateUserProfileAndImage here;
                updateUserProfileAndImage(userId, fullName, email, bio, gender, date_of_birth, picturePath);
            } else {
                // Call the updateUserProfileInfo here;
                updateUserProfileInfo(userId, fullName, email, bio, gender, date_of_birth);
            }
            shadow_View.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);
            finish();
        });
    }

    private void openMultipleImagesGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PERMISSION_CODE);

        }

    private void updateUserProfileAndImage(String userId, String name, String email, String userBio, String gender, String date_of_birth, String picturePath) {
        File userProfileFile = new File(picturePath);

        RequestBody request_userId = RequestBody.create(okhttp3.MediaType.parse("text/plain"), userId);
        RequestBody request_userName = RequestBody.create(okhttp3.MediaType.parse("text/plain"), name);
        RequestBody request_userEmail = RequestBody.create(okhttp3.MediaType.parse("text/plain"), email);
        RequestBody request_userBio = RequestBody.create(okhttp3.MediaType.parse("text/plain"), userBio);
        RequestBody request_gender = RequestBody.create(okhttp3.MediaType.parse("text/plain"), gender);
        RequestBody request_date_of_birth = RequestBody.create(okhttp3.MediaType.parse("text/plain"), date_of_birth);
        RequestBody frontRequestBody = RequestBody.create(okhttp3.MediaType.parse("*/*"), userProfileFile);
        MultipartBody.Part multipartProfile = MultipartBody.Part.createFormData("image", userProfileFile.getName(), frontRequestBody);

        Call<AuthResponseModel> call = service.updateUserProfileAndImage(request_userId, request_userName,
                request_userEmail, request_userBio, request_gender, request_date_of_birth, multipartProfile);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponseModel> call, @NonNull Response<AuthResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        UserDataModel user = response.body().getUserDataModel();
                        int id = user.getId();
                        String facebook_id = user.getFacebook_id();
                        String google_id = user.getGoogle_id();
                        String name = user.getName();
                        String email = user.getEmail();
                        String email_verified_at = user.getEmail_verified_at();
                        String profile_image = user.getProfile_image();
                        String bio = user.getBio();
                        String gender = user.getGender();
                        String date_of_birth = user.getDate_of_birth();
                        String portfolio_url = user.getPortfolio_url();
                        String CNIC_front_image = user.getCnic_front_image();
                        String CNIC_back_image = user.getCnic_back_image();
                        String average_rating = user.getAverage_rating();
                        String is_verified = user.getIs_verified();
                        String type = user.getType();
                        String token = user.getToken();

                        Utilities.saveInt(context, "id", id);
                        Utilities.saveString(context, "facebook_id", facebook_id);
                        Utilities.saveString(context, "google_id", google_id);
                        Utilities.saveString(context, "name", name);
                        Utilities.saveString(context, "email", email);
                        Utilities.saveString(context, "email_verified_at", email_verified_at);
                        Utilities.saveString(context, "profile_image", profile_image);
                        Utilities.saveString(context, "bio", bio);
                        Utilities.saveString(context, "gender", gender);
                        Utilities.saveString(context, "date_of_birth", date_of_birth);
                        Utilities.saveString(context, "portfolio_url", portfolio_url);
                        Utilities.saveString(context, "CNIC_front_image", CNIC_front_image);
                        Utilities.saveString(context, "CNIC_back_image", CNIC_back_image);
                        Utilities.saveString(context, "average_rating", average_rating);
                        Utilities.saveString(context, "is_verified", is_verified);
                        Utilities.saveString(context, "type", type);
                        Utilities.saveString(context, "token", token);

                    } else {
                        CustomCookieToast.showFailureToast(EditProfileActivity.this, response.body().getMessage());
                    }
                } else {
                    CustomCookieToast.showFailureToast(EditProfileActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(EditProfileActivity.this, t.getMessage());
            }
        });
    }

    private void updateUserProfileInfo(String userId, String name, String email, String userBio, String gender, String date_of_birth) {
        Call<AuthResponseModel> call = service.updateUserProfileInfo(userId, name, email, userBio, gender, date_of_birth);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponseModel> call, @NonNull Response<AuthResponseModel> response) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    if (status) {
                        UserDataModel user = response.body().getUserDataModel();
                        int id = user.getId();
                        String facebook_id = user.getFacebook_id();
                        String google_id = user.getGoogle_id();
                        String name = user.getName();
                        String email = user.getEmail();
                        String email_verified_at = user.getEmail_verified_at();
                        String profile_image = user.getProfile_image();
                        String bio = user.getBio();
                        String gender = user.getGender();
                        String date_of_birth = user.getDate_of_birth();
                        String portfolio_url = user.getPortfolio_url();
                        String CNIC_front_image = user.getCnic_front_image();
                        String CNIC_back_image = user.getCnic_back_image();
                        String average_rating = user.getAverage_rating();
                        String is_verified = user.getIs_verified();
                        String type = user.getType();
                        String token = user.getToken();

                        Utilities.saveInt(context, "id", id);
                        Utilities.saveString(context, "facebook_id", facebook_id);
                        Utilities.saveString(context, "google_id", google_id);
                        Utilities.saveString(context, "name", name);
                        Utilities.saveString(context, "email", email);
                        Utilities.saveString(context, "email_verified_at", email_verified_at);
                        Utilities.saveString(context, "profile_image", profile_image);
                        Utilities.saveString(context, "bio", bio);
                        Utilities.saveString(context, "gender", gender);
                        Utilities.saveString(context, "date_of_birth", date_of_birth);
                        Utilities.saveString(context, "portfolio_url", portfolio_url);
                        Utilities.saveString(context, "CNIC_front_image", CNIC_front_image);
                        Utilities.saveString(context, "CNIC_back_image", CNIC_back_image);
                        Utilities.saveString(context, "average_rating", average_rating);
                        Utilities.saveString(context, "is_verified", is_verified);
                        Utilities.saveString(context, "type", type);
                        Utilities.saveString(context, "token", token);

                    } else {
                        CustomCookieToast.showFailureToast(EditProfileActivity.this, response.body().getMessage());
                    }
                } else {
                    CustomCookieToast.showFailureToast(EditProfileActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(EditProfileActivity.this, t.getMessage());
            }
        });
    }

    public void requestDeleteAccount(String user_id) {
        Call<MainResponseModel> call = service.deleteAccount(user_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            finishAffinity();
                            Utilities.clearSharedPref(context);
                            Intent intent = new Intent(context, SignUpActivity.class);
                            startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                            startActivity(intent);
                        } else {
                            CustomCookieToast.showRequiredToast(EditProfileActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(EditProfileActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(EditProfileActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

//    date picker
    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        edit_dateOfBirth.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void doYouWantToDeleteAccount() {
        AlertDialog reportPostPopup;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.popup_delete_account, null);
        builder.setView(customLayout);

        TextView btn_yes = customLayout.findViewById(R.id.text_Yes);
        TextView btn_cancel = customLayout.findViewById(R.id.text_No);

        reportPostPopup = builder.create();
        reportPostPopup.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        reportPostPopup.show();
        reportPostPopup.setCancelable(false);

        btn_cancel.setOnClickListener(v -> reportPostPopup.dismiss());

        btn_yes.setOnClickListener(v -> {
            // Clear SharedPref and send to login
            reportPostPopup.dismiss();
            requestDeleteAccount(String.valueOf(userId));
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Uri selectedMediaUri = data.getData();
        if (resultCode == RESULT_OK){
            if (requestCode == PERMISSION_CODE) {
                user_ProfileImage.setImageURI(selectedMediaUri);
                picturePath = FileUtils.getPath(context, selectedMediaUri);
            }

        }
    }
}