package com.example.shadi.printerdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shadi.printerdemo.Nav_Fragments.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class User_Setting extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Users");
    private DatabaseReference mDatabase;
    EditText fname,lname,email,pass,phone,newpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__setting);

        fname=(EditText)findViewById(R.id.fname);
        lname=(EditText)findViewById(R.id.lname);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        phone=(EditText)findViewById(R.id.phone);
        newpass=(EditText)findViewById(R.id.new_password);
        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
     //   Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users/"+currentFirebaseUser.getUid()+"/user_info");


       // final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("users/"+currentFirebaseUser.getUid()+"/user_info");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

               //String demo=user.email;
                fname.setText(user.fname);
                email.setText(user.email);
              //  pass.setText(user.password);
                lname.setText(user.lname);
                phone.setText(user.mobile);
              //  Toast.makeText(User_Setting.this, ""+ demo , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        Button update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email.getText().toString(), pass.getText().toString());


// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    if(!pass.getText().toString().trim().isEmpty()) {
                                        if (!newpass.getText().toString().trim().isEmpty()) {
                                            user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        ref.setValue(new User(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),phone.getText().toString(),newpass.getText().toString()));

                                                        //   Log.d(TAG, "Password updated");
                                                        Toast.makeText(User_Setting.this, "Updated", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //     Log.d(TAG, "Error password not updated")
                                                    }
                                                }
                                            });
                                        }else {



                                         ref.setValue(new User(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),phone.getText().toString(),pass.getText().toString()));
                                            Toast.makeText(User_Setting.this, "Updated", Toast.LENGTH_SHORT).show();





                                        }

                                    }else
                                    {

                                        Toast.makeText(User_Setting.this, "Enter your password first", Toast.LENGTH_SHORT).show();

                                    }

                                } else {
                                    Toast.makeText(User_Setting.this, "Failed", Toast.LENGTH_SHORT).show();

                                    //  Log.d(TAG, "Error auth failed")
                                }
                            }
                        });
            }
        });



    }


}
