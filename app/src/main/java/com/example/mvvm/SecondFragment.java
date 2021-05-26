package com.example.mvvm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mvvm.databinding.FragmentSecondBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SecondFragment extends DialogFragment {

    protected FragmentSecondBinding binding;
    protected ToDoViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        if(viewModel != null) {
            binding.setBinding(viewModel);
        }
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TodoModel model = new TodoModel(
                        binding.getBinding().getSelectedTodoModel().name,
                        binding.getBinding().getSelectedTodoModel().content,
                        binding.getBinding().getSelectedTodoModel().isDone
                );

                Log.d("MY_TAG", model.toString());

                StorageManager.createTodo(getContext(), model);

                isOK = true;
                SecondFragment.this.dismiss();
            }
        });

    }

    public void setModel(TodoModel todoModel)
    {
        binding.edtTitle.setText(todoModel.name);
        binding.edtContent.setText(todoModel.content);
        binding.checkboxIsDone.setSelected(todoModel.isDone);
    }

    public void setButtonText(String str)
    {
        binding.buttonSecond.setText(str);
    }

    public boolean isOK = false;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        isOK = false;

        viewModel.addTodo(new TodoModel("","", false));
        viewModel.setSelectedTodo_Index(viewModel.getTodoList().size() - 1);

        return new Dialog(getActivity(), getTheme())
        {
            @Override
            public void onBackPressed() {
                SecondFragment.this.dismiss();
            }
        };
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(!isOK) {
            binding.getBinding().removeTodo(binding.getBinding().getSelectedTodoModel());
            binding.getBinding().setSelectedTodo_Index(0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}