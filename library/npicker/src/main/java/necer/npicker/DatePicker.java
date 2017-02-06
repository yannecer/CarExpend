package necer.npicker;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import necer.npicker.widget.WheelView;
import necer.npicker.widget.adapters.ArrayWheelAdapter;
import necer.npicker.widget.adapters.OnWheelSelectListener;

/**
 * Created by necer on 2017/2/4.
 */

public class DatePicker extends BasePicker implements OnWheelSelectListener {


    private WheelView wl_year;
    private WheelView wl_month;
    private WheelView wl_day;

    private ArrayList<String> yearsList = new ArrayList<>();
    private ArrayList<String> monthsList = new ArrayList<>();
    private ArrayList<String> daysList = new ArrayList<>();

    private int beganYear = 1970;
    private int endYear = 2050;


    private String selectYear;
    private String selectMonth;
    private String selectDay;


    private OnDatePickListener mDatePickListener;

    private Calendar calendar = Calendar.getInstance();

    private ArrayWheelAdapter<String> dayWheelAdapter;

    public DatePicker(Activity activity) {
        super(activity);
        initData();
    }

    @Override
    protected void initView(View pickView) {
        wl_year = (WheelView) pickView.findViewById(R.id.wl_year);
        wl_month = (WheelView) pickView.findViewById(R.id.wl_month);
        wl_day = (WheelView) pickView.findViewById(R.id.wl_day);

        wl_year.setCyclic(false);
        wl_month.setCyclic(true);
        wl_day.setCyclic(true);

        wl_year.setTag(0);
        wl_month.setTag(1);
        wl_day.setTag(2);

        wl_year.setOnWheelSelectListener(this);
        wl_month.setOnWheelSelectListener(this);
        wl_day.setOnWheelSelectListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_picker_date;
    }

    @Override
    protected void onSure() {
        if (mDatePickListener != null) {
            selectYear = yearsList.get(wl_year.getCurrentItem());
            selectMonth = monthsList.get(wl_month.getCurrentItem());
            selectDay = daysList.get(wl_day.getCurrentItem());
            mDatePickListener.onSelect(selectYear, selectMonth, selectDay);
        }
    }


    private void initData() {
        yearsList.clear();
        monthsList.clear();
        daysList.clear();

        for (int i = beganYear; i <= endYear; i++) {
            yearsList.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            monthsList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        for (int i = 1; i <= 31; i++) {
            daysList.add(i < 10 ? "0" + i : String.valueOf(i));
        }

        wl_year.setViewAdapter(new ArrayWheelAdapter<>(mContext, yearsList));
        wl_month.setViewAdapter(new ArrayWheelAdapter<>(mContext, monthsList));
        dayWheelAdapter = new ArrayWheelAdapter<>(mContext, daysList);
        wl_day.setViewAdapter(dayWheelAdapter);


        selectYear = String.valueOf(calendar.get(Calendar.YEAR));
        selectMonth = calendar.get(Calendar.MONTH) + 1 < 10 ? "0" + (calendar.get(Calendar.MONTH) + 1) : String.valueOf(calendar.get(Calendar.MONTH) + 1);
        selectDay = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        calculateDate();

        wl_year.setCurrentItem(findItemIndex(yearsList, calendar.get(Calendar.YEAR)));
        wl_month.setCurrentItem(findItemIndex(monthsList, calendar.get(Calendar.MONTH) + 1));
        wl_day.setCurrentItem(findItemIndex(daysList, calendar.get(Calendar.DAY_OF_MONTH)));

    }

    @Override
    public void onItemSeclet(WheelView wheel, int index) {
        int tag=(int)wheel.getTag();
        switch (tag) {
            case 0:
                selectYear = yearsList.get(index);
                calculateDate();
                break;

            case 1:
                selectMonth = monthsList.get(index);
                calculateDate();
                break;

            case 2:
                selectDay = daysList.get(index);
                break;
        }
    }

    public interface OnDatePickListener {
        void onSelect(String year, String month, String day);
    }

    public DatePicker setOnDatePickListener(OnDatePickListener onDatePickListener) {
        this.mDatePickListener = onDatePickListener;
        return this;
    }

    //折半查找有序元素的索引
    private int findItemIndex(ArrayList<String> items, int item) {
        int index = Collections.binarySearch(items, item, new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                String lhsStr = lhs.toString();
                String rhsStr = rhs.toString();

                return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
            }
        });

        return index;
    }



    private void calculateDate() {
        int month = Integer.parseInt(selectMonth);
        if (month == 1 ||month == 3 ||month == 5 ||month == 7 ||month == 8 || month ==10 ||month == 12) {
            if (!daysList.contains("29")) {
                daysList.add("29");
            }
            if (!daysList.contains("30")) {
                daysList.add("30");
            }
            if (!daysList.contains("31")) {
                daysList.add("31");
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (!daysList.contains("29")) {
                daysList.add("29");
            }
            if (!daysList.contains("30")) {
                daysList.add("30");
            }
            if (daysList.contains("31")) {
                daysList.remove("31");
            }
            if (selectDay.equals("31")) {
                wl_day.setCurrentItem(29);
            }

        }

        if (month == 2) {

            if (daysList.contains("31")) {
                daysList.remove("31");
            }
            if (daysList.contains("30")) {
                daysList.remove("30");
            }
            if (!daysList.contains("29")) {
                daysList.add("29");

            }

            // 是否PING年
            if ((Integer.parseInt(selectYear) % 4 == 0 && (Integer.parseInt(selectYear) % 100 != 0) || (Integer.parseInt(selectYear) % 400 == 0))) {

                //上月是结尾
                if (selectDay.equals("30" )|| selectDay.equals("31")) {
                    wl_day.setCurrentItem(28);
                }

            } else {
                if (daysList.contains("29")) {
                    daysList.remove("29");
                }

                if (selectDay.equals("30" )|| selectDay.equals("31")||selectDay.equals("29")) {
                    wl_day.setCurrentItem(27);
                }
            }
        }
        //刷新日期
        dayWheelAdapter.notifyDataSetChanged();


    }

    public DatePicker setSelect(String date) {

        if ("".equals(date)) {
            return this;
        }

        String[] split = date.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];


        int yearIndex = yearsList.indexOf(year);
        int monthIndex = monthsList.indexOf(month);
        int dayIndex = daysList.indexOf(day);

        wl_year.setCurrentItem(yearIndex == -1 ? 0 : yearIndex);
        wl_month.setCurrentItem(monthIndex == -1 ? 0 : monthIndex);
        wl_day.setCurrentItem(dayIndex == -1 ? 0 : dayIndex);
        return this;
    }


    /**
     * 设置
     * @param beganYear
     * @param endYear
     */
    public void setYearInterval(int beganYear,int endYear) {
        this.beganYear = beganYear;
        this.endYear = endYear;
        initData();
    }
}
