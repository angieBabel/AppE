package com.example.yoo.appeb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class agregargasto extends Fragment {


    public agregargasto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregargasto, container, false);
        //text = (TextView) view.findViewById(R.id.Productos);
        super.onCreate(savedInstanceState);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        LinearLayout layoutRE = (LinearLayout)getView().findViewById(R.id.RE);
        LinearLayout layoutRN = (LinearLayout)getView().findViewById(R.id.RN);
        RadioGroup RG = (RadioGroup)getView().findViewById(R.id.RubrosRB);
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                  switch(checkedId){
                      case R.id.rubroE:
                          // do operations specific to this selection
                          mostrarRE(getView());
                          break;

                      case R.id.rubroN:
                          // do operations specific to this selection
                          mostrarRN(getView());
                          break;
                  }
              }
          }
        );
    }
    public void mostrarRE(View view) {
        //((TextView) view.findViewById(R.id.textView_superior)).setText(mItem.textoEncima);
        ((LinearLayout) view.findViewById(R.id.RE)).setVisibility(View.VISIBLE);
        ((LinearLayout) view.findViewById(R.id.RN)).setVisibility(View.GONE);


    }
    public void mostrarRN(View view){
        ((LinearLayout) view.findViewById(R.id.RN)).setVisibility(View.VISIBLE);
        ((LinearLayout) view.findViewById(R.id.RE)).setVisibility(View.GONE);
    }
}
