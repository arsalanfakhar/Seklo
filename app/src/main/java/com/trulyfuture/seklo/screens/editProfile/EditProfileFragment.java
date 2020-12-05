package com.trulyfuture.seklo.screens.editProfile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentEditProfileBinding;
import com.trulyfuture.seklo.databinding.PopoutLogoutBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.screens.login.LoginActivity;
import com.trulyfuture.seklo.utils.ProgressDialog;
import com.trulyfuture.seklo.utils.SharedPreferenceClass;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;

    private MainActivityViewModel activityViewModel;
    private Users currentUser;
    private EditProfileViewModel viewModel;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mFileUri = null;

    private FirebaseStorage mFirebaseStorage;
    private static final String TAG = "EditProfileFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressDialog.showLoader(getActivity());
        setupViews();
    }

    private void setupViews() {
        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);

        mFirebaseStorage = FirebaseStorage.getInstance();

        getUser();


        //On click listeners

        binding.changeProfilePicBtn.setOnClickListener(view -> {

//            Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();

            if (isInternetAvailable()) {
                Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
                mediaChooser.setType("image/*");
                startActivityForResult(Intent.createChooser(mediaChooser, "Select Picture"), PICK_IMAGE_REQUEST);
            } else
                Toast.makeText(getContext(), "Check your internet connection to update profile image", Toast.LENGTH_SHORT).show();


        });

        binding.signoutBtn.setOnClickListener(view -> {

            openLogoutPopup();




        });

        binding.updateOverviewBtn.setOnClickListener(view -> {

            if (!TextUtils.isEmpty(binding.overviewTxt.getText())) {
                Users users = new Users();
                users.setOverview(binding.overviewTxt.getText().toString().trim());
                viewModel.updateUserOverView(users, activityViewModel.getUserId()).observe(getViewLifecycleOwner(),sekloResults -> {
                    if(sekloResults.getResults().getCode()==1){
                        getUser();
                        Toast.makeText(getContext(),"Updated sucessfully",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        binding.saveBtn.setOnClickListener(view1 -> {
            if (!isFieldEmpty()) {
                Users user = new Users();
                user.setFname(binding.firstName.getText().toString());
                user.setLname(binding.lastName.getText().toString());
                user.setEmail(binding.email.getText().toString());
                user.setNumber(binding.mobileNumber.getText().toString());

                viewModel.updateUserDetails(user, activityViewModel.getUserId()).observe(getViewLifecycleOwner(), sekloResults -> {
                    if (sekloResults.getResults().getCode() == 1) {
//                        getUser();

                        Toast.makeText(getContext(), "Details updated sucessfully", Toast.LENGTH_SHORT).show();


                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_editProfileFragment_to_profileFragment);


                    } else {
                        Toast.makeText(getContext(), sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

    }

    private void getUser() {
        activityViewModel.getCurrentUser().observe(getViewLifecycleOwner(), userResults -> {

            if (userResults.getCode() == 1) {
                currentUser = userResults.getUserResultList().get(0);
                loadUserData();

                if (ProgressDialog.isShowing())
                    ProgressDialog.hideLoader();

            }
        });

    }

    private void loadUserData() {

        if (TextUtils.isEmpty(currentUser.getUserImage())) {
            //TODO Load dummy image
            Glide.with(this)
                    .load(R.drawable.nouser)
                    .into(binding.userImage);
        } else {
            Glide.with(this).asBitmap()
                    .load(currentUser.getUserImage())
                    .into(binding.userImage);
        }

        binding.firstName.setText(currentUser.getFname());
        binding.lastName.setText(currentUser.getLname());
        binding.email.setText(currentUser.getEmail());
        binding.mobileNumber.setText(currentUser.getNumber());

        if (currentUser.getOverview() != null) {
            if (!currentUser.getOverview().isEmpty())
                binding.overviewTxt.setText(currentUser.getOverview());
        }

    }

    private boolean isFieldEmpty() {
        if (TextUtils.isEmpty(binding.firstName.getText())
                || TextUtils.isEmpty(binding.lastName.getText())
                || TextUtils.isEmpty(binding.email.getText())
                || TextUtils.isEmpty(binding.mobileNumber.getText())) {
            Toast.makeText(getContext(), "Please fill all fields to continue", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void uploadImageToFirebase() {

        StorageReference storageReference = mFirebaseStorage.getReference("Users/ProfilePictures/" + currentUser.getID());

        //Upload to storage
        StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                + "." + getFileExtension(mFileUri));

        UploadTask uploadTask = fileReference.putFile(mFileUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            if (taskSnapshot.getMetadata() != null) {
                if (taskSnapshot.getMetadata().getReference() != null) {

                    mFileUri = null;
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(uri -> {

                        String fileUrl = uri.toString();

                        //Update the image

                        Map<String, String> userMap = new HashMap<>();
                        userMap.put("profilePic", fileUrl);

                        viewModel.updateUserImage(userMap, currentUser.getID()).observe(getViewLifecycleOwner(), sekloResults -> {
                            ProgressDialog.hideLoader();

                            Toast.makeText(getContext(), sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();
                            if (sekloResults.getResults().getCode() == 1) {
                                getUser();
                            }

                        });

                    });


                }
            }
        }).addOnFailureListener(e -> {
            //dismiss dialog
            if (ProgressDialog.isShowing())
                ProgressDialog.hideLoader();

            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mFileUri = data.getData();

            ProgressDialog.showLoader(getActivity());

            uploadImageToFirebase();
//            Glide.with(this).load(mFileUri).into(binding.userImage);

//            binding.userImage.setImageURI(mFileUri);

            //Convert image to base64

            //Open updating spinner

            //After its updated load the image
        } else {
            Toast.makeText(getContext(), "Error choosing image", Toast.LENGTH_SHORT).show();
        }

    }


    public static String getMimeType(Context context, Uri uri) {
        String extension;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }

    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void removeFromSharedPrefs() {
        SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(getContext(), SharedPreferenceClass.UserDetails);
        sharedPreferenceClass.RemoveValue("userId");
        sharedPreferenceClass.DoCommit();
    }

    private void openLogoutPopup(){
        PopoutLogoutBinding popoutLogoutBinding=PopoutLogoutBinding.inflate(getLayoutInflater());

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(popoutLogoutBinding.getRoot());

        AlertDialog dialog = alertBuilder.create();
        dialog.show();

        popoutLogoutBinding.logoutYesBtn.setOnClickListener(view -> {
            removeFromSharedPrefs();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

        });

        popoutLogoutBinding.logoutNoBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }

}