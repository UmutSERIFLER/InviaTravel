package modal;



import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by umutserifler on 21.12.2017.
 */

public class RepoJSONObjectItem {



    String subscribersURL;
    String listOfSubscribers;
    String repositoryNameString;
    String descriptionString;
    String numberOfForks;
    String repositoryOwnerAvatarURL;



    public ImageView repositoryOwnerAvatarImageView;

    public String getRepositoryNameString() {
        return repositoryNameString;
    }

    public void setRepositoryNameString(String repositoryNameString) {
        this.repositoryNameString = repositoryNameString;
    }

    public String getNumberOfForks() {
        return numberOfForks;
    }

    public void setNumberOfForks(String numberOfForks) {
        this.numberOfForks = numberOfForks;
    }

    public String getDescriptionString() {
        return descriptionString;
    }

    public void setDescriptionString(String descriptionString) {
        this.descriptionString = descriptionString;
    }

    public String getSubscribersURL() {
        return subscribersURL;
    }

    public void setSubscribersURL(String subscribersURL) {
        this.subscribersURL = subscribersURL;
    }
    public String getListOfSubscribers() {
        return listOfSubscribers;
    }

    public void setListOfSubscribers(String listOfSubscribers) {
        this.listOfSubscribers = listOfSubscribers;
    }

    public String getRepositoryOwnerAvatarURL() {
        return repositoryOwnerAvatarURL;
    }

    public void setRepositoryOwnerAvatarURL(String repositoryOwnerAvatarURL) {

        this.repositoryOwnerAvatarURL = repositoryOwnerAvatarURL;
    }

    public ImageView getRepositoryOwnerAvatarImageView() {
        return repositoryOwnerAvatarImageView;
    }

    public void setRepositoryOwnerAvatarImageView(ImageView repositoryOwnerAvatarImageView) {
        this.repositoryOwnerAvatarImageView = repositoryOwnerAvatarImageView;
    }

}
