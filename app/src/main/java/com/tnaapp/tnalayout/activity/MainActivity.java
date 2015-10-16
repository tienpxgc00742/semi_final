package com.tnaapp.tnalayout.activity;

import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.tnaapp.tnalayout.tien.code.TActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.fragments.AboutFragment;
import com.tnaapp.tnalayout.activity.fragments.FavoriteFragment;
import com.tnaapp.tnalayout.activity.fragments.GuideFragment;
import com.tnaapp.tnalayout.activity.fragments.HistoryFragment;
import com.tnaapp.tnalayout.activity.fragments.HomeFragment;
import com.tnaapp.tnalayout.activity.fragments.SearchResultFragment;
import com.tnaapp.tnalayout.activity.fragments.VideosChannelFragment;
import com.tnaapp.tnalayout.control.DraggableViewGroup;
import com.tnaapp.tnalayout.control.DraggableViewListener;
import com.tnaapp.tnalayout.control.SwipeDismissTouchListener;
import com.tnaapp.tnalayout.control.video.CustomPlayerControllerVisibilityListener;
import com.tnaapp.tnalayout.control.video.CustomVideoView;
import com.tnaapp.tnalayout.control.video.DefaultCustomPlayerController;
import com.tnaapp.tnalayout.utils.DownloadImageTask;
import com.tnaapp.tnalayout.utils.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener, CustomPlayerControllerVisibilityListener, SearchView.OnQueryTextListener {
    private static String TAG = MainActivity.class.getSimpleName();

    public static Toolbar mToolbar;
    public static FragmentDrawer mFragmentDrawer;
    public static HomeFragment mHomeFragment;
    private AboutFragment mAboutFragment;
    private HistoryFragment mHistoryFragment;
    private FavoriteFragment mFavoriteFragment;
    private GuideFragment mGuideFragment;
    private VideosChannelFragment mVideosChannelFragment;
    public static AccessToken mAccessToken;
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
    static AccessTokenTracker mAccessTokenTracker;
    private SearchView mSearchView;

    private static SharedPreferences _SHARED_PREFS;
    private SettingsActivity mSettingsActivity;
    public static int mCurrentTabSelected = 0;

    private static DraggableViewGroup mDraggableViewGroup;
    private static CustomVideoView mCustomVideoView;
    private static DefaultCustomPlayerController mController;
    private static LinearLayout mLayoutDraggable;
    private static ViewGroup mDismissableContainer;

    private boolean mBackpressed;

    @Override
    public void onBackPressed() {
        if (mFragmentDrawer.isDrawerOpen())
            mFragmentDrawer.closeDrawer();
        else {
            if (mDraggableViewGroup.isMinimize)
                handleBackPressed();
            else {
                mDraggableViewGroup.minimize();
            }
        }
    }

    private void handleBackPressed() {
        if (mBackpressed) {
            super.onBackPressed();
            return;
        }
        T.s(this, getResources().getString(R.string.back_pressed));
        mBackpressed = true;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mBackpressed = false;
            }
        }, 2000);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadUserSettings();
        Log.d("onPostCreate", "onPostCreate reached.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "OnCreate");

        //facebook login init
        FacebookSdk.sdkInitialize(getApplicationContext());
        //load prefs settings - base context - éo biết context là cái mẹ gì
        _SHARED_PREFS = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        try {
            Log.d("", "Start");
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.tnaapp.tnalayout",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("", "Error");
        } catch (NoSuchAlgorithmException e) {
            Log.d("", "Error");
        }

        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        mLoginButton = (LoginButton) findViewById(R.id.login_button);
        mLoginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Log.d("Token: ", loginResult.getAccessToken().getToken());
                initLoggedInSession(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login: ", "Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Login: ", "Login attempt failed.");
            }
        });

        //theo dõi login
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                //logout
                if (currentAccessToken == null) {
                    FragmentDrawer.mUserImage.setClickable(true);
                    FragmentDrawer.mUserName.setText(getResources().getString(R.string.login));
                    FragmentDrawer.mUserImage.setImageResource(R.drawable.ic_profile);

                    //dừng theo dõi
                    this.stopTracking();
                }
            }
        };
        initLoggedInSession(null);
        //end fb login

        //toolbar and fragment init
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mFragmentDrawer = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mFragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mFragmentDrawer.setFragmentDrawerListener(this);
        //end toolbar and fragment init

        //khởi tạo các fragment
        mHomeFragment = new HomeFragment();
        mHistoryFragment = new HistoryFragment();
        mAboutFragment = new AboutFragment();
        mFavoriteFragment = new FavoriteFragment();
        mGuideFragment = new GuideFragment();
        mVideosChannelFragment = new VideosChannelFragment();
        mSearchResultFragment = new SearchResultFragment();
        //end fragment init

        //khởi tạo searchview

        mSettingsActivity = new SettingsActivity();
        //hết searchview

        //trình chạy video nổi
        mLayoutDraggable = (LinearLayout) findViewById(R.id.dismissable_container);
        mCustomVideoView = (CustomVideoView) findViewById(R.id.videoViewPlayer);
        mController = (DefaultCustomPlayerController) findViewById(R.id.play_video_controller);
        mController.setVisibilityListener(this);
        mCustomVideoView.setMediaController(mController);
        mCustomVideoView.setOnPlayStateListener(mController);
        mDraggableViewGroup = (DraggableViewGroup) findViewById(R.id.youtubeLayout);
        mDraggableViewGroup.setRunnerActivity(this);
        mDismissableContainer = (ViewGroup) findViewById(R.id.dismissable_container);
        mDraggableViewGroup.setOnTouchListener(new SwipeDismissTouchListener(mDraggableViewGroup, null, new SwipeDismissTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(Object token) {
//                đặt các phương thức tại đây
                if (mDraggableViewGroup.isMinimize) {
                    // thu nhỏ
                    return true;
                }
                // phóng to
                return false;
            }

            @Override
            public void onDismiss(View view, Object token) {
                mDismissableContainer.removeView(mDraggableViewGroup);
                mDraggableViewGroupExist = false;
            }
        }));
        mDraggableViewGroup.setDraggableViewListener(new DraggableViewListener() {
            @Override
            public void onMaximized() {
                Log.d("DraggableView", "Maximized");
            }

            @Override
            public void onMinimized() {
                Log.d("DraggableView", "Minimized");
            }
        });

        //kết thúc trình chạy video nổi

        //theo dõi cảm biến xoay - lật lại video khi xoay lên hoặc xoay ngang - khốn nạn vcc
        mOrientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int args) {
//                Log.d("onOrientationChanged", String.valueOf(args));
                if (!isRotationLock) {
                    //xoay lần 1 - xoay do cảm biến
                    if (args > 70 && args < 110 || args > 250 && args < 290) {
                        Log.d("onOrientationChanged", "orientation state 1 reached!");
                        mRotateState1 = true;
                    }
                    //xoay lần 2 - xoay do người dùng chỉnh hướng - lần 1 và 2 có thể đảo chiều
                    if (args > 350 && args < 360 || args > 0 && args < 20 || args > 160 && args < 200) {
                        Log.d("onOrientationChanged", "orientation state 2 reached!");
                        mRotateState2 = true;
                    }
                    //khi xoay 2 lần - đưa về auto rotate và dừng sensor tracking
                    if (mRotateState1 == mRotateState2 && !isRotationLock) {
                        Log.d("onOrientationChanged", "2 stage orientation reached, rotate requested, change to auto rotation mode.");
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        isForceFullScreenRequest = false;
                        mRotateState1 = mRotateState2 = false;
//                        Log.d("onOrientationChanged", "reset rotate stage to stage 1: " + String.valueOf(mRotateState1) + ", stage 2: " + String.valueOf(mRotateState2));
                        this.disable();
                    }
                } else {
                    Log.d("onOrientationChanged", "Auto Rotation is locked, disable orientation tracking");
                    this.disable();
                }
            }
        };
        //end control orientation sensor

        //chế animation cho Float Player
        mDraggableViewGroupAnimator = ObjectAnimator.ofFloat(
                mDraggableViewGroup, "alpha", 1.0f).setDuration(250);
        //ngừng chế =))

        displayView(0);
        TActivity.configMainActivity(this);
    }

    private static ObjectAnimator mDraggableViewGroupAnimator;
    private static boolean mDraggableViewGroupExist = false;

    //khởi tạo trình phát video - thêm link trong setVideo
    public static void reloadFloatVideoPlayer(String url) {
        int dismissableViewChildCount = mDismissableContainer.getChildCount();
        Log.d("DismissViewChildCount", String.valueOf(dismissableViewChildCount));
        if (mCustomVideoView.getPreviousStream() != url) {
            mCustomVideoView.setMediaController(mController);
            mCustomVideoView.setOnPlayStateListener(mController);
            mLayoutDraggable.setVisibility(View.VISIBLE);
            mDismissableContainer.invalidate();
            mCustomVideoView.setVideo(url, DefaultCustomPlayerController.DEFAULT_VIDEO_START);
            mCustomVideoView.start();
        }
        if (dismissableViewChildCount != 1 && dismissableViewChildCount < 1) {
            mDismissableContainer.addView(mDraggableViewGroup);
        }
        if (!mDraggableViewGroupExist) {
            mDraggableViewGroup.setAlpha(0.0f);
        }
        mDraggableViewGroup.maximize();
        if (!mDraggableViewGroupExist) {
            mDraggableViewGroupAnimator.start();
            mDraggableViewGroupExist = true;
        }
    }

    //bật theo dõi login
    //lấy thông tin username, avatar..
    public static void initLoggedInSession(AccessToken at) {
        if (at == null) {
            //lấy accessToken từ đăng nhập trước
            mAccessToken = AccessToken.getCurrentAccessToken();
        } else {
            mAccessToken = at;
        }
        //nếu còn phiên
        if (mAccessToken != null) {
            //bắt đầu theo dõi login
            mAccessTokenTracker.startTracking();

            FragmentDrawer.mUserImage.setClickable(true);
            FragmentDrawer.mUserName.setText(FragmentDrawer.mUserName.getContext().getResources().getString(R.string.loading));
            GraphRequest request = GraphRequest.newMeRequest(
                    mAccessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                FragmentDrawer.mUserName.setText(object.getString("name"));

                                Profile profile = Profile.getCurrentProfile();
                                if (profile != null) {
                                    new DownloadImageTask(FragmentDrawer.mUserImage).execute(Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString());
                                }
                            } catch (JSONException | NullPointerException e) {
                                FragmentDrawer.mUserName.setText(FragmentDrawer.mUserName.getContext().getString(R.string.warning_no_network));
                                //không cho nhấn user image
                                FragmentDrawer.mUserImage.setClickable(false);
                                return;
                            }
                        }
                    });
            request.executeAsync();
        }
    }

    //tải thông tin từ prefs
    public void loadUserSettings() {
        isRotationLock = _SHARED_PREFS.getBoolean("prefLockRotation", false);
        Log.w("isRotationLock", String.valueOf(isRotationLock));
        if (isRotationLock) {
            Log.w("onPostCreate", "rotation locked.");
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            Log.w("onPostCreate", "rotation enabled.");
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    public VideosChannelFragment getVideosChannelFragment() {
        if (mVideosChannelFragment != null) {
            return mVideosChannelFragment;
        }
        return new VideosChannelFragment();
    }

    public void loadChannelForPlayer() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.viewDesc, mVideosChannelFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "Resumed");
        loadUserSettings();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "Paused");
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent menuIntent = new Intent(this, mSettingsActivity.getClass());
            startActivityForResult(menuIntent, 0);
            return true;
        }
        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    //lựa chọn fragments
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = mHomeFragment;
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = mFavoriteFragment;
                title = getString(R.string.title_favorite);
                break;
            case 2:
                fragment = mHistoryFragment;
                title = getString(R.string.title_history);
                break;
            case 3:
                fragment = mGuideFragment;
                title = getString(R.string.title_guide);
                break;
            case 4:
                fragment = null;
                Intent menuIntent = new Intent(this, mSettingsActivity.getClass());
                startActivityForResult(menuIntent, 0);
                title = getString(R.string.action_settings);
                break;
            case 5:
                fragment = mAboutFragment;
                title = getString(R.string.title_about);
                break;
            default:
                break;
        }
        if (fragment != null) {
            mFragmentDrawer.selectItem(position);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (position != 0) {
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            }
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onControlsVisibilityChange(boolean value) {
    }

    // search
    private void handleSearchText(String text) {
        String tag = getResources().getString(R.string.search_fragment_tag);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(tag) == null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            fragmentTransaction.replace(R.id.container_body, mSearchResultFragment, tag);
            fragmentTransaction.commit();
// set the toolbar title
            getSupportActionBar().setTitle(R.string.action_search);
        } else {
            mSearchResultFragment = (SearchResultFragment) fragmentManager.findFragmentByTag(tag);
        }

        mSearchResultFragment.setQuery(text);
    }

    private SearchResultFragment mSearchResultFragment;

    //quan trọng
    //nút fullscreen trên trình chạy nổi - CẦN tích hợp vào javaclass
    @Override
    public void requestFullScreen() {
        isForceFullScreenRequest = true;
        mOrientationEventListener.enable();
        if (!mDraggableViewGroup.isFullScreen) {
            //ép buộc nhấn phím, không xoay bằng cảm biến
            mDraggableViewGroup.forceRequestFullscreenPlayer();
            Log.d("onFullScreenRequested", "start tracking orientation sensor.");
            mController.setFullSreenButtonIconByStage(mDraggableViewGroup.isFullScreen);
        } else {
            mDraggableViewGroup.forceRequestFloatPlayer();
            Log.d("onFullScreenRequested", "start tracking orientation sensor.");
            mController.setFullSreenButtonIconByStage(mDraggableViewGroup.isFullScreen);
        }
    }

    //điều khiển xoay - fullscreen - khốn nạn vcc
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //thiết đặt mặc định khi player chưa hiển thị
        if (!isRotationLock) {
            if (!mDraggableViewGroup.isMinimize && mController.getMediaPlayer() != null && mController.getMediaPlayer().isPlaying() && !isForceFullScreenRequest) {
                //xoay bằng cảm biến
                if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mController.setFullSreenButtonIconByStage(true);
                    mDraggableViewGroup.requestFullScreenPlayer();
                }
                if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mController.setFullSreenButtonIconByStage(false);
                    mDraggableViewGroup.requestFloatPlayer();
                }
            }
        }
    }

    //biến điều khiển xoay
    //nếu thực hiện fullscreen bằng button
    //xoay lên 1 lần nữa để đưa về tự động xoay
    private boolean mRotateState1, mRotateState2 = false; //lưu lại state khi xoay
    private boolean isForceFullScreenRequest = false; //bấm nút fullscreen hay dùng xoay bằng cảm biếm
    private OrientationEventListener mOrientationEventListener; //theo dõi cảm biến xoay
    private boolean isRotationLock = true; //khóa xoay - vô hiệu xoay cảm biến - load từ settings

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();
        handleSearchText(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return true;
    }
}
