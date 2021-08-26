package com.example.ggpic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerAdapter.InnerHolder>{

    private final List<Explorer> mdata;
    private final Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public ExplorerAdapter(List<Explorer> data, Context context){
        this.mdata=data;
        mContext=context;
    }

    public interface OnItemClickListener
    {
        //子条目单机事件
        void onItemClick(View view, int position);
        //子条目长按事件
        void onItemLongClick(View view,int position);
    }

    //回调方法 将接口传递进来
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public InnerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.list_explore, null);
        return new InnerHolder(view);
    }
    /*绑定holder
     *
     *
     *
     * */
    @Override
    public void onBindViewHolder(@NonNull @NotNull ExplorerAdapter.InnerHolder holder, int position) {
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
        private TextView text;
        public InnerHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            Card=itemView.findViewById(R.id.ex_card);
            image=(ImageView)itemView.findViewById(R.id.ex_image);
            text=(TextView)itemView.findViewById(R.id.ex_text);
        }

        public void setdata(Explorer explorer) {
            text.setText(explorer.getText());
            Glide.with(mContext).load(explorer.getPicUrl())
                    .into(image);
        }
    }

    public void add(Explorer explorer) {
        mdata.add(explorer);
    }

    public Explorer getItem(int i){
        return mdata.get(i);
    }
}