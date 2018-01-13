package natalie.dunn.itp341.final_project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Report_Fragment extends Fragment {
    private EditText date;
    private EditText location;
    private EditText time;
    private EditText description;
    private Button report;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    public DatabaseReference mDatabase;
    private String mUserId;
    private Button test;
    public Incident incident;


    public Report_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        super.onActivityCreated(savedInstanceState);


        date = (EditText) getActivity().findViewById(R.id.textView3);
        time = (EditText) getActivity().findViewById(R.id.textView4);
        location = (EditText) getActivity().findViewById(R.id.textView5);
        description = (EditText) getActivity().findViewById(R.id.textView6);
        test = (Button) getActivity().findViewById(R.id.button8);



        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (!FirebaseApp.getApps(getActivity()).isEmpty()) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        mUserId = mFirebaseUser.getUid();


        //listener for when user clicks on "Report" button
        test.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                    //creates Incident object containing all user-inputted data from the EditText views
                    incident = new Incident(date.getText().toString(), time.getText().toString(),
                            location.getText().toString(), description.getText().toString());

                    //writes to Firebase "all" node all the info that the user entered about the incident. Will
                    //be used for Archive list.
                    mDatabase.child("all").push().child("incident").setValue(incident.joinAll(date.getText().toString(), time.getText().toString(),
                            location.getText().toString(), description.getText().toString()));


                    //writes to Firebase "mapLocations" node the location that the user entered. Will be used to display
                    //on Map.
                    mDatabase.child("mapLocations").push().child("location").setValue(incident.getLocation());


                    //writes to Firebase "personal" node, specifically on the individual users child node,
                    //all the info the user entered about the incidient. Will be used for My Incidents list.
                    mDatabase.child("personal").child(mUserId).push().child("incident").setValue(incident.joinAll(date.getText().toString(), time.getText().toString(),
                            location.getText().toString(), description.getText().toString()));

                    date.setText(R.string.date);
                    time.setText(R.string.time);
                    location.setText(R.string.location);
                    description.setText(R.string.Description);
            }

        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment and return View that the user sees
        final View rootView = inflater.inflate(R.layout.fragment_report_, container, false);
        return rootView;
    }

}
