package com.example.staffmanagement.Presenter.Admin;

import android.content.Context;
import android.view.View;

import com.example.staffmanagement.Model.Database.DAL.RequestDbHandler;
import com.example.staffmanagement.Model.Database.Entity.Request;
import com.example.staffmanagement.View.Admin.DetailRequestUser.DetailRequestUserActivity;
import com.example.staffmanagement.View.Ultils.Constant;

import java.security.PublicKey;

public class DetailRequestPresenter {
    private Context mContext;
    private DetailRequestUserActivity mInterface;

    public DetailRequestPresenter(Context mContext, DetailRequestUserActivity mInterface) {
        this.mContext = mContext;
        this.mInterface = mInterface;
    }

    public int getIdStateByName(String stateName){
        RequestDbHandler db =new RequestDbHandler(mContext);
        return db.getIdStateByName(stateName);
    }

    public String getStateNameById(int idState){
        RequestDbHandler db =new RequestDbHandler(mContext);
        return db.getStateNameById(idState);
    }

     public void update(Request request){
        RequestDbHandler db = new RequestDbHandler(mContext);
        db.update(request);
     }
}