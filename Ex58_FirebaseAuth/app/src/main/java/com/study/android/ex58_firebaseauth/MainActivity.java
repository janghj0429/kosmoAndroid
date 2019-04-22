package com.study.android.ex58_firebaseauth;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    private static final int RC_SIGN_IN = 1001;

    //Firebase - Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;

    //Views
    private SignInButton mBtnGoogleSignIn;  //로그인 버튼
    private Button mBtnGoogleSignOut;       //로그 아웃 버튼
    private TextView mTxtProfileInfo;       //사용자 정보 표시
    private ImageView mImgProfile;          //사용자 프로플 이미지 표시

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFirebaseAuth();
    }

    private void initView(){
        mBtnGoogleSignIn = findViewById(R.id.btn_google_signin);
        mBtnGoogleSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signIn();
            }
        });

        mTxtProfileInfo = findViewById(R.id.txt_profile_info);
        mImgProfile = findViewById(R.id.img_profile);
        mBtnGoogleSignOut = findViewById(R.id.btn_google_signout);
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gsio = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gsio)
                .build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateProfile();
            }
        };
    }

    private void updateProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // 비 로그인 상태
            mBtnGoogleSignIn.setVisibility(View.VISIBLE);
            mBtnGoogleSignOut.setVisibility(View.GONE);
            mTxtProfileInfo.setVisibility(View.GONE);
            mImgProfile.setVisibility(View.GONE);
        } else {
            // 로그인 상태
            mBtnGoogleSignIn.setVisibility(View.GONE);
            mBtnGoogleSignOut.setVisibility(View.VISIBLE);
            mTxtProfileInfo.setVisibility(View.VISIBLE);
            mImgProfile.setVisibility(View.VISIBLE);

            String userName = user.getDisplayName(); // 채팅에 사용 될 닉네임 설정
            String userEmail = user.getEmail();
            StringBuilder profile = new StringBuilder();
            profile.append(userName).append("\n").append(userEmail);
            mTxtProfileInfo.setText(profile);
            Uri userPhoto = user.getPhotoUrl();
            if (userPhoto != null){
                Picasso.with(this).load(userPhoto).into(mImgProfile);
            }
        }
    }

    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut(View v){
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateProfile();
                    }
                }
        );
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                updateProfile();
            }
        }
    }

}
