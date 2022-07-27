package com.arfideveloper.novelnest.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.apimodels.CategoriesModel;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.fragments.HomeFragment;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.FileUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadNovelActivity extends AppCompatActivity {

    Context context;
    GetDataService service;
    View shadow_View;
    GifImageView loading;

    RelativeLayout layout_backBtn, layout_novelName, layout_novelsEmptyPicture, layout_imageCancel,
            layout_pdfMain, layout_novelDescription, layout_descriptionMain, layout_selectPdf,
            layout_iconPdf, layout_pdfFileSelect, layout_doted, layout_seePdf;
    Button btn_uploadNovels;
    ImageView image_pdfSelect, image_descriptionSelect, image_pdfIcon;

    TextView text_selectPdf;
    EditText edit_novelName, edit_description;

    MaterialSpinner languageSpinner, categorySpinner;
    RoundedImageView image_novel;


    int author_id;

    boolean isPdfSelected = false;

    public static final int PERMISSION_CODE = 1001;

    int SELECT_PDF = 500;

    List<CategoriesModel> categoriesModelList;
    List<String> categoriesName = new ArrayList<>();

    String language = "", category_id = "", picturePathNovel = "", picturePathNovelFile = "", pdfPath = "", novelType = "text";

    @SuppressLint({"ClickableViewAccessibility", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_novel);
        context = UploadNovelActivity.this;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        shadow_View = findViewById(R.id.shadow_View);
        loading = findViewById(R.id.loading);

        author_id = getIntent().getIntExtra("author_id", 1);


        layout_backBtn = findViewById(R.id.layout_backBtn);
        layout_novelName = findViewById(R.id.layout_novelName);
        btn_uploadNovels = findViewById(R.id.btn_uploadNovels);
        languageSpinner = findViewById(R.id.languageSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        layout_novelsEmptyPicture = findViewById(R.id.layout_novelsEmptyPicture);
        image_novel = findViewById(R.id.image_novel);
        layout_imageCancel = findViewById(R.id.layout_imageCancel);
        edit_novelName = findViewById(R.id.edit_novelName);
        layout_pdfMain = findViewById(R.id.layout_pdfMain);
        image_pdfSelect = findViewById(R.id.image_pdfSelect);
        layout_descriptionMain = findViewById(R.id.layout_descriptionMain);
        image_descriptionSelect = findViewById(R.id.image_descriptionSelect);
        layout_novelDescription = findViewById(R.id.layout_novelDescription);
        layout_iconPdf = findViewById(R.id.layout_iconPdf);
        edit_description = findViewById(R.id.edit_description);
        layout_selectPdf = findViewById(R.id.layout_selectPdf);
        layout_pdfFileSelect = findViewById(R.id.layout_pdfFileSelect);
        layout_doted = findViewById(R.id.layout_doted);
        text_selectPdf = findViewById(R.id.text_selectPdf);
        image_pdfIcon = findViewById(R.id.image_pdfIcon);
        layout_seePdf = findViewById(R.id.layout_seePdf);

        categoriesModelList = HomeFragment.categoriesModelList;
        setEditTextSelected();

        layout_backBtn.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });


        languageSpinner.setItems("English", "Urdu");

        languageSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> language = item);

        categorySpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>)
                (view, position, id, item) -> category_id = String.valueOf(categoriesModelList.get(position).getId()));

        for (int i = 0; i < categoriesModelList.size(); i++) {
            categoriesName.add(categoriesModelList.get(i).getCategory_name());
        }
        categorySpinner.setItems(categoriesName);


        layout_imageCancel.setOnClickListener(v -> {
            layout_novelsEmptyPicture.setVisibility(View.VISIBLE);
            image_novel.setVisibility(View.GONE);
            picturePathNovel = null;
            layout_imageCancel.setVisibility(View.GONE);
        });

        layout_selectPdf.setOnClickListener(v -> {
            pickPDFFromGallery();
        });

        layout_pdfMain.setOnClickListener(v -> {
            novelType = "pdf";
            if (!isPdfSelected) {
                setPdfSelected();
            }
        });

        layout_descriptionMain.setOnClickListener(v -> {
            novelType = "text";
            if (isPdfSelected) {
                setEditTextSelected();
            }
        });


        layout_novelsEmptyPicture.setOnClickListener(view -> {
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

        btn_uploadNovels.setOnClickListener(v -> {

            String novelName = edit_novelName.getText().toString();
            String languages = languageSpinner.getText().toString();

            if (isPdfSelected) {

                if (picturePathNovel.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please upload novel picture");
                } else if (novelName.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please write novel name");
                } else if (languages.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please Select Language");
                } else if (category_id.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please Select category");
                } else {
                    loading.setVisibility(View.VISIBLE);
                    shadow_View.setVisibility(View.VISIBLE);
                    requestForAddNovelsPdf(String.valueOf(author_id), category_id, novelName, picturePathNovel, novelType, picturePathNovelFile, language);
                }
            } else {
                String description = edit_description.getText().toString();
                if (picturePathNovel.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please upload novel picture");
                } else if (novelName.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please write novel name");
                } else if (languages.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please Select Language");
                } else if (category_id.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please Select category");
                } else if (description.isEmpty()) {
                    CustomCookieToast.showRequiredToast(UploadNovelActivity.this, "Please some write novel description");
                } else {
                    loading.setVisibility(View.VISIBLE);
                    shadow_View.setVisibility(View.VISIBLE);
                    requestForAddNovelsText(String.valueOf(author_id), category_id, novelName, picturePathNovel, novelType, description, language);
                }
            }
        });


        edit_description.setOnTouchListener((v, event) -> {
            if (edit_description.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_SCROLL) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    return true;
                }
            }
            return false;
        });

    }

    private void setEditTextSelected() {
        isPdfSelected = false;
        layout_novelDescription.setEnabled(true);
        edit_description.setEnabled(true);
        layout_selectPdf.setEnabled(false);
        image_descriptionSelect.setImageResource(R.drawable.selected_radio_icon);
        image_pdfSelect.setImageResource(R.drawable.unselect_radio_icon);

        layout_novelDescription.setBackgroundResource(R.drawable.bg_white_outline_app_color_rounded_4dp);
        edit_description.setHintTextColor(this.getResources().getColor(R.color.AppColor));
        layout_doted.setBackgroundResource(R.drawable.bg_grey_doted_rounded_10dp);
    }

    private void setPdfSelected() {
        isPdfSelected = true;
        layout_selectPdf.setEnabled(true);
        layout_novelDescription.setEnabled(false);

        edit_description.setEnabled(false);
        image_pdfSelect.setImageResource(R.drawable.selected_radio_icon);
        image_descriptionSelect.setImageResource(R.drawable.unselect_radio_icon);

        layout_novelDescription.setBackgroundResource(R.drawable.bg_grey_outline_grey_color_rounded_4dp);
        edit_description.setHintTextColor(this.getResources().getColor(R.color.grey));
        layout_doted.setBackgroundResource(R.drawable.doted_rounded_10dp);
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
                Toast.makeText(UploadNovelActivity.this, "Permission were denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void requestForAddNovelsText(String author_id, String category_id, String novel_title, String picturePathNovel, String novel_type, String novel_text, String language) {
        File novelImageFile = new File(picturePathNovel);

        RequestBody request_author_id = RequestBody.create(okhttp3.MediaType.parse("text/plain"), author_id);
        RequestBody request_category_id = RequestBody.create(okhttp3.MediaType.parse("text/plain"), category_id);
        RequestBody request_novel_title = RequestBody.create(okhttp3.MediaType.parse("text/plain"), novel_title);
        RequestBody request_novel_type = RequestBody.create(okhttp3.MediaType.parse("text/plain"), novel_type);
        RequestBody request_novel_text = RequestBody.create(okhttp3.MediaType.parse("text/plain"), novel_text);
        RequestBody request_language = RequestBody.create(okhttp3.MediaType.parse("text/plain"), language);
        RequestBody request_novel_image = RequestBody.create(okhttp3.MediaType.parse("*/*"), novelImageFile);
        MultipartBody.Part multipartNovelImage = MultipartBody.Part.createFormData("novel_banner", novelImageFile.getName(), request_novel_image);


        Call<MainResponseModel> call = service.
                addNovelText(request_author_id, request_category_id, request_novel_title,
                        request_novel_type, request_novel_text, request_language, multipartNovelImage);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    loading.setVisibility(View.GONE);
                    shadow_View.setVisibility(View.GONE);
                    if (status) {
                        finish();
                        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                    } else {
                        CustomCookieToast.showRequiredToast(UploadNovelActivity.this, response.body().getMessage());
                    }
                } else {
                    loading.setVisibility(View.GONE);
                    shadow_View.setVisibility(View.GONE);
                    CustomCookieToast.showFailureToast(UploadNovelActivity.this, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(UploadNovelActivity.this, t.getMessage());
            }
        });
    }

    public void requestForAddNovelsPdf(String author_id, String category_id, String novel_title,
                                       String picturePathNovel, String novel_type, String picturePathNovelFile, String language) {
        File novelImageFile = new File(picturePathNovel);
        File novelPdfFile = new File(picturePathNovelFile);

        RequestBody request_author_id = RequestBody.create(okhttp3.MediaType.parse("text/plain"), author_id);
        RequestBody request_category_id = RequestBody.create(okhttp3.MediaType.parse("text/plain"), category_id);
        RequestBody request_novel_title = RequestBody.create(okhttp3.MediaType.parse("text/plain"), novel_title);
        RequestBody request_novel_type = RequestBody.create(okhttp3.MediaType.parse("text/plain"), novel_type);
        RequestBody request_language = RequestBody.create(okhttp3.MediaType.parse("text/plain"), language);
        RequestBody request_novel_file = RequestBody.create(okhttp3.MediaType.parse("*/*"), novelPdfFile);
        RequestBody request_novel_image = RequestBody.create(okhttp3.MediaType.parse("*/*"), novelImageFile);
        MultipartBody.Part multipartNovelImage = MultipartBody.Part.createFormData("novel_banner", novelImageFile.getName(), request_novel_image);
        MultipartBody.Part multipartNovelFile = MultipartBody.Part.createFormData("novel_file", novelPdfFile.getName(), request_novel_file);


        Call<MainResponseModel> call = service.addNovelForPdf(request_author_id, request_category_id, request_novel_title, request_novel_type, request_language, multipartNovelFile, multipartNovelImage);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    boolean status = response.body().isStatus();
                    loading.setVisibility(View.GONE);
                    shadow_View.setVisibility(View.GONE);
                    if (status) {
                        finish();
                        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                    } else {
                        CustomCookieToast.showRequiredToast(UploadNovelActivity.this, response.body().getMessage());
                    }
                } else {
                    loading.setVisibility(View.GONE);
                    shadow_View.setVisibility(View.GONE);
                    CustomCookieToast.showFailureToast(UploadNovelActivity.this, response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {
                shadow_View.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                CustomCookieToast.showFailureToast(UploadNovelActivity.this, t.getMessage());
            }
        });
    }

    private void openMultipleImagesGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(Intent.createChooser(intent, "Select Picture"), PERMISSION_CODE);
    }

    private void pickPDFFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] mimeTypes = {"application/pdf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityIfNeeded(intent, SELECT_PDF);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            Uri selectedMediaUri = data.getData();
            if (requestCode == SELECT_PDF) {
                pdfPath = FileUtils.getPath(context, selectedMediaUri);
                assert pdfPath != null;
                if (!pdfPath.isEmpty()) {
                    text_selectPdf.setText("Change Pdf");
                    layout_seePdf.setVisibility(View.VISIBLE);
                    image_pdfIcon.setImageURI(selectedMediaUri);
                    image_pdfIcon.setImageResource(R.drawable.ic_video_library);

                    layout_seePdf.setOnClickListener(v -> {
                        Intent intent = new Intent(UploadNovelActivity.this, PdfActivity.class);
                        intent.putExtra("pdfUri", selectedMediaUri.toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                    });

                } else {
                    Toast.makeText(context, "Pdf is Not Selected", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Pdf is Selected", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == PERMISSION_CODE) {
                picturePathNovel = FileUtils.getPath(context, selectedMediaUri);
                image_novel.setImageURI(selectedMediaUri);
                layout_novelsEmptyPicture.setVisibility(View.GONE);
                image_novel.setVisibility(View.VISIBLE);
                layout_imageCancel.setVisibility(View.VISIBLE);
            }

        }

    }

}