package teamscott.androidclientcfg.metrics;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamscott.androidclientcfg.ItemClickListener;
import teamscott.androidclientcfg.MainActivity;
import teamscott.androidclientcfg.profile.ProfileItemAdapter;

/**
 * Created by User on 04/12/2015.
 */
public class MetricsFragment extends Fragment implements ItemClickListener
{

//    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ProfileItemAdapter mAdapter;
    private MainActivity main;

    public static MetricsFragment newInstance(MainActivity main) {
        MetricsFragment fragment = new MetricsFragment();
        fragment.main = main;
        return fragment;
    }

    public MetricsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void itemClicked(View v, int pos) {

    }
}
