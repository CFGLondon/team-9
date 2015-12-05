package teamscott.androidclientcfg.challenge;

/**
 * Created by User on 05/12/2015.
 */

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
public class ChallengeItemAdapter extends RecyclerView.Adapter<ChallengeItemAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<ChallengeInfo> mInfo = Collections.emptyList();
    private Context mContext;
    private ItemClickListener mClickListener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView descTextView, titleTextView;
        ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.challenge_item_title);
            descTextView = (TextView) itemView.findViewById(R.id.challenge_item_desc);
            imageView = (ImageView) itemView.findViewById(R.id.challenge_item_image);
            LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.challenge_item_layout);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (mClickListener != null)
                mClickListener.itemClicked(v, getAdapterPosition());
        }
    }

    public ChallengeItemAdapter(Context context, List<ChallengeInfo> info)
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
        View view = mInflater.inflate(R.layout.challenge_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ChallengeInfo current = mInfo.get(position);
        holder.descTextView.setText(current.description);
        holder.titleTextView.setText(current.title);
        holder.imageView.setImageResource(current.imageId);
    }

    @Override
    public int getItemCount()
    {
        return mInfo.size();
    }
}

