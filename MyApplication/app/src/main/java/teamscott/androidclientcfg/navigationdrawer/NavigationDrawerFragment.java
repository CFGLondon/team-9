package teamscott.androidclientcfg.navigationdrawer;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import teamscott.androidclientcfg.ItemClickListener;
import teamscott.androidclientcfg.MainActivity;
import teamscott.androidclientcfg.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements ItemClickListener
{
    private View mContainerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer, mFromSavedInstanceState;
    private RecyclerView mRecyclerView;
    private DrawerItemAdapter mAdapter;
    private List<DrawerItemInfo> mInfo;
    private Toolbar mToolbar;
    private ImageView mNavDrawerImage;

    public static String PREF_FILE_NAME = "drawer_pref";
    public static String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null)
            mFromSavedInstanceState = true;

        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);
        mNavDrawerImage = (ImageView) layout.findViewById(R.id.nav_image);

        mInfo = getData();
        mAdapter = new DrawerItemAdapter(getActivity(), mInfo);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<DrawerItemInfo> getData()
    {
        List<DrawerItemInfo> data = new ArrayList<DrawerItemInfo>();

        String[] strings = {
                "Profile", "Metrics", "Challenge", "Social", "Save The Children"};
        int[] icons = {
                R.drawable.profile_icon, R.drawable.metrics_icon, R.drawable.challenge_icon,
                R.drawable.social_icon, R.drawable.savethechildren_logo};

        for (int i = 0; i < strings.length && i < icons.length; i++)
        {
            DrawerItemInfo info = new DrawerItemInfo();
            info.iconId = icons[i];
            info.title = strings[i];
            data.add(info);
        }

        return data;
    }

    public void setUp(int containerId, int mainContentId, final DrawerLayout drawerLayout, Toolbar toolbar)
    {
        mContainerView = getActivity().findViewById(containerId);
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;

        final RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(mainContentId);
        final ImageView icon = (ImageView) getActivity().findViewById(R.id.nav_image);
        final RecyclerView recyclerView = mRecyclerView;

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
                float drawerWidth = mContainerView.getWidth();
                relativeLayout.setTranslationX(slideOffset * drawerWidth);
                float x = 0.5f;
                float alpha = (slideOffset > x) ? (slideOffset - x) / (1 - x) : 0;

                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int height = size.y;
                float transOffset = (1 - slideOffset) * 0.1f * height;

                mNavDrawerImage.setTranslationY(-transOffset);

                icon.setAlpha(alpha);
                for (int i = 0; i < recyclerView.getChildCount(); i++)
                {
                    recyclerView.getChildAt(i).setAlpha(alpha);
                    recyclerView.getChildAt(i).setTranslationY(transOffset * (1 + 0.5f * i));
                }
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState)
            mDrawerLayout.openDrawer(mContainerView);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String key, String value)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        pref.edit().putString(key, value).apply();
    }

    public static String readFromPreferences(Context context, String key, String defValue)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, defValue);
    }

    @Override
    public void itemClicked(View v, int pos)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        int id = mInfo.get(pos).iconId;

        if (id == R.drawable.metrics_icon) {
            transaction.replace(R.id.content_fragment, MainActivity.mapFragment).commit();
            mToolbar.setTitle(mInfo.get(pos).title);
        }
        if (id == R.drawable.profile_icon) {
            transaction.replace(R.id.content_fragment, MainActivity.listingsFragment).commit();
            mToolbar.setTitle(mInfo.get(pos).title);
        }

        if (id == R.drawable.social_icon)
            MainActivity.startRateIntent();

    }
}
