package net.webnetworksolutions.mama.fragments;


import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.webnetworksolutions.mama.R;
import net.webnetworksolutions.mama.adapters.CountiesAdapter;
import net.webnetworksolutions.mama.maps.GetNearbyPlacesData;
import net.webnetworksolutions.mama.pojo.Counties;
import net.webnetworksolutions.mama.support.LocationsContentProvider;
import net.webnetworksolutions.mama.support.LocationsDB;


import java.util.ArrayList;
import java.util.List;


public class SliderFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, LoaderManager.LoaderCallbacks<Cursor> {


    private SupportMapFragment mapFragment;
    private CountiesAdapter adapter;
    private List<Counties> countiesList;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLOR = "color";

    // TODO: Rename and change types of parameters
    private int color;


    private static final String TAG = SliderFragment.class.getSimpleName();

    private View rootView;


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int PERMISSION_REQUEST_LOCATON_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;

    public SliderFragment() {
        //Required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            color = getArguments().getInt(ARG_COLOR);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_slider, container, false);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            getActivity().finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
            if (mapFragment == null) {
                mapFragment = SupportMapFragment.newInstance();
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mMap = googleMap;
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED ) {
                                buildGoogleApiClient();
                                mMap.setMyLocationEnabled(true);
                            }
                        } else {
                            buildGoogleApiClient();
                            mMap.setMyLocationEnabled(true);
                        }
                    }
                });
                // R.id.map is a FrameLayout, not a Fragment
                getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
            }
            // Invoke LoaderCallbacks to retrieve and draw already saved locations in map
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }


        return rootView;
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getActivity());
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView= view;

        countiesList= new ArrayList<>();
        adapter= new CountiesAdapter(getContext(),countiesList);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Button button= view.findViewById(R.id.buttonHospital);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object dataTrasnsfer[]= new Object[2];
                GetNearbyPlacesData getNearbyPlacesData= new GetNearbyPlacesData();

                mMap.clear();
               String hospital= "hospital";
               String url= getUrl(latitude, longitude, hospital);

                dataTrasnsfer[0]= mMap;
                dataTrasnsfer[1]= url;

                getNearbyPlacesData.execute(dataTrasnsfer);

                Toast.makeText(getActivity(), "Showing nearby Hospitals", Toast.LENGTH_LONG).show();

            }
        });

/*
        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Counties counties = countiesList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), counties.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        */

        prepareCounties();

    }

    private String getUrl(double latitude, double longitude, String  nearbyPlace){
        StringBuilder googlePlaceUrl= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location"+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyCVa7wJ1We2tQeh1y11izEzvCKpbjSvvjQ");

        return googlePlaceUrl.toString();

    }

    private void prepareCounties() {
        int[] covers = new int[]{
                R.drawable.nairobi,
                R.drawable.mombasa,
                R.drawable.nakuru,
                R.drawable.kisumu,


                R.drawable.kiambu,
                R.drawable.bomet,
                R.drawable.bungoma,
                R.drawable.busia,

                R.drawable.elgeyo_marakwet,
                R.drawable.embu,
                R.drawable.garissa,
                R.drawable.homabay,

                R.drawable.isiolo,
                R.drawable.kajiado,
                R.drawable.kakamega,
                R.drawable.kericho,

                R.drawable.kilifi,
                R.drawable.kirinyaga,
                R.drawable.kisii,
                R.drawable.baringo,

                R.drawable.kitui,
                R.drawable.kwale,
                R.drawable.laikipia,
                R.drawable.lamu,

                R.drawable.machakos,
                R.drawable.makueni,
                R.drawable.mandera,
                R.drawable.meru,

                R.drawable.migori,
                R.drawable.marsabit,
                R.drawable.muranga,
                R.drawable.nandi,

                R.drawable.narok,
                R.drawable.nyamira,
                R.drawable.nyandarua,
                R.drawable.nyeri,

                R.drawable.samburu,
                R.drawable.siaya,
                R.drawable.taita_taveta,
                R.drawable.tana_river,

                R.drawable.tharaka_nithi,
                R.drawable.transnzoia,
                R.drawable.turkana,
                R.drawable.uasin_gichu,

                R.drawable.vihiga,
                R.drawable.wajir,
                R.drawable.west_pokot,

        };

        Counties a = new Counties("Nairobi County", " Level 5", covers[0]);
        countiesList.add(a);

        a = new Counties("Mombasa County", " Level 5", covers[1]);
        countiesList.add(a);

        a = new Counties("Nakuru County", " Level 5", covers[2]);
        countiesList.add(a);

        a = new Counties("Kisumu County", " Level 5", covers[3]);
        countiesList.add(a);

        a = new Counties("Kiambu County", " Level 5", covers[4]);
        countiesList.add(a);

        a = new Counties("Bomet County", " Level 5", covers[5]);
        countiesList.add(a);

        a = new Counties("Bungoma County", " Level 5", covers[6]);
        countiesList.add(a);

        a = new Counties("Busia County", " Level 5", covers[7]);
        countiesList.add(a);

        a = new Counties("Elgeyo M County", " Level 5", covers[8]);
        countiesList.add(a);

        a = new Counties("Embu County", " Level 5", covers[9]);
        countiesList.add(a);

        a = new Counties("Garissa County", " Level 5", covers[10]);
        countiesList.add(a);

        a = new Counties("Homa Bay County", " Level 5", covers[11]);
        countiesList.add(a);

        a = new Counties("Isiolo County", " Level 5", covers[12]);
        countiesList.add(a);

        a = new Counties("Kajiado County", " Level 5", covers[13]);
        countiesList.add(a);

        a = new Counties("Kakamega County", " Level 5", covers[14]);
        countiesList.add(a);

        a = new Counties("Kericho County", " Level 5", covers[15]);
        countiesList.add(a);

        a = new Counties("Kilifi County", " Level 5", covers[16]);
        countiesList.add(a);

        a = new Counties("Kirinyaga County", " Level 5", covers[17]);
        countiesList.add(a);

        a = new Counties("Kisii County", " Level 5", covers[18]);
        countiesList.add(a);

        a = new Counties("Baringo County", " Level 5", covers[19]);
        countiesList.add(a);

        a = new Counties("Kitui County", " Level 5", covers[20]);
        countiesList.add(a);

        a = new Counties("Kwale County", " Level 5", covers[21]);
        countiesList.add(a);

        a = new Counties("Laikipia County", " Level 5", covers[22]);
        countiesList.add(a);

        a = new Counties("Lamu County", " Level 5", covers[23]);
        countiesList.add(a);

        a = new Counties("Machakos County", " Level 5", covers[24]);
        countiesList.add(a);

        a = new Counties("Makueni County", " Level 5", covers[25]);
        countiesList.add(a);

        a = new Counties("Mandera County", " Level 5", covers[26]);
        countiesList.add(a);

        a = new Counties("Meru County", " Level 5", covers[27]);
         countiesList.add(a);

        a = new Counties("Migori County", " Level 5", covers[28]);
        countiesList.add(a);

        a = new Counties("Marsabit County", " Level 5", covers[29]);
        countiesList.add(a);

        a = new Counties("Muranga County", " Level 5", covers[30]);
        countiesList.add(a);

        a = new Counties("Nandi County", " Level 5", covers[31]);
        countiesList.add(a);

        a = new Counties("Narok County", " Level 5", covers[32]);
        countiesList.add(a);

        a = new Counties("Nyamira County", " Level 5", covers[33]);
        countiesList.add(a);

        a = new Counties("Nyandarua County", " Level 5", covers[34]);
        countiesList.add(a);

        a = new Counties("Nyeri County", " Level 5", covers[35]);
        countiesList.add(a);

        a = new Counties("Samburu County", " Level 5", covers[36]);
        countiesList.add(a);

        a = new Counties("Siaya County", " Level 5", covers[37]);
        countiesList.add(a);

        a = new Counties("Taita Taveta County", " Level 5", covers[38]);
        countiesList.add(a);

        a = new Counties("Tana River County", " Level 5", covers[39]);
        countiesList.add(a);

        a = new Counties("Tharaka Nithi County", " Level 5", covers[40]);
        countiesList.add(a);

        a = new Counties("Trans Nzoia County", " Level 5", covers[41]);
        countiesList.add(a);

        a = new Counties("Turkana County", " Level 5", covers[42]);
        countiesList.add(a);

        a = new Counties("Uasin Gichu County", " Level 5", covers[43]);
        countiesList.add(a);

        a = new Counties("Vihiga County", " Level 5", covers[44]);
        countiesList.add(a);

        a = new Counties("Wajir County", " Level 5", covers[45]);
        countiesList.add(a);

       a = new Counties("West Pokot County", " Level 5", covers[46]);
       countiesList.add(a);



        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


  @Override
  public void onConnected(Bundle bundle) {
      locationRequest = new LocationRequest();
      locationRequest.setInterval(1000);
      locationRequest.setFastestInterval(1000);
      locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
      if (ContextCompat.checkSelfPermission(getActivity(),
              Manifest.permission.ACCESS_FINE_LOCATION)
              == PackageManager.PERMISSION_GRANTED) {
          LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
      }

      mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

          @Override
          public void onMapClick(LatLng point) {

              // Drawing marker on the map
              drawMarker(point);

              // Creating an instance of ContentValues
              ContentValues contentValues = new ContentValues();

              // Setting latitude in ContentValues
              contentValues.put(LocationsDB.FIELD_LAT, point.latitude );

              // Setting longitude in ContentValues
              contentValues.put(LocationsDB.FIELD_LNG, point.longitude);

              // Setting zoom in ContentValues
              contentValues.put(LocationsDB.FIELD_ZOOM, mMap.getCameraPosition().zoom);

              // Creating an instance of LocationInsertTask
              LocationInsertTask insertTask = new LocationInsertTask();

              // Storing the latitude, longitude and zoom level to SQLite database
              insertTask.execute(contentValues);

              Toast.makeText(getActivity().getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();
          }
      });

      mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
          @Override
          public void onMapLongClick(LatLng point) {

              // Removing all markers from the Google Map
              mMap.clear();

              // Creating an instance of LocationDeleteTask
              LocationDeleteTask deleteTask = new LocationDeleteTask();

              // Deleting all the rows from SQLite database table
              deleteTask.execute();

              Toast.makeText(getActivity().getBaseContext(), "All markers are removed", Toast.LENGTH_LONG).show();
          }
      });
  }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode()="+ connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        lastLocation = location;
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();


        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currentLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        Toast.makeText(getActivity(),"Your Current Location", Toast.LENGTH_LONG).show();


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }

    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_LOCATON_CODE);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_LOCATON_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATON_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0,
                                         Bundle arg1) {

        // Uri to the content provider LocationsContentProvider
        Uri uri = LocationsContentProvider.CONTENT_URI;

        // Fetches all the rows from locations table
        return new CursorLoader(getActivity(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0,
                               Cursor arg1) {
        int locationCount = 0;
        double lat=0;
        double lng=0;
        float zoom=0;

        // Number of locations available in the SQLite database table
        locationCount = arg1.getCount();

        // Move the current record pointer to the first row of the table
        arg1.moveToFirst();

        for(int i=0;i<locationCount;i++){

            // Get the latitude
            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LAT));

            // Get the longitude
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LNG));

            // Get the zoom level
            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.FIELD_ZOOM));

            // Creating an instance of LatLng to plot the location in Google Maps
            LatLng location = new LatLng(lat, lng);

            // Drawing the marker in the Google Maps
            drawMarker(location);

            // Traverse the pointer to the next row
            arg1.moveToNext();
        }

        if(locationCount>0){
            // Moving CameraPosition to last clicked position
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));

            // Setting the zoom level in the map on last position  is clicked
            mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }

    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void>{
        @Override
        protected Void doInBackground(ContentValues... contentValues) {

            /** Setting up values to insert the clicked location into SQLite database */
            getActivity().getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {

            /** Deleting all the locations stored in SQLite database */
            getActivity().getContentResolver().delete(LocationsContentProvider.CONTENT_URI, null, null);
            return null;
        }
    }
}