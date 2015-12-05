package teamscott.androidclientcfg.social;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import teamscott.androidclientcfg.ItemClickListener;
import teamscott.androidclientcfg.MainActivity;
import teamscott.androidclientcfg.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SocialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialFragment extends Fragment implements ItemClickListener
{
    private MainActivity main;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private SocialItemAdapter adapter;
    private List<SocialItemInfo> info;

    public static SocialFragment newInstance(MainActivity main) {
        SocialFragment fragment = new SocialFragment();
        return fragment;
    }

    public SocialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_social, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.social_list);

        List<SocialItemInfo> info = getInfo();
        adapter = new SocialItemAdapter(getActivity(), info);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        return layout;
    }

    int ids[] = {
            R.drawable.profilepic1, R.drawable.profilepic2, R.drawable.profilepic3,
            R.drawable.profilepic4, R.drawable.profilepic5, R.drawable.profilepic6
    };

    public List<SocialItemInfo> getInfo()
    {
        List<SocialItemInfo> info = new ArrayList<SocialItemInfo>();

        for (int i = 0; i < 20; i++) {
            SocialItemInfo inf = new SocialItemInfo();
            inf.iconId = ids[i % 6];
            info.add(inf);
        }

        return info;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void itemClicked(View v, int pos) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
