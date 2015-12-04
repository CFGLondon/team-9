package teamscott.androidclientcfg.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import teamscott.androidclientcfg.ItemClickListener;
import teamscott.androidclientcfg.R;

/**
 * Created by User on 17/07/2015.
 */
public class ProfileItemAdapter extends RecyclerView.Adapter<ProfileItemAdapter.ViewHolder>
{
    private ItemClickListener mClickListener;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<CompetitorInfo> mInfo;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.listings_item_text);
            LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.listings_item_layout);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.itemClicked(v, getAdapterPosition());
        }
    }

    public ProfileItemAdapter(Context context, List<CompetitorInfo> info)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInfo = info;
    }

    public void setClickListener(ItemClickListener clickListener)
    {
        mClickListener = clickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.listings_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.textView.setText(mInfo.get(position).text);
    }

    @Override
    public int getItemCount() {
        return mInfo.size();
    }
}
