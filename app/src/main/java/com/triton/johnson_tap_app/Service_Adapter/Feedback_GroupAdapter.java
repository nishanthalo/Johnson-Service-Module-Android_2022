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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.responsepojo.Feedback_GroupResponse;

import java.util.List;


public class Feedback_GroupAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ActivityBasedListAdapter";
    private List<Feedback_GroupResponse.DataBean> dataBeanList;
    private Context context;
    Feedback_GroupResponse.DataBean currentItem;
    private int size;
 //   private UserTypeSelectListener1 userTypeSelectListener;

    public Feedback_GroupAdapter(Context context, List<Feedback_GroupResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
      //  this.userTypeSelectListener = userTypeSelectListener;

    }

    public Feedback_GroupAdapter(Context applicationContext, List<Feedback_GroupResponse.DataBean> dataBeanList, String s) {
    }

    public void filterList(List<Feedback_GroupResponse.DataBean> filterllist)
    {
        dataBeanList = filterllist;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_card, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        if(currentItem.getTitle() != null){
            holder.name.setText(currentItem.getTitle());
            holder.code.setText(currentItem.getCodes());
        }


//        if(currentItem.getTitle().contains("LEVEL PROBLEM")){
//            holder.chx_usertypes.setChecked(true);
//        }
//        else {
//            holder.chx_usertypes.setChecked(false);
//        }

        holder.chx_usertypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                dataBeanList.get(position).setSelected(true);
                data = data + "\n" + dataBeanList.get(position).getCodes();
                Log.d("ssss", data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView name,code;
        CardView cv_root;
        LinearLayout ll_usertypes;
        CheckBox chx_usertypes;

        public ViewHolderOne(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            code = itemView.findViewById(R.id.codes);
            ll_usertypes = itemView.findViewById(R.id.ll_usertypes);
            chx_usertypes = itemView.findViewById(R.id.chkSelected);
        }
    }
}
