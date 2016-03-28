package co.wouri.libreexchange.uis.recipient.fragments;

import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.wouri.libreexchange.BuildConfig;
import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.uis.ChooseAmountActivity;
import co.wouri.libreexchange.uis.recipient.adapters.ChooseRecipientAdapter;
import co.wouri.libreexchange.utils.ContactsQuery;
import co.wouri.libreexchange.utils.ImageLoader;
import co.wouri.libreexchange.utils.Utils;

public class RecipientsListFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {

    public static Recipient recipient;
    private static final String TAG = "Contacts";
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private Button nextButton;
    private ChooseRecipientAdapter mAdapter;
    private List<Recipient> recipients;
    private ImageLoader mImageLoader;
    private ArrayList<Recipient> mPeopleList;
    private ProgressDialog progressDialog;
    private View layout;

    public static RecipientsListFragment newInstance(String ...param1) {
        RecipientsListFragment fragment = new RecipientsListFragment();
        return fragment;
    }

    public RecipientsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout =  inflater.inflate(R.layout.fragment_recipients, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        mImageLoader = new ImageLoader(getActivity(), getListPreferredItemHeight()) {
            @Override
            protected Bitmap processBitmap(Object data) {
                // This gets called in a background thread and passed the data from
                // ImageLoader.loadImage().
                return loadContactPhotoThumbnail((String) data, getImageSize());
//                return retrieveContactPhoto((String) data);
//                return loadContactPhoto(getContentResolver(), new Long((String)data));
            }
        };

        // Set a placeholder loading image for the image loader
        mImageLoader.setLoadingImage(R.drawable.unknown);

        // Add a cache to the image loader
        mImageLoader.addImageCache(getActivity().getSupportFragmentManager(), 0.1f);


        mRecyclerView = (RecyclerView) layout.findViewById(R.id.settingsRecyclerView);


        nextButton = (Button) layout.findViewById(R.id.Button_next);
        nextButton.setVisibility(View.VISIBLE);
        // Set an adapter to this recycler view

        ProfileManager.getCurrentUserProfile();
        Log.d(TAG, "Account profile id " + ProfileManager.getCurrentUserProfile().getAccount().getId());
        Log.d(TAG, "Recipient list: " + ProfileManager.getRecipients());
        Log.d(TAG, "Recipient before add: " + ProfileManager.getRecipients().size());

        recipients = ProfileManager.getRecipients();
        if (recipients.size() < 1) {
//            getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
        }

        mAdapter = new ChooseRecipientAdapter(getActivity(), recipients, mImageLoader);


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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipient != null) {
                    Intent intent = new Intent(getActivity(), ChooseAmountActivity.class);
                    intent.putExtra("recipient", (Parcelable) recipient);
                    startActivity(intent);
                } else
                    Toast.makeText(getActivity(), "You must select a recipient", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.updateItems();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onPause() {
        super.onPause();
        mImageLoader.setPauseWork(false);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        progressDialog = new ProgressDialog(getActivity());
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
            return new CursorLoader(getActivity(),
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
                Cursor phones = getActivity().getContentResolver().query(
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
        if (!isAdded() || getActivity() == null) {
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
            afd = getActivity().getContentResolver().openAssetFileDescriptor(thumbUri, "r");

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
                Log.d(TAG, "Contact photo thumbnail not found for contact " + photoData
                        + ": " + e.toString());
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
        getActivity().getTheme().resolveAttribute(
                android.R.attr.listPreferredItemHeight, typedValue, true);

        // Create a new DisplayMetrics object
        final DisplayMetrics metrics = new android.util.DisplayMetrics();

        // Populate the DisplayMetrics
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Return theme value based on DisplayMetrics
        return (int) typedValue.getDimension(metrics);
    }

}
