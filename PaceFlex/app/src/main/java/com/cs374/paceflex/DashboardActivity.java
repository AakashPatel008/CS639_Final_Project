package com.cs374.paceflex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private DashboardFragment dashboardFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar=findViewById(R.id.mytoolbar);
        bottomNavigationView=findViewById(R.id.bottomNav);
        frameLayout=findViewById(R.id.main_frame);
        toolbar.setTitle("PaceFlex");
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
            this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=findViewById(R.id.naview);
        navigationView.setNavigationItemSelectedListener(this);

        dashboardFragment=new DashboardFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        setFragment(dashboardFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.teal_700);
                        return true;
                    case R.id.income:
                        setFragment(incomeFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.green_700);
                        return true;
                    case R.id.expense:
                        setFragment(expenseFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.md_theme_light_error);
                        return true;
                    default:
                        return false;
                }
            }


        });

    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed(){
        DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else{
            super.onBackPressed();
        }

    }
    public void displaySelected(int itemId){
        Fragment fragment=null;
        switch (itemId){
            case R.id.dashboard:
                break;
            case R.id.income:
                break;
            case R.id.expense:
                break;
        }
        if (fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame,fragment);
            ft.commit();
        }
        DrawerLayout drawerLayout=findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelected(item.getItemId());
        return true;
    }
}