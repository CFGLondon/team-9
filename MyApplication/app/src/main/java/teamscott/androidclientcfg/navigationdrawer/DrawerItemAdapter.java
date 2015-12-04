package teamscott.androidclientcfg.navigationdrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import teamscott.androidclientcfg.ItemClickListener;
import teamscott.androidclientcfg.R;

/**
 * Created by User on 16/07/2015.
 */
public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<DrawerItemInfo> mInfo = Collections.emptyList();
    private Context mContext;
    private ItemClickListener mClickListener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.drawer_item_text);
            imageView = (ImageView) itemView.findViewById(R.id.drawer_item_icon);
            LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.drawer_item_layout);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (mClickListener != null)
                mClickListener.itemClicked(v, getAdapterPosition());
        }
    }

    public DrawerItemAdapter(Context context, List<DrawerItemInfo> info)
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
        View view = mInflater.inflate(R.layout.drawer_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        DrawerItemInfo current = mInfo.get(position);
        holder.textView.setText(current.title);
        holder.imageView.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount()
    {
        return mInfo.size();
    }
}
