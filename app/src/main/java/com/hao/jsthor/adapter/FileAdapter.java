package com.hao.jsthor.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hao.jsthor.R;
import com.hao.jsthor.bean.FileBean;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    public Context context;
    public List<FileBean.DataBean.ListBean> data;
    public FileAdapter(Context context, List<FileBean.DataBean.ListBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_file, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.zdTitle.setText(data.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.itemView,position);
            }
        });

        if(position%2 == 0){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else{
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.gray4));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView zdTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            zdTitle = itemView.findViewById(R.id.zd_title);
        }
    }

    //私有属性
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v,int position);
    }
}
