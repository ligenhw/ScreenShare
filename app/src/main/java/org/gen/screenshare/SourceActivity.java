package org.gen.screenshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.gen.screensharesdk.DeviceInfo;
import org.gen.screensharesdk.ScreenShareException;
import org.gen.screensharesdk.source.SourceManager;

public class SourceActivity extends Activity implements
        SourceManager.OnInitListener,
        SourceManager.ScreenShareStatusListener {

    private static final String TAG = "SourceActivity";
    private SourceManager ss;
    private ListView mListView;
    private ArrayAdapter<DeviceInfo> adapter;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);
        initViews();
        ss = SourceManager.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "SourceActivity destory");
        super.onDestroy();
    }

    private void initViews() {
        mListView = findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);
        mListView.setAdapter(adapter);

        textView = findViewById(R.id.result);
    }

    public void initClick(View view) {
        ss.init(this);
    }

    public void destoryClick(View view) {
        ss.destory();
    }

    public void start(View view) {
        // must invoke after onComplete.
        int port = 0;
        try {
            port = ss.start(this);
            textView.setText("server listening at " + port);
        } catch (ScreenShareException e) {
            e.printStackTrace();
        }
    }

    public void stop(View view) {
        try {
            ss.stop();
        } catch (ScreenShareException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeviceAdded(DeviceInfo device) {
        adapter.add(device);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeviceRemoved(DeviceInfo device) {
        adapter.remove(device);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onInit() {

    }
}
