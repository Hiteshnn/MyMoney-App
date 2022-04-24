package com.example.accountsapp;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


import com.example.accountsapp.Model.ToDoModel;
import com.example.accountsapp.Utils.DatabaseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class AddNewTask extends DialogFragment {

    public static final String TAG = "ActionDialog";
    private EditText newTaskText,place,gave_amount,taken_amount,notes,phone_number;
    private Button newTaskSaveButton,historyButton,messageButton;
    private TextView net_amount,status;
    private String username;



    private DatabaseHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainActivity activity=(MainActivity)getActivity();
        username=activity.getUsername();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = Objects.requireNonNull(getView()).findViewById(R.id.et1);
        newTaskSaveButton= getView().findViewById(R.id.save);
        place = Objects.requireNonNull(getView()).findViewById(R.id.et2);
        net_amount= Objects.requireNonNull(getView()).findViewById(R.id.tv);
        status=Objects.requireNonNull(getView()).findViewById(R.id.status);
        gave_amount=getView().findViewById(R.id.etn2);
        taken_amount=getView().findViewById(R.id.etn1);
        historyButton=getView().findViewById(R.id.history);
        messageButton=getView().findViewById(R.id.messageButton);
        phone_number=getView().findViewById(R.id.phonenumber);
        notes=getView().findViewById(R.id.notes);

        gave_amount.setText(String.valueOf(0));
        taken_amount.setText(String.valueOf(0));
        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String name = bundle.getString("entry_name");
            newTaskText.setText(name);
            String place_1=bundle.getString("place");
            place.setText(place_1);
            String pn=bundle.getString("phone_number");
            phone_number.setText(pn);
            int amount=bundle.getInt("net_amount");
            net_amount.setText(String.valueOf(amount));
            String status_1=bundle.getString("status");
            status.setText(status_1);

        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();



        final boolean finalIsUpdate = isUpdate;
        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newTaskText.getText().toString();
                String place_1 = place.getText().toString();
                String pn=phone_number.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getContext(),"Enter the name",Toast.LENGTH_LONG).show();
                }
                else{
                    if(place_1.isEmpty()){
                        Toast.makeText(getContext(),"Enter the place",Toast.LENGTH_LONG).show();
                    }
                    else{

                        updateTransactTable();

                        if(finalIsUpdate){

                            //newTaskText.setEnabled(false);
                            int gave_am=Integer.parseInt(gave_amount.getText().toString());
                            int taken_am=Integer.parseInt(taken_amount.getText().toString());
                            int amount=parseInt(net_amount.getText().toString());
                            String status_1= status.getText().toString();


                            if(gave_am>0&&taken_am>0){
                                if(gave_am>taken_am){
                                    gave_am=gave_am-taken_am;
                                    taken_am=0;
                                }
                                else{
                                    taken_am=taken_am-gave_am;
                                    gave_am=0;
                                }
                            }
                            if(gave_am>0){
                                if(status_1.equalsIgnoreCase("null")){
                                    amount=gave_am;
                                    status_1="Given";
                                }
                                else {
                                    if (status_1.equalsIgnoreCase("Given")) {
                                        amount = amount + gave_am;
                                    } else if (status_1.equalsIgnoreCase("Taken")) {
                                        amount = -amount + gave_am;
                                    }
                                    if (amount < 0) {
                                        status_1 = "Taken";
                                        amount = Math.abs(amount);
                                    }
                                }
                            }

                            if(taken_am>0){
                                if(status_1.equalsIgnoreCase("null")){
                                    amount=taken_am;
                                    status_1="Taken";
                                }
                                else {
                                    if (status_1.equalsIgnoreCase("Given")) {
                                        amount = amount - taken_am;
                                    } else if (status_1.equalsIgnoreCase("Taken")) {
                                        amount = -amount - taken_am;
                                    }
                                    if (amount < 0) {
                                        status_1 = "Taken";
                                        amount = Math.abs(amount);
                                    }
                                }
                            }

                            if(amount==0 ){
                                status_1="null";
                            }

                            db.updateName(bundle.getInt("id"), name);
                            db.updatePlace(bundle.getInt("id"), place_1);
                            db.updateNet_amount(bundle.getInt("id"),amount);
                            db.updateStatus(bundle.getInt("id"), status_1);
                            db.updatePhonenumber(bundle.getInt("id"),pn);

                            gave_amount.setText(String.valueOf(0));
                            taken_amount.setText(String.valueOf(0));
                            dismiss();

                        }
                        else {

                            int gave_am=parseInt(gave_amount.getText().toString());
                            int taken_am=parseInt(taken_amount.getText().toString());

                            String status_1="";
                            int amount = 0;

                            if(gave_am>0&&taken_am>0){
                                if(gave_am>taken_am){
                                    gave_am=gave_am-taken_am;
                                    taken_am=0;
                                }
                                else{
                                    taken_am=taken_am-gave_am;
                                    gave_am=0;
                                }
                            }
                            if(gave_am>0){
                                amount =gave_am;
                                status_1="Given";
                            }
                            if(taken_am>0){
                                amount=taken_am;
                                status_1="Taken";
                            }


                            ToDoModel task = new ToDoModel();
                            task.setName(name);
                            task.setPlace(place_1);
                            task.setNet_amount(amount);
                            task.setStatus(status_1);
                            task.setUsername(username);
                            task.setPhonenumber(phone_number.getText().toString());


                            db.insertEntry(task);

                            gave_amount.setText(String.valueOf(0));
                            taken_amount.setText(String.valueOf(0));
                            dismiss();

                        }
                    }
                }



            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newTaskText.getText().toString();
                //taskList= db.getAllDetails(name);
                Intent i = new Intent(getActivity(),Info_page.class);
                i.putExtra("Name",name);
                i.putExtra("username",username);
                i.putExtra("Place",place.getText().toString());
                startActivity(i);
                getActivity().finish();
            }
        });
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //try {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    if(status.getText().toString().equalsIgnoreCase("Given")){
                        sendMessage();
                    }
                    else{
                        Toast.makeText(getContext(), "You didn't give money to this person", Toast.LENGTH_LONG).show();
                    }

                    }
                else{
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},100);
                }

            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100&& grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            sendMessage();
        }
        else{
            Toast.makeText(getContext(),"Permission Denied!",Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMessage(){
        String message = "Hi " + newTaskText.getText().toString() +", this is "+username+ ".\n" + "This is a remainder message that I gave you " + net_amount.getText().toString() + " rupees.";
        String pn = phone_number.getText().toString();
        if(pn.isEmpty()){
            Toast.makeText(getContext(), "Fill the phone number field", Toast.LENGTH_SHORT).show();
        }
        else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(pn, null, message, null, null);
            Toast.makeText(getContext(), "Message is sent", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTransactTable() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
        String dateTime=simpleDateFormat.format(calendar.getTime());
        db.openDatabase();
        String name = newTaskText.getText().toString();
        String place_1 = place.getText().toString();
        int gave_am = parseInt(gave_amount.getText().toString());
        int taken_am = parseInt(taken_amount.getText().toString());
        String pn=phone_number.getText().toString();
        String note=notes.getText().toString();
        String status_1 = "";
        int amount = 0;

        if(gave_am>0&&taken_am>0){
            if(gave_am>taken_am){
                gave_am=gave_am-taken_am;
                taken_am=0;
            }
            else{
                taken_am=taken_am-gave_am;
                gave_am=0;
            }
        }
        if (gave_am > 0) {
            amount = gave_am;
            status_1 = "Given";
        }
        if (taken_am > 0) {
            amount = taken_am;
            status_1 = "Taken";
        }

        if(amount!=0){
            ToDoModel task = new ToDoModel();
            task.setName(name);
            task.setPlace(place_1);
            task.setNet_amount(amount);
            task.setStatus(status_1);
            task.setNote(note);
            task.setDate(dateTime);
            task.setPhonenumber(pn);
            task.setUsername(username);
            db.insertEntry2(task);}

    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog){


        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener)
            ((DialogCloseListener)activity).handleDialogClose(dialog);

    }
}
