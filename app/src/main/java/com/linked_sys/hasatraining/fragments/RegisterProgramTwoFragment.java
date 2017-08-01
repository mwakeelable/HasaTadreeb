package com.linked_sys.hasatraining.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.activities.RegisterProgramActivity;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONObject;

public class RegisterProgramTwoFragment extends Fragment {
    RegisterProgramActivity activity;
    TextView txtMobile, txtNID, txtName, txtCat, txtSpe, txtEmail, txtTitle, txtExtra, txtLevel,
            txtSector, txtWorkEmail, txtWezaraNumber, txtSchool, txtAdminMobile, txtAdminName, txtManagerMobile, txtManagerName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (RegisterProgramActivity) getActivity();
        return inflater.inflate(R.layout.register_program_two_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txtMobile = (TextView) view.findViewById(R.id.txtUserMobile);
        txtNID = (TextView) view.findViewById(R.id.txtUserNationalID);
        txtName = (TextView) view.findViewById(R.id.txtUserFullName);
        txtCat = (TextView) view.findViewById(R.id.txtUserCategory);
        txtSpe = (TextView) view.findViewById(R.id.txtUserSpecialize);
        txtEmail = (TextView) view.findViewById(R.id.txtUserEmail);
        txtTitle = (TextView) view.findViewById(R.id.txtUserTitle);
        txtExtra = (TextView) view.findViewById(R.id.txtUserExtra);
        txtLevel = (TextView) view.findViewById(R.id.txtUserStudyLevel);
        txtSector = (TextView) view.findViewById(R.id.txtUserSector);
        txtWorkEmail = (TextView) view.findViewById(R.id.txtWorkEmail);
        txtWezaraNumber = (TextView) view.findViewById(R.id.txtUserWezaryNumber);
        txtSchool = (TextView) view.findViewById(R.id.txtUserSchool);
        txtAdminMobile = (TextView) view.findViewById(R.id.txtAdminMobile);
        txtAdminName = (TextView) view.findViewById(R.id.txtAdminName);
        txtManagerMobile = (TextView) view.findViewById(R.id.txtManagerPhone);
        txtManagerName = (TextView) view.findViewById(R.id.txtManagerName);
        getUserData();
    }

    private void getUserData() {
        String url = ApiEndPoints.GET_USER_DATA + "?APPCode=" + CacheHelper.getInstance().appCode + "&UserRefId=" + activity.session.getUserDetails().get(activity.session.KEY_ID_REF);
        ApiHelper api = new ApiHelper(activity, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                JSONObject res = (JSONObject) response;
                JSONObject userObj = res.optJSONObject("con");
                txtMobile.setText(userObj.optString("Mobile"));
                txtNID.setText(userObj.optString("ID"));
                txtName.setText(userObj.optString("FullName"));
                txtCat.setText(userObj.optString("Worktype"));
                txtSpe.setText(userObj.optString("Specialized"));
                txtEmail.setText(userObj.optString("Email"));
                txtTitle.setText(userObj.optString("Current_work"));
                txtExtra.setText("");
                txtLevel.setText(userObj.optString("School_Level"));
                txtSector.setText(userObj.optString("School_Sector"));
                txtWorkEmail.setText(userObj.optString("School_Email_work"));
                txtWezaraNumber.setText(userObj.optString("School_ID"));
                txtSchool.setText(userObj.optString("School"));
                txtAdminMobile.setText(userObj.optString("Mobile_Monasaq"));
                txtAdminName.setText(userObj.optString("Name_Monasaq"));
                txtManagerMobile.setText(userObj.optString("Mobile_Manger"));
                txtManagerName.setText(userObj.optString("Name_Manger"));
                activity.userID = userObj.optString("ID");
                activity.userMobile = userObj.optString("Mobile");
                activity.userName = userObj.optString("FullName");
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true, false);
    }
}
