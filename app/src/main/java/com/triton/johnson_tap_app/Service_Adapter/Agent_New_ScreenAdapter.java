package com.triton.johnson_tap_app.Service_Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.New_Screen.Agent_Pop_New_ScreenActivity;
import com.triton.johnson_tap_app.New_Screen.Services_List_New_ScreenActivity;
import com.triton.johnson_tap_app.R;
import com.triton.johnson_tap_app.responsepojo.Agent_new_screenResponse;

import java.util.List;


public class Agent_New_ScreenAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "ActivityBasedListAdapter";
    private List<Agent_new_screenResponse.DataBean> dataBeanList;
    private Context context;
    Agent_new_screenResponse.DataBean currentItem;
    private int size;
    String message;
    TextView  txt_last_login,txt_last_job,txt_pending_today,txt_pending_total,txt_completed_today,txt_completed_monthly;
 //   private UserTypeSelectListener1 userTypeSelectListener;
  AlertDialog dialog;

    public Agent_New_ScreenAdapter(Context context, List<Agent_new_screenResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
      //  this.userTypeSelectListener = userTypeSelectListener;

    }

    public void filterList(List<Agent_new_screenResponse.DataBean> filterllist)
    {
        dataBeanList = filterllist;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        if(currentItem.getUser_name() != null){
            holder.time.setText(currentItem.getUser_name());
        //    holder.code.setText(currentItem.getCodes());
        }

        if(currentItem.getUser_type().equals("USER")){
            holder.img_red.setImageResource(R.drawable.ic_person_bule);
        }
        else {
            holder.img_red.setImageResource(R.drawable.ic_person_red);
        }


        holder.time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent n_act = new Intent(context, Agent_Pop_New_ScreenActivity.class);
                n_act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(n_act);
            }
        });

        holder.img_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dataBeanList.get(position).getUser_name();
                String s = dataBeanList.get(position).getUser_name();

                Intent n_act = new Intent(context, Services_List_New_ScreenActivity.class);
                n_act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                n_act.putExtra("title", s);
                context.startActivity(n_act);
            }
        });
    }

    private Object getSystemService(String windowService) {

        return 0;
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
        ImageView img_icon,img_red;

        public ViewHolderOne(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.text1);
            img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
            img_red = (ImageView) itemView.findViewById(R.id.img_red);
        }
    }
}
