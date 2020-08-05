package com.example.staffmanagement.Presenter.Admin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.staffmanagement.Model.BUS.RequestBUS;
import com.example.staffmanagement.Model.BUS.StateRequestBUS;
import com.example.staffmanagement.Model.BUS.UserBUS;
import com.example.staffmanagement.Model.Database.Entity.Request;
import com.example.staffmanagement.Model.Database.Entity.StateRequest;
import com.example.staffmanagement.Presenter.Admin.Background.UserRequestActUiHandler;
import com.example.staffmanagement.Presenter.Staff.Background.MyMessage;
import com.example.staffmanagement.View.Admin.UserRequestActivity.UserRequestApdater;
import com.example.staffmanagement.View.Admin.UserRequestActivity.UserRequestInterface;
import com.example.staffmanagement.View.Admin.ViewModel.UserRequestViewModel;
import com.example.staffmanagement.View.Data.AdminRequestFilter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class UserRequestPresenter {
    private Context mContext;
    private UserRequestInterface mInterface;
    private UserRequestActUiHandler mHandler;
    private RequestBUS bus;

    public UserRequestPresenter(Context context, UserRequestInterface mInterface) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        this.mContext = weakReference.get();
        this.mInterface = mInterface;
        mHandler = new UserRequestActUiHandler(mInterface);

    }

    public void destroyBus() {
        bus = null;

    }

    public void getFullNameById(final int idUser, final UserRequestApdater.ViewHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserBUS bus = new UserBUS();
                final String name = bus.getFullNameById(mContext, idUser);
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInterface.onSuccessFullNameById(idUser, name, holder);
                    }
                });
                destroyBus();
            }
        }).start();

    }

    public void updateRequest(final Request request) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBUS bus = new RequestBUS();
                bus.updateStateRequest(mContext, request);
            }
        }).start();
    }

    public void getAllStateRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StateRequestBUS bus = new StateRequestBUS();
                final List<StateRequest> list = bus.getAllStateRequest(mContext);
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInterface.onSuccessGetAllStateRequest(list);
                    }
                });

            }
        }).start();
    }

    public void getLimitListRequestForUser(final int idUser, final int offset, final int numRow, final AdminRequestFilter criteria) {
        bus = new RequestBUS();
        bus.getLimitListRequestForUser(mContext, idUser, offset, numRow, criteria);
        bus.getListLiveData().observe((LifecycleOwner) mContext, new Observer<List<Request>>() {
            @Override
            public void onChanged(List<Request> requests) {
                if(requests != null)
                    for(int i=0;i<requests.size();i++)
                    {
                        Log.i("GETDATA",requests.get(i).getTitle());
                    }
                mHandler.sendMessage(MyMessage.getMessage(UserRequestActUiHandler.MSG_ADD_LOAD_MORE_LIST, requests));
            }
        });

    }
}

