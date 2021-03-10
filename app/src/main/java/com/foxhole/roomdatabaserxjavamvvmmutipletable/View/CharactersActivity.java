package com.foxhole.roomdatabaserxjavamvvmmutipletable.View;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter.CharacterAdapter;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Character;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel.CharacterViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CharactersActivity extends AppCompatActivity  implements CreateCharacterDialog.OnCreateCharacterListener{
    private static final String TAG = "CharactersActivity_Tag";
    private static final int START_DELAY = 2;

    private TextView mTitleView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private CharacterAdapter characterAdapter;
    private String mParty = "";
    private int player_id;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CharacterViewModel characterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        mTitleView = findViewById(R.id.toolbar_title);
        mProgressBar = findViewById(R.id.progress);
        mRecyclerView = findViewById(R.id.playersRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        intToolbar();
        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mParty = extras.getString("party");
            player_id = extras.getInt("uid");
            Log.d(TAG, "onStart: "+ player_id +""+mParty);
            mTitleView.setText(mParty);

        }

        Disposable disposable = characterViewModel.getAllCharacters(player_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Character>>() {
                    @Override
                    public void accept(List<Character> characters) throws Exception {
                        Log.d(TAG, "accept: getAllPlayers");
                        characterAdapter = new CharacterAdapter(characters);
                        mRecyclerView.setAdapter(characterAdapter);
                        for (Character character: characters){
                            Log.d(TAG, "accept: "+character.getName());
                        }
                    }
                });

        compositeDisposable.add(disposable);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                characterViewModel.delete(characterAdapter.getCharacterAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mRecyclerView);


        //Check Loading State
        characterViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: "+aBoolean);
                if (aBoolean!=null){
                    if (aBoolean){
                        mProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab_parties);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddCharacterDialog();
            }
        });
    }

    private void openAddCharacterDialog() {
        CreateCharacterDialog createCharacterDialog = new CreateCharacterDialog();
        createCharacterDialog.show(getSupportFragmentManager(),"Create Character Dialog");
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deleteAllCharacters();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllCharacters() {
        characterViewModel.deleteAllCharactersByPlayer(player_id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void saveNewCharacter(Character character) {
        Character currentCharacter = character;
        currentCharacter.setPlayer_id(String.valueOf(player_id));
        characterViewModel.insert(currentCharacter);
    }

}
