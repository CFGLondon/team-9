package teamscott.androidclientcfg;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import teamscott.androidclientcfg.challenge.ChallengeFragment;
import teamscott.androidclientcfg.metrics.MetricsFragment;
import teamscott.androidclientcfg.navigationdrawer.NavigationDrawerFragment;
import teamscott.androidclientcfg.profile.CompetitorInfo;
import teamscott.androidclientcfg.profile.ProfileFragment;
import teamscott.androidclientcfg.social.SocialFragment;
import teamscott.androidclientcfg.splash.SplashFragment;


public class MainActivity extends AppCompatActivity implements
        ProfileFragment.OnFragmentInteractionListener,
        MetricsFragment.OnFragmentInteractionListener,
        ChallengeFragment.OnFragmentInteractionListener,
        SocialFragment.OnFragmentInteractionListener,
        SplashFragment.OnFragmentInteractionListener,
        OnMapReadyCallback
{
    public Toolbar toolbar;
    public GoogleMap googleMap;

    public static MainActivity main;
    public static SupportMapFragment mapFragment;
    public static MetricsFragment metricsFragment;
    public static ProfileFragment listingsFragment;
    public static ChallengeFragment challengeFragment;
    public static SocialFragment socialFragment;
    public static NavigationDrawerFragment drawerFragment;

    public static LatLng BRISTOL;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        main = this;

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        BRISTOL = new LatLng(51.454314, -2.592598);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_fragment, SplashFragment.newInstance(main))
                .commit();

        setContentView(R.layout.activity_main);

        listingsFragment = ProfileFragment.newInstance(main);
        metricsFragment = MetricsFragment.newInstance(main);
        challengeFragment = ChallengeFragment.newInstance(main);
        socialFragment = SocialFragment.newInstance(main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);



        drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);

        drawerFragment.setUp(
                R.id.fragment_navigation_drawer,
                R.id.main_content,
                drawerLayout,
                toolbar);


    }

    public void loadData()
    {
        new LoadEventInfo().execute();
        drawerFragment.unlockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_fragment, LoadingFragment.newInstance())
                .commit();
    }


    private int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("DEBUG", "Fragment Interaction");
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Log.d("DEBUG", "Map Ready");
        this.googleMap = googleMap;
        setUpMap();
    }

    public void setUpMap()
    {
        try
        {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BRISTOL, 10));
            googleMap.setMyLocationEnabled(true);
        }
        catch (Exception e) {}
    }

    public static void startRateIntent()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.albinobunny.android"));
        try
        {
            main.startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(main, "Unable to rate at this time", Toast.LENGTH_SHORT);
        }
    }

    public void refreshData()
    {
        listingsFragment = ProfileFragment.newInstance(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_fragment, LoadingFragment.newInstance())
                .commit();
        new LoadEventInfo().execute();
    }

    public void addEntryToDB(String name, String size)
    {
        new ConnectToAddEntry(name, size).execute();
    }


//    public static final String URL = "http://www.androidexample.com/media/webservice/JsonReturn.php";
    public static final String URL = "http://104.155.17.225/get_stars.php";
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public class LoadEventInfo extends AsyncTask<String, Integer, Void>
    {

        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                java.net.URL url = new URL(URL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

//                jsonObject = new JSONObject(responseStrBuilder.toString());
                jsonArray = new JSONArray(responseStrBuilder.toString());
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (jsonArray != null)
            {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++)
                {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        CompetitorInfo info = new CompetitorInfo();
                        info.text = "Name: " + obj.getString("name") + "\n" +
                                    "Times larger than the sun: " + obj.getInt("size");
                        listingsFragment.addItem(info);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

//            if (listingsFragment.getInfo().size() > 5)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_fragment, listingsFragment)
                    .commit();
//            else new LoadEventInfo().execute();
        }
    }

    public class ConnectToAddEntry extends AsyncTask<String, Integer, Void>
    {
        String name, size;

        public ConnectToAddEntry(String name, String size)
        {
            this.name = name; this.size = size;
        }

        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                String URL = "http://104.155.17.225/add_star.php?";
                URL += "name=" + name + "&size=" + size;
                Log.d("DB", URL);
                URL = URL.replaceAll(" ", "%20");

                java.net.URL url = new URL(URL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                Log.d("DB", responseStrBuilder.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.d("DB", "Unable to connect");
            }

            return null;
        }
    }

}
