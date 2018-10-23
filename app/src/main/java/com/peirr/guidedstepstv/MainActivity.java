package com.peirr.guidedstepstv;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.app.GuidedStepSupportFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            GuidedStepFragment.addAsRoot(this, new DetailsEnteryFormFragment(), android.R.id.content);
        }
    }


}
