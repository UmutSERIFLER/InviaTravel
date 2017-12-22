package com.example.umutserifler.inviatravelapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.RepoSubScriberAdapter;
import instance.RepoModalClassSingleton;
import listener.RepoModelClassListener;
import modal.ITRepoModalClass;
import modal.RepoJSONObjectItem;
import modal.RepoSubscriberJSONObjectItem;

/**
 * Created by umutserifler on 22.12.2017.
 */

public class DetailRepoActivity extends AppCompatActivity implements RepoModelClassListener {

    private static String TAG = DetailRepoActivity.class.getSimpleName();

    ITRepoModalClass repoModalClass;

    String repoName, subScribersURL;

    ProgressDialog loadingDialog;

    ArrayList<RepoSubscriberJSONObjectItem> subScribersListViewArrayList = new ArrayList<RepoSubscriberJSONObjectItem>();
    ListView subScribersListView;
    private RepoSubScriberAdapter _subScriberAdapter;

    TextView subScriberRepoName, subScriberCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_detail_activity);

        Bundle p = getIntent().getExtras();
        repoName =p.getString("repoName");
        subScribersURL = p.getString("subscribersUrl");


        repoModalClass = RepoModalClassSingleton.getInstance(DetailRepoActivity.this).getRepoClass();
        repoModalClass.setListener(DetailRepoActivity.this);
        repoModalClass.getRelatedRepoSubscribers(subScribersURL);
        callLoadingWindow();
        initializeComponents();



    }

    private void callLoadingWindow(){
        loadingDialog = new ProgressDialog(DetailRepoActivity.this);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setIndeterminate(true);
        loadingDialog.show();
    }

    private void initializeComponents(){
        subScribersListView = (ListView) findViewById(R.id.subScribersListView);
        _subScriberAdapter = new RepoSubScriberAdapter(DetailRepoActivity.this,subScribersListViewArrayList);
        subScribersListView.setAdapter(_subScriberAdapter);
        subScriberRepoName = (TextView) findViewById(R.id.subScriberRepoNameTextView);
        subScriberRepoName.setText("Repo " + repoName);
        subScriberCount = (TextView) findViewById(R.id.subScribersCountTextView);
        subScriberCount.setText("SubScribersCount : " + String.valueOf(subScribersListViewArrayList.size()));
    }


    @Override
    public void updateCurrentList(ArrayList<RepoJSONObjectItem> takenDataList) {

    }

    @Override
    public void updateCurrentListSub(ArrayList<RepoSubscriberJSONObjectItem> takenDataList) {
         subScribersListViewArrayList = takenDataList;
        _subScriberAdapter = new RepoSubScriberAdapter(DetailRepoActivity.this,subScribersListViewArrayList);
        subScribersListView.setAdapter(_subScriberAdapter);
        subScriberCount.setText("SubScribersCount : " + String.valueOf(subScribersListViewArrayList.size()));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                synchronized(subScribersListView){
                    subScribersListView.notify();
                }
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
            }
        });
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
