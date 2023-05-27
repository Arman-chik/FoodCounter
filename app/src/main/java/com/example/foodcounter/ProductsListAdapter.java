package com.example.foodcounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<Products> arrayMyProducts;
    public ProductsListAdapter(Context ctx) {
        mLayoutInflater = LayoutInflater.from(ctx);
        arrayMyProducts = new ArrayList<>();
    }

    public List<Products> getArrayMyData() {
        return arrayMyProducts;
    }

    public void setArrayMyData(List<Products> arrayMyData) {
        this.arrayMyProducts = arrayMyData;
        notifyDataSetChanged();
    }

    public int getCount () {
        return arrayMyProducts.size();
    }

    public Object getItem (int position) {

        return arrayMyProducts.get(position);
    }

    @Override
    public long getItemId(int i) {
        return arrayMyProducts.get(i).id;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.product_item, null);

        TextView productNameTV = convertView.findViewById(R.id.product_name);
        TextView productBelkiTV = convertView.findViewById(R.id.product_belki);
        TextView productZhiryTV = convertView.findViewById(R.id.product_zhiry);
        TextView productCaloriesTV = convertView.findViewById(R.id.product_calories);
        TextView productUglTV = convertView.findViewById(R.id.product_ugl);

        Products p = arrayMyProducts.get(i);
        productNameTV.setText(p.name);
        productBelkiTV.setText(""+p.belki);
        productZhiryTV.setText(""+p.zhiri);
        productCaloriesTV.setText(""+ p.calories);
        productUglTV.setText(""+p.uglevod);


        return convertView;
    }


}
