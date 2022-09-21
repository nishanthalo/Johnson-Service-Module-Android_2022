package com.triton.johnson_tap_app.Service_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.New_Screen.Job_List_New_ScreenActivity;
import com.triton.johnson_tap_app.New_Screen.Services_List_New_ScreenActivity;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.responsepojo.Agent_new_screenResponse;
import com.triton.johnson_tap_app.responsepojo.Feedback_GroupResponse;
import com.triton.johnson_tap_app.responsepojo.Service_list_new_screenResponse;

import java.util.List;


public class Services_List_new_SecrrenAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ActivityBasedListAdapter";
    private List<Service_list_new_screenResponse.DataBean> dataBeanList;
    private Context context;
    Service_list_new_screenResponse.DataBean currentItem;
    private int size;
    String message;
    TextView  txt_last_login,txt_last_job,txt_pending_today,txt_pending_total,txt_completed_today,txt_completed_monthly;
 //   private UserTypeSelectListener1 userTypeSelectListener;

    public Services_List_new_SecrrenAdapter(Context context, List<Service_list_new_screenResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
      //  this.userTypeSelectListener = userTypeSelectListener;

    }

    public void filterList(List<Service_list_new_screenResponse.DataBean> filterllist)
    {
        dataBeanList = filterllist;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item1, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        if(currentItem.getService_name() != null){
            holder.time.setText(currentItem.getService_name());
        //    holder.code.setText(currentItem.getCodes());
        }


        holder.time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });

        holder.img_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String s = dataBeanList.get(position).getService_name();

                Intent n_act = new Intent(context, Job_List_New_ScreenActivity.class);
                n_act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                n_act.putExtra("service_title", s);
                context.startActivity(n_act);
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
        TextView time;
        ImageView img_icon;

        public ViewHolderOne(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.text1);
            img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
        }
    }
}
