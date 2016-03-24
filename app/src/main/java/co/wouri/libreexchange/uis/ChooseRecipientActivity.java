package co.wouri.libreexchange.uis;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import co.wouri.libreexchange.BuildConfig;
import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.uis.recipient.adapters.ChooseRecipientAdapter;
import co.wouri.libreexchange.utils.ContactsQuery;
import co.wouri.libreexchange.utils.ImageLoader;
import co.wouri.libreexchange.utils.UIUtils;
import co.wouri.libreexchange.utils.Utils;

public class ChooseRecipientActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    public static Recipient recipient;
    private RecyclerView mRecyclerView;
    private ChooseRecipientAdapter mAdapter;
    private Menu menu;
    private Button nextButton;
    List<Recipient> recipients;
    public static final String TAG = "ChooseRecipientActivity";
    private ProgressDialog progressDialog;
    public static ArrayList<Recipient> mPeopleList = new ArrayList();

    private ImageLoader mImageLoader; // Handles loading the contact image in a background thread
    private Profile profile;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onPause() {
        super.onPause();

        mImageLoader.setPauseWork(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient);

        initUI();

        Locale locale = Locale.getDefault();
        Currency currency = Currency.getInstance(locale);
        Log.e("Locale", "Currency Code: " + currency.getCurrencyCode());
        Log.e("Locale", "Symbol: " + currency.getSymbol());
        Log.e("Locale", "Default Fraction Digits: " + currency.getDefaultFractionDigits());

         /*
         * An ImageLoader object loads and resizes an image in the background and binds it to the
         * QuickContactBadge in each item layout of the ListView. ImageLoader implements memory
         * caching for each image, which substantially improves refreshes of the ListView as the
         * user scrolls through it.
         *
         * To learn more about downloading images asynchronously and caching the results, read the
         * Android training class Displaying Bitmaps Efficiently.
         *
         * http://developer.android.com/training/displaying-bitmaps/
         */
        Log.i("params", "image size : " + getListPreferredItemHeight());
        mImageLoader = new ImageLoader(this, getListPreferredItemHeight()) {
            @Override
            protected Bitmap processBitmap(Object data) {
                // This gets called in a background thread and passed the data from
                // ImageLoader.loadImage().
                return loadContactPhotoThumbnail((String) data, getImageSize());
//                return retrieveContactPhoto((String) data);
            }
        };

        // Set a placeholder loading image for the image loader
        mImageLoader.setLoadingImage(R.drawable.unknown);

        // Add a cache to the image loader
        mImageLoader.addImageCache(0.1f);

        mRecyclerView = (RecyclerView) findViewById(R.id.settingsRecyclerView);


        nextButton = (Button) findViewById(R.id.Button_next);
        nextButton.setVisibility(View.VISIBLE);
        // Set an adapter to this recycler view

        ProfileManager.getCurrentUserProfile();
        Log.d(TAG, "Account profile id " + ProfileManager.getCurrentUserProfile().getAccount().getId());
        Log.d(TAG, "Recipient list: " + ProfileManager.getRecipients());
        Log.d(TAG, "Recipient before add: " + ProfileManager.getRecipients().size());

        recipients = ProfileManager.getRecipients();
        if (recipients.size() < 1) {
            getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
        }

        mAdapter = new ChooseRecipientAdapter(this, recipients, mImageLoader);


        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    mImageLoader.setPauseWork(true);
                } else {
                    mImageLoader.setPauseWork(false);
                }
            }
        });


        // Set the behaviour of this recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipient != null) {

                    Intent intent = new Intent(ChooseRecipientActivity.this, ChooseAmountActivity.class);
                    intent.putExtra("recipient", (Parcelable) recipient);
                    startActivity(intent);
                } else
                    Toast.makeText(ChooseRecipientActivity.this, "You must select a recipient", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.updateItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user_step, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //  return true;
        //}
        return false;
        //return super.onOptionsItemSelected(item);
    }

    private void buildToolBars() {
        View toolbar = findViewById(R.id.toolbar);


        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("CHOOSE RECIPIENT");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        option.setImageResource(R.drawable.ic_autorenew_black_18dp);
        title.setVisibility(View.VISIBLE);
        option.setVisibility(View.VISIBLE);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, ChooseRecipientActivity.this);
//                startActivity(new Intent(ChooseRecipientActivity.this, AddRecipientActivity.class));
            }
        });


    }

    void initUI() {
        profile = ProfileManager.getCurrentUserProfile();

        buildToolBar();
        buildDrawer();

    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView menu = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView close = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setVisibility(View.VISIBLE);
        close.setVisibility(View.VISIBLE);

        title.setText("CHOOSE RECIPIENT");

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    void buildDrawer() {

        Locale locale = Locale.getDefault();
        Currency currency = Currency.getInstance(locale);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        LinearLayout linearProfile = (LinearLayout) navigationView.findViewById(R.id.linear_profile);
        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRecipientActivity.this, ProfileActivity.class);
                intent.putExtra("profile", (Parcelable) profile.getAccount());
                intent.putExtra("isUpdate", true);
                startActivity(intent);
            }
        });

        TextView username = (TextView) navigationView.findViewById(R.id.username);
        TextView userEmail = (TextView) navigationView.findViewById(R.id.useremail);
        TextView userBalance = (TextView) navigationView.findViewById(R.id.userbalance);

        String usern = profile.getAccount().getFirstName() == null ? profile.getAccount().getPhoneNumber() : profile.getAccount().getFirstName();
        username.setText(usern);
        userEmail.setText(profile.getAccount().getEmail());
        userBalance.setText(currency.getSymbol() + " " + profile.getAccount().getBalance());

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, userBalance, userEmail, username);


        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    UIUtils.applyFontToMenuItem(UIUtils.Font.MUSEOSANS_500, subMenuItem);
                }
            }

            //the method we have create in activity
            UIUtils.applyFontToMenuItem(UIUtils.Font.MUSEOSANS_500, mi);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                menuItem.setChecked(true);

                if (menuItem.getItemId() == R.id.about_item) {
                    startActivity(new Intent(ChooseRecipientActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    startActivity(new Intent(ChooseRecipientActivity.this, TransferHistoryActivity.class));
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    startActivity(new Intent(ChooseRecipientActivity.this, BalanceActivity.class));
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Retrieving data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
//        return new CursorLoader(this, CONTENT_URI, null, null, null, null);

        // If this is the loader for finding contacts in the Contacts Provider
        // (the only one supported)
        if (id == ContactsQuery.QUERY_ID) {
            Uri contentUri;

            // There are two types of searches, one which displays all contacts and
            // one which filters contacts by a search query. If mSearchTerm is set
            // then a search query has been entered and the latter should be used.

//            if (mSearchTerm == null) {
//                // Since there's no search string, use the content URI that searches the entire
//                // Contacts table
            contentUri = ContactsQuery.CONTENT_URI;
//            } else {
//                // Since there's a search string, use the special content Uri that searches the
//                // Contacts table. The URI consists of a base Uri and the search string.
//                contentUri =
//                        Uri.withAppendedPath(ContactsQuery.FILTER_URI, Uri.encode(mSearchTerm));
//            }

            // Returns a new CursorLoader for querying the Contacts table. No arguments are used
            // for the selection clause. The search string is either encoded onto the content URI,
            // or no contacts search string is used. The other search criteria are constants. See
            // the ContactsQuery interface.
            return new CursorLoader(this,
                    contentUri,
                    ContactsQuery.PROJECTION,
                    ContactsQuery.SELECTION,
                    null,
                    ContactsQuery.SORT_ORDER);
        }

        Log.e(TAG, "onCreateLoader - incorrect ID provided (" + id + ")");
        return null;


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mPeopleList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            String photoUri = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            Recipient recipient = new Recipient();
            recipient.setFirstName(contactName);
            recipient.setImageUri(photoUri);

            if ((Integer.parseInt(hasPhone) > 0)) {
                // You know have the number so now query it like this
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null, null);
                int i = 0;
                while (phones.moveToNext()) {
                    Log.d("safer", "onLoadFinished " + i++);
                    //store numbers and display a dialog letting the user select which.
                    String phoneNumber = phones.getString(
                            phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                    recipient.getPhoneNumbers().add(phoneNumber);
                    //Then add this map to the list.

                }
                mPeopleList.add(recipient);
                Log.d("safer", "onLoadFinished list.size " + mPeopleList.size());
                phones.close();
                progressDialog.dismiss();
            }
        }
        recipients = mPeopleList;
        mAdapter.notifyDataSetChanged();
        ProfileManager.getCurrentUserProfile().setRecipients(mPeopleList);
        ProfileManager.saveProfile();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private Bitmap retrieveContactPhoto(String contactID) {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    /**
     * Gets the preferred height for each item in the ListView, in pixels, after accounting for
     * screen density. ImageLoader uses this value to resize thumbnail images to match the ListView
     * item height.
     *
     * @return The preferred height in pixels, based on the current theme.
     */
    private int getListPreferredItemHeight() {
        final TypedValue typedValue = new TypedValue();

        // Resolve list item preferred height theme attribute into typedValue
        getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);

        // Create a new DisplayMetrics object
        final DisplayMetrics metrics = new android.util.DisplayMetrics();

        // Populate the DisplayMetrics
//        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Return theme value based on DisplayMetrics
        return (int) typedValue.getDimension(metrics);
    }


    /**
     * Decodes and scales a contact's image from a file pointed to by a Uri in the contact's data,
     * and returns the result as a Bitmap. The column that contains the Uri varies according to the
     * platform version.
     *
     * @param photoData For platforms prior to Android 3.0, provide the Contact._ID column value.
     *                  For Android 3.0 and later, provide the Contact.PHOTO_THUMBNAIL_URI value.
     * @param imageSize The desired target width and height of the output image in pixels.
     * @return A Bitmap containing the contact's image, resized to fit the provided image size. If
     * no thumbnail exists, returns null.
     */
    private Bitmap loadContactPhotoThumbnail(String photoData, int imageSize) {

        // Ensures the Fragment is still added to an activity. As this method is called in a
        // background thread, there's the possibility the Fragment is no longer attached and
        // added to an activity. If so, no need to spend resources loading the contact photo.
        if (this == null) {
            return null;
        }

        // Instantiates an AssetFileDescriptor. Given a content Uri pointing to an image file, the
        // ContentResolver can return an AssetFileDescriptor for the file.
        AssetFileDescriptor afd = null;

        // This "try" block catches an Exception if the file descriptor returned from the Contacts
        // Provider doesn't point to an existing file.
        try {
            Uri thumbUri;
            // If Android 3.0 or later, converts the Uri passed as a string to a Uri object.
            if (Utils.hasHoneycomb()) {
                thumbUri = Uri.parse(photoData);
            } else {
                // For versions prior to Android 3.0, appends the string argument to the content
                // Uri for the Contacts table.
                final Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, photoData);

                // Appends the content Uri for the Contacts.Photo table to the previously
                // constructed contact Uri to yield a content URI for the thumbnail image
                thumbUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }
            // Retrieves a file descriptor from the Contacts Provider. To learn more about this
            // feature, read the reference documentation for
            // ContentResolver#openAssetFileDescriptor.
            afd = getContentResolver().openAssetFileDescriptor(thumbUri, "r");

            // Gets a FileDescriptor from the AssetFileDescriptor. A BitmapFactory object can
            // decode the contents of a file pointed to by a FileDescriptor into a Bitmap.
            FileDescriptor fileDescriptor = afd.getFileDescriptor();

            if (fileDescriptor != null) {
                // Decodes a Bitmap from the image pointed to by the FileDescriptor, and scales it
                // to the specified width and height
                return ImageLoader.decodeSampledBitmapFromDescriptor(
                        fileDescriptor, imageSize, imageSize);
            }
        } catch (FileNotFoundException e) {
            // If the file pointed to by the thumbnail URI doesn't exist, or the file can't be
            // opened in "read" mode, ContentResolver.openAssetFileDescriptor throws a
            // FileNotFoundException.
            if (BuildConfig.DEBUG) {
                Log.d("Thumbnail", "Contact photo thumbnail not found for contact " + photoData
                        + ": " + e.getMessage());
            }
        } finally {
            // If an AssetFileDescriptor was returned, try to close it
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    // Closing a file descriptor might cause an IOException if the file is
                    // already closed. Nothing extra is needed to handle this.
                }
            }
        }

        // If the decoding failed, returns null
        return null;
    }
}
