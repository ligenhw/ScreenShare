package org.gen.screenshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.gen.screensharesdk.DeviceInfo;
import org.gen.screensharesdk.ScreenShare;

public class SourceActivity extends Activity implements
        ScreenShare.Callback, ScreenShare.DeviceListListener {

    private static final String TAG = "SourceActivity";
    private ScreenShare ss;
    private ListView mListView;
    private ArrayAdapter<DeviceInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);
        initViews();
        ss = ScreenShare.getInstance();
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
    }

    @Override
    public void onComplete() {
    }

    public void initClick(View view) {
        ss.init(this, this);
    }

    public void destoryClick(View view) {
        ss.destory();
    }

    public void start(View view) {
        // must invoke after onComplete.
        ss.start(this);
    }

    public void stop(View view) {
        ss.stop();
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
}
