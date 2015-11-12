package co.wouri.coaze.uis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.core.managers.AccountManager;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.uis.transferhistory.TransferHistoryAdapter;
import co.wouri.coaze.utils.UIUtils;

public class TransferHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Transfer> transfers;
    TransferHistoryAdapter adapter;
//    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_history);
        recyclerView = (RecyclerView) findViewById(R.id.transfer_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        cardView = (CardView) findViewById(R.id.card_view_item);

//        rv.setHasFixedSize(true);
        initializeData();
        initializeAdapter();
        buildToolBar();
    }

    private void initializeData() {
        transfers = AccountManager.getTransferts();

//        persons.add(new Person("Beyonce Knowles", BitmapUtils.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.beyonce), 200), 100, "SEND", "18.07.2015"));
//        persons.add(new Person("Barack Obama", BitmapUtils.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barackobama), 200), 50, "RECIEVED", "11.05.2015"));
//        persons.add(new Person("Barry Green", BitmapUtils.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.unknown), 200), 200, "SEND", "12.03.2015"));
    }

    private void initializeAdapter() {
        adapter = new TransferHistoryAdapter(transfers, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("TRANSFER HISTORY");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quit) {
            Log.d("Coaze", "Click on Finish button");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
