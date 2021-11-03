package com.example.expmanager;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expmanager.Adapter.NotificationsListViewAdapter;
import com.example.expmanager.DataBaseHandler.MyDBHandler;
import com.example.expmanager.Model.Data;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class NotificationFragment extends Fragment {

    List<Data> dataAllIncome, dataAllExpense;
    String TAG = "Janhvi Notifications Activity";
    int LastYear, PreviousYear;
    ListView NotificationsListView;
    List<String> notificationItems = new ArrayList<>();
    HashMap<Integer, String> Months = new HashMap<Integer, String>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        NotificationsListView=view.findViewById(R.id.notifications_listView);
        final NotificationsListViewAdapter nAdapter =new NotificationsListViewAdapter(notificationItems);
        NotificationsListView.setAdapter(nAdapter);


        MyDBHandler handler = new MyDBHandler(getContext());
        dataAllIncome=handler.getIncomeTransactions();
        dataAllExpense = handler.getExpenseTransactions();

        Months.put(1,"January");
        Months.put(2,"February");
        Months.put(3,"March");
        Months.put(4,"April");
        Months.put(5,"May");
        Months.put(6,"June");
        Months.put(7,"July");
        Months.put(8,"August");
        Months.put(9,"September");
        Months.put(10,"October");
        Months.put(11,"November");
        Months.put(12,"December");



        String now = String.valueOf(java.time.LocalDate.now());
        LastYear= Integer.parseInt(now.substring(0, 4));
        //   = Integer.parseInt(dataAllIncome.get(dataAllIncome.size()-1).getDate().substring(0,4));
        PreviousYear = LastYear-1;



        ArrayList<Double> dataIncomeCurrentY=NotificationsIncomeCurrent();
        ArrayList<Double> dataIncomePreviousY =NotificationsIncomePrevious();
        ArrayList<Double> dataExpensePreviousY =NotificationsExpensePrevious();
        ArrayList<Double> dataExpenseCurrentY =NotificationsExpenseCurrent();

        GenerateExpenseMonthNotification(dataExpenseCurrentY,dataExpensePreviousY);
        GenerateIncomeMonthNotification(dataIncomeCurrentY,dataIncomePreviousY);
        GenerateIncomeExpenseNotification(dataIncomeCurrentY,dataExpenseCurrentY);
        GenerateHighestExpenseNotification(dataAllExpense);

        ExpenseSegmentation();


//        //marquee
//        TextView marquee = view.findViewById(R.id.notification_marquee);
//        marquee.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        marquee.setSelected(true);


        return view;
    }

    private void ExpenseSegmentation()
    {
        int avgUtil = 30;
        int avgEntertainmentandFood = 10;
        int avgInsurancesCarandHealthcare = 6;
        int avgShoppingPersonal = 12;
        int avgMisc = 25;
        int avgGroceriesHousehold = 12;

        notificationItems.add("Average student spends about 30% of their income on rent and utilities " +
                "and about 12% on groceries and household supplies");
        notificationItems.add("Average student spends about 10% of their income on Entertainment and hanging out with friends " +
                "and about 12% on shopping and personal needs");
        notificationItems.add("Average student spends about 6% of their income on Insurances " +
                "and about 25% on miscellaneous needs");
        notificationItems.add("Average student saves about 5% of their part time Income");

    }
    private ArrayList<Double> NotificationsIncomeCurrent()
    {

        double Jan=0, Feb=0, March=0, Apr=0, May=0, Jun=0, Jul=0, Aug=0, Sept=0, Oct=0, Nov=0, Dec=0;


        ArrayList<Double> dataIncomeCurrentY = new ArrayList<>();

        try {
            for (int i = 0; i < dataAllIncome.size(); i++)
            {
                int YearData = Integer.parseInt(dataAllIncome.get(i).getDate().substring(0,4));
                if(YearData == LastYear)
                {
                    String monthData = dataAllIncome.get(i).getDate().substring(5,7);

                    switch (monthData)
                    {

                        case "01":
                            Jan+=dataAllIncome.get(i).getAmount();
                            break;

                        case "02":
                            Feb+=dataAllIncome.get(i).getAmount();
                            break;

                        case "03":
                            March+=dataAllIncome.get(i).getAmount();
                            break;
                        case "04":
                            Apr+=dataAllIncome.get(i).getAmount();
                            break;
                        case "05":
                            May+=dataAllIncome.get(i).getAmount();
                            break;
                        case "06":
                            Jun+=dataAllIncome.get(i).getAmount();
                            break;
                        case "07":
                            Jul+=dataAllIncome.get(i).getAmount();
                            break;
                        case "08":
                            Aug+=dataAllIncome.get(i).getAmount();
                            break;
                        case "09":
                            Sept+=dataAllIncome.get(i).getAmount();
                            break;
                        case "10":
                            Oct+=dataAllIncome.get(i).getAmount();
                            break;
                        case "11":
                            Nov+=dataAllIncome.get(i).getAmount();
                            break;
                        case "12":
                            Dec+=dataAllIncome.get(i).getAmount();
                            break;
                        default:
                            break;
                    }
                }
                else
                {
                    continue;
                }}
       //     Toast.makeText(getContext(),"Month calculation done",Toast.LENGTH_SHORT).show();


        }
        catch (Exception ex)
        {
            Log.d(TAG,"Notifications Income Current data not set"+ ex.getMessage());

        }

        dataIncomeCurrentY.add(Jan);
        dataIncomeCurrentY.add(Feb);
        dataIncomeCurrentY.add(March);
        dataIncomeCurrentY.add(Apr);
        dataIncomeCurrentY.add(May);
        dataIncomeCurrentY.add(Jun);
        dataIncomeCurrentY.add(Jul);
        dataIncomeCurrentY.add(Aug);
        dataIncomeCurrentY.add(Sept);
        dataIncomeCurrentY.add(Oct);
        dataIncomeCurrentY.add(Nov);
        dataIncomeCurrentY.add(Dec);

        Log.d(TAG,"NotificationsIncomeCurrent" +dataIncomeCurrentY.get(5));


        return dataIncomeCurrentY;
    }
    private ArrayList<Double> NotificationsIncomePrevious()
    {

        double Jan=0, Feb=0, March=0, Apr=0, May=0, Jun=0, Jul=0, Aug=0, Sept=0, Oct=0, Nov=0, Dec=0;

        ArrayList<Double> dataIncomePreviousY = new ArrayList<>();

        try {
            for (int i = 0; i < dataAllIncome.size(); i++)
            {
                int YearData = Integer.parseInt(dataAllIncome.get(i).getDate().substring(0,4));
                if(YearData == PreviousYear)
                {
                    String monthData = dataAllIncome.get(i).getDate().substring(5,7);

                    switch (monthData)
                    {

                        case "01":
                            Jan+=dataAllIncome.get(i).getAmount();
                            break;

                        case "02":
                            Feb+=dataAllIncome.get(i).getAmount();
                            break;

                        case "03":
                            March+=dataAllIncome.get(i).getAmount();
                            break;
                        case "04":
                            Apr+=dataAllIncome.get(i).getAmount();
                            break;
                        case "05":
                            May+=dataAllIncome.get(i).getAmount();
                            break;
                        case "06":
                            Jun+=dataAllIncome.get(i).getAmount();
                            break;
                        case "07":
                            Jul+=dataAllIncome.get(i).getAmount();
                            break;
                        case "08":
                            Aug+=dataAllIncome.get(i).getAmount();
                            break;
                        case "09":
                            Sept+=dataAllIncome.get(i).getAmount();
                            break;
                        case "10":
                            Oct+=dataAllIncome.get(i).getAmount();
                            break;
                        case "11":
                            Nov+=dataAllIncome.get(i).getAmount();
                            break;
                        case "12":
                            Dec+=dataAllIncome.get(i).getAmount();
                            break;
                        default:
                            break;
                    }
                }
                else
                {
                    continue;
                }
            }

       //     Toast.makeText(getContext(),"Month calculation done",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Log.d(TAG,"Notifications Income Previous data not set"+ ex.getMessage());

        }

        dataIncomePreviousY.add(Jan);
        dataIncomePreviousY.add(Feb);
        dataIncomePreviousY.add(March);
        dataIncomePreviousY.add(Apr);
        dataIncomePreviousY.add(May);
        dataIncomePreviousY.add(Jun);
        dataIncomePreviousY.add(Jul);
        dataIncomePreviousY.add(Aug);
        dataIncomePreviousY.add(Sept);
        dataIncomePreviousY.add(Oct);
        dataIncomePreviousY.add(Nov);
        dataIncomePreviousY.add(Dec);

        Log.d(TAG,"NotificationsIncomePrevious" +dataIncomePreviousY.get(5));
        return dataIncomePreviousY;
    }
    private ArrayList<Double> NotificationsExpensePrevious()
    {

        double Jan=0, Feb=0, March=0, Apr=0, May=0, Jun=0, Jul=0, Aug=0, Sept=0, Oct=0, Nov=0, Dec=0;

        ArrayList<Double> dataExpensePreviousY = new ArrayList<>();

        try {
            for (int i = 0; i < dataAllExpense.size(); i++)
            {
                int YearData = Integer.parseInt(dataAllExpense.get(i).getDate().substring(0,4));

                if(YearData == PreviousYear)
                {
                    String monthData = dataAllExpense.get(i).getDate().substring(5,7);

                    switch (monthData)
                    {

                        case "01":
                            Jan+=dataAllExpense.get(i).getAmount();
                            break;

                        case "02":
                            Feb+=dataAllExpense.get(i).getAmount();
                            break;

                        case "03":
                            March+=dataAllExpense.get(i).getAmount();
                            break;
                        case "04":
                            Apr+=dataAllExpense.get(i).getAmount();
                            break;
                        case "05":
                            May+=dataAllExpense.get(i).getAmount();
                            break;
                        case "06":
                            Jun+=dataAllExpense.get(i).getAmount();
                            break;
                        case "07":
                            Jul+=dataAllExpense.get(i).getAmount();
                            break;
                        case "08":
                            Aug+=dataAllExpense.get(i).getAmount();
                            break;
                        case "09":
                            Sept+=dataAllExpense.get(i).getAmount();
                            break;
                        case "10":
                            Oct+=dataAllExpense.get(i).getAmount();
                            break;
                        case "11":
                            Nov+=dataAllExpense.get(i).getAmount();
                            break;
                        case "12":
                            Dec+=dataAllExpense.get(i).getAmount();
                            break;
                        default:
                            break;

                    }

                }else
                {
                    continue;
                }
            }
        //    Toast.makeText(getContext(),"Month calculation done",Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex)
        {
            Log.d(TAG,"Notifications Expense Previous data not set"+ ex.getMessage());

        }

        dataExpensePreviousY.add(Jan);
        dataExpensePreviousY.add(Feb);
        dataExpensePreviousY.add(March);
        dataExpensePreviousY.add(Apr);
        dataExpensePreviousY.add(May);
        dataExpensePreviousY.add(Jun);
        dataExpensePreviousY.add(Jul);
        dataExpensePreviousY.add(Aug);
        dataExpensePreviousY.add(Sept);
        dataExpensePreviousY.add(Oct);
        dataExpensePreviousY.add(Nov);
        dataExpensePreviousY.add(Dec);

        return dataExpensePreviousY;
    }
    private ArrayList<Double> NotificationsExpenseCurrent()
    {

        double Jan=0, Feb=0, March=0, Apr=0, May=0, Jun=0, Jul=0, Aug=0, Sept=0, Oct=0, Nov=0, Dec=0;

        ArrayList<Double> dataExpenseCurrentY = new ArrayList<>();

        try {
            for (int i = 0; i < dataAllExpense.size(); i++)
            {
                int YearData = Integer.parseInt(dataAllExpense.get(i).getDate().substring(0,4));
                if(YearData == LastYear)
                {
                    String monthData = dataAllExpense.get(i).getDate().substring(5,7);

                    switch (monthData)
                    {

                        case "01":
                            Jan+=dataAllExpense.get(i).getAmount();
                            break;

                        case "02":
                            Feb+=dataAllExpense.get(i).getAmount();
                            break;

                        case "03":
                            March+=dataAllExpense.get(i).getAmount();
                            break;
                        case "04":
                            Apr+=dataAllExpense.get(i).getAmount();
                            break;
                        case "05":
                            May+=dataAllExpense.get(i).getAmount();
                            break;
                        case "06":
                            Jun+=dataAllExpense.get(i).getAmount();
                            break;
                        case "07":
                            Jul+=dataAllExpense.get(i).getAmount();
                            break;
                        case "08":
                            Aug+=dataAllExpense.get(i).getAmount();
                            break;
                        case "09":
                            Sept+=dataAllExpense.get(i).getAmount();
                            break;
                        case "10":
                            Oct+=dataAllExpense.get(i).getAmount();
                            break;
                        case "11":
                            Nov+=dataAllExpense.get(i).getAmount();
                            break;
                        case "12":
                            Dec+=dataAllExpense.get(i).getAmount();
                            break;
                        default:
                            break;
                    }
                }else
                {
                    continue;
                }
            }
       //     Toast.makeText(getContext(),"Month calculation done",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Log.d(TAG,"Notifications Expense Current data not set"+ ex.getMessage());

        }

        dataExpenseCurrentY.add(Jan);
        dataExpenseCurrentY.add(Feb);
        dataExpenseCurrentY.add(March);
        dataExpenseCurrentY.add(Apr);
        dataExpenseCurrentY.add(May);
        dataExpenseCurrentY.add(Jun);
        dataExpenseCurrentY.add(Jul);
        dataExpenseCurrentY.add(Aug);
        dataExpenseCurrentY.add(Sept);
        dataExpenseCurrentY.add(Oct);
        dataExpenseCurrentY.add(Nov);
        dataExpenseCurrentY.add(Dec);

        return dataExpenseCurrentY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GenerateExpenseMonthNotification(ArrayList<Double> thisYear, ArrayList<Double> lastYear)
    {
        String newNotif="";

        //  Date date = new Date();
        // String dates = String.valueOf(date);
        try {

            String now = String.valueOf(java.time.LocalDate.now());
            int day = Integer.parseInt(now.substring(8,10));
            int month = Integer.parseInt(now.substring(5, 7));
            int year = Integer.parseInt(now.substring(0, 4));
           // int day = 1;

            int counter = 0;
            double perDiff = 0;

                if (thisYear.get(month - 2) > lastYear.get(month - 2)) {
                    perDiff = ((thisYear.get(month - 2) - lastYear.get(month - 2)) / lastYear.get(month - 2))*100;
                    newNotif = "Your Expenses in the month of " + Months.get(month - 1) +
                            " this year were " + String.format("%.2f", perDiff) + "% more than the last year";
                } else if (thisYear.get(month - 2) < lastYear.get(month - 2)) {
                    perDiff = ((lastYear.get(month - 2) - thisYear.get(month - 2)) / lastYear.get(month - 2)) * 100;
                    newNotif = "Your Expenses in the month of " + Months.get(month - 1) +
                            " this year were " + String.format("%.2f", perDiff) + "% less than the last year";

                } else {
                    newNotif = "Your Expenses in the month of " + Months.get(month - 1) +
                            " this year were equal to the last year";

                }
           //     String x = "this year "+thisYear.get(month - 2)+" Last year "+lastYear.get(month - 2);
                notificationItems.add(newNotif);

        }
        catch (Exception ex)
        {
            Log.d(TAG,"Expense Notification Not set"+ ex.getMessage());

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GenerateIncomeMonthNotification(ArrayList<Double> thisYear, ArrayList<Double> lastYear)
    {
        String newNotif="";

        //  Date date = new Date();
        // String dates = String.valueOf(date);
        try {

            String now = String.valueOf(java.time.LocalDate.now());
            int day = Integer.parseInt(now.substring(8,10));
            int month = Integer.parseInt(now.substring(5, 7));
            int year = Integer.parseInt(now.substring(0, 4));
           // int day = 1;

            int counter = 0;
            double perDiff = 0;

                if (thisYear.get(month - 2) > lastYear.get(month - 2)) {
                    perDiff = ((thisYear.get(month - 2) - lastYear.get(month - 2)) / lastYear.get(month - 2)) * 100;
                    newNotif = "Your Income in the month of " + Months.get(month - 1) +
                            " this year was " + String.format("%.2f", perDiff) + "% more than the last year";
                } else if (thisYear.get(month - 2) < lastYear.get(month - 2)) {
                    perDiff = ((lastYear.get(month - 2) - thisYear.get(month - 2)) / lastYear.get(month - 2)) * 100;
                    newNotif = "Your Income in the month of " + Months.get(month - 1) +
                            " this year was " + String.format("%.2f", perDiff) + "% less than the last year";

                } else {
                    newNotif = "Your Income in the month of " + Months.get(month - 1) +
                            " this year was equal to the last year";

                }
                notificationItems.add(newNotif);

        }
        catch (Exception ex)
        {
            Log.d(TAG,"Income Notification Not Set"+ ex.getMessage());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GenerateIncomeExpenseNotification(ArrayList<Double> Income, ArrayList<Double> Expense)
    {
        String newNotif="";

        //  Date date = new Date();
        // String dates = String.valueOf(date);
        try {

            String now = String.valueOf(java.time.LocalDate.now());
            int day = Integer.parseInt(now.substring(8,10));
            int month = Integer.parseInt(now.substring(5, 7));
            int year = Integer.parseInt(now.substring(0, 4));
           // int day = 1;

            int counter = 0;

                if (Income.get(month - 2) > Expense.get(month - 2))
                {
                    newNotif = "You Saved " +(Income.get(month - 2)- Expense.get(month - 2)) +" CAD in the month of "+ Months.get(month - 1);

                }
                else if (Income.get(month - 2) < Expense.get(month - 2))
                {
                    newNotif = "Your Expenses were "+(Income.get(month - 2) < Expense.get(month - 2))+
                            " CAD more than your income in the month of " + Months.get(month - 1);

                }
                else {
                    newNotif = "You spent everything you earned, no more no less, in the month of "+Months.get(month-1);

                }
                notificationItems.add(newNotif);
            }

        catch (Exception ex)
        {
            Log.d(TAG,"Income Expense Notification Not Set"+ ex.getMessage());

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void GenerateHighestExpenseNotification(List<Data> maxExp)
    {
        try{
        String now = String.valueOf(java.time.LocalDate.now());
        int day = Integer.parseInt(now.substring(8,10));
        int month = Integer.parseInt(now.substring(5, 7));
        int year = Integer.parseInt(now.substring(0, 4));

        double max =0;
        String maxMessage="";

        for (int i = 0; i < maxExp.size(); i++)
        {
            int m =Integer.parseInt(maxExp.get(i).getDate().substring(5,7));
            int y =Integer.parseInt(maxExp.get(i).getDate().substring(0,4));
            if(m == month-1 && y==year)
            {
                if(maxExp.get(i).getAmount()>max)
                {
                    max = maxExp.get(i).getAmount();
                    maxMessage = maxExp.get(i).getType();
                 }

            }
        }

        String newNotif = "Your Highest Transaction in "+Months.get(month-1)+" was "+max+"CAD for "+maxMessage;
        notificationItems.add(newNotif);
        }
        catch (Exception ex)
        {
            Log.d(TAG,"Highest Expense Notification Not Set"+ ex.getMessage());

        }
    }
}