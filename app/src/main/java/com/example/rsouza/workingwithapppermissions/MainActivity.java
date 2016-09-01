package com.example.rsouza.workingwithapppermissions;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        List<String> listPermission = new ArrayList<String>();

        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo;

        try {
            List<ApplicationInfo> listApplicationInfo = packageManager.getInstalledApplications(0);
            int idxApplication = 0;
            for (ApplicationInfo applicationInfo : listApplicationInfo){
                listDataHeader.add(applicationInfo.packageName);
                packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

                //Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;
//                List<String>listPermission = new ArrayList<String>();
                listPermission.clear();
                if (requestedPermissions != null){
                    for (String sPermission : requestedPermissions){
                        listPermission.add(sPermission);
                    }
                    listDataChild.put(listDataHeader.get(idxApplication), listPermission);
                }

                /*for (PermissionInfo permission : packageInfo.permissions) {

                }*/

                idxApplication++;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
