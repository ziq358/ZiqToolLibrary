package com.example.recycleviewrelatedlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<T> mData;
    private List<BaseViewType> mViewTypes = new ArrayList<>();

    public BaseAdapter(Context context) {
        this(context, null);
    }

    public BaseAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
        initViewType(mViewTypes);
    }

    protected abstract void initViewType(List<BaseViewType> mViewTypes);

    public final Context getContext(){
        return mContext;
    }

    public void setData(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        mData = null;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        for (BaseViewType type : mViewTypes) {
            if(type.isMatchViewType(position)){
                return type.getItemViewType();
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (BaseViewType type : mViewTypes) {
            if(type.getItemViewType() == viewType){
                return type.onCreateViewHolder(parent);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        for (BaseViewType type : mViewTypes) {
            if(type.isMatchViewType(position)){
                type.onBindViewHolder(holder, position);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}