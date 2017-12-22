package com.example.umutserifler.inviatravelapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.jar.Manifest;

import adapter.RepoBaseAdapter;
import instance.RepoModalClassSingleton;
import listener.RepoModelClassListener;
import modal.ITRepoModalClass;
import modal.RepoJSONObjectItem;
import modal.RepoSubscriberJSONObjectItem;

public class MainActivity extends AppCompatActivity implements RepoModelClassListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private ListView repoListView;

    ArrayList<RepoJSONObjectItem> repoListViewArrayList = new ArrayList<RepoJSONObjectItem>();
    private  RepoBaseAdapter _repoBaseAdapter;

    ITRepoModalClass repoModalClass; //= RepoModalClassSingleton.getInstance(MainActivity.this).getRepoClass();

    ProgressDialog loadingDialog;


    SearchView repoSearchView;
    Button searchButton;

    String tempSearchViewQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        repoModalClass = RepoModalClassSingleton.getInstance(MainActivity.this).getRepoClass();
        repoModalClass.setListener(MainActivity.this);

        initializeComponents();

        repoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailRepoActivity.class);
                String repoName  = repoListViewArrayList.get(position).getRepositoryNameString();
                String subscribersUrl  = repoListViewArrayList.get(position).getSubscribersURL();
                intent.putExtra("repoName", repoName);
                intent.putExtra("subscribersUrl", subscribersUrl);
                startActivity(intent);
            }
        });



        repoSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(tempSearchViewQuery.equals(query) || query.isEmpty()){
                    return false;
                }
                callLoadingWindow();
                repoModalClass.getRequestSearch(query);
                repoSearchView.clearFocus();
                tempSearchViewQuery = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tempSearchViewQuery.equals(String.valueOf(repoSearchView.getQuery())) || (repoSearchView.getQuery().length() == 0)){
                    return;
                }
                callLoadingWindow();
                repoModalClass.getRequestSearch(String.valueOf(repoSearchView.getQuery()));
                repoSearchView.clearFocus();
                tempSearchViewQuery = String.valueOf(repoSearchView.getQuery());
            }
        });

    }


    private void initializeComponents(){
        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setText(R.string.search_button);
        repoListView = (ListView) findViewById(R.id.repoListView);
        _repoBaseAdapter = new RepoBaseAdapter(MainActivity.this,repoListViewArrayList);
        repoListView.setAdapter(_repoBaseAdapter);
        repoSearchView = (SearchView) findViewById(R.id.searchViewForRepo);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle(R.string.confirmation_message);
        alertDialogBuilder
                .setMessage(R.string.exit_message)
                .setCancelable(false)
                .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume Has Been Called");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause Has Been Called");
        super.onPause();
    }

    @Override
    protected void onStart() {
        repoListViewArrayList = repoModalClass.getRepoListViewArrayList();
        _repoBaseAdapter = new RepoBaseAdapter(MainActivity.this,repoListViewArrayList);
        repoListView.setAdapter(_repoBaseAdapter);
        Log.d(TAG,"onStart Has Been Called");
        super.onStart();
    }
    @Override
    protected void onRestart() {

        Log.d(TAG,"onRestart Has Been Called");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop Has Been Called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy Has Been Called");
        super.onDestroy();
    }

    private void callLoadingWindow(){
                loadingDialog = new ProgressDialog(MainActivity.this);
                loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loadingDialog.setIndeterminate(true);
                loadingDialog.show();
    }


    @Override
    public void updateCurrentList(ArrayList<RepoJSONObjectItem> takenDataList) {

        repoListViewArrayList = takenDataList;
        _repoBaseAdapter = new RepoBaseAdapter(MainActivity.this,repoListViewArrayList);
        repoListView.setAdapter(_repoBaseAdapter);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                synchronized(repoListView){
                    repoListView.notify();
                }
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void updateCurrentListSub(ArrayList<RepoSubscriberJSONObjectItem> takenDataList) {

    }

    @Override
    public void showLoadingDialogWindow(Boolean state) {
        if (loadingDialog != null) {
            if (!state) {
                loadingDialog.dismiss();
            } else {
                callLoadingWindow();
            }
        }
    }
}
