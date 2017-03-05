package ccorp.android.bloodfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajkumar on 2/22/17.
 */

public class HomeScreenActivity extends AppCompatActivity {

    Toolbar mtoolbar_Main;
    private CoordinatorLayout coordinatorLayout;
    LinearLayout dotsContainer;
    TextView[] dots;
    int selectedPage;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mtoolbar_Main=(Toolbar)findViewById(R.id.toolbar);
        mtoolbar_Main.setTitle("Blood Finder");
        setSupportActionBar(mtoolbar_Main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HomeScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };



        ViewPagerAdapter adater = new ViewPagerAdapter(getSupportFragmentManager(),createFragmentList(2));
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adater);


        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.mlayout);

       /* mtoolbar_Main.inflateMenu(R.menu.main);
        mtoolbar_Main.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String menuItem=(String)item.getTitle();
                Snackbar.make(coordinatorLayout,menuItem,Snackbar.LENGTH_LONG).show();

                return false;
            }
        });*/

        // change dots color in page change
        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        dots[selectedPage].setTextColor(getResources().getColor(R.color.pageNotSelected));
                        selectedPage = i;
                        dots[selectedPage].setTextColor(getResources().getColor(R.color.pageSelected));
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                }
        );

        selectedPage = 0;
        dotsContainer = (LinearLayout)findViewById(R.id.dots_indicator_container);
        setupDotsPagination(adater.getCount());

    }

    public void setupDotsPagination(int numOfViews){
        dots = new TextView[numOfViews];

        // dot size in sp
        float dotSize =  60 / getResources().getDisplayMetrics().density;

        for (int i = 0; i < numOfViews; i++) {
            dots[i] = new TextView(this);

            // html code for bullet - &#8226;
            dots[i].setText(Html.fromHtml("&#8226;"));

            dots[i].setTextColor(getResources().getColor(R.color.pageNotSelected));
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, dotSize);
            //dots[i].setTextSize(30);

            // add dot to container
            dotsContainer.addView(dots[i]);
            Log.d("TAG", "got here");
        }

        dots[selectedPage].setTextColor(getResources().getColor(R.color.pageSelected));
    }

    public List<Fragment> createFragmentList(int count){
        List<Fragment> returnList = new ArrayList<>(count);

            returnList.add(new DonorListFragment());
            returnList.add(new BeaDonorActivity());

        return returnList;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar.make(coordinatorLayout,item.getTitle().toString(),Snackbar.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
