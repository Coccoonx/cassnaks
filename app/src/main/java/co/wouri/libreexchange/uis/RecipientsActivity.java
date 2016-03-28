package co.wouri.libreexchange.uis;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.util.HashMap;
import java.util.Map;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.uis.recipient.fragments.RecipientsListFragment;


public class RecipientsActivity extends AppCompatActivity {


    public static final String KEY_CURRENT_STEP = "KEY_CURRENT_STEP";
    public static final String KEY_INCIDENT_REPORT = "KEY_INCIDENT_REPORT";
    Map<String, Fragment> fragmentMap = new HashMap<>();
    View layout;
    private FragmentManager fragmentManager;
    RecipientsListFragment recipientsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient);


        recipientsListFragment = RecipientsListFragment.newInstance("", "");

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, recipientsListFragment);
        fragmentTransaction.commit();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}