package com.example.notesapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

abstract public class BaseFragment extends Fragment {
    Toolbar fragmentToolbar;

    @NonNull
    protected INavigator requireNavigator() {
        final Activity activity = requireActivity();

        if (!(activity instanceof INavigator)) {
            throw new IllegalStateException("Activity must implement " + INavigator.class.getCanonicalName());
        }

        return (INavigator) activity;
    }

    @NonNull
    protected IToolbarHolder requireIToolbarHolder() {
        final Activity activity = requireActivity();

        if (!(activity instanceof IToolbarHolder)) {
            throw new IllegalStateException("Activity must implement " + IToolbarHolder.class.getCanonicalName());
        }

        return (IToolbarHolder) activity;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    protected void initToolbar() {
        fragmentToolbar = requireIToolbarHolder().requireToolbar();
        fragmentToolbar.getMenu().clear();
        setupToolbar(fragmentToolbar);
    }

    abstract protected void setupToolbar(Toolbar toolbar);

}