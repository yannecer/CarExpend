package com.necer.carexpend.ui.activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.necer.carexpend.R;
import com.necer.carexpend.base.BaseActivity;
import butterknife.Bind;

/**
 * Created by necer on 2017/3/10.
 */

public class AboutActivity extends BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void setData(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
