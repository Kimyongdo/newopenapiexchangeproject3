package com.example.newopenapiexchangeproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class UpdateAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<UpdateVO> updateVOS;

    public UpdateAdapter(Context context, ArrayList<UpdateVO> updateVOS) {
        this.context = context;
        this.updateVOS = updateVOS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.update_itemlist, parent, false);
        UpdateVH holder = new UpdateVH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UpdateVH updateVH = (UpdateVH)holder;
        UpdateVO updateVO = updateVOS.get(position);
        updateVH.UpdateTv.setText(updateVO.getUpdateTv());
        updateVH.UpdateName.setText(updateVO.getUpdateName());
        updateVH.UpdateTime.setText(updateVO.getUpdateTime());



    }

    @Override
    public int getItemCount() {
        return updateVOS.size();
    }

    class UpdateVH extends RecyclerView.ViewHolder{
        TextView UpdateTv;
        TextView UpdateName;
        TextView UpdateTime;
        public UpdateVH(@NonNull View itemView) {
            super(itemView);
            UpdateTv = itemView.findViewById(R.id.update_content);
            UpdateName = itemView.findViewById(R.id.tv_update_name);
            UpdateTime = itemView.findViewById(R.id.update_tv_date);

        }
    }
}
