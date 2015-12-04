package teamscott.androidclientcfg.profile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import teamscott.androidclientcfg.ItemClickListener;
import teamscott.androidclientcfg.MainActivity;
import teamscott.androidclientcfg.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements ItemClickListener
{
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ProfileItemAdapter mAdapter;
    private List<CompetitorInfo> mInfo;
    private MainActivity main;

    public static ProfileFragment newInstance(MainActivity main) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.mInfo = new ArrayList<CompetitorInfo>();
        fragment.main = main;
        return fragment;
    }

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view =  inflater.inflate(R.layout.fragment_listings, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listings_recycler_view);
        mAdapter = new ProfileItemAdapter(getActivity(), mInfo);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        Button refreshButton = (Button) view.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.refreshData();
            }
        });

        Button submitButton = (Button) view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText nameEditText = (EditText) view.findViewById(R.id.name_edit_text);
                EditText sizeEditText = (EditText) view.findViewById(R.id.size_edit_text);
                String name = nameEditText.getText().toString();
                String size = sizeEditText.getText().toString();

                main.addEntryToDB(name, size);

                CompetitorInfo info = new CompetitorInfo();
                info.text = "Name: " + name + "\n" + "Times larger than the sun: " + size;
                addItem(info);
            }
        });

        return view;
    }

    public void addItem(JSONObject obj)
    {
        CompetitorInfo newInfo = new CompetitorInfo();
        newInfo.text = obj.toString();
        mInfo.add(newInfo);
    }

    public void addItem(CompetitorInfo info)
    {
        mInfo.add(info);
    }

    public List<CompetitorInfo> getInfo() { return mInfo; }

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
