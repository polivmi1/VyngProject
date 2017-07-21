package com.example.polivmi1.vyngproject;


public interface BaseContract {
    interface View {
    }

    interface Presenter<V> {
        void attachView(V view);

        void detachView();
    }
}
