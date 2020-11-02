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

import listener.OnNoticeListener;

public class NoticeAdapter  extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private ArrayList<NoticeInfo> mDataset2;
    private FragmentNotice activity2;
    private MainActivity mainActivity2;
    PopupMenu popup;
    private FirebaseFirestore firebaseFirestore2;
    private OnNoticeListener onNoticeListener;

    static class NoticeViewHolder extends RecyclerView.ViewHolder {
        CardView cardView2;
        NoticeViewHolder(CardView v) {
            super(v);
            cardView2 = v;
        }
    }

    public NoticeAdapter(FragmentNotice activity, ArrayList<NoticeInfo> myDataset2) {
        mDataset2 = myDataset2;
        this.activity2 = activity;
        System.out.println("myDataset2" + myDataset2);
        firebaseFirestore2 = FirebaseFirestore.getInstance();
    }

    public void setOnNoticeListener(OnNoticeListener onNoticeListener){
        this.onNoticeListener = onNoticeListener;
    }

    @NonNull
    @Override
    public NoticeAdapter.NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView2 = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        final NoticeViewHolder noticeViewHolder = new NoticeViewHolder(cardView2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardView2.findViewById(R.id.menu2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup2(v, noticeViewHolder.getAdapterPosition());
            }
        });

        return noticeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NoticeViewHolder holder, int position) {
        CardView cardView2 = holder.cardView2;
//        CardView cardView = holder.cardView;

        // 게시판 리스트 요소들 id 가져오기
        TextView textView2 = cardView2.findViewById(R.id.textView2);
        TextView textContent2 = cardView2.findViewById(R.id.textContent2);
        TextView textName2 = cardView2.findViewById(R.id.textName2);
        TextView textDate2 = cardView2.findViewById(R.id.textDate2);

        String[] dateArray = mDataset2.get(position).getCreatedAt2().toString().split(" ");
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
        textView2.setText(mDataset2.get(position).getTitle2());
        textContent2.setText(mDataset2.get(position).getContents2());
        textName2.setText(mDataset2.get(position).getName2());
        textDate2.setText(mDataset2.get(position).getCreatedAt2().toString().substring(mDataset2.get(position)
                .getCreatedAt2().toString().length()-4, mDataset2.get(position).getCreatedAt2().toString().length())
                + "." + String.valueOf(month)
                + "." + dateArray[2]);

        // 테스트 출력
        System.out.println(mDataset2.get(position).getName2());

    }

    @Override
    public int getItemCount() {
        return mDataset2.size();
    }

    public void showPopup2(View v, final int position2) {
        PopupMenu popup2 = new PopupMenu(mainActivity2.mContext, v);
        popup2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String id2 = mDataset2.get(position2).getId2();;
                switch (menuItem.getItemId()) {
                    case R.id.modify:
                        onNoticeListener.onModify2(id2);
                        return true;
                    case R.id.delete:
                        onNoticeListener.onDelect2(id2);
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater2 = popup2.getMenuInflater();
        inflater2.inflate(R.menu.notice, popup2.getMenu());
        popup2.show();
    }
}