package com.example.silverstore_app.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silverstore_app.ProductActivity;
import com.example.silverstore_app.R;
import com.example.silverstore_app.StoreActivity;
import com.example.silverstore_app.adapter.ItemProductAdapter;
import com.example.silverstore_app.api.ApiService;
import com.example.silverstore_app.api.IClickItemProductListener;
import com.example.silverstore_app.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private View view;
    private ImageView imgSearch;
    private EditText txtSearch;
    private List<Product> listProduct;
    private ArrayList<String> listSpnCate, listSpnPrice, listSpnAtoZ;
    private Spinner spnCate, spnPrice, spnAtoZ;
    private RecyclerView rclProduct;
    private ItemProductAdapter itemProductAdapter;
    private SearchView searchView;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        reference();
        setHasOptionsMenu(true);
        getAllProduct();
        setAdapterSpinner();

        //event click spinner category
        spnCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    getAllProduct();
                    loadRecycleView(listProduct, itemProductAdapter);
                }else if(i == 1){
                    setItemCate(1,listProduct);
                } else if(i == 2){
                    setItemCate(2,listProduct);
                } else if(i == 3){
                    setItemCate(3,listProduct);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                loadRecycleView(listProduct, itemProductAdapter);
            }
        });

        //event click spinner price
        spnPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    sortAscending(listProduct);
                    loadRecycleView(listProduct, itemProductAdapter);
                } else if(i == 1){
                    sortDecreasing(listProduct);
                    loadRecycleView(listProduct, itemProductAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                loadRecycleView(listProduct, itemProductAdapter);
            }
        });

        //event search
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return view;

    }

    private void reference(){

        listProduct = new ArrayList<Product>();

        itemProductAdapter = new ItemProductAdapter(this, new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product item) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ProductActivity.class);
                intent.putExtra("product",item);
                startActivity(intent);
            }
        });

        listSpnCate = new ArrayList<>();
        listSpnCate.add("All");
        listSpnCate.add("Ring");
        listSpnCate.add("Chain");
        listSpnCate.add("Earring");

        listSpnPrice = new ArrayList<>();
        listSpnPrice.add("Ascending");
        listSpnPrice.add("Decreasing");

        listSpnAtoZ = new ArrayList<>();
        listSpnAtoZ.add("A to Z");
        listSpnAtoZ.add("Z to A");

        spnAtoZ = view.findViewById(R.id.spnAtoZ);
        spnCate = view.findViewById(R.id.spnCategory);
        spnPrice = view.findViewById(R.id.spnPrice);

        rclProduct = view.findViewById(R.id.rclProduct);

        //listTest = new ArrayList<>();

        imgSearch = view.findViewById(R.id.imgSearch);
        txtSearch = view.findViewById(R.id.txtSearch);
    }

    //set item for spinner
    private void setAdapterSpinner(){
        ArrayAdapter adapterCate = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, listSpnCate);
        ArrayAdapter adapterPrice = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, listSpnPrice);
        ArrayAdapter adapterAtoZ = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, listSpnAtoZ);

        spnCate.setAdapter(adapterCate);
        spnPrice.setAdapter(adapterPrice);
        spnAtoZ.setAdapter(adapterAtoZ);
    }

    //get all products from server
    private void getAllProduct(){
        ApiService.apiService.callApiProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Product> products) {
                        listProduct = products;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        loadRecycleView(listProduct, itemProductAdapter);
                    }
                });
    }


    //set item product from product list

    //set item list for spinner category
    private void setItemCate(int categoryID, List<Product> list){
        List<Product> listTest = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getCategoryID() == categoryID){
                listTest.add(list.get(i));
            }
        }
        loadRecycleView(listTest, itemProductAdapter);
    }

    private void sortAscending(List<Product> itemList){
        Collections.sort(itemList);
    }

    private void sortDecreasing(List<Product> itemList){
        Collections.sort(itemList);
        Collections.reverse(itemList);
    }


    @Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull Menu menu, @androidx.annotation.NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.itemSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemProductAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemProductAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    //load data into recycle view
    private void loadRecycleView(List<Product> itemList, ItemProductAdapter itemAdapter){
        FragmentActivity userFragment = getActivity();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(userFragment, 2);
        rclProduct.setLayoutManager(gridLayoutManager);

        itemProductAdapter.setData(itemList);
        rclProduct.setAdapter(itemAdapter);
    }

}