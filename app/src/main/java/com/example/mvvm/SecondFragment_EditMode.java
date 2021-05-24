package com.example.mvvm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class SecondFragment_EditMode extends SecondFragment{
    TodoModel temp;
    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSecond.setText("Sửa");
        binding.edtTitle.setEnabled(false);
        temp = new TodoModel(
                binding.getBinding().getSelectedTodoModel().name,
                binding.getBinding().getSelectedTodoModel().content,
                binding.getBinding().getSelectedTodoModel().isDone
                );
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        if(!isOK)
        {
            binding.getBinding().replaceTodo(temp, binding.getBinding().getSelectedTodo_Index());
        }
    }
}
