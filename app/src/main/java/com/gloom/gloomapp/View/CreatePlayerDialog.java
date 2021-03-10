package com.gloom.gloomapp.View;

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

import com.gloom.gloomapp.Model.Player;
import com.gloom.gloomapp.R;
import com.gloom.gloomapp.Utils.DataConverter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class CreatePlayerDialog extends AppCompatDialogFragment {

    private static final String TAG = "CreatePlayerDialog";
    private EditText mName;
    private MaterialCardView mImageSelectBtn;
    private Button mSaveBtn;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private OnCreatePlayerListener onCreatePlayerListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.player_layout_dialog, null);

        builder.setView(view);
        builder.setCancelable(true);
        builder.setTitle(null);

        mName = view.findViewById(R.id.et_name);
        mImageSelectBtn = view.findViewById(R.id.select_image);
        mSaveBtn = view.findViewById(R.id.btn_save_player);
        mImageView = view.findViewById(R.id.partyImage);

        mImageSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to gallery
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();

                if (!name.isEmpty() && mBitmap != null) {
                    Player player = new Player(name, DataConverter.convertImage2ByteArray(mBitmap));
                    onCreatePlayerListener.saveNewPlayer(player);
                    dismiss();
                } else if (!name.isEmpty()) {
                    Player player = new Player(name, DataConverter.convertImage2ByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.default_player)));
                    onCreatePlayerListener.saveNewPlayer(player);
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
                    Log.d(TAG, "onActivityResult: ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onCreatePlayerListener = (OnCreatePlayerListener) context;
    }

    public interface OnCreatePlayerListener {
        void saveNewPlayer(Player player);
    }
}
