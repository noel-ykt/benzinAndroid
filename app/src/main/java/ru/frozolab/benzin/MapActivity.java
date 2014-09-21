package ru.frozolab.benzin;

import android.app.Activity;
import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity implements LocationListener {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapview)).getMap();
            map.getUiSettings().setMyLocationButtonEnabled(true);

            try {
                MapsInitializer.initialize(MapActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (map != null) {
                map.setMyLocationEnabled(true);

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, true);
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    onLocationChanged(location);
                }
                locationManager.requestLocationUpdates(provider, 2000, 0, this);

                Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                        .title("Hamburg"));
//                Marker kiel = map.addMarker(new MarkerOptions()
//                        .position(KIEL)
//                        .title("Kiel")
//                        .snippet("Kiel is cool")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
            }
            // Move the camera instantly to hamburg with a zoom of 15.
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

            // Zoom in, animating the camera.
//            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
//        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
//        map.animateCamera(CameraUpdateFactory.zoomTo(5));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
