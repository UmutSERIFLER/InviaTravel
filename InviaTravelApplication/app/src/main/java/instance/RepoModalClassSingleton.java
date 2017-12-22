package instance;

import android.content.Context;

import modal.ITRepoModalClass;

/**
 * Created by umutserifler on 21.12.2017.
 */

public class RepoModalClassSingleton {
    private static RepoModalClassSingleton repoModalClassSingleton = null;

    private ITRepoModalClass repoModalClass;

    private RepoModalClassSingleton(Context context){
        repoModalClass = new ITRepoModalClass(context);
    }

    public static RepoModalClassSingleton getInstance(Context context) {
        if(repoModalClassSingleton == null) {
            repoModalClassSingleton = new RepoModalClassSingleton(context);
        }
        return repoModalClassSingleton;
    }

    public ITRepoModalClass getRepoClass(){
        return this.repoModalClass;
    }

    public void resetITRepoModalClass(){
        repoModalClassSingleton = null;
    }



}
