package com.example.flearning;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {

    /*Фрагменты не могут напрямую взаимодействовать между собой
    * Для этого им нужен контекст, которым выступает класс Activity
    *
    * Для обращения к Activity создаём вложенный интерфейс*/
    interface OnFragmentSendDataListener {
        void onSendData(String data);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;
    String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            /*Прикрепление текущего фрагмента к Activity, чтобы он мог
             * взаимодействовать с другим фрагментом*/
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // получаем элемент ListView
        ListView countriesList = view.findViewById(R.id.countriesList);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, countries);
        // устанавливаем для списка адаптер
        countriesList.setAdapter(adapter);
        // добавляем для списка слушатель
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String selectedItem = (String)parent.getItemAtPosition(position);
                /*Отправка данных Activity*/
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });
        return view;
    }
}
