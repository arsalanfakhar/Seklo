package com.trulyfuture.seklo.screens.uploadCV;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.trulyfuture.seklo.MainActivityViewModel;
import com.trulyfuture.seklo.databinding.FragmentUploadCVBinding;
import com.trulyfuture.seklo.models.ResumeResults;
import com.trulyfuture.seklo.utils.ProgressDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class UploadCVFragment extends Fragment {

    private FragmentUploadCVBinding binding;
    private static final int UPLOAD_CV_REQUEST = 1;
    private static final String TAG = "UploadCVFragment";
    private MainActivityViewModel activityViewModel;

    private ResumeResults.Resume userResume;
    private FirebaseStorage mFirebaseStorage;

    private UploadCVViewModel uploadCVViewModel;

    private Uri mFileUri = null;
    private String displayName = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUploadCVBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        uploadCVViewModel = ViewModelProviders.of(this).get(UploadCVViewModel.class);

        setupViews();
        setupObservers();

    }

    private void setupViews() {

        mFirebaseStorage = FirebaseStorage.getInstance();
        PRDownloader.initialize(getActivity().getApplicationContext());

        binding.browseBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, UPLOAD_CV_REQUEST);

        });

        binding.uploadCVBtn.setOnClickListener(view -> {
            if (displayName == null || mFileUri == null) {
                Toast.makeText(getContext(),"No CV selected",Toast.LENGTH_SHORT).show();
            } else {
                ProgressDialog.showLoader(getActivity());
//                uploadCVToFirebase();
                convertPdfToBytesAndUpload();
            }
        });
    }

    private void setupObservers() {

        loadCV();


    }

    private void loadCV() {
        ProgressDialog.showLoader(getActivity());

        activityViewModel.getUserResume(activityViewModel.getUserId()).observe(getViewLifecycleOwner(), resumeResults -> {
            if (!resumeResults.getResults().isEmpty()) {

                //Change button
                binding.uploadCVBtn.setText("Change your CV");

                userResume = resumeResults.getResults().get(0);

//                binding.pdfView.fromFile(null).load();
//                binding.pdfView.recycle();
//                binding.pdfView.setVisibility(View.INVISIBLE);

//                binding.pdfView.recycle();
                if (userResume.getResume().contains("data")) {
                    loadCvFromBase64();
                } else {
                    loadCvFromUrl();
                }
            } else {
                if (ProgressDialog.isShowing())
                    ProgressDialog.hideLoader();
            }
        });
    }

    private void loadCvFromBase64() {

        int index = userResume.getResume().indexOf("base64");

        String resumeBase64 = userResume.getResume().substring((index + 7));
//        Toast.makeText(getContext(),"index"+resumeBase64,Toast.LENGTH_SHORT).show();

//        Log.v(TAG, resumeBase64);
//
//
        byte[] pdfAsBytes = Base64.decode(resumeBase64, 0);

        binding.pdfView.fromBytes(pdfAsBytes).load();
        if (ProgressDialog.isShowing())
            ProgressDialog.hideLoader();
        binding.pdfView.setVisibility(View.VISIBLE);


    }

    private void loadCvFromUrl() {
        PRDownloader.download(userResume.getResume(), getRootDirectoryPath(), userResume.getResumeName())
                .build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        File cvfile = new File(getRootDirectoryPath(), userResume.getResumeName());

                        binding.pdfView.fromFile(cvfile).load();
                        if (ProgressDialog.isShowing())
                            ProgressDialog.hideLoader();
                        binding.pdfView.setVisibility(View.VISIBLE);


                    }

                    @Override
                    public void onError(Error error) {
                        if (ProgressDialog.isShowing())
                            ProgressDialog.hideLoader();
                        Toast.makeText(getContext(), "Error in downloading file", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPLOAD_CV_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of the selected file
            mFileUri = data.getData();



            String uriString = mFileUri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(mFileUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            binding.fileName.setText(displayName);

//            String encodedFile=encodeFileToBase64Binary(new File(myFile.getPath()));

//            Log.v(TAG,encodedFile);

//            String realPath=getRealPathFromURI(getContext(),uri);
//            Log.v(TAG, realPath);


//            //Load pdf
//            binding.pdfView.fromUri(mFileUri).load();
//            binding.pdfView.setVisibility(View.VISIBLE);

        }
    }

    private void convertPdfToBytesAndUpload(){

        try {
            InputStream inputStream=getActivity().getContentResolver().openInputStream(mFileUri);
            byte[] pdfInBytes=new byte[Objects.requireNonNull(inputStream).available()];
            inputStream.read(pdfInBytes);
            String encodedPdf=Base64.encodeToString(pdfInBytes,Base64.DEFAULT);

            encodedPdf="data:application/pdf;base64,"+encodedPdf;

            //Update the image
            Map<String, Object> resumeMap = new HashMap<>();
            resumeMap.put("User_ID", activityViewModel.getUserId());
            resumeMap.put("Resume_Name", displayName);
            resumeMap.put("Resume_Size", pdfInBytes.length);
            resumeMap.put("Resume_Type", "application/pdf");
            resumeMap.put("Resume", encodedPdf);

            mFileUri = null;
            displayName = null;

            if(userResume==null){
                uploadCVViewModel.addUserResume(resumeMap).observe(getViewLifecycleOwner(), sekloResults -> {
                    if (ProgressDialog.isShowing())
                        ProgressDialog.hideLoader();

                    if (sekloResults.getResults().getCode() == 1) {
                        Toast.makeText(getContext(), "Sucessfully uploaded", Toast.LENGTH_SHORT).show();
                        binding.pdfView.setVisibility(View.INVISIBLE);
                        loadCV();

                    }
                });

            }
            else {
                uploadCVViewModel.updateUserResume(resumeMap,userResume.getResumeID()).observe(getViewLifecycleOwner(), sekloResults -> {
                    if (ProgressDialog.isShowing())
                        ProgressDialog.hideLoader();

                    if (sekloResults.getResults().getCode() == 1) {
                        Toast.makeText(getContext(), "Sucessfully updated", Toast.LENGTH_SHORT).show();
                        binding.pdfView.setVisibility(View.INVISIBLE);
                        loadCV();
                    }
                });
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void uploadCVToFirebase() {
        StorageReference storageReference = mFirebaseStorage.getReference("Users/Cv/" + activityViewModel.getUserId());

        //Upload to storage
        StorageReference fileReference = storageReference.child("userCV");

        UploadTask uploadTask = fileReference.putFile(mFileUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            if (taskSnapshot.getMetadata() != null) {
                if (taskSnapshot.getMetadata().getReference() != null) {


                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(uri -> {

                        String fileUrl = uri.toString();

                        //Update the image
                        Map<String, Object> resumeMap = new HashMap<>();
                        resumeMap.put("User_ID", activityViewModel.getUserId());
                        resumeMap.put("Resume_Name", displayName);
                        resumeMap.put("Resume_Size", 3232);
                        resumeMap.put("Resume_Type", "application/pdf");
                        resumeMap.put("Resume", fileUrl);

                        mFileUri = null;
                        displayName = null;

                        if(userResume==null){
                            uploadCVViewModel.addUserResume(resumeMap).observe(getViewLifecycleOwner(), sekloResults -> {
                                if (ProgressDialog.isShowing())
                                    ProgressDialog.hideLoader();

                                if (sekloResults.getResults().getCode() == 1) {
                                    Toast.makeText(getContext(), "Sucessfully uploaded", Toast.LENGTH_SHORT).show();
                                    binding.pdfView.setVisibility(View.INVISIBLE);
                                    loadCV();

                                }
                            });

                        }
                        else {
                            uploadCVViewModel.updateUserResume(resumeMap,userResume.getResumeID()).observe(getViewLifecycleOwner(), sekloResults -> {
                                if (ProgressDialog.isShowing())
                                    ProgressDialog.hideLoader();

                                if (sekloResults.getResults().getCode() == 1) {
                                    Toast.makeText(getContext(), "Sucessfully updated", Toast.LENGTH_SHORT).show();
                                    binding.pdfView.setVisibility(View.INVISIBLE);
                                    loadCV();
                                }
                            });
                        }


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

    private String getRootDirectoryPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File mfile = ContextCompat.getExternalFilesDirs(getActivity().getApplicationContext(), null)[0];
            return mfile.getAbsolutePath();

        } else
            return getActivity().getApplicationContext().getFilesDir().getAbsolutePath();
    }

    private String encodeFileToBase64Binary(File yourFile) {
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return encoded;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


}