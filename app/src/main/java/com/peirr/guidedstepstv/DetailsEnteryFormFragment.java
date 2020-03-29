package com.peirr.guidedstepstv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.leanback.app.GuidedStepFragment;
import androidx.leanback.app.GuidedStepSupportFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;
import androidx.leanback.widget.GuidedActionAdapter;
import androidx.leanback.widget.GuidedActionsStylist;
import androidx.leanback.widget.GuidedDatePickerAction;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.example.guidedkeyboardfixerlib.MyGuidedStepSupportFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kurt on 2016/02/29.
 */
public class DetailsEnteryFormFragment extends MyGuidedStepSupportFragment {

    private static final String TAG = "DetailsEntFormFragment";

    private final int ACTION_NAME = 0;
    private final int ACTION_DOB = 1;
    private final int ACTION_GENDER = 2;
    private final int ACTION_GENDER_MALE = 20;
    private final int ACTION_GENDER_FEMALE = 21;



    private final int ACTION_CONTINUE = 3;


    static Details entry = new Details();



    private static void addAction(List<GuidedAction> actions, long id, String title, String desc){
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build());
    }

    private static void addEditableAction(List<GuidedAction> actions,long id, String title, String desc,int inputType,Context context){
        actions.add(new GuidedAction.Builder(context)
                .id(id)
                .title(title)
                .descriptionEditable(true)
                .descriptionInputType(inputType)
                .description(desc)
                .build());
    }

    private static void addDropDownAction(List<GuidedAction> actions,long id, String title, String desc,List<GuidedAction> selectionActions){
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .subActions(selectionActions)
                .build());
    }

    private static void addCheckedAction(List<GuidedAction> actions, int id ,String title, String desc, boolean checked) {
        GuidedAction guidedAction = new GuidedAction.Builder()
                .title(title)
                .description(desc)
                .checkSetId(id)
                .build();
        guidedAction.setChecked(checked);
        actions.add(guidedAction);
    }

    private static void addDateAction(Context context,List<GuidedAction> actions,long id, String title, long date){
        actions.add(new GuidedDatePickerAction.Builder(context)
                .id(id)
                .date(date)
                .datePickerFormat("DMY")
                .maxDate(new Date().getTime())
                .title(title)
                .build());
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v  =  super.onCreateView(inflater, container, savedInstanceState);
//        try {
//            Field mAdap = super.getClass().getField("mAdapter");
//            mAdap.setAccessible(true);
//            GuidedActionAdapter adapter = (GuidedActionAdapter) mAdap.get(this);
//            Field actionEditor = adapter.getClass().getField("mActionEditListener");
//            Object o = actionEditor.get(adapter);
//            o
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return v;
//    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = getString(R.string.two_opts_title);
        String breadcrumb = getString(R.string.two_opts_breadcrumb);
        String description = getString(R.string.two_opts_desc);
        Drawable icon = getActivity().getDrawable(R.drawable.ic_action_account);
        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        addEditableAction(actions,ACTION_NAME,getString(R.string.two_opts_name),entry.getName(),InputType.TYPE_CLASS_TEXT,getActivity());
        addDateAction(getActivity(),actions,ACTION_DOB,getString(R.string.two_opts_dob),entry.getDob());

        List<GuidedAction> genderActions = new ArrayList<>();
        addAction(genderActions,ACTION_GENDER_MALE,getString(R.string.two_opts_gender_male),"");
        addAction(genderActions,ACTION_GENDER_FEMALE,getString(R.string.two_opts_gender_female),"");

        addDropDownAction(actions, ACTION_GENDER,getString(R.string.two_opts_gender),entry.isFemale()?"Female":"Male",genderActions);
    }

    @Override
    public void onCreateButtonActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        super.onCreateButtonActions(actions,savedInstanceState);
        addAction(actions,ACTION_CONTINUE,getString(R.string.two_opts_submit),"");
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        super.onGuidedActionClicked(action);
        if (action.getId() == ACTION_CONTINUE) {
            Log.d(TAG, "onGuidedActionClicked() [ACTION_CONTINUE]");
            Toast.makeText(getActivity(),entry.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public long onGuidedActionEditedAndProceed(GuidedAction action) {
        if (action.getId() == ACTION_NAME) {
            Log.d(TAG, "onGuidedActionClicked() [ACTION_NAME] : " + action.getDescription());
            entry.setName(action.getDescription().toString());

        }else if(action.getId() ==  ACTION_DOB) {
            GuidedDatePickerAction daction = (GuidedDatePickerAction) action;
            Log.d(TAG, "onGuidedActionClicked() [ACTION_DOB] : " + new Date(daction.getDate()));
            entry.setDob(daction.getDate());
        }
        return super.onGuidedActionEditedAndProceed(action);
    }

    @Override
    public boolean onSubGuidedActionClicked(GuidedAction action) {
        if(action.getId() == ACTION_GENDER_MALE){
            Log.d(TAG, "onGuidedActionClicked() [ACTION_GENDER_MALE]");
            entry.setFemale(false);
        }else if(action.getId() == ACTION_GENDER_FEMALE){
            Log.d(TAG, "onGuidedActionClicked() [ACTION_GENDER_FEMALE]");
            entry.setFemale(true);
        }
        return super.onSubGuidedActionClicked(action);
    }




}
