package com.triton.johnson_tap_app.Service_Activity.SiteAudit;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.GetFieldListResponse;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.responsepojo.Auditcheckresponse;
import com.triton.johnson_tap_app.responsepojo.Preventive_ChecklistResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LiftAuditAdapter_SiteAudit extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String status,service_title;
    List<GetFieldListResponse.DataBean> breedTypedataBeanList;
    SharedPreferences sharedPreferences;
    GetFieldListResponse.DataBean currentItem;
    private List<?> arrayListdropdown;
    int ITEMS_PER_PAGE;
    int TOTAL_NUM_ITEMS;
    int currentPage;
    int check = 0;


    public LiftAuditAdapter_SiteAudit(Context context, List<GetFieldListResponse.DataBean> dataBeanList, String status, int ITEMS_PER_PAGE, int TOTAL_NUM_ITEMS) {

        this.context = context;
        this.breedTypedataBeanList = dataBeanList;
        this.status = status;
        this.ITEMS_PER_PAGE = ITEMS_PER_PAGE;
        this.TOTAL_NUM_ITEMS = TOTAL_NUM_ITEMS;

        Log.e("Status", "" + status);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        service_title = sharedPreferences.getString("service_title", "Services");

        Log.e("Name" , " " +service_title);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_liftaudit, parent, false);
        return new LiftAuditAdapter_SiteAudit.ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        initLayoutone((ViewHolderOne) holder , position);
    }

    private void initLayoutone(ViewHolderOne holder, int position) {

        currentItem = breedTypedataBeanList.get(position);
        int startItem = currentPage * ITEMS_PER_PAGE + position;

        holder.txt_Comments.setText(currentItem.getField_comments());

        if (currentItem.getField_type().equals("Dropdown")) {

           // holder.edt_number.setVisibility(View.GONE);
            holder.edt_String.setVisibility(View.GONE);
            holder.ll_dropdown.setVisibility(View.VISIBLE);

            arrayListdropdown = currentItem.getDrop_down();
            Log.w(TAG, "currentItem.getDrop_down() : " + arrayListdropdown);

            ArrayList<String> arrayList = new ArrayList<>();

            arrayList.add("Select");

            for (int i = 0; i < currentItem.getDrop_down().size(); i++) {
                String string = currentItem.getDrop_down().get(i).toString();
                Log.w(TAG, "spr string-->" + string);
                arrayList.add(string);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spr_dropdown.setAdapter(adapter);
            holder.spr_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if (++check > 1) {

                        Log.w(TAG, "currentItem POS " + startItem);

                        Log.w(TAG, "currentItem POS " + parent.getItemAtPosition(pos));

                       // getSpinnerListener.getSpinnerListener(holder.spr_dropdown, startItem, parent.getItemAtPosition(pos).toString(), currentItem.getField_length());
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return Math.min(breedTypedataBeanList.size(), ITEMS_PER_PAGE);
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView txt_Comments;
        EditText edt_Comments,edt_String;
        LinearLayout ll_dropdown;
        Spinner spr_dropdown;

        public ViewHolderOne(View view) {

            super(view);

            txt_Comments = view.findViewById(R.id.txt_comments);
            edt_Comments = view.findViewById(R.id.edt_comments);
            edt_String = view.findViewById(R.id.edt_string);
            ll_dropdown = view.findViewById(R.id.ll_dropdown);
            spr_dropdown = view.findViewById(R.id.spr_dropdown);
        }
    }
}
