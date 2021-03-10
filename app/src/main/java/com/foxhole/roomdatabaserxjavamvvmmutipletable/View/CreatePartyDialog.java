package com.foxhole.roomdatabaserxjavamvvmmutipletable.View;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Party;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Utils.DataConverter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.vanniktech.rxpermission.RealRxPermission;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class CreatePartyDialog extends AppCompatDialogFragment {

    private EditText mParty;
    private EditText mReputation;
    private MaterialCardView mImageSelectBtn;
    private Button mSaveBtn;
    private ImageView mImageView;
    private CreatePartyListener mListener;
    private Bitmap mBitmap;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.party_layout_dialog, null);

        builder.setView(view);
        builder.setCancelable(true);
        builder.setTitle(null);

        mParty = view.findViewById(R.id.et_party_name);
        mImageSelectBtn = view.findViewById(R.id.select_image);
        mSaveBtn = view.findViewById(R.id.btn_save_party);
        mImageView = view.findViewById(R.id.partyImage);
        mReputation = view.findViewById(R.id.et_party_reputation);

        mImageSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check Permission
                RealRxPermission.getInstance(getActivity())
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe();
                //Go to gallery
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String party_name = mParty.getText().toString();
                String reputation = mReputation.getText().toString();

                if (!party_name.isEmpty() && mBitmap != null) {
                    Party party = new Party(party_name, DataConverter.convertImage2ByteArray(mBitmap), reputation);
                    mListener.saveNewParty(party);
                    dismiss();
                } else if(!party_name.isEmpty()){
                    Party party = new Party(party_name, DataConverter.convertImage2ByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.default_party)), reputation);
                    mListener.saveNewParty(party);
                    dismiss();
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                File imageFile = new File(getRealPathFromURI(selectedImage));
                try {
                    mBitmap = new Compressor(getActivity()).compressToBitmap(imageFile);
                    mImageView.setImageBitmap(mBitmap);
                    Log.d("CreatePartyDialog", "onActivityResult: ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreatePartyListener) context;
    }

    public interface CreatePartyListener {
        void saveNewParty(Party party);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
