package com.example.staffmanagement.View.Staff.RequestManagement.RequestActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffmanagement.Model.Database.DAL.RequestDbHandler;
import com.example.staffmanagement.Model.Database.Entity.Request;

import com.example.staffmanagement.Model.Database.Entity.User;
import com.example.staffmanagement.Presenter.Staff.StaffRequestPresenter;
import com.example.staffmanagement.View.Ultils.Constant;
import com.example.staffmanagement.View.Staff.RequestManagement.RequestCrudActivity.StaffRequestCrudActivity;

import com.example.staffmanagement.R;
import com.example.staffmanagement.View.Ultils.GeneralFunc;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class StaffRequestListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Request> items;
    private StaffRequestPresenter mPresenter;
    private final int ITEM_VIEW_TYPE = 1;
    private final int LOADING_VIEW_TYPE = 2;

    public StaffRequestListAdapter(Context mContext, List<Request> items, StaffRequestPresenter mPresenter) {
        this.mContext = mContext;
        this.items = items;
        this.mPresenter = mPresenter;
        WeakReference<Context> weak = new WeakReference<>(this.mContext);
    }

    public void updateRequest(Request request){
        for(int i=0;i<items.size();i++){
            if(request.getId() == items.get(i).getId()){
               items.set(i,request);
               notifyDataSetChanged();
               return;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? LOADING_VIEW_TYPE : ITEM_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == ITEM_VIEW_TYPE) {
            v = ((Activity) mContext).getLayoutInflater().inflate(R.layout.item_user_request_for_staff, parent, false);
            return new ViewHolder(v);
        } else {
            v = ((Activity) mContext).getLayoutInflater().inflate(R.layout.view_load_more, parent, false);
            return new LoadingViewHolder(v);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof LoadingViewHolder) {
            return;
        }

        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setTxtTitle(items.get(position).getTitle());

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String stateName = mPresenter.getStateNameById(items.get(position).getIdState());
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.setTxtState(stateName);
                    }
                });
            }
        }).start();


        switch (items.get(position).getIdState()) {
            case 1:
                viewHolder.getTxtState().setTextColor(mContext.getResources().getColor(R.color.colorWaiting));
                viewHolder.getLila().setBackgroundColor(mContext.getResources().getColor(R.color.colorWaiting));
                break;
            case 2:
                viewHolder.getTxtState().setTextColor(mContext.getResources().getColor(R.color.colorAccept));
                viewHolder.getLila().setBackgroundColor(mContext.getResources().getColor(R.color.colorAccept));
                break;
            case 3:
                viewHolder.getTxtState().setTextColor(mContext.getResources().getColor(R.color.colorDecline));
                viewHolder.getLila().setBackgroundColor(mContext.getResources().getColor(R.color.colorDecline));
                break;
        }
        viewHolder.setTxtDateTime(GeneralFunc.convertMilliSecToDateString(items.get(position).getDateTime()));

        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StaffRequestCrudActivity.class);
                intent.setAction(StaffRequestActivity.ACTION_EDIT_REQUEST);
                intent.putExtra(Constant.REQUEST_DATA_INTENT,items.get(position));
                ((Activity) mContext).startActivityForResult(intent, StaffRequestActivity.getRequestCodeEdit());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView txtTitle, txtDateTime, txtState;
        private LinearLayout lila;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtTitle = view.findViewById(R.id.textView_item_title_request_non_admin);
            txtState = view.findViewById(R.id.textView_item_state_request_non_admin);
            txtDateTime = view.findViewById(R.id.textView_item_dateTime_request_non_admin);
            lila = view.findViewById(R.id.linearLayout_color_vertical);
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public void setTxtTitle(String title) {
            this.txtTitle.setText(title);
        }

        public void setTxtDateTime(String dateTime) {
            this.txtDateTime.setText(dateTime);
        }

        public TextView getTxtState() {
            return txtState;
        }

        public void setTxtState(String state) {
            this.txtState.setText(state);
        }

        public LinearLayout getLila() {
            return lila;
        }

    }
}
