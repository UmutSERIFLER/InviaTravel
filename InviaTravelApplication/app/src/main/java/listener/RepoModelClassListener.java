package listener;

import java.util.ArrayList;
import modal.RepoJSONObjectItem;
import modal.RepoSubscriberJSONObjectItem;

/**
 * Created by umutserifler on 21.12.2017.
 */

public interface RepoModelClassListener {

    void updateCurrentList(ArrayList<RepoJSONObjectItem> takenDataList);

    void updateCurrentListSub(ArrayList<RepoSubscriberJSONObjectItem> takenDataList);

    void showLoadingDialogWindow(Boolean state);
}
