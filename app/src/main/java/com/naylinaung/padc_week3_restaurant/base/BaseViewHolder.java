package com.naylinaung.padc_week3_restaurant.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by aung on 10/19/16.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    protected boolean mDetechedFromWindow;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                mDetechedFromWindow = false;
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                mDetechedFromWindow = true;
            }
        });
    }

    public abstract void bind(T data);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object obj) {

    }

    @Override
    public void onClick(View v) {

    }
}
