package com.example.giboon_ver3;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import listener.OnPostListener;

public class MainAdapter  extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ArrayList<PostInfo> mDataset;
    private FragmentCampaign activity;
    private MainActivity mainActivity;
    PopupMenu popup;
    private FirebaseFirestore firebaseFirestore;
    private OnPostListener onPostListener;

    static class MainViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        MainViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public MainAdapter(FragmentCampaign activity, ArrayList<PostInfo> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
        System.out.println("myDataset" + myDataset);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setOnPostListener(OnPostListener onPostListener){
        this.onPostListener = onPostListener;
    }

    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, mainViewHolder.getAdapterPosition());
            }
        });

        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        // 게시판 리스트 요소들 id 가져오기
        TextView textView = cardView.findViewById(R.id.textView);
        TextView textContent = cardView.findViewById(R.id.textContent);
        TextView textName = cardView.findViewById(R.id.textName);
        TextView textDate = cardView.findViewById(R.id.textDate);

        String[] dateArray = mDataset.get(position).getCreatedAt().toString().split(" ");
        int month = 0;
        switch(dateArray[1]){
            case "Jan" : month = 1; break;
            case "Feb" : month = 2; break;
            case "Mar" : month = 3; break;
            case "Apr" : month = 4; break;
            case "May" : month = 5; break;
            case "June" : month = 6; break;
            case "July" : month = 7; break;
            case "Aug" : month = 8; break;
            case "Sept" : month = 9; break;
            case "Oct" : month = 10; break;
            case "Nov" : month = 11; break;
            case "Dec" : month = 12; break;
            default : month = 0; break;
        }

        // 게시판 리스트 요소 setText 하기
        textView.setText(mDataset.get(position).getTitle());
        textContent.setText(mDataset.get(position).getContents());
        textName.setText(mDataset.get(position).getName());
        textDate.setText(mDataset.get(position).getCreatedAt().toString().substring(mDataset.get(position)
                .getCreatedAt().toString().length()-4, mDataset.get(position).getCreatedAt().toString().length())
                + "." + String.valueOf(month)
                + "." + dateArray[2]);

        // 테스트 출력
        System.out.println(mDataset.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(mainActivity.mContext, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String id = mDataset.get(position).getId();;
                switch (menuItem.getItemId()) {
                    case R.id.modify:
                        onPostListener.onModify(id);
                        return true;
                    case R.id.delete:
                        onPostListener.onDelect(id);
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }
}