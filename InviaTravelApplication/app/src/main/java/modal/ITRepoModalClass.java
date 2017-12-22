package modal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import listener.OperationListener;
import listener.RepoModelClassListener;
import operation.HttpGetRequest;
import util.Constants;

/**
 * Created by umutserifler on 21.12.2017.
 */

public class ITRepoModalClass {


    private Context context;



    ArrayList<RepoJSONObjectItem> repoListViewArrayList = new ArrayList<RepoJSONObjectItem>();
    ArrayList<RepoSubscriberJSONObjectItem> subScribersListViewArrayList = new ArrayList<RepoSubscriberJSONObjectItem>();

    private static RepoModelClassListener listener;

    public void setListener(RepoModelClassListener listener) {
        ITRepoModalClass.listener = listener;
    }


    public ITRepoModalClass(Context context) {
        setContext(context);
    }

    private void setContext(Context context) {
        this.context = context;
    }

    public void getRequestSearch(String searchString){
        repoListViewArrayList.clear();
        String result;
        HttpGetRequest getRequest = new HttpGetRequest(new OperationListener() {
            @Override
            public void httpCallBack(String result) {
                try {
                    if (result == null) {
                        listener.showLoadingDialogWindow(false);
                        return;
                    }
                    JSONObject tempObject = new JSONObject(result);
                    JSONArray jsonArray = new JSONArray(tempObject.getString(Constants.REQUESTED_QUERY_ITEMS));
                    if (jsonArray.length() == 0){
                        listener.showLoadingDialogWindow(false);
                        return;
                    }
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        RepoJSONObjectItem tempRepoObject = new RepoJSONObjectItem();
                        tempRepoObject.setRepositoryNameString("Name : " + jsonObject.getString(Constants.REPOSITORY_NAME));
                        tempRepoObject.setDescriptionString("Description : " + jsonObject.getString(Constants.REPOSITORY_DESCRIPTION));
                        tempRepoObject.setNumberOfForks("Forks : " + jsonObject.getString(Constants.REPOSITORY_FORKS));
                        tempRepoObject.setSubscribersURL(jsonObject.getString(Constants.REPOSITORY_SUBSCRIBERS_URL));
                        tempRepoObject.setRepositoryOwnerAvatarURL(jsonObject.getJSONObject(Constants.REPOSITORY_OWNER).getString(Constants.REPOSITORY_OWNER_AVATAR_URL));
                        repoListViewArrayList.add(tempRepoObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateUI();
            }
        });
        getRequest.execute(Constants.REQUEST_URL+searchString);
    }


    public ArrayList<RepoJSONObjectItem> getRepoListViewArrayList() {
        return repoListViewArrayList;
    }

    private String getRepositoryOwnerAvatarURL(JSONObject repositoryOwnerJSONObject){
        JSONObject jsonrepoOwnerObject = repositoryOwnerJSONObject;
        try {
            return (jsonrepoOwnerObject.getString(Constants.REPOSITORY_OWNER_AVATAR_URL) != null) ? jsonrepoOwnerObject.getString(Constants.REPOSITORY_OWNER_AVATAR_URL) : "0";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    // TO DO ; add new listviewarray for subscribers
    public void getRelatedRepoSubscribers(String subscribersURL){
        String result;
        subScribersListViewArrayList.clear();
        HttpGetRequest getRequest = new HttpGetRequest(new OperationListener() {
            @Override
            public void httpCallBack(String result) {
                try {
                    if (result != null){
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject jsonObjectForSubscribers;
                        for (int order = 0; order < jsonArray.length(); order++ ){
                                jsonObjectForSubscribers = jsonArray.getJSONObject(order);
                                RepoSubscriberJSONObjectItem tempRepoSubscriberObject = new RepoSubscriberJSONObjectItem();
                                tempRepoSubscriberObject.setSubscribersURL("Url : " + jsonObjectForSubscribers.getString(Constants.SUBSCRIBER_URL));
                                tempRepoSubscriberObject.setSubscribersHtmlURL("Html Url : " + jsonObjectForSubscribers.getString(Constants.SUBSCRIBER_HTML_URL));
                                tempRepoSubscriberObject.setSubScriberAvatarURL(jsonObjectForSubscribers.getString(Constants.SUBSCRIBER_AVATAR));
                                subScribersListViewArrayList.add(tempRepoSubscriberObject);
                        }
                    }else{
                            listener.showLoadingDialogWindow(false);
                            return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateSubScriberUI();
            }
        });
        getRequest.execute(subscribersURL);
    }


    private void updateUI(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.updateCurrentList(repoListViewArrayList);
            }
        });
    }

    private void updateSubScriberUI(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                listener.updateCurrentListSub(subScribersListViewArrayList);
            }
        });
    }





}
