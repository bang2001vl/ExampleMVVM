package com.example.mvvm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class SecondFragment_EditMode extends SecondFragment{
    TodoModel temp;
    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSecond.setText("Sửa");
        binding.edtTitle.setEnabled(false);
    }

    @NonNull
    @Override
    public @NotNull Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        isOK = false;

        temp = new TodoModel(
                viewModel.getSelectedTodoModel().name,
                viewModel.getSelectedTodoModel().content,
                viewModel.getSelectedTodoModel().isDone
        );

        return new Dialog(getActivity(), getTheme())
        {
            @Override
            public void onBackPressed() {
                SecondFragment_EditMode.this.dismiss();
            }
        };
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        if(!isOK)
        {
            binding.getBinding().replaceTodo(temp, binding.getBinding().getSelectedTodo_Index());
        }
    }
}
