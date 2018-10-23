package com.example.guidedkeyboardfixerlib;

import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidedActionsStylist;

public class MyGuidedStepFragment extends GuidedStepFragment {

    @Override
    public GuidedActionsStylist onCreateActionsStylist() {
        return new GuidedActionsStylist(){
            @Override
            public int onProvideItemLayoutId() {
                return R.layout.guidedactions_item;
//                return super.onProvideItemLayoutId(viewType);
            }
        };
//        return super.onCreateActionsStylist();
    }

    @Override
    public GuidedActionsStylist onCreateButtonActionsStylist() {
        GuidedActionsStylist stylist =  new GuidedActionsStylist(){
            @Override
            public int onProvideItemLayoutId() {
                return R.layout.guidedactions_item;
//                return super.onProvideItemLayoutId(viewType);
            }
        };
        stylist.setAsButtonActions();
        return stylist;
//        return super.onCreateButtonActionsStylist();
    }

}