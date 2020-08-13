package com.example.staffmanagement.Model.Repository.User;

import com.example.staffmanagement.Model.LocalDb.BUS.UserBUS;
import com.example.staffmanagement.Model.LocalDb.Database.Entity.User;
import com.example.staffmanagement.Model.Repository.Base.ApiResponse;
import com.example.staffmanagement.Model.Repository.Base.NetworkBoundResource;

import java.util.List;

public class UserRepository {
    private UserService service;
    private UserBUS bus;

    public UserRepository(){
        service = new UserService();
        bus = new UserBUS();
    }

    public List<User> getAll(){
        return new NetworkBoundResource<List<User>, List<User>>() {
            @Override
            protected List<User> loadFromDb() {
                return bus.getAll();
            }

            @Override
            protected boolean shouldFetchData(List<User> data) {
                return data.isEmpty();
            }

            @Override
            protected void createCall(ApiResponse apiResponse) {
                service.getAll(apiResponse);
            }

            @Override
            protected void saveCallResult(List<User> data) {
                bus.insertRange(data);
            }

            @Override
            protected void onFetchFail() {

            }

            @Override
            protected void onFetchSuccess(List<User> data) {

            }
        }.run();
    }
}
