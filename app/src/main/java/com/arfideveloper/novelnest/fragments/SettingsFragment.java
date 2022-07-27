package com.arfideveloper.novelnest.fragments;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arfideveloper.novelnest.R;
import com.arfideveloper.novelnest.activities.FavoritesActivity;
import com.arfideveloper.novelnest.activities.PrivacyPolicyActivity;
import com.arfideveloper.novelnest.activities.ProfileImageActivity;
import com.arfideveloper.novelnest.activities.UploadNovelActivity;
import com.arfideveloper.novelnest.activities.VerifyAuthorActivity;
import com.arfideveloper.novelnest.activities.auth.ChangePasswordActivity;
import com.arfideveloper.novelnest.activities.auth.EditProfileActivity;
import com.arfideveloper.novelnest.activities.auth.LoginActivity;
import com.arfideveloper.novelnest.activities.auth.SignUpActivity;
import com.arfideveloper.novelnest.apimodels.MainResponseModel;
import com.arfideveloper.novelnest.network.GetDataService;
import com.arfideveloper.novelnest.network.RetrofitClientInstance;
import com.arfideveloper.novelnest.utilities.CustomCookieToast;
import com.arfideveloper.novelnest.utilities.Utilities;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {
    public static String PACKAGE_NAME;
    Context context;
    GetDataService service;
    RelativeLayout layout_contactUs, layout_profileBg, layout_favoriteNovels, layout_userEditProfile,
            layout_padlockChangePassword, layout_socialChangeLanguage, layout_deleteYourAccount, layout_shareProfileMain,
            layout_privacyPolicyCopy, layout_contactUsAt, layout_aboutUsUser, layout_logOut, layout_mainBecomeAuthor, layout_mainUploadNovel;
    LinearLayout layout_profileDetails;
    RoundedImageView image_profile;
    ImageView ivAuthorArrow, ivAuthor;
    TextView txt_profileName, txt_countryName, textBecomeAuthor;
    String user_id, author_id, profile_image, name, bio, userType, isVerified;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        userType = Utilities.getString(requireActivity(), "type");
                        setAuthorViews();
                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        context = requireActivity();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        user_id = String.valueOf(Utilities.getInt(context, "user_id"));
        author_id = String.valueOf(Utilities.getInt(context, "author_id"));
        name = Utilities.getString(context, "name");
        userType = Utilities.getString(context, "type");
        isVerified = Utilities.getString(context, "is_verified");
        PACKAGE_NAME = context.getApplicationContext().getPackageName();

        txt_profileName = view.findViewById(R.id.txt_profileName);
        txt_countryName = view.findViewById(R.id.txt_countryName);
        textBecomeAuthor = view.findViewById(R.id.txt_becomeAuthor);
        ivAuthor = view.findViewById(R.id.image_author);
        ivAuthorArrow = view.findViewById(R.id.image_forwardArrowAuthor);

        layout_favoriteNovels = view.findViewById(R.id.layout_favoriteNovels);
        layout_userEditProfile = view.findViewById(R.id.layout_userEditProfile);
        layout_padlockChangePassword = view.findViewById(R.id.layout_padlockChangePassword);
        layout_socialChangeLanguage = view.findViewById(R.id.layout_socialChangeLanguage);
        layout_deleteYourAccount = view.findViewById(R.id.layout_deleteYourAccount);
        layout_shareProfileMain = view.findViewById(R.id.layout_shareAppMain);
        layout_privacyPolicyCopy = view.findViewById(R.id.layout_privacyPolicyCopy);
        layout_contactUsAt = view.findViewById(R.id.layout_contactUsAt);
        layout_aboutUsUser = view.findViewById(R.id.layout_aboutUsUser);
        layout_logOut = view.findViewById(R.id.layout_logOut);
        layout_profileBg = view.findViewById(R.id.layout_profileBg);
        layout_contactUs = view.findViewById(R.id.layout_contactUs);
        layout_mainBecomeAuthor = view.findViewById(R.id.layout_mainBecomeAuthor);
        layout_profileDetails = view.findViewById(R.id.layout_profileDetails);
        image_profile = view.findViewById(R.id.image_profile);
        layout_mainUploadNovel = view.findViewById(R.id.layout_mainUploadNovel);


        setAuthorViews();


        layout_favoriteNovels.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, FavoritesActivity.class);
            context.startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_userEditProfile.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, EditProfileActivity.class);
            context.startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_padlockChangePassword.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ChangePasswordActivity.class);
            context.startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_privacyPolicyCopy.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, PrivacyPolicyActivity.class);
            context.startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_profileBg.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, EditProfileActivity.class);
            context.startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_logOut.setOnClickListener(view1 -> {
            doYouWantToLogout();
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_deleteYourAccount.setOnClickListener(view12 -> {
            doYouWantToDeleteAccount();
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_contactUs.setOnClickListener(view12 -> {
            showPopupContactUs();
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        image_profile.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfileImageActivity.class);
            intent.putExtra("imageUrl", profile_image);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
        });

        layout_mainBecomeAuthor.setOnClickListener(v -> {
            if (Objects.equals(userType, "1")) {
                Intent intent = new Intent(context, VerifyAuthorActivity.class);
                activityResultLauncher.launch(intent);
                requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
            }
        });

        layout_mainUploadNovel.setOnClickListener(v -> {
            if (Objects.equals(userType, "2")) {
                if (Objects.equals(isVerified, "true")) {
                    Intent intent = new Intent(context, UploadNovelActivity.class);
                    startActivity(intent);
                    requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                } else {
                    CustomCookieToast.showRequiredToast(requireActivity(), "Please wait for your account to be verified");
                }
            } else {
                showPopupForVerifyUser();
            }
        });

        layout_shareProfileMain.setOnClickListener(view13 -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/work/apps/details?id=" + PACKAGE_NAME);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        return view;
    }

    private void setAuthorViews() {

        if (Objects.equals(userType, "2")) {
            if (TextUtils.equals("true", isVerified)) {
                layout_mainBecomeAuthor.setVisibility(View.GONE);
                layout_mainUploadNovel.setVisibility(View.VISIBLE);
            } else {
                layout_mainBecomeAuthor.setVisibility(View.VISIBLE);
                layout_mainUploadNovel.setVisibility(View.GONE);
                textBecomeAuthor.setText("Requested for Author");
                textBecomeAuthor.setTextColor(ContextCompat.getColor(context, R.color.colorDoveGray));
                ivAuthor.setColorFilter(ContextCompat.getColor(context, R.color.colorDoveGray));
                ivAuthorArrow.setColorFilter(ContextCompat.getColor(context, R.color.colorDoveGray));
            }
        } else {
            layout_mainUploadNovel.setVisibility(View.GONE);
            layout_mainBecomeAuthor.setVisibility(View.VISIBLE);
            textBecomeAuthor.setText("Become an Author");
            textBecomeAuthor.setTextColor(ContextCompat.getColor(context, R.color.black_off));
            ivAuthor.setColorFilter(ContextCompat.getColor(context, R.color.black_off));
            ivAuthorArrow.setColorFilter(ContextCompat.getColor(context, R.color.black_off));
        }
    }

    private void showPopupContactUs() {
        AlertDialog reportPostPopup;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.popup_contact_us, null);
        builder.setView(customLayout);

        TextView btn_cancel = customLayout.findViewById(R.id.text_cancel);
        TextView btn_send = customLayout.findViewById(R.id.text_send);
        EditText edit_email = customLayout.findViewById(R.id.edit_email);
        EditText edit_subject = customLayout.findViewById(R.id.edit_subject);
        EditText edit_message = customLayout.findViewById(R.id.edit_message);

        reportPostPopup = builder.create();
        reportPostPopup.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        reportPostPopup.show();
        reportPostPopup.setCancelable(false);

        btn_cancel.setOnClickListener(v -> reportPostPopup.dismiss());

        btn_send.setOnClickListener(v -> {
            // Clear SharedPref and send to login
            String email = edit_email.getText().toString();
            boolean isValidEmail = Utilities.isValidEmail(email);
            String subject = edit_subject.getText().toString();
            String message = edit_message.getText().toString();
            if (email.isEmpty()) {
                CustomCookieToast.showFailureToast(requireActivity(), "Please Enter Email");
            } else if (!isValidEmail) {
                CustomCookieToast.showRequiredToast(requireActivity(), "Please Enter Valid Email");
            } else if (subject.isEmpty()) {
                CustomCookieToast.showFailureToast(requireActivity(), "Please Enter Subject");
            } else if (message.isEmpty()) {
                CustomCookieToast.showFailureToast(requireActivity(), "Please Enter Message");
            } else {
                reportPostPopup.dismiss();
                requestForContactUs(email, subject, message, name);
            }
        });
    }

    private void doYouWantToLogout() {
        AlertDialog reportPostPopup;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.popup_logout, null);
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
            requestLogoutUser(String.valueOf(user_id));
        });
    }

    public void requestLogoutUser(String user_id) {
        Call<MainResponseModel> call = service.logoutUser(user_id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {
                            requireActivity().finishAffinity();
                            Utilities.clearSharedPref(context);
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("finish", true);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                            startActivity(intent);
                        } else {
                            CustomCookieToast.showRequiredToast(requireActivity(), response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(requireActivity(), response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(requireActivity(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {

            }
        });
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
            requestDeleteAccount(String.valueOf(user_id));
        });
    }

    private void showPopupForVerifyUser() {
        AlertDialog reportPostPopup;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.popup_author_verify, null);
        builder.setView(customLayout);

        TextView btn_yes = customLayout.findViewById(R.id.text_Yes);
        TextView btn_cancel = customLayout.findViewById(R.id.text_No);

        reportPostPopup = builder.create();
        reportPostPopup.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        reportPostPopup.show();
        reportPostPopup.setCancelable(false);

        btn_cancel.setOnClickListener(v -> reportPostPopup.dismiss());

        btn_yes.setOnClickListener(v -> {
            reportPostPopup.dismiss();
            Intent intent = new Intent(context, VerifyAuthorActivity.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
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
                            Utilities.clearSharedPref(context);
                            requireActivity().finishAffinity();
                            Intent intent = new Intent(context, SignUpActivity.class);
                            startActivity(intent);
                            ((Activity) context).overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                            startActivity(intent);
                        } else {
                            CustomCookieToast.showRequiredToast(requireActivity(), response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(requireActivity(), response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(requireActivity(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {

            }
        });
    }

    public void requestForContactUs(String email, String subject, String message, String name) {
        Call<MainResponseModel> call = service.forContactUs(email, subject, message, name);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MainResponseModel> call, @NonNull Response<MainResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        boolean status = response.body().isStatus();
                        if (status) {

                        } else {
                            CustomCookieToast.showRequiredToast(requireActivity(), response.body().getMessage());
                        }
                    } else {
                        CustomCookieToast.showFailureToast(requireActivity(), response.message());
                    }
                } else {
                    CustomCookieToast.showFailureToast(requireActivity(), response.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<MainResponseModel> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        profile_image = Utilities.getString(context, "profile_image");
        name = Utilities.getString(context, "name");
        bio = Utilities.getString(context, "bio");

        Glide.with(context).load(profile_image).placeholder(R.drawable.default_place_holder).error(R.drawable.default_place_holder).into(image_profile);
        txt_profileName.setText(name);
        txt_countryName.setText(bio);
    }
}
