package com.example.guidedkeyboardfixerlib;

import androidx.leanback.app.GuidedStepSupportFragment;
import androidx.leanback.widget.GuidedActionsStylist;

public class MyGuidedStepSupportFragment extends GuidedStepSupportFragment {


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
