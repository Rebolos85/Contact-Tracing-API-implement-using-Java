package com.contact.tracing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.contact.tracing.adapter.retrofit.DashboardPageAdapter;
import com.contact.tracing.controller.fragment.dashboard.AddPatientFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import devlight.io.library.ntb.NavigationTabBar;

public class Dashboard extends AppCompatActivity {

    LinearLayout dashboard;
    NavigationTabBar navigationTabBar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        initUI();
    }

    public void init() {
        dashboard = findViewById(R.id.dashboard_layout);
        navigationTabBar = findViewById(R.id.ntb_horizontal);
        fab = findViewById(R.id.fab);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);

        viewPager.setAdapter(new DashboardPageAdapter(getBaseContext()));


        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_24dp),
                        Color.parseColor(colors[0]))
                        .title("Home")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_notifications_black_24dp),
                        Color.parseColor(colors[1]))
                        .title("Notifications")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });


        final CoordinatorLayout coordinatorLayout = findViewById(R.id.parent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.add_patient_fragment_screen, new AddPatientFragment()).commit();
                dashboard.setVisibility(View.GONE);
                navigationTabBar.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout =
                findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#009F90AF"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#9f90af"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dashboard.setVisibility(View.VISIBLE);
        navigationTabBar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }


}