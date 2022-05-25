package android.example.house_assist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private static final String TAG = "OrderFrag";
    Context context;
    RecyclerView rvRequests, rvOrders;
    ArrayList<ServiceProvider> serviceRequestsArrayList;
    ArrayList<ServiceProvider> ordersArrayList;
    ServiceRequestsAdapter adapter;
    OrdersAdapter adapter2;
    FirebaseFirestore db;
    FirebaseUser user;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        context = view.getContext();
        rvRequests = view.findViewById(R.id.rv_service_requests);
        rvRequests.setHasFixedSize(true);
        rvRequests.setLayoutManager(new LinearLayoutManager(context));

        rvOrders = view.findViewById(R.id.rv_orders);
        rvOrders.setHasFixedSize(true);
        rvOrders.setLayoutManager(new LinearLayoutManager(context));

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();
        serviceRequestsArrayList = new ArrayList<ServiceProvider>();
        ordersArrayList = new ArrayList<ServiceProvider>();
        adapter = new ServiceRequestsAdapter(context ,serviceRequestsArrayList);
        adapter2 = new OrdersAdapter(context ,ordersArrayList);
        rvRequests.setAdapter(adapter);
        rvOrders.setAdapter(adapter2);

        EventChangeListener();
        getOrders();
        Log.d(TAG, "ordersArrayList: "+ordersArrayList);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void EventChangeListener() {

        db.collection("ServiceRequests").whereEqualTo("receiver", uid).addSnapshotListener((value, error) -> {
            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    serviceRequestsArrayList.add(dc.getDocument().toObject(ServiceProvider.class));
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getOrders() {

        db.collection("Orders").addSnapshotListener((value, error) -> {
            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    ordersArrayList.add(dc.getDocument().toObject(ServiceProvider.class));
                }
                adapter2.notifyDataSetChanged();
            }
        });

    }

}