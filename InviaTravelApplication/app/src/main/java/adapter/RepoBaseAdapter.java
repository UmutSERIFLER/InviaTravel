package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.umutserifler.inviatravelapplication.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import modal.RepoJSONObjectItem;


/**
 * Created by umutserifler on 21.12.2017.
 */

public class RepoBaseAdapter extends BaseAdapter{



        ArrayList<RepoJSONObjectItem> myCurrentList = new ArrayList<RepoJSONObjectItem>();

        Context context;
        LayoutInflater inflater;

        public RepoBaseAdapter(Context context, ArrayList<RepoJSONObjectItem> myList) {
            this.context = context;
            this.myCurrentList = myList;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return myCurrentList.size();
        }

        @Override
        public Object getItem(int position) {
            return myCurrentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_repo_listview_layout, viewGroup, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            RepoJSONObjectItem currentListData = (RepoJSONObjectItem) getItem(position);

            if (currentListData.getRepositoryNameString() != null) {
                mViewHolder.textViewForRepoName.setText(currentListData.getRepositoryNameString());
            }
            if (currentListData.getDescriptionString() != null) {
                mViewHolder.textViewForDescription.setText(currentListData.getDescriptionString());
            }
            if (currentListData.getNumberOfForks() != null) {
                mViewHolder.textViewForNumberOfForks.setText(currentListData.getNumberOfForks());
            }
            if (currentListData.getRepositoryOwnerAvatarURL() != null){
                Picasso.with(context)
                        .load(currentListData.getRepositoryOwnerAvatarURL())
                        .resize(50, 50)
                        .centerCrop()
                        .into(mViewHolder.imageViewRepoOwnerAvatar);
            }
            return convertView;
        }


        private class MyViewHolder {
            TextView textViewForRepoName;
            TextView textViewForDescription;
            TextView textViewForNumberOfForks;
            ImageView imageViewRepoOwnerAvatar;


            public MyViewHolder(View item) {
                textViewForRepoName = (TextView) item.findViewById(R.id.textViewRepoName);
                textViewForDescription = (TextView) item.findViewById(R.id.textViewDescription);
                textViewForNumberOfForks = (TextView) item.findViewById(R.id.textViewNumberOfForks);
                imageViewRepoOwnerAvatar = (ImageView) item.findViewById(R.id.imageViewRepoOwnerAvatar);
            }
        }
    }

