package natalie.dunn.itp341.final_project;

import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.messages.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class IncidentMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public DatabaseReference mDatabase;
    public String location;
    Geocoder coder;
    List<android.location.Address> address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //creates reference to the Firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //creates listener on "mapLocations" node in Firebase. Calls func to add marker on all locations
        //currently stored in the DB, and will be updated whenever a new location is added by the user.
        mDatabase.child("mapLocations").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //captures location string stored in the Firebase DB and sends it to getLocationFromAddress func
                location = dataSnapshot.child("location").getValue().toString();
                getLocationFromAddress(location);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getLocationFromAddress(String strAddress) {
        try {
            coder = new Geocoder(this);
            //Get list of top 5 latLng coordinates from the user-inputted location
            address = coder.getFromLocationName(strAddress,5);

            //check for null
            if (address == null) {
                return;
            }

            //Capture the first choice of LatLng coordinates for the location and stores as a LatLng object
            android.location.Address location=address.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            //Put marker on map at that LatLng
            Marker srchMarker = mMap.addMarker(new MarkerOptions().position(latLng));

            //Animate and Zoon on that map location
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                //marker.remove();
                return true;
            }
        });
    }


    }