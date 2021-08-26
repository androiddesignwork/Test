package com.example.ggpic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.InnerHolder>{

    private final List<Pics> mdata;
    private PicsAdapter.OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public PicsAdapter(List<Pics> data,Context context){
        this.mdata=data;
        mContext=context;
    }

    public Pics getitem(int position) {
        return this.mdata.get(position);
    }

    public interface OnItemClickListener
    {
        //子条目单机事件
        void onItemClick(View view, int position);
        //子条目长按事件
        void onItemLongClick(View view,int position);
    }

    //回调方法 将接口传递进来
    public void setOnItemClickListener(PicsAdapter.OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public PicsAdapter.InnerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_pics, null);
        return new PicsAdapter.InnerHolder(view);
    }
    /*绑定holder
     *
     *
     *
     * */
    @Override
    public void onBindViewHolder(@NonNull @NotNull PicsAdapter.InnerHolder holder, int position) {
        holder.setdata(mdata.get(position));
        //activity调用setOnItemClickListener() 如果调用接口不为空执行下面逻辑
        if (mOnItemClickListener != null)
        {
            holder.Card.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //返回对应view的信息
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.Card,pos);
                }
            });
            holder.Card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.Card,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mdata!=null){
            return mdata.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private CardView Card;
        private ImageView image;
        private TextView author;
        private TextView title;
        public InnerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Card=itemView.findViewById(R.id.pic_card);
            image=(ImageView)itemView.findViewById(R.id.pic_image);
        }

        public void setdata(Pics pics) {
                Glide.with(mContext).load(pics.getmPicUrl())
                        .into(image);
        }
    }

    public void add(Pics pic){
          mdata.add(pic);
    }

    public Pics getItem(int i){
        return mdata.get(i);
    }
}

