package com.gazetinc.shopninja.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gazetinc.shopninja.Adapter.SingleAdapter;
import com.gazetinc.shopninja.Model.WalletModel;
import com.gazetinc.shopninja.R;
import com.gazetinc.shopninja.Utility.ApiConsumer;
import com.gazetinc.shopninja.Utility.ApiResponse;
import com.gazetinc.shopninja.Utility.AppPref;
import com.gazetinc.shopninja.Utility.AppUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WalletTransactionFragment extends BaseFragment implements ApiResponse,SingleAdapter.ReturnView
{
    private ListView listview;

    private ArrayList<WalletModel> walletModelArrayList=new ArrayList();

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_wallet_transaction;
    }

    @Override
    public void initializeView(View view) {
        getActivity().setTitle("Wallet Transactions");
        listview=(ListView) view.findViewById(R.id.listview);

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("user_id=").append(AppPref.getInstance().getUSERID());

        String content=stringBuilder.toString();

        ApiConsumer apiConsumer = new ApiConsumer(getActivity(), AppUrl.WALLET_TRANSACTIONS, 0, content, true, "loading ...",this);
        apiConsumer.execute();
    }

    @Override
    public void getApiResponse(String responseData, int serviceCounter) {
        try
        {
            Log.d("responsedata",responseData);
            JSONArray jsonArray=new JSONArray(responseData);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String user_id=jsonObject.getString("user_id");
                String amount=jsonObject.getString("amount");
                String txn_id=jsonObject.getString("txn_id");
                String date=jsonObject.getString("date");

                walletModelArrayList.add(new WalletModel(id,txn_id,user_id,amount,date));
            }

            listview.setAdapter(new SingleAdapter(getActivity(),R.layout.layout_wallet_transaction_singleitem,walletModelArrayList,this,0));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void getAdapterView(View view, List objects, int position, int from) {
            TextView txn_id=(TextView) view.findViewById(R.id.txn_id);
            TextView price=(TextView) view.findViewById(R.id.price);
            TextView date=(TextView) view.findViewById(R.id.date);

            WalletModel walletModel=walletModelArrayList.get(position);

            txn_id.setText("Txn Id : "+walletModel.getOrder_id());
            price.setText("Rs : "+walletModel.getAmount());
            date.setText(walletModel.getDate());
    }
}
