package com.cs374.paceflex;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import com.cs374.paceflex.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    private FloatingActionButton fab_main;
    private FloatingActionButton fab_income;
    private FloatingActionButton fab_expense;
    private TextView fab_income_txt;
    private TextView fab_expense_txt;

    private boolean isOpen=false;
    private Animation fade_open,fade_close;

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_dashboard, container, false);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid=mUser.getUid ();
        mIncomeDatabase= FirebaseDatabase.getInstance().getReference ().child("IncomeData").child(uid);
        mExpenseDatabase=FirebaseDatabase.getInstance().getReference ().child("ExpenseData").child(uid);
        fab_main=myview.findViewById(R.id.fb_main_plus_btn);
        fab_income=myview.findViewById(R.id.income_Ft_button);
        fab_expense=myview.findViewById(R.id.expense_Ft_btn);
        fab_income_txt=myview.findViewById(R.id.income_ft_text);
        fab_expense_txt=myview.findViewById(R.id.expense_ft_text);
        fade_open= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_open);
        fade_close= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_close);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                if (isOpen){
                    fab_income.startAnimation(fade_close);
                    fab_expense.startAnimation(fade_close);
                    fab_income.setClickable(false);
                    fab_expense.setClickable(false);
                    fab_income_txt.startAnimation(fade_close);
                    fab_expense_txt.startAnimation(fade_close);
                    fab_income_txt.setClickable(false);
                    fab_expense_txt.setClickable(false);
                    isOpen=false;

                }
                else {
                    fab_income.startAnimation(fade_open);
                    fab_expense.startAnimation(fade_open);
                    fab_income.setClickable(true);
                    fab_expense.setClickable(true);
                    fab_income_txt.startAnimation(fade_open);
                    fab_expense_txt.startAnimation(fade_open);
                    fab_income_txt.setClickable(true);
                    fab_expense_txt.setClickable(true);
                    isOpen=true;
                }
            }
        });
        return myview;
    }
    private void ftAnimation(){
        if (isOpen){
            fab_income.startAnimation(fade_close);
            fab_expense.startAnimation(fade_close);
            fab_income.setClickable(false);
            fab_expense.setClickable(false);
            fab_income_txt.startAnimation(fade_close);
            fab_expense_txt.startAnimation(fade_close);
            fab_income_txt.setClickable(false);
            fab_expense_txt.setClickable(false);
            isOpen=false;

        }
        else {
            fab_income.startAnimation(fade_open);
            fab_expense.startAnimation(fade_open);
            fab_income.setClickable(true);
            fab_expense.setClickable(true);
            fab_income_txt.startAnimation(fade_open);
            fab_expense_txt.startAnimation(fade_open);
            fab_income_txt.setClickable(true);
            fab_expense_txt.setClickable(true);
            isOpen=true;
        }
    }
    private void addData(){
        fab_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInsert();
            }
        });
        fab_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseDataInsert();
            }
        });

    }
    public void incomeDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myviewm=inflater.inflate(R.layout.custom_layout_for_insert,null);
        mydialog.setView(myviewm);

        AlertDialog dialog=mydialog.create();
        mydialog.setCancelable(false);
        EditText edtAmount=myviewm.findViewById(R.id.amount_edt);
        EditText edtType=myviewm.findViewById(R.id.type_edt);
        EditText edtNote=myviewm.findViewById(R.id.note_edt);
        Button btnSave=myviewm.findViewById(R.id.btn_save);
        Button btnCansel=myviewm.findViewById(R.id.btn_cancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type=edtType.getText().toString() .trim();
                String amount=edtAmount.getText().toString().trim();
                String note=edtNote.getText ().toString().trim();
                if (TextUtils.isEmpty(type)){
                    edtType.setError("Required Field...");
                    return;
                }
                if (TextUtils.isEmpty(amount)){
                    edtAmount.setError("Required Field...");
                    return;
                }
                int ouramount=Integer.parseInt(amount);
                if (TextUtils.isEmpty(note)){
                    edtNote.setError("Required Field...");
                    return;
                }
                String id=mIncomeDatabase.push().getKey();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String mdate = sdf.format(new Date());
                com.cs374.paceflex.Model.Data data=new Data(ouramount,type,note,id,mdate);
                mIncomeDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(),"Data Added",Toast.LENGTH_SHORT).show();
                ftAnimation();
                dialog.dismiss();
            }
        });
        btnCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void expenseDataInsert(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myviewm=inflater.inflate(R.layout.custom_layout_for_insert,null);
        mydialog.setView(myviewm);
        mydialog.setCancelable(false);
        final AlertDialog dialog=mydialog.create();
        final EditText amount=myviewm.findViewById(R.id.amount_edt);
        final EditText type=myviewm.findViewById(R.id.type_edt);
        EditText note=myviewm.findViewById(R.id.note_edt);
        Button btnSave=myviewm.findViewById(R.id.btn_save);
        Button btnCansel=myviewm.findViewById(R.id.btn_cancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmAmmount=amount.getText().toString().trim();
                String tmtype=type.getText().toString().trim();
                String tmnote=note.getText().toString().trim();
                if (TextUtils.isEmpty (tmAmmount)) {
                    amount.setError ("Required Field...");
                    return;
                }
                if (TextUtils.isEmpty (tmtype)) {
                    type.setError ("Required Field...");
                    return;
                }
                int inamount=Integer.parseInt(tmAmmount);
                if (TextUtils.isEmpty (tmnote)){
                    note.setError ("Required Field...");
                    return;
                }
                String id=mExpenseDatabase.push().getKey();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String mdate = sdf.format(new Date());
                com.cs374.paceflex.Model.Data data=new Data(inamount,tmtype,tmnote,id,mdate);
                mExpenseDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(),"Data Added",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                ftAnimation();
            }

        });
        btnCansel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}