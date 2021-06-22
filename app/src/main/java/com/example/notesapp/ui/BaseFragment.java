package com.example.notesapp.ui;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

abstract public class BaseFragment extends Fragment {

    @NonNull
    protected INavigator requireNavigator() {
        final Activity activity = requireActivity();

        if (!(activity instanceof INavigator)) {
            throw new IllegalStateException("Activity must implement " + INavigator.class.getCanonicalName());
        }

        return (INavigator) activity;
    }

}