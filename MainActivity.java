package com.aqib.mymedreminder;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aqib.mymedreminder.activity.HotelListActivity;
import com.aqib.mymedreminder.fragment.ChangePasswordFragment;
import com.aqib.mymedreminder.fragment.HomeFragment;
import com.aqib.mymedreminder.fragment.MusicFragment;

public class MainActivity extends DrawerActivity {

    private static final String TAG_HOME = "home";
    private static final String TAG_Change_Password = "password";

    private static final String TAG_MUSIC = "music";


    public static String CURRENT_TAG = TAG_HOME;
    public static String Previous_TAG="";

    // index to identify current nav menu item
    public static int navItemIndex = 0;


    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles1);
        mHandler=new Handler();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        main_img_playmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navItemIndex = 2;
                CURRENT_TAG = TAG_MUSIC;
                loadHomeFragment();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        101);
            }
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_change_password){
            navItemIndex = 1;
            Previous_TAG=CURRENT_TAG;
            CURRENT_TAG = TAG_Change_Password;
            loadHomeFragment();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true; // Indicate that the event was handled
    }

    private void setToolbarTitle() {

        toolbar.setTitle(activityTitles[navItemIndex]);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {


        // set toolbar title
        setToolbarTitle();

        if(Previous_TAG==CURRENT_TAG){
            //  drawer.closeDrawers();
            return;
        }

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
//        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
//            drawer.closeDrawers();
//
//
//            return;
//        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }

                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }



        //Closing drawer on item click
        //  drawer.closeDrawers();


    }

    private Fragment getHomeFragment() {

        switch (navItemIndex) {
            case 0:
                /// Dashborad fragmnet
                return  HomeFragment.newInstance("","");
            case 1:
                /// About us Fragment
                return ChangePasswordFragment.newInstance("","");
            case 2:
                /// contact us fragment
                return MusicFragment.newInstance("","");
            case 3:
               // return new NotificationFragment();
            case 4:
               // return ProfileFragment.newInstance("","");
            case 5:
               // return ShorttermFragment.newInstance("","");

            default:
                return  HomeFragment.newInstance("","");
        }
    }

    @Override
    public void onBackPressed() {

//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        super.onBackPressed();
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                Previous_TAG=CURRENT_TAG;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        finishAffinity();
        // super.onBackPressed();
    }

}
