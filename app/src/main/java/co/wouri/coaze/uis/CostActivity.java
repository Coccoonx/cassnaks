package co.wouri.coaze.uis;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import co.wouri.coaze.R;
import co.wouri.coaze.utils.UIUtils;

public class CostActivity extends AppCompatActivity {

    int state = 0;

    Button button;

    TextView one;
    TextView two;
    TextView three1;
    TextView three2;
    TextView four;
    TextView five;
    TextView six;
    TextView seven1;
    TextView seven2;
    TextView marks1;
    TextView marks2;
    ImageView costGlobe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        addListenerOnButton();
        buildToolBar();
        initUI();
    }

    private void initUI() {

        one = (TextView) findViewById(R.id.cost_1_line);
        two = (TextView) findViewById(R.id.cost_2_line);
        three1 = (TextView) findViewById(R.id.cost_3_line);
        three2 = (TextView) findViewById(R.id.cost_3_line2);
        four = (TextView) findViewById(R.id.cost_4_line);
        five = (TextView) findViewById(R.id.cost_5_line);
        six = (TextView) findViewById(R.id.cost_6_line);
        seven1 = (TextView) findViewById(R.id.cost_7_line);
        seven2 = (TextView) findViewById(R.id.cost_7_line2);
        marks1 = (TextView) findViewById(R.id.marks1);
        marks2 = (TextView) findViewById(R.id.marks2);
        costGlobe = (ImageView) findViewById(R.id.cost_globe);

        two.setText(R.string.coaze_text222);
        three2.setText(R.string.coaze_text444);
        marks1.setVisibility(View.VISIBLE);
        costGlobe.setVisibility(View.VISIBLE);
        marks2.setVisibility(View.INVISIBLE);
        four.setVisibility(View.INVISIBLE);
        six.setText(R.string.coaze_text888);
        seven2.setText(R.string.coaze_text1000);


        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, one, two, three1, three2, four, five, six, seven1, seven2);
    }

    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.costbutton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                updateUI(state);
//
//                Intent intent = new Intent(context, MainActivity.class);
//                startActivity(intent);

            }

        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (state == 1) {
            updateUI(0);
            state = 0;
        } else if (state == 0) {
            initUI();
        }
    }

    private void updateUI(int b) {


        switch (b) {
            case 0:

                two.setText(R.string.coaze_text22);
                three2.setText(R.string.coaze_text44);

                marks1.setVisibility(View.VISIBLE);
                costGlobe.setVisibility(View.INVISIBLE);
                marks2.setVisibility(View.VISIBLE);
                four.setVisibility(View.VISIBLE);
                four.setText(R.string.coaze_text55);

                six.setText(R.string.coaze_text88);
                seven2.setText(R.string.coaze_text100);
                state = 1;
                break;

            case 1:
                two.setText(R.string.coaze_text2);

                three1.setText(R.string.coaze_text3);
                three1.setVisibility(View.VISIBLE);
                three2.setText(R.string.coaze_text4);

                four.setText(R.string.coaze_text5);
                six.setText(R.string.coaze_text8);

                seven1.setText(R.string.coaze_text3);
                seven1.setVisibility(View.VISIBLE);
                seven2.setText(R.string.coaze_text10);
                marks1.setVisibility(View.INVISIBLE);
                break;

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        finish();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.home) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);
        close.setColorFilter(Color.argb(255, 255, 255, 255));

        title.setText("ABOUT");
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
//                startActivity(new Intent(ChooseRecipientActivity.this, AddRecipientActivity.class));
            }
        });

//        title.setVisibility(View.VISIBLE);
//        option.setVisibility(View.VISIBLE);

    }
}
