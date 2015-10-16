package com.tnaapp.tnalayout.tien.code;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tnaapp.tnalayout.R;
import com.tnaapp.tnalayout.tien.box.ImageAndCaption;
import com.tnaapp.tnalayout.tien.box.WebImageLoader;

import java.util.ArrayList;

public class SelectActivity extends Activity {

    private ArrayList<ImageAndCaption> imageAndCaptions;
    private ImageRowAdapter imageRowAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tien_on_pickteam);
        imageAndCaptions = ImageAndCaption.getList();
        imageRowAdapter = new ImageRowAdapter(this, imageAndCaptions);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(imageRowAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), imageRowAdapter.getItem(position).getImageURL() , Toast.LENGTH_LONG).show();
            }
        });
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

    class ImageRowAdapter extends BaseAdapter {
        private final ArrayList<ImageAndCaption> itemRows;
        private final LayoutInflater inflater;

        public ImageRowAdapter(Context context, ArrayList<ImageAndCaption> itemRows) {
            this.itemRows = itemRows;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return itemRows.size();
        }

        @Override
        public ImageAndCaption getItem(int position) {
            return itemRows.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.tien_listview_row, null);
            }

            final TextView text = (TextView) view.findViewById(R.id.text);
            final ImageView icon = (ImageView) view.findViewById(R.id.icon);

            text.setText(itemRows.get(position).getImageClubName());
            icon.setImageResource(R.drawable.loading);
            WebImageLoader.getInstance().bindWebImageToImageView(
                    itemRows.get(position).getImageURL(), icon);

            return view;
        }
    }
}
