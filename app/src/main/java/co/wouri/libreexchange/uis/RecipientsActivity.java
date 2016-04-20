package co.wouri.libreexchange.uis;


import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Toast;


import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.wouri.libreexchange.BuildConfig;
import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.uis.recipient.adapters.ChooseRecipientAdapter;
import co.wouri.libreexchange.uis.recipient.fragments.RecipientsListFragment;
import co.wouri.libreexchange.utils.ContactsQuery;
import co.wouri.libreexchange.utils.ImageLoader;
import co.wouri.libreexchange.utils.Utils;


public class RecipientsActivity extends AppCompatActivity {


    private static final String TAG = "RecipientActivity";
    private ChooseRecipientAdapter mAdapter;
    private List<Recipient> recipients;
    private RecyclerView mRecyclerView;
    private ImageLoader mImageLoader;
    private ArrayList<Recipient> mPeopleList;
    private Button nextButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }




    @Override
    protected void onPause() {
        super.onPause();
        mImageLoader.setPauseWork(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.updateItems();
        mImageLoader.setPauseWork(false);
    }

}