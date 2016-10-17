package com.untitledapps.meetasweedt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
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
import com.untitledapps.MapsAPI.GetNearbyPlacesData;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Shaotime on 10/2/2016.
 */

public class FikaMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private int PROXIMITY_RADIUS = 10000; //change this int to whatever your radius m
    private GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fika_map);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Checks to see if phone has permissions, then initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true); //enable current user location
            }

        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        //Button for showing the nearby cafes
        Button buttonCafe = (Button) findViewById(R.id.btnCafe);
        buttonCafe.setOnClickListener(new View.OnClickListener() {
            String Cafe = "cafe";

            @Override
            public void onClick(View v) {

                if (!visible) {

                    Log.d("onClick", "Button is Clicked");
                    mMap.clear(); //refreshes the map so that any pre-deposited markers are deleted
                    String url = getUrl(latitude, longitude, Cafe); //making a url that is used to get information about nearby cafes on google maps
                    Object[] DataTransfer = new Object[2];
                    DataTransfer[0] = mMap;
                    DataTransfer[1] = url;
                    Log.d("onClick", url);
                    GetNearbyPlacesData gnpData = new GetNearbyPlacesData();
                    gnpData.execute(DataTransfer); //markers are added on nearby restaurants using this method
                    Toast.makeText(FikaMapActivity.this, "Nearby Cafes", Toast.LENGTH_LONG).show();
                    visible = true;
                } else {

                    mMap.clear();
                    visible = false;
                }
            }
        });
    }

            //builder method that initializes Google Play Services
        protected synchronized void buildGoogleApiClient(){
            mGoogleApiClient = new GoogleApiClient.Builder(this) //used to configure client
                    .addConnectionCallbacks(this) //provides callbacks that are called when client connected or disconnected
                    .addOnConnectionFailedListener(this) //covers scenarios of failed attempt of connect client to service
                    .addApi(LocationServices.API) //adds the LocationServices API endpoint from Google Play Services
                    .build();
            mGoogleApiClient.connect(); //A client must be connected before executing any operation
        }

        @Override
        public void onConnected(Bundle bundle){
            //used to get quality of service location updates from the FusedLocationProviderApi using requestLocationUpdates
            System.out.println("onConnected FikaMapActivity");
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }


        @Override
        public void onConnectionSuspended(int i){

        }


        @Override
        public void onConnectionFailed(ConnectionResult connectionResult){

        }


        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
            switch (requestCode) { //switch around requestCode values for different cases
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    //if request is cancelled, permissions array are empty and the result arrays are empty
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //results are not empty and permission was granted
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            if (mGoogleApiClient == null) {
                                buildGoogleApiClient();
                            }
                            mMap.setMyLocationEnabled(true);
                        }

                    } else {
                        Toast.makeText(this, "Permission denied,", Toast.LENGTH_LONG).show(); //permission was denied
                    }
                    return;
                }

                //other 'case' lines here to check for other permissions this app might request
                //add here other case statements according to requirements


            }
        }

        @Override
        public void onLocationChanged(Location location){
            Log.d("onLocationChanged", "entered");

            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            //getting coordinates of current location and then positioning marker on it
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mMap.addMarker(markerOptions);

            //animates the camera to current location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
            Toast.makeText(FikaMapActivity.this, "Your Current Location", Toast.LENGTH_LONG).show();

            Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude));
            System.out.println(DateFormat.getTimeInstance().format(new Date()));

            //stop location updates
            if (mGoogleApiClient == null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                Log.d("onLocationChanged", "Removing Location Updates");
            }
            Log.d("onLocationChanged", "Exit");
        }


        public boolean checkLocationPermission(){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { //checkSelfPermission should return PackageManager.PERMISSION_GRANTED, then return true and the app can proceed with the operation
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) { // returns true if the app has requested this permission previously and the user denied the request, returns false if user has chosen Don't ask again option when it previously asked for permission

                    //show an explanation to the user *asynchronously* -- don't block
                    //this thread waiting for the user's response! After the user
                    //sees the explanation, try again to request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION); //Prompt the user once explanation has been shown
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION); //No explanation needed, we can request the permission
                }
                return false;
            } else {
                return true;
            }
        }

        //Checks if Google Play Services available or not
        private boolean CheckGooglePlayServices () {
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance(); //this is the Helper class for verifying that the Google Play Services APk is available and up-to-date
            int result = googleAPI.isGooglePlayServicesAvailable(this);
            if (result != ConnectionResult.SUCCESS) {
                if (googleAPI.isUserResolvableError(result)) {
                    googleAPI.getErrorDialog(this, result, 0).show();
                }
                return false;
            }
            return true;
        }

        private String getUrl ( double latitude, double longitude, String nearbyPlace){
            StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            googlePlacesUrl.append("location=" + latitude + "," + longitude);
            googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
            googlePlacesUrl.append("&type=" + nearbyPlace);
            googlePlacesUrl.append("&sensor=true");
            googlePlacesUrl.append("&key=" + "AIzaSyBDVnMhxw_K6vbt-zwSvMkbNbr0GbpYDlA ");
            Log.d("getUrl", googlePlacesUrl.toString());
            return (googlePlacesUrl.toString());
        }
}