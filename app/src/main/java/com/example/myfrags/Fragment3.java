package com.example.myfrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class Fragment3 extends Fragment {

    private TextView text;
    private Button button;
    private FragsData fragsData;
    private Observer<Integer> numberObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        //1. dostęp do pola tekstowego i przycisku
        text = (TextView) view.findViewById(R.id.current);
        button = (Button) view.findViewById(R.id.button_decrease);

        //2. pobranie obiektu ViewModel
        fragsData = new ViewModelProvider(requireActivity()).get(FragsData.class);

        //3. Stworzenie obserwatora
        numberObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer newInteger) {
                text.setText(newInteger.toString());
            }
        };

        //4. Podłączenie obserwatora do obserwowanej zmiennej
        fragsData.counter.observe(getViewLifecycleOwner(), numberObserver);

        //5. oprogranie przycisku
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer i = fragsData.counter.getValue();
                fragsData.counter.setValue(--i);
            }
        });


        return view;
    }
}