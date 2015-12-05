package teamscott.androidclientcfg.social;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.List;

import teamscott.androidclientcfg.ItemClickListener;
import teamscott.androidclientcfg.R;

/**
 * Created by User on 16/07/2015.
 */
public class SocialItemAdapter extends RecyclerView.Adapter<SocialItemAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<SocialItemInfo> mInfo = Collections.emptyList();
    private Context mContext;
    private ItemClickListener mClickListener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.social_item_text);
            imageView = (ImageView) itemView.findViewById(R.id.social_item_icon);
            LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.social_item_layout);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (mClickListener != null)
                mClickListener.itemClicked(v, getAdapterPosition());
        }
    }

    public SocialItemAdapter(Context context, List<SocialItemInfo> info)
    {
        mInflater = LayoutInflater.from(context);
        mInfo = info;
        mContext = context;
    }

    public void setClickListener(ItemClickListener clickListener)
    {
        mClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.social_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        SocialItemInfo current = mInfo.get(position);
//        holder.textView.setText(current.text);
        holder.imageView.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount()
    {
        return mInfo.size();
    }
}
