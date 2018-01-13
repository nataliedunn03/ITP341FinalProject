package natalie.dunn.itp341.final_project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Button logout;
    private Button reportIncidient;
    private Button myIncidents;
    private Button incidentMap;
    private Button allIncidents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = (Button) findViewById(R.id.button7);
        reportIncidient = (Button) findViewById(R.id.button4);
        myIncidents = (Button) findViewById(R.id.button3);
        incidentMap = (Button) findViewById(R.id.button5);
        allIncidents = (Button) findViewById(R.id.button6);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                loadLogInView();
            }
        });

        //launches fragment to show all user created incidents
        allIncidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment f = new All_Incidents_Fragment(); //instantiated the fragment
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, f);
                ft.commit();
            }
        });

        //launches fragment to show map of incidents
        incidentMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, IncidentMapActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //launches fragment to show user incidients
        myIncidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment f = new My_Incidents_Fragment(); //instantiated the fragment
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, f);
                ft.commit();
            }
        });

        //launches fragment to show report screen
        reportIncidient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment f = new Report_Fragment(); //instantiated the fragment
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, f);
                ft.commit();
            }
        });
    }

    //launches intent to show login page, prompting users to login to the application
    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
