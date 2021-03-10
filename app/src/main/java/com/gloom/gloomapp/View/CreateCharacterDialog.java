package com.gloom.gloomapp.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.gloom.gloomapp.Model.Character;
import com.gloom.gloomapp.R;
import com.gloom.gloomapp.Utils.DataConverter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class CreateCharacterDialog extends AppCompatDialogFragment {
    private static final String TAG = "CreatePlayerDialog";
    private EditText mName;
    private MaterialCardView mImageSelectBtn;
    private Button mSaveBtn;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private Spinner spinner;
    private CreateCharacterDialog.OnCreateCharacterListener onCreateCharacterListener;
    private String clazz[] = {"Sawas Skałosercy", "Orchidea Tkaczka Zaklęć", "Szczurok Myślołap", "Inoks Kark", "Człowiek Szelma", "Kwatryl Druciarz"};

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.character_dialog, null);

        builder.setView(view);
        builder.setCancelable(true);
        builder.setTitle(null);

        mName = view.findViewById(R.id.character_name);
        mImageSelectBtn = view.findViewById(R.id.select_image);
        mSaveBtn = view.findViewById(R.id.btn_save_character);
        mImageView = view.findViewById(R.id.partyImage);
        spinner = view.findViewById(R.id.ClassSpinner);


        spinner.setOnItemSelectedListener(spinner.getOnItemSelectedListener());
        final ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, clazz);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setPrompt(arrayAdapter.getItem(position).toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                String clazz = spinner.getPrompt().toString();

                if (!name.isEmpty() && mBitmap != null) {
                    Character character = new Character(name, DataConverter.convertImage2ByteArray(mBitmap), clazz);
                    onCreateCharacterListener.saveNewCharacter(character);
                    dismiss();
                } else {
                    Snackbar.make(view, R.string.error_note, Snackbar.LENGTH_LONG).show();
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
        if (cursor == null) { // Source is Dropbox or other similar local file path
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
        onCreateCharacterListener = (CreateCharacterDialog.OnCreateCharacterListener) context;
    }

    public interface OnCreateCharacterListener {
        void saveNewCharacter(Character character);
    }

}
