package com.example.shadi.printerdemo.Nav_Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shadi.printerdemo.Blog;
import com.example.shadi.printerdemo.MainActivity;
import com.example.shadi.printerdemo.R;
import com.example.shadi.printerdemo.SplashScreen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mBlogList ;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("order");

        mBlogList = (RecyclerView) view.findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBlogList.setLayoutManager(new LinearLayoutManager(getContext()));

        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    //   startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    //  finish();
                }


            }
        };
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    //  user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getContext(), SplashScreen.class));
                }


            }
        };
        auth = FirebaseAuth.getInstance();



        return view;

    }

    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog, MainActivity.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, MainActivity.BlogViewHolder>(

                Blog.class,R.layout.blog_row,MainActivity.BlogViewHolder.class,mDatabase)
        {
            @Override
            protected void populateViewHolder(MainActivity.BlogViewHolder viewHolder, Blog model, int position) {

                viewHolder.setTitle(model.gettime());
                viewHolder.setname(model.getname());
                viewHolder.setFilament(model.getFilament());
                viewHolder.setImage(getActivity(), model.getImage());
            }
        };

        mBlogList.setAdapter(firebaseRecyclerAdapter);
        mBlogList.setNestedScrollingEnabled(true);
        auth.addAuthStateListener(authListener);

    }

}
