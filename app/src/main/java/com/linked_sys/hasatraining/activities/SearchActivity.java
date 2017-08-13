package com.linked_sys.hasatraining.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.linked_sys.hasatraining.R;
import com.linked_sys.hasatraining.adapters.SearchAdapter;
import com.linked_sys.hasatraining.core.CacheHelper;
import com.linked_sys.hasatraining.models.SearchResult;
import com.linked_sys.hasatraining.network.ApiCallback;
import com.linked_sys.hasatraining.network.ApiEndPoints;
import com.linked_sys.hasatraining.network.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    LinearLayout placeholder,searchDataContainer;
    public SearchAdapter mAdapter;
    public ArrayList<SearchResult> programs = new ArrayList<>();
    TextView txtPeriod,txtTeacherName, txtTeacherID, txtTeacherMobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        final ImageView btnSearch = (ImageView) findViewById(R.id.btnSearch);
        LinearLayout dataContainer = (LinearLayout) findViewById(R.id.searchResultContainer);
        final EditText txtSearch = (EditText) findViewById(R.id.txtSearch);
        placeholder = (LinearLayout) findViewById(R.id.no_data_placeholder);
        searchDataContainer = (LinearLayout) findViewById(R.id.searchDataContainer);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txtPeriod = (TextView) findViewById(R.id.txtPeriod);
        txtTeacherName = (TextView) findViewById(R.id.txtTeacherName);
        txtTeacherID = (TextView) findViewById(R.id.txtTeacherID);
        txtTeacherMobile = (TextView) findViewById(R.id.txtTeacherMobile);
        mAdapter = new SearchAdapter(this, programs);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        setupUI(dataContainer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            txtPeriod.setText(bundle.getString("period"));
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchResult(txtSearch.getEditableText().toString());
            }
        });
        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (txtSearch.getEditableText().toString().equals("")) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        hideSoftKeyboard(SearchActivity.this);
                        handled = true;
                    }
                    return handled;
                } else {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        btnSearch.performClick();
                        hideSoftKeyboard(SearchActivity.this);
                        handled = true;
                    }
                    return handled;
                }
            }
        });
    }

    private void getSearchResult(String teacherID) {
        final String url = ApiEndPoints.SEARCH_TEACHERS
                + "?APPCode=" + CacheHelper.getInstance().appCode
                + "&SchoolId=" + session.getUserDetails().get(session.KEY_ID)
                + "&TeacherId=" + teacherID
                + "&PeriodId=" + CacheHelper.getInstance().adminPeriodSelectedID;

        ApiHelper api = new ApiHelper(this, url, Request.Method.GET, new ApiCallback() {
            @Override
            public void onSuccess(Object response) {
                programs.clear();
                try {
                    JSONObject res = (JSONObject) response;
                    JSONArray programsArray = res.optJSONArray("con");
                    if (programsArray.length() > 0) {
                        searchDataContainer.setVisibility(View.VISIBLE);
                        placeholder.setVisibility(View.GONE);
                        for (int i = 0; i < programsArray.length(); i++) {
                            JSONObject programObj = programsArray.optJSONObject(i);
                            SearchResult program = new SearchResult();
                            program.setProgramName(programObj.optString("ProgramName"));
                            program.setProgramTime(programObj.optString("ProgramTime"));
                            program.setProgramDate(programObj.optString("ProgramDate"));
                            program.setProgramStatus(programObj.optString("ProgramStatus"));
                            program.setRegistrationDate(programObj.optString("RegistrationDate"));
                            program.setTeacherName(programObj.optString("MotadarebFullName"));
                            program.setTeacherID(programObj.optString("MotadarebID"));
                            program.setTeacherMobile(programObj.optString("MotadarebMobile"));
                            programs.add(program);
                        }
                        txtTeacherName.setText(programs.get(0).getTeacherName());
                        txtTeacherID.setText(programs.get(0).getTeacherID());
                        txtTeacherMobile.setText(programs.get(0).getTeacherMobile());
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        searchDataContainer.setVisibility(View.GONE);
                        placeholder.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
        api.executeRequest(true,false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.search_activity;
    }
}
