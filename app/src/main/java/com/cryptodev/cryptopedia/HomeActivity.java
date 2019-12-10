package com.cryptodev.cryptopedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    GoogleSignInAccount googleSignInAccount;
    GoogleSignInClient googleSignInClient;
    public static final String GOOGLE_ACCOUNT = "google_account";
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment =null;
    String username = "guest";
    private String mURL="url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        if(savedInstanceState==null){
            fragment = new PriceFragment();
            callFragment(fragment);
            setTitle("price");


        }
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
        TextView navEmail = (TextView) headerView.findViewById(R.id.userEmail);


        if (getIntent().getParcelableExtra("username")!=null){
            GoogleSignInAccount googleSignInAccount=getIntent().getParcelableExtra("username");
            username = googleSignInAccount.getGivenName();
            Uri imageUrl = googleSignInAccount.getPhotoUrl();
            String userEmail = googleSignInAccount.getEmail();



            Picasso.get().load(imageUrl).into(imageView);
            navUsername.setText(username);
            navEmail.setText(userEmail);


        }
        else if (getIntent().getStringExtra("username")!=null){

            imageView.setImageResource(R.drawable.ic_person_white_24dp);
            navUsername.setText(getIntent().getStringExtra("username"));
            navEmail.setText("example@gmail.com");

        }
        else{
            imageView.setImageResource(R.drawable.ic_person_white_24dp);
            navUsername.setText("guest");
            navEmail.setText("example@gmail.com");
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestProfile()
                .requestEmail()
                .build();
        Log.w("tes", "onCreate");
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        LoaderManager loaderManager = getSupportLoaderManager();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView =  (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");


        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            fragment = new PriceFragment();
            callFragment(fragment);
            setTitle("price");
        } else if (id == R.id.nav_gallery) {
            fragment = new FormationFragment();
            callFragment(fragment);
            setTitle("Formasi");
        } else if (id == R.id.nav_slideshow) {
            fragment = new NewsFragment();
            callFragment(fragment);
            setTitle("Berita");
        } else if (id == R.id.nav_logOut){
            Log.w("tes", "onSignout");
            signOut();

        }
        else if (id == R.id.nav_tools){
            Intent intent = new Intent(HomeActivity.this,chatroom.class);
            intent.putExtra("username",username);
            startActivity(intent);

        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {



            googleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.w("tes", "onSignout2");
                            revokeAccess();
                        }
                    });


    }

    private void revokeAccess() {
        googleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.w("tes", "revokeAcces");
                        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
    }


    private void callFragment(Fragment fragment){

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_home,fragment)
                .commit();
    }



}
