package com.tnaapp.tnalayout.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.activity.MainActivity;
import com.tnaapp.tnalayout.activity.fragments.SwipeNewsFragment;
import com.tnaapp.tnalayout.adapter.NewsRecyclerAdapter;
import com.tnaapp.tnalayout.ai.DataSource;
import com.tnaapp.tnalayout.ai.MyConverter;
import com.tnaapp.tnalayout.ai.News;
import com.tnaapp.tnalayout.model.NewsFragmentItem;
import com.tnaapp.tnalayout.model.NewsItem;
import com.tnaapp.tnalayout.tien.box.TTool;
import com.tnaapp.tnalayout.tien.model.Client;
import com.tnaapp.tnalayout.tien.model.Item;
import com.tnaapp.tnalayout.tien.model.RSS;

import java.util.ArrayList;
import java.util.List;

public class ReceiverActivity extends FragmentActivity {

    private SwipeNewsFragment mSwipeNewsFragment;

    private int mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        handleBundle(bundle);
    }

    private void handleBundle(Bundle bundle) {
        if(bundle != null) {
            String text = bundle.getString("type");
            mId = Integer.parseInt(bundle.getString("id"));
            loadData();
        }
    }

   private void loadData() {
       final List<NewsItem> mItemDemo = new ArrayList<>();
       NewsItem items = new NewsItem();
       final Client client = new Client();
       client.setListener(new Client.Listener() {
           @Override
           public void doneExecute() {
               RSS rss = MyConverter.jsonToRSS(client.result);
               Log.wtf("RSS", client.result);
               if(client.result != null)
               {
                   Log.wtf("Size: ",String.valueOf(rss.getList().size()));
                   int count = 0;
                   int size = rss.getList().size();
                   int postion = 0;
                   for(Item i : rss.getList())
                   {
                       if(i.getId() == mId){
                           postion = count;
                       }
                       NewsItem items = new NewsItem();
                       //items.setId("coincard");
                       items.setTitle(i.getTitle());
                       items.setDes(i.getDescription());
                       items.setThumbnail(i.getSummaryImg());
                       items.setId(String.valueOf(i.getId()));
                       items.setmTime(TTool.calTime("2015-10-16 10:05:27"));
                       mItemDemo.add(items);
                       count++;
                       if(count == size){

                           mSwipeNewsFragment = new SwipeNewsFragment();
                           mSwipeNewsFragment.setNewsItems(mItemDemo);
                           handleIndex(postion,mItemDemo);
                           break;
                       }
                   }

                   DataSource.getInstance().setNews(rss);
               }
               else
               {

               }
           }

           @Override
           public void preExcute() {

           }
       });
       client.execute("http://104.155.237.47/web/api/pull","json");


   }


    private void handleIndex(final int position,List<NewsItem> items) {
        mSwipeNewsFragment.setDefaultViewItem(position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.mContainer,mSwipeNewsFragment);
        fragmentTransaction.commit();
//                        ((MainActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Client client = new Client();
        client.setListener(new Client.Listener() {
            @Override
            public void doneExecute() {
                Log.wtf("RS: ", client.result);
                News news = MyConverter.jsonToRootNew(client.result);
                Log.wtf("NEW:", news.getContent());
                NewsFragmentItem current = (NewsFragmentItem) mSwipeNewsFragment.getAdapter().getItem(position);
                current.getWebView().loadData(news.getContent(), "text/html; charset=UTF-8", null);
            }

            @Override
            public void preExcute() {

            }
        });
        client.execute("http://104.155.237.47/web/api/data?id="+items.get(position).getId(),"json");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
