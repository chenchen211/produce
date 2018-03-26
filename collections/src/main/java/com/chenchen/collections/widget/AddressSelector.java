package com.chenchen.collections.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chenchen.collections.R;
import com.chenchen.collections.utils.Constants;
import com.chenchen.collections.utils.Data4Address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址选择器
 */
public class AddressSelector extends PopupWindow implements View.OnClickListener,AdapterView.OnItemClickListener{
    private static final String TAG = "AddressSelector";
    private Context context;
    private List<String> datas;
    private TextView textView;
    private ListView lv;
    private int type;
    private Map<String,String> selected = new HashMap<>();
    private ArrayAdapter<String> adapter;
    private Data4Address data4Address;
    private OnFinishedListener listener;
    private String province;
    private String city;
    private String county;

    private AddressSelector(@NonNull Context context, @NonNull View view, OnFinishedListener listener){
        super(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        this.context = context;
        this.listener = listener;
        if(listener == null){
            throw new IllegalArgumentException("listener con`t be null");
        }
        textView = view.findViewById(R.id.tv_address_choose_title);
        lv = view.findViewById(R.id.lv_address_choose);
        lv.setOnItemClickListener(this);
        lv.requestFocus();
        view.findViewById(R.id.btn_address_choose).setOnClickListener(this);
        data4Address = Data4Address.getInstance(context);
        init();
    }

    public AddressSelector(@NonNull Context context,OnFinishedListener listener){
        this(context,LayoutInflater.from(context).inflate(R.layout.address_choose, null),listener);
    }
    private void init(){
        province = null;
        city = null;
        county = null;
        show(Constants.INTENT_EXTRA_TYPE_PROVINCE,data4Address.getProvince());
    }
    /**
     * 显示不同的数据
     * @param type 数据类型
     * @param datas 数据源
     */
    public void show(int type,List<String> datas){
        this.datas = datas;
        this.type = type;
        switch (type){
            case  Constants.INTENT_EXTRA_TYPE_PROVINCE:
                textView.setText("请选择省份");
                break;
            case Constants.INTENT_EXTRA_TYPE_CITY:
                textView.setText("请选择城市");
                break;
            case Constants.INTENT_EXTRA_TYPE_COUNTY:
                textView.setText("请选择县区");
                break;
        }
        adapter = new ArrayAdapter<>(context,R.layout.item_citys,datas);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_address_choose && isShowing()) {
            dismiss();
            init();
            listener.cancel();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(datas!=null){
            switch (type){
                case Constants.INTENT_EXTRA_TYPE_PROVINCE:
                    province = datas.get(position);
                    selected.put("province",province);
                    show(Constants.INTENT_EXTRA_TYPE_CITY,data4Address.getCityByProvice(province));
                    break;
                case Constants.INTENT_EXTRA_TYPE_CITY:
                    city = datas.get(position);
                    selected.put("city",city);
                    show(Constants.INTENT_EXTRA_TYPE_COUNTY,data4Address.getCountyByCity(city));
                    break;
                case Constants.INTENT_EXTRA_TYPE_COUNTY:
                    county = datas.get(position);
                    selected.put("county",county);
                    dismiss();
                    init();
                    listener.onFinish(province,city,county);
                    break;
            }
        }
    }

    public interface OnFinishedListener{
        void onFinish(String province,String city,String county);
        void cancel();
    }
}
