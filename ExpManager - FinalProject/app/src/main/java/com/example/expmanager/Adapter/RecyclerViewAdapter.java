package com.example.expmanager.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expmanager.DataBaseHandler.MyDBHandler;
import com.example.expmanager.HomeActivity;
import com.example.expmanager.Model.Data;
import com.example.expmanager.R;

import java.util.Calendar;
import java.util.List;
//Adapter used to populate the income and expense fragments.
//Used this feature to show the user bifurcated views of income and expenses.
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Data> dataList;

    public TextView amount;
    public TextView type;
    public TextView note;
    public TextView date;
    public ImageView image;
    public ImageView update;

    // Date
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    ///update edit text

    private EditText edtAmount;
    private Spinner edtType;
    private EditText edtNote;

    //button for update and delete

    private Button btnUpdate;
    private Button btnDelete;

    public RecyclerViewAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    View view;

    // where to get the single card as view holder object
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_data,parent,false);
        return new ViewHolder(view);
    }


    Data data;
    // Populate the view holder with the data from Data Class.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        data = dataList.get(position);
        amount.setText(""+data.getAmount());
        type.setText(data.getType());
        note.setText(data.getNote());
        date.setText(data.getDate());
        image.setOnClickListener(v -> {
            MyDBHandler db = new MyDBHandler(context);
            Toast.makeText(context, "Record Deleted", Toast.LENGTH_SHORT).show();
            List<Data> dataList;
            if (data.getFlag().equals("I")) {
                dataList = db.getIncomeTransactions();
            } else {
                dataList = db.getExpenseTransactions();
            }
            db.deleteContact(dataList.get(position).getId());
            removeItem(holder.getAdapterPosition());

            v.getContext().startActivity(new Intent(context,HomeActivity.class));
        });

        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDataItem(v.getContext(),position);
                        //Context context = v.getContext();

                    }
                });

    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(context, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        if (month < 10 && day < 10) {
            return year + "-0" + month + "-0" + day;
        }
        else if (month < 10) {
            return year + "-0" + month + "-" + day;
        }
        else if (day < 10) {
            return year + "-" + month + "-0" + day;
        }
        else {
            return year + "-" + month + "-" + day;
        }
    }

    private void updateDataItem(Context context, int position){
        AlertDialog.Builder mydialog =new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
        View myview = inflater.inflate(R.layout.update_data_item,null );
        mydialog.setView(myview);

        initDatePicker();
        dateButton = myview.findViewById(R.id.datePickerButton);
        dateButton.setText(dataList.get(position).getDate());

        dateButton.setOnClickListener(v -> datePickerDialog.show());

        edtAmount=myview.findViewById(R.id.amount_edit);
        //edtType = myview.findViewById(R.id.type_edit);
        edtNote = myview.findViewById(R.id.note_edit);




//        ArrayAdapter<CharSequence> spinAdapter;
//        if (dataList.get(position).getFlag().equals("E")) {
//            spinAdapter = ArrayAdapter.createFromResource(context,R.array.spinnerTransactionExpenseOptions, android.R.layout.simple_spinner_item);
//
//        } else {
//            spinAdapter = ArrayAdapter.createFromResource(context,R.array.spinnerTransactionIncomeOptions, android.R.layout.simple_spinner_item);
//        }
//        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        edtType.setAdapter(spinAdapter);
//        edtType.setSelection(position);

        edtAmount.setText(""+dataList.get(position).getAmount());
        edtNote.setText(dataList.get(position).getNote());

        btnUpdate = myview.findViewById(R.id.btn_upd_Update);
        btnDelete = myview.findViewById(R.id.btnuPD_Delete);

        AlertDialog dialog= mydialog.create();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String type = edtType.getSelectedItem().toString().trim();
                String Stringamount = edtAmount.getText().toString().trim();
                String note = edtNote.getText().toString().trim();
                String date = dateButton.getText().toString().trim();
                //input validation
//                if(edtType.getSelectedItemPosition() == -1)
//                {
//                    edtType.setSelection(0);
//                    return;
//                }
                if(TextUtils.isEmpty(Stringamount))
                {
                    edtAmount.setError("Required Field..");
                    return;
                }
                double amount = Double.parseDouble(Stringamount);
                if(amount<=0)
                {
                    edtAmount.setError("Enter Valid Number Greater than 0");
                    return;
                }

                if(TextUtils.isEmpty(note))
                {
                    edtNote.setError("Required Field..");
                    return;
                }

                try {
                    Data inData = new Data(amount, dataList.get(position).getType(), note, date ,dataList.get(position).getUserId(), dataList.get(position).getFlag());
                    MyDBHandler inDatabaseData = new MyDBHandler(context);
                    inDatabaseData.addTransaction(inData);

                    inDatabaseData.deleteContact(dataList.get(position).getId());
                    removeItem(position);

                    v.getContext().startActivity(new Intent(context,HomeActivity.class));

                    Log.d("Data Update Kovid","User added with ID: "+inData.getUserId());
                    Toast.makeText(context,"Data updated - Kovid",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                catch(Exception e)
                {
                    Log.d("data insert floating","Data update not saved");
                }


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //Delete Transaction Actions
    private void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            amount = itemView.findViewById(R.id.amount_txt_income);
            type = itemView.findViewById(R.id.type_txt_income);
            note = itemView.findViewById(R.id.note_txt_income);
            date = itemView.findViewById(R.id.date_txt_income);
            image = itemView.findViewById(R.id.imageViewDelete);
            update = itemView.findViewById(R.id.imageViewUpdate);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
