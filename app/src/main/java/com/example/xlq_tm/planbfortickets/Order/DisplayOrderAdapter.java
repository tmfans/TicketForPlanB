package com.example.xlq_tm.planbfortickets.Order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xlq_tm.planbfortickets.R;

import java.util.List;
import java.util.Map;

public class DisplayOrderAdapter extends RecyclerView.Adapter<DisplayOrderAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Map<String,String>> mData;
    private OnClickItemListener mOnClickItemListener;

    public DisplayOrderAdapter(Context context,List<Map<String,String>> mData){
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DisplayOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.display_train_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.fromStation = view.findViewById(R.id.from_station);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.fromStation.setText(mData.get(position).get("from_station_name"));
        holder.fromStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickItemListener.onItemClick(holder,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView fromStation;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickItemListener{
        void onItemClick(ViewHolder view,int position);
    }

    public void setOnClickItemListener(OnClickItemListener mListener){
        this.mOnClickItemListener = mListener;
    }

}
