package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.umutserifler.inviatravelapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import modal.RepoSubscriberJSONObjectItem;

/**
 * Created by umutserifler on 22.12.2017.
 */

public class RepoSubScriberAdapter extends BaseAdapter {


    ArrayList<RepoSubscriberJSONObjectItem> subScribersArrayList = new ArrayList<RepoSubscriberJSONObjectItem>();

    Context context;
    LayoutInflater inflater;

    public RepoSubScriberAdapter(Context context, ArrayList<RepoSubscriberJSONObjectItem> myList) {
        this.context = context;
        this.subScribersArrayList = myList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return subScribersArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return subScribersArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        RepoSubScriberAdapter.MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_subscriber_listview_layout, viewGroup, false);
            mViewHolder = new RepoSubScriberAdapter.MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (RepoSubScriberAdapter.MyViewHolder) convertView.getTag();
        }

        RepoSubscriberJSONObjectItem currentListData = (RepoSubscriberJSONObjectItem) getItem(position);

        if (currentListData.getSubscribersURL() != null) {
            mViewHolder.textViewsubScriberUrl.setText(currentListData.getSubscribersURL());
        }
        if (currentListData.getSubscribersHtmlURL() != null) {
            mViewHolder.textViewsubScriberHtmlUrl.setText(currentListData.getSubscribersHtmlURL());
        }
        if (currentListData.getSubScriberAvatarURL() != null){
            Picasso.with(context)
                    .load(currentListData.getSubScriberAvatarURL())
                    .resize(50, 50)
                    .centerCrop()
                    .into(mViewHolder.imageViewSubscriberOwnerAvatar);
        }
        return convertView;
    }

    private class MyViewHolder {
        TextView textViewsubScriberUrl;
        TextView textViewsubScriberHtmlUrl;
        ImageView imageViewSubscriberOwnerAvatar;


        public MyViewHolder(View item) {
            textViewsubScriberUrl = (TextView) item.findViewById(R.id.textViewsubScriberUrl);
            textViewsubScriberHtmlUrl = (TextView) item.findViewById(R.id.textViewsubScriberHtmlUrl);
            imageViewSubscriberOwnerAvatar = (ImageView) item.findViewById(R.id.imageViewSubscriberOwnerAvatar);
        }
    }


}
