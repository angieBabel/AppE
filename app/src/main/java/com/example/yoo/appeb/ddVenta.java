package com.example.yoo.appeb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ddVenta extends Fragment {


    public ddVenta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dd_venta, container, false);
        super.onCreate(savedInstanceState);
        return view;

    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        LinearLayout deudor = (LinearLayout)getView().findViewById(R.id.deudor);
        RadioGroup RG = (RadioGroup)getView().findViewById(R.id.ModopagoRB);
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                      switch(checkedId){
                          case R.id.pagoCredito:
                              // do operations specific to this selection
                              showDeudor(getView());
                              break;

                          case R.id.pagoContado:
                              // do operations specific to this selection
                              hideDeudor(getView());
                              break;
                      }
                  }
              }
        );
    }

    public void showDeudor(View view) {
        //((TextView) view.findViewById(R.id.textView_superior)).setText(mItem.textoEncima);
        ((LinearLayout) view.findViewById(R.id.deudor)).setVisibility(View.VISIBLE);



    }
    public void hideDeudor(View view){
        ((LinearLayout) view.findViewById(R.id.deudor)).setVisibility(View.GONE);
    }


}
