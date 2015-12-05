package teamscott.androidclientcfg.metrics;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * {@link MetricsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MetricsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MetricsFragment extends Fragment implements ItemClickListener
{
    private MainActivity main;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MetricsItemAdapter adapter;
    private List<MetricsItemInfo> info;



    public static MetricsFragment newInstance(MainActivity main) {
        MetricsFragment fragment = new MetricsFragment();
        fragment.main = main;
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MetricsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MetricsFragment newInstance(String param1, String param2) {
        MetricsFragment fragment = new MetricsFragment();
        return fragment;
    }

    public MetricsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public List<MetricsItemInfo> getInfo()
    {
        List<MetricsItemInfo> info = new ArrayList<MetricsItemInfo>();

        int ids[] = {
                R.drawable.money, R.drawable.steps, R.drawable.heart, R.drawable.distance,
                R.drawable.calories_burnt, R.drawable.clock, R.drawable.badge
        };

        String strings[] = {
                "1234 pounds raised to help children!", "234532 steps ran.", "124,132 heart beats.", "214.2 miles.",
                "13,123 calories burnt.", "15.1 days spent training", "13 badges earned."
        };

        for (int i = 0; i < 7; i++) {
            MetricsItemInfo inf = new MetricsItemInfo();
            inf.iconId = ids[i];
            inf.text = strings[i];
            info.add(inf);
        }

        return info;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_metrics, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.metrics_list);

        info = getInfo();
        adapter = new MetricsItemAdapter(getActivity(), info);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
