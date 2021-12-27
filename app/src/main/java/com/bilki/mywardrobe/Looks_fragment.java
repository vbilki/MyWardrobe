package com.bilki.mywardrobe;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Looks_fragment extends Fragment {

    private Context context;
    private View view;
    private RecyclerView looksRecycler;
    private LooksAdapter adapter;
    private ArrayList<Upload> uploads;
    public String user_email;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private CollectionReference collectionReference, collectionReferenceImages;
    private DocumentSnapshot document;
    private final static String TAG = "bilki: Looks ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_looks_fragment, container, false);



        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("users/");
        documentReference = collectionReference.document(FirebaseAuth.getInstance().getCurrentUser().getEmail() + "/");

//        collectionReferenceImages = collectionReference.document(user_email + "/").collection("images");
//        collectionReference = firebaseFirestore.collection("images/");

        context = container.getContext();
        looksRecycler = (RecyclerView) view.findViewById(R.id.looks_recycler);


//        getUserEmail();
        looksRecycler();

        return view;

    }

    public void getUserEmail() {

        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    document = task.getResult();

                    if (document.exists()) {

                        user_email = document.getString("email");
                        Log.d(TAG, "Email taken " + user_email);

                    } else {

                        Log.d(TAG, "No such document");

                    }

                } else {

                    Log.d(TAG, "Get failed with ", task.getException());

                }

            }
        });

    }

    private void looksRecycler() {

//        Query query = collectionReference.orderBy("name", Query.Direction.DESCENDING);
        Query query = documentReference.collection("images/").orderBy("name", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Upload> options = new FirestoreRecyclerOptions.Builder<Upload>()
                .setQuery(query, Upload.class)
                .build();

        adapter = new LooksAdapter(context, options);
//        adapter.setHasStableIds(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        looksRecycler.setLayoutManager(gridLayoutManager);
        looksRecycler.setHasFixedSize(false);
        looksRecycler.setAdapter(adapter);
        Log.d(TAG, "looksRecycler: Adapter set");
//        loadrecyclerViewData();

//        uploads = new ArrayList<>();
//        adapter = new LooksAdapter(getContext(), uploads);
//        looksRecycler.setAdapter(adapter);
//        loadrecyclerViewData();

//        ArrayList<LooksHelperClass> looksLocations = new ArrayList<>();
//
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//        looksLocations.add(new LooksHelperClass(R.drawable.hanger));
//
//        adapter = new LooksAdapter(looksLocations);
//        looksRecycler.setAdapter(adapter);

    }

    private void loadrecyclerViewData() {


        String uploadId = firebaseFirestore.collection("images").document().getId();
        DocumentReference documentReference = firebaseFirestore.collection("images/").document(uploadId + "/");
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}