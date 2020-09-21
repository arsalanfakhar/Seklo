package com.trulyfuture.seklo.screens.editProfile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.R;
import com.trulyfuture.seklo.databinding.FragmentEditProfileBinding;
import com.trulyfuture.seklo.models.Users;
import com.trulyfuture.seklo.utils.ProgressDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

        getUser();


        //On click listeners

        binding.changeProfilePicBtn.setOnClickListener(view -> {

            Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();

//            Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
//            mediaChooser.setType("image/*");
//            startActivityForResult(Intent.createChooser(mediaChooser, "Select Picture"), PICK_IMAGE_REQUEST);

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
                        getUser();

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
            // Decode base64 string to image
//            String removeAdditionalText=currentUser.getUserImage().substring(22);
//
//            byte[] decodedString = Base64.decode(removeAdditionalText, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            binding.userImage.setImageBitmap(decodedByte);
//
//            Glide.with(this).asBitmap()
//                    .load(currentUser.getUserImage())
//                    .into(binding.userImage);
        }

//        if (TextUtils.isEmpty(currentUser.getUserImage())) {
//            //Load dummy image
//
//        } else {
//
//            // Decode base64 string to image
//            byte[] decodedString = Base64.decode(currentUser.getUserImage(), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            binding.userImage.setImageBitmap(decodedByte);
//
//
//        }


        binding.firstName.setText(currentUser.getFname());
        binding.lastName.setText(currentUser.getLname());
        binding.email.setText(currentUser.getEmail());
        binding.mobileNumber.setText(currentUser.getNumber());
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mFileUri = data.getData();

//            Glide.with(this).load(mFileUri).into(binding.userImage);

//            binding.userImage.setImageURI(mFileUri);

            //Convert image to base64
            try {
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(mFileUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , mFileUri);
                String encodedImage = encodeImage(selectedImage);

                //Set encoded image
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.userImage.setImageBitmap(decodedByte);


                encodedImage = "data:image/png;base64," + encodedImage;

                Log.v(TAG, encodedImage);


                Map<String, String> userMap = new HashMap<>();
                userMap.put("userPic", encodedImage);

                viewModel.updateUserImage(userMap, activityViewModel.getUserId()).observe(getViewLifecycleOwner(), sekloResults -> {
                    Toast.makeText(getContext(), sekloResults.getResults().getMessage(), Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Open updating spinner

            //After its updated load the image
        } else {
            Toast.makeText(getContext(), "Error chosing image", Toast.LENGTH_SHORT).show();
        }

    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
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

}