package com.gloom.gloomapp.View;

import android.content.Intent;
import android.os.Bundle;

import com.gloom.gloomapp.Adapter.PlayersAdapter;
import com.gloom.gloomapp.Model.Player;
import com.gloom.gloomapp.ViewModel.PlayerActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gloom.gloomapp.R;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayersActivity extends AppCompatActivity implements CreatePlayerDialog.OnCreatePlayerListener,
        PlayersAdapter.OnPlayerClickListener {

    private static final String TAG = "PlayersActivity_Tag";
    private static final int START_DELAY = 2;

    private TextView mTitleView;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private PlayersAdapter playersAdapter;
    private String mParty = "";
    private int party_id;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PlayerActivityViewModel playerActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        mTitleView = findViewById(R.id.toolbar_title);
        mProgressBar = findViewById(R.id.progress);
        mRecyclerView = findViewById(R.id.playersRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        intToolbar();
        playerActivityViewModel = ViewModelProviders.of(this).get(PlayerActivityViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mParty = extras.getString("party");
            party_id = extras.getInt("uid");
            Log.d(TAG, "onStart: " + party_id + "" + mParty);
            mTitleView.setText(mParty);

        }

        Disposable disposable = playerActivityViewModel.getAllPlayers(party_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Player>>() {
                    @Override
                    public void accept(List<Player> players) throws Exception {
                        Log.d(TAG, "accept: getAllPlayers");
                        setDataToRecyclerView(players);
                        for (Player player : players) {
                            Log.d(TAG, "accept: " + player.getName());
                        }
                    }
                });

        compositeDisposable.add(disposable);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                playerActivityViewModel.delete(playersAdapter.getPlayerAt(viewHolder.getAdapterPosition()));
            }

        }).attachToRecyclerView(mRecyclerView);


        //Check Loading State
        playerActivityViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: " + aBoolean);
                if (aBoolean != null) {
                    if (aBoolean) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab_players);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddPlayerDialog();
            }
        });
    }

    private void openAddPlayerDialog() {
        CreatePlayerDialog createPlayerDialog = new CreatePlayerDialog();
        createPlayerDialog.show(getSupportFragmentManager(), "Create Player Dialog");
    }

    private void setDataToRecyclerView(List<Player> players) {
        playersAdapter = new PlayersAdapter(players);
        playersAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(playersAdapter);
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_delete) {
            deleteAllPlayers();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllPlayers() {
        playerActivityViewModel.deleteAllPlayersByParty(party_id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void saveNewPlayer(Player player) {
        Player currentPlayer = player;
        currentPlayer.setParty_id(party_id);
        playerActivityViewModel.insert(currentPlayer);
    }

    @Override
    public void onPlayerClick(Player player) {
        moveToCharactersActivity(player);
    }

    public void moveToCharactersActivity(Player player) {
        Intent intent = new Intent(PlayersActivity.this, CharactersActivity.class);
        intent.putExtra("party", player.getName());
        intent.putExtra("uid", player.getUid());
        startActivity(intent);
    }
}
