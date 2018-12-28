package com.example.tuananh.mytravels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tuananh.mytravels.base.BaseActivity;
import com.example.tuananh.mytravels.entity.Travel;
import com.example.tuananh.mytravels.utils.MyDate;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.TravelViewHolder> {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Travel> mList;
    private ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener{
        void onListItemClick(View v,int position,Travel travel);
    }

    public void setmListItemClickListener(ListItemClickListener mListItemClickListener) {
        this.mListItemClickListener = mListItemClickListener;
    }

    public TravelListAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<Travel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.main_travel_item,parent,false);
        return new TravelViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelViewHolder holder, int position) {
        if (getItemCount()==0) return;
        Travel item = mList.get(position);
        holder.titleTxt.setText(item.getTitle());
        holder.placeTxt.setText(item.getPlaceName()+" / "+item.getPlaceAddr());
        holder.dateTxt.setText(MyDate.getString(item.getStartDt()) +" - "+MyDate.getString(item.getEndDt()));
    }

    @Override
    public int getItemCount() {
        if (mList==null) return 0;
        return mList.size();
    }

    private Travel getItem(int position){
        if (getItemCount()==0) return null;
        return mList.get(position);
    }

    protected class TravelViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTxt,placeTxt,dateTxt;
        private final ImageButton editBtn;

        public TravelViewHolder(@NonNull final View itemView) {
            super(itemView);
            if (mListItemClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListItemClickListener.onListItemClick(view,getAdapterPosition(),getItem(getAdapterPosition()));
                    }
                });
            }
            titleTxt = itemView.findViewById(R.id.title_txt);
            placeTxt = itemView.findViewById(R.id.place_txt);
            dateTxt = itemView.findViewById(R.id.date_txt);
            editBtn = itemView.findViewById(R.id.editBtn);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Travel travel = getItem(getAdapterPosition());
                    Intent intent = new Intent(mContext,EditTravelActivity.class);
                    intent.putExtra(BaseActivity.REQ_KEY_TRAVEL,travel);
                    intent.setAction(BaseActivity.REQ_KEY_EDIT_TRAVEL);
                    ((MainActivity) mContext).startActivityForResult(intent,BaseActivity.REQ_TRAVEL_EDIT);
                }
            });
        }
    }
}
