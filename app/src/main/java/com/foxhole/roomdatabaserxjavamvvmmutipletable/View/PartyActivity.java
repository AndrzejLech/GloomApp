package com.foxhole.roomdatabaserxjavamvvmmutipletable.View;

import android.content.Intent;
import android.os.Bundle;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter.PartyAdapter;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Party;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel.PartyActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PartyActivity extends AppCompatActivity implements
        CreatePartyDialog.CreatePartyListener,
        PartyAdapter.OnPartyClickListener {

    private static final String TAG = "MainActivity_TAG";
    private static final int START_DELAY = 3;

    private PartyActivityViewModel partyActivityViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView mRecyclerView;
    private PartyAdapter partyAdapter;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        intToolbar();
        FloatingActionButton fab = findViewById(R.id.fab_parties);
        Button ib = findViewById(R.id.ItemsButton);
        mRecyclerView = findViewById(R.id.partyRecycler);
        mProgressbar = findViewById(R.id.progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //ViewModel
        partyActivityViewModel = ViewModelProviders.of(this).get(PartyActivityViewModel.class);

        //Disposable for avoid memory leak
        Disposable disposable = partyActivityViewModel.getAllParty().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Party>>() {
                    @Override
                    public void accept(List<Party> partys) throws Exception {
                        Log.d(TAG, "accept: Called");
                        setDataToRecyclerView(partys);
                    }
                });


        //Add Disposable
        compositeDisposable.add(disposable);

        //Check Loading State
        partyActivityViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: " + aBoolean);
                if (aBoolean != null) {
                    if (aBoolean) {
                        mProgressbar.setVisibility(View.VISIBLE);
                    } else {
                        mProgressbar.setVisibility(View.GONE);
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreatePartyDialog();
            }
        });

        ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ItemsActivity.class);
                startActivity(i);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                partyActivityViewModel.deleteParty(partyAdapter.getPartyAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private void setDataToRecyclerView(List<Party> parties) {
        partyAdapter = new PartyAdapter(parties);
        partyAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(partyAdapter);
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
            deleteParty();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteParty() {
        partyActivityViewModel.deleteAllParty();
    }

    public void openCreatePartyDialog() {
        CreatePartyDialog createPartyDialog = new CreatePartyDialog();
        createPartyDialog.show(getSupportFragmentManager(), "create dialog");
    }

    @Override
    public void saveNewParty(Party party) {
        Log.d(TAG, "saveNewParty: " + party.getName());
        partyActivityViewModel.insert(party);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Remove Disposable
        compositeDisposable.dispose();
    }

    @Override
    public void onPartyClick(Party party) {
        Log.d(TAG, "onPartyClick: onItemClick");
        moveToMoviesActivity(party);
    }

    public void moveToMoviesActivity(Party party) {
        Intent intent = new Intent(PartyActivity.this, PlayersActivity.class);
        intent.putExtra("party", party.getName());
        intent.putExtra("uid", party.getUid());
        intent.putExtra("reputation", party.getReputation());
        startActivity(intent);
    }
}
