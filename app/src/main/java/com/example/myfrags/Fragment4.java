package com.example.myfrags;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class Fragment4 extends Fragment {

    //1. pola do podłączenia danych
    private FragsData fragsData;
    private Observer<Integer> numberObserver;

    //2. pola do obsługi pola edycyjnego
    private EditText edit;
    private TextWatcher textWatcher;
    private boolean turnOffWatcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_4, container, false);

        //1. Dostęp do pola edycyjnego
        edit = view.findViewById(R.id.editTextNumber);

        //2. Pobranie obiektu ViewModel
        fragsData = new ViewModelProvider(requireActivity()).get(FragsData.class);

        //3. Stworzenie obserwatora
        numberObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer newInteger) {
                turnOffWatcher = true;
                edit.setText(newInteger.toString());
                edit.setSelection(edit.getText().length());
            }
        };

        //4. Podłączenie obserwatora do obserwowanej zmiennej
        fragsData.counter.observe(getViewLifecycleOwner(), numberObserver);

        //5. Stworzenie obiektu TextWatchera
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!turnOffWatcher){
                    Integer i;
                    try{
                        i = Integer.parseInt(s.toString());
                        fragsData.counter.setValue(i);
                    }
                    catch(NumberFormatException e){
                        i = fragsData.counter.getValue();
                    }
                }
                else{
                    turnOffWatcher = !turnOffWatcher;
                }
            }
        };

        //6. Podłączenie TextWatchera do pola edycyjnego
        edit.addTextChangedListener(textWatcher);

        return view;
    }
}