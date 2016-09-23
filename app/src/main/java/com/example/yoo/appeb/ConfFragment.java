package com.example.yoo.appeb;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfFragment extends Fragment {
    /*tvFI aqui esta el textview para mostrar la fecha inicial
    btnFI este es el boton de la fecha inicial

    tvFF aqui esta el textview para mostrar la fecha final
    btnFF este es el boton de la fecha final


    spinnerGrafica spinner

     */
    Button button;
    Button buttonFF;
    TextView FI;
    TextView FF;


    public ConfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conf, container, false);
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        button = (Button)getView().findViewById(R.id.btnFI);
        buttonFF = (Button)getView().findViewById(R.id.btnFF);
        FI = (TextView)getView().findViewById(R.id.tvFI);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });


    }
    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            FI.setText(month+"/"+day+"/"+year);
        }

    }

}

