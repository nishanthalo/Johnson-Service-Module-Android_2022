package com.triton.johnson_tap_app.Service_Activity.Preventive_Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import java.util.ArrayList;

public class ESCTRV_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> arliMonth;
    Context context;
    ArrayList<String> arliJobDate = new ArrayList<>();
    JobDateListener jobDateListener;
    SharedPreferences sharedPreferences;

    public ESCTRV_adapter(ArrayList<String> arliMonth, Context context, JobDateListener jobDateListener) {
        this.arliMonth = arliMonth;
        this.context = context;
        this.jobDateListener = jobDateListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_card, parent, false);
        return new ESCTRV_adapter.ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ESCTRV_adapter.ViewHolderOne) holder, position);
    }

    private void initLayoutOne(ESCTRV_adapter.ViewHolderOne holder, int position) {

        if (arliMonth != null && arliMonth.size()>0){

            holder.name.setText(arliMonth.get(position));

            holder.chx_usertypes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.chx_usertypes.isChecked()){

                        arliJobDate.add(arliMonth.get(position));
                    } else {
                        arliJobDate.remove(arliMonth.get(position));
                    }
                    jobDateListener.onMonthchange(arliJobDate);

                    Log.e("LIST",""+arliJobDate);

                    ArrayList<String> list = arliJobDate;

                    //  String list = String.join(" ",arliJobDate);

                    Log.e("List 1",""+list);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("List", String.valueOf(list));
                    editor.apply();
                }

            });
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("List", String.valueOf(arliJobDate));
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return arliMonth.size();
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView name,code;
        CardView cv_root;
        LinearLayout ll_usertypes;
        CheckBox chx_usertypes;
        public ViewHolderOne(View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            code = view.findViewById(R.id.codes);
            ll_usertypes = view.findViewById(R.id.ll_usertypes);
            chx_usertypes = view.findViewById(R.id.chkSelected);
        }
    }
}
