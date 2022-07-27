package com.arfideveloper.novelnest.activities;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.FileUtils;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyAuthorActivity extends AppCompatActivity {
    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    RelativeLayout layout_backBtn, layout_changeImage, layout_frontSide, layout_backSide, btnSelectBackSide,
            btnSelectFrontSide, layout_uploadPhoto, layout_imageCancelFront, layout_imageCancelBack;
    TextInputLayout input_FullName, input_Email, text_Uri;
    Button btn_uploadVerification;
    CircleImageView image_uploadPic;
    RoundedImageView image_frontSide, image_backSide;
    TextInputEditText edit_email, edit_authorName, edit_portfolioUrl;

    String user_id, portfolio_url;

    String picturePath = "";
    String frontPicturePath = "";
    String backPicturePath = "";
    public static final int PERMISSION_CODE = 1001;
    public static final int FRONT_IMAGE = 1000;
    public static final int BACK_IMAGE = 10000;
    final int MAX_IMAGES = 1;
    private final List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_author);
        context = VerifyAuthorActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        user_id = String.valueOf(Utilities.getInt(context, "user_id"));

        layout_backBtn = findViewById(R.id.layout_backBtn);
        input_FullName = findViewById(R.id.input_FullName);
        input_Email = findViewById(R.id.input_Email);
        text_Uri = findViewById(R.id.text_Uri);
        btn_uploadVerification = findViewById(R.id.btn_uploadVerification);
        image_uploadPic = findViewById(R.id.image_uploadPic);
        edit_email = findViewById(R.id.edit_email);
        layout_changeImage = findViewById(R.id.layout_changeImage);
        layout_frontSide = findViewById(R.id.layout_frontSide);
        layout_backSide = findViewById(R.id.layout_backSide);
        image_frontSide = findViewById(R.id.image_frontSide);
        image_backSide = findViewById(R.id.image_backSide);
        btnSelectFrontSide = findViewById(R.id.btnSelectFrontSide);
        btnSelectBackSide = findViewById(R.id.btnSelectBackSide);
        edit_authorName = findViewById(R.id.edit_authorName);
        edit_portfolioUrl = findViewById(R.id.edit_portfolioUrl);
        layout_uploadPhoto = findViewById(R.id.layout_uploadPhoto);
        layout_imageCancelFront = findViewById(R.id.layout_imageCancelFront);
        layout_imageCancelBack = findViewById(R.id.layout_imageCancelBack);


        layout_backBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        image_uploadPic.setOnClickListener(v -> {
            Intent intent = new Intent(VerifyAuthorActivity.this, ProfileImageActivity.class);
            intent.putExtra("imageUrl", picturePath);
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

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


            layout_imageCancelFront.setOnClickListener(v -> {
                btnSelectFrontSide.setVisibility(View.VISIBLE);
                image_frontSide.setVisibility(View.GONE);
                frontPicturePath = null;
                layout_imageCancelFront.setVisibility(View.GONE);
            });

        layout_imageCancelBack.setOnClickListener(v -> {
            btnSelectBackSide.setVisibility(View.VISIBLE);
            image_backSide.setVisibility(View.GONE);
            backPicturePath = null;
            layout_imageCancelBack.setVisibility(View.GONE);
        });



        btnSelectFrontSide.setOnClickListener(view -> {
            openImagesGalleryFrontID();
        });

        btnSelectBackSide.setOnClickListener(view -> {
            openImagesGalleryBackID();
        });

        btn_uploadVerification.setOnClickListener(v -> {
            String authorName = Objects.requireNonNull(Objects.requireNonNull(input_FullName.getEditText()).getText().toString());
            String email = Objects.requireNonNull(input_Email.getEditText()).getText().toString();
            portfolio_url = Objects.requireNonNull(edit_portfolioUrl.getText()).toString();
            boolean isValidEmail = Utilities.isValidEmail(email);
            if (picturePath.isEmpty()) {
                CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, "Please upload profile picture");
            } else if (authorName.isEmpty()) {
                CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, "Please Enter pen Name");
            } else if (email.isEmpty()) {
                CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, "Please Enter Personal Email");
            } else if (!isValidEmail) {
                CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, "Please Enter Valid Email");
            } else if (portfolio_url.isEmpty()) {
                CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, "Please Enter Portfolio Url");
            } else if (frontPicturePath.isEmpty()) {
                CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, "Please upload CNIC front picture");
            } else if (backPicturePath.isEmpty()) {
                CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, "Please upload CNIC back picture");
            } else {
                shadow_View.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                requestBecomeAuthorValid(user_id, authorName, portfolio_url, email, frontPicturePath, backPicturePath, picturePath);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission was granted
                openMultipleImagesGallery();
            } else {
                //permission was denied
                Toast.makeText(VerifyAuthorActivity.this, "Permission were denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void requestBecomeAuthorValid(String user_id, String pen_name, String portfolio_url, String personal_email, String frontPicturePath, String backPicturePath, String picturePath) {
        File userProfileFile = new File(picturePath);
        File cnicFrontImage = new File(frontPicturePath);
        File cnicBackImage = new File(backPicturePath);

        RequestBody request_userId = RequestBody.create(okhttp3.MediaType.parse("text/plain"), user_id);
        RequestBody request_penName = RequestBody.create(okhttp3.MediaType.parse("text/plain"), pen_name);
        RequestBody request_personalEmail = RequestBody.create(okhttp3.MediaType.parse("text/plain"), personal_email);
        RequestBody request_portfolio_url = RequestBody.create(okhttp3.MediaType.parse("text/plain"), portfolio_url);
        RequestBody frontRequestBody = RequestBody.create(okhttp3.MediaType.parse("*/*"), userProfileFile);
        RequestBody cnicFrontRequestBody = RequestBody.create(okhttp3.MediaType.parse("*/*"), cnicFrontImage);
        RequestBody cnicBackRequestBody = RequestBody.create(okhttp3.MediaType.parse("*/*"), cnicBackImage);

        MultipartBody.Part multipartCnicFront = MultipartBody.Part.createFormData("cnic_front_image", cnicFrontImage.getName(), cnicFrontRequestBody);
        MultipartBody.Part multipartCnicBack = MultipartBody.Part.createFormData("cnic_back_image", cnicBackImage.getName(), cnicBackRequestBody);
        MultipartBody.Part multipartImage = MultipartBody.Part.createFormData("verification_image", userProfileFile.getName(), frontRequestBody);


        Call<MainResponseModel> call = service.forBecomeAuthor(request_userId, request_penName, request_personalEmail, request_portfolio_url, multipartCnicFront, multipartCnicBack, multipartImage);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            Toast.makeText(VerifyAuthorActivity.this, "Your Request to Become Author has been Submitted to Novel Nest Administrators!", Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                            shadow_View.setVisibility(View.GONE);
                            Utilities.saveInt(VerifyAuthorActivity.this, "author_id", Integer.parseInt(user_id));
                            Utilities.saveString(VerifyAuthorActivity.this, "type", "2");
                            setResult(RESULT_OK);
                            finish();
                            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                        } else {
                            CustomCookieToast.showRequiredToast(VerifyAuthorActivity.this, response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(VerifyAuthorActivity.this, response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(VerifyAuthorActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(VerifyAuthorActivity.this, t.getMessage());
            }
        });
    }

    private void openMultipleImagesGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(Intent.createChooser(intent, "Select Picture"),PERMISSION_CODE);

    }

    private void openImagesGalleryFrontID() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(Intent.createChooser(intent, "Select Picture"),FRONT_IMAGE);

    }

    private void openImagesGalleryBackID() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(Intent.createChooser(intent, "Select Picture"),BACK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Uri selectedMediaUri = data.getData();
        if (resultCode == RESULT_OK){
            if (requestCode == PERMISSION_CODE) {
                image_uploadPic.setImageURI(selectedMediaUri);
                picturePath = FileUtils.getPath(context, selectedMediaUri);
        }
        }if (requestCode == FRONT_IMAGE){
            frontPicturePath = FileUtils.getPath(context, selectedMediaUri);
            image_frontSide.setVisibility(View.VISIBLE);
            image_frontSide.setImageURI(selectedMediaUri);
            btnSelectFrontSide.setVisibility(View.GONE);
            layout_imageCancelFront.setVisibility(View.VISIBLE);
        }if (requestCode == BACK_IMAGE){
            backPicturePath = FileUtils.getPath(context, selectedMediaUri);
            btnSelectBackSide.setVisibility(View.GONE);
            image_backSide.setVisibility(View.VISIBLE);
            image_backSide.setImageURI(selectedMediaUri);
            layout_imageCancelBack.setVisibility(View.VISIBLE);
        }
    }

}