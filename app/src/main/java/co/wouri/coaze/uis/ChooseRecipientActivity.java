package co.wouri.coaze.uis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.core.managers.AccountManager;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.uis.recipient.adapters.ChooseRecipientAdapter;
import co.wouri.coaze.utils.UIUtils;

public class ChooseRecipientActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ChooseRecipientAdapter mAdapter;
    private Menu menu;
    private Button nextButton;
    List<Recipient> recipients;
    public static final String TAG = "ChooseRecipientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient);

        buildToolBar();

        mRecyclerView = (RecyclerView) findViewById(R.id.settingsRecyclerView);


        nextButton = (Button) findViewById(R.id.Button_next);
        nextButton.setVisibility(View.VISIBLE);
        // Set an adapter to this recycler view

        AccountManager.getCurrentUserAccount();
        Log.d(TAG, "User account id" + AccountManager.getCurrentUserAccount().getUser().getUserId());
        Log.d(TAG, "Recipient list: " + AccountManager.getRecipients());

        mRecyclerView.setAdapter(new ChooseRecipientAdapter(this, AccountManager.getRecipients()));

        // Set the behaviour of this recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " next button clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChooseRecipientActivity.this, ChooseAmountActivity.class));
            }
        });
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

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("CHOOSE RECIPIENT");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseRecipientActivity.this, AddRecipientActivity.class));
            }
        });

        title.setVisibility(View.VISIBLE);
        option.setVisibility(View.VISIBLE);

    }
}
