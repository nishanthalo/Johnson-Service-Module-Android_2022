package com.triton.johnson_tap_app.Service_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.UserTypeSelectListener1;
import com.triton.johnson_tap_app.responsepojo.BD_DetailsResponse;
import com.triton.johnson_tap_app.responsepojo.Preventive_ChecklistResponse;

import java.util.List;


public class Preventive_ChecklistAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PetBreedTypesListAdapter";
    private Context context;
    Preventive_ChecklistResponse.DataBean currentItem;
    private List<Preventive_ChecklistResponse.DataBean> breedTypedataBeanList;
    private UserTypeSelectListener1 userTypeSelectListener;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    String data = "";

    public Preventive_ChecklistAdapter(Context context, List<Preventive_ChecklistResponse.DataBean> breedTypedataBeanList, UserTypeSelectListener1 userTypeSelectListener ) {
        this.context = context;
        this.breedTypedataBeanList = breedTypedataBeanList;
        this.userTypeSelectListener = userTypeSelectListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bd_card, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint("LogNotTimber")
    private void initLayoutOne(ViewHolderOne holder, final int position) {
        currentItem = breedTypedataBeanList.get(position);

//        Intent intent = new Intent("message_subject_intent");
//        intent.putExtra("cust_name" , currentItem.getCUSNAME());
//        intent.putExtra("cont_no" , currentItem.getCONTNO());
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        if(currentItem.getCheck_list_value() != null){
            holder.text_title.setText(currentItem.getCheck_list_value());
            holder.chkSelected.setChecked(breedTypedataBeanList.get(position).isSelected());
            holder.chkSelected.setTag(new Integer(position));
        }

//        if(position == 0 && breedTypedataBeanList.get(0).isSelected() && holder.chkSelected.isChecked())
//        {
//            lastChecked = holder.chkSelected;
//            lastCheckedPos = 0;
//        }

        holder.chkSelected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String data = "";
                breedTypedataBeanList.get(position).setSelected(true);
                data = data + "\n" + breedTypedataBeanList.get(position).getCheck_list_value();
                Log.d("ssss", data);
            }
        });

     //  holder.chkSelected.setChecked(currentItem.isSelected());

    }
    @Override
    public int getItemCount() {
        return breedTypedataBeanList.size();
    }

    public void filterList(List<Preventive_ChecklistResponse.DataBean> breedTypedataBeanListFiltered) {
        breedTypedataBeanList = breedTypedataBeanListFiltered;
        Log.w(TAG,"breedTypedataBeanList : "+new Gson().toJson(breedTypedataBeanList));

        notifyDataSetChanged();
    }




    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView text_title,last_used_time,upload_count,pending_count,failure_count,paused_count,jobs_count;
        public LinearLayout ll_root;
        public CheckBox chkSelected;

        public ViewHolderOne(View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.tvName);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
        }

    }
}
