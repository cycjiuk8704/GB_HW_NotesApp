package com.example.notesapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

abstract public class BaseFragment extends Fragment {

    abstract public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState,
            @NonNull Toolbar toolbar
    );

    @Nullable
    @Override
    final public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        Toolbar toolbar = requireIToolbarHolder().requireToolbar();
        toolbar.getMenu().clear();

        return onCreateView(inflater, container, savedInstanceState, toolbar);
    }

    @NonNull
    protected INavigator requireNavigator() {
        final Activity activity = requireActivity();

        if (!(activity instanceof INavigator)) {
            throw new IllegalStateException("Activity must implement " + INavigator.class.getCanonicalName());
        }

        return (INavigator) activity;
    }

    @NonNull
    private IToolbarHolder requireIToolbarHolder() {
        final Activity activity = requireActivity();

        if (!(activity instanceof IToolbarHolder)) {
            throw new IllegalStateException("Activity must implement " + IToolbarHolder.class.getCanonicalName());
        }

        return (IToolbarHolder) activity;
    }

}