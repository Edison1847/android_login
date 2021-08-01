package com.internal.test_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.icu.text.IDNA;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button logout;
    Button addButton;
    EditText addText;
    private ListView listView;
    private Uri imageUri;
    private static final String TAG = "ERROR :  ";
    private static final int IMAGE_REQUEST = 2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addText = findViewById(R.id.addText);
        addButton = findViewById(R.id.addButton);
        logout = findViewById(R.id.logout);
        listView = findViewById(R.id.listView);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logging Out !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ADD TO REALTIME FIREBASE DATABASE
//                String text = addText.getText().toString();
//                if(text.isEmpty()){
//                    Toast.makeText(MainActivity.this, "Text Field is Empty", Toast.LENGTH_SHORT).show();
//                }else {
//                    FirebaseDatabase.getInstance().getReference().child("Test Database - V1").child("Names").child("Name").setValue(text);
//                    Toast.makeText(MainActivity.this, "Text Added Successfully", Toast.LENGTH_SHORT).show();
//                }
                
                openImage();
            }
        });

        ArrayList<String> list = new ArrayList<>();

        //        show in a list
        ArrayAdapter adaptor = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listView.setAdapter(adaptor);




        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Test Database - V1").child("Information");
        System.out.println("SYSTEM REFERENCES :   "+reference);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshotDoc : snapshot.getChildren()){
                    Information info = snapshotDoc.getValue(Information.class);
                    System.out.println("INFORMATION : "+info);
                    String txt = info.getUnit()+" : "+info.getEmail();
                    System.out.println(txt);
                    list.add(txt);
                }
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("n1", "Dilshan");
//        map.put("n2", "Nimal");
//        map.put("n3", "Hasitha");
//        map.put("n4", "Suren");
////
//        HashMap<String, Object> mapColors = new HashMap<>();
//        mapColors.put("c1", "Red");
//        mapColors.put("c2", "Orange");
//        mapColors.put("c3", "Blue");
//        mapColors.put("c4", "Green");

//        HashMap<String, Object> mapInformation = new HashMap<>();
//        mapInformation.put("email", "re@gmail.com");
//        mapInformation.put("unit", "MD I");
//
//        HashMap<String, Object> mapInformation1 = new HashMap<>();
//        mapInformation1.put("email", "green@gmail.com");
//        mapInformation1.put("unit", "MD II");
//
//        HashMap<String, Object> mapInformation2 = new HashMap<>();
//        mapInformation2.put("email", "orange@gmail.com");
//        mapInformation2.put("unit", "MD III");

//
//        FirebaseDatabase.getInstance().getReference().child("Test Database - V1").child("Names").updateChildren(map);
//        FirebaseDatabase.getInstance().getReference().child("Test Database - V1").child("Colors").updateChildren(mapColors);
//        FirebaseDatabase.getInstance().getReference().child("Test Database - V1").child("Information").child("branch1").updateChildren(mapInformation);
//        FirebaseDatabase.getInstance().getReference().child("Test Database - V1").child("Information").child("branch2").updateChildren(mapInformation1);
//        FirebaseDatabase.getInstance().getReference().child("Test Database - V1").child("Information").child("branch3").updateChildren(mapInformation2);
//


//        FIRESTORE
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        Map<String, Object> city = new HashMap<>();
//        city.put("name","Colombo");
//        city.put("province", "western");
//        city.put("Country", "Srilanka");
//
//        db.collection("cities").document("SJSR").set(city).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "Value Added", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        INSERTING TO FIRESOTRE DB
//        Map<String, Object> data = new HashMap<>();
//        data.put("Capital", false);
//
//        db.collection("cities").document("SJSR").set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "Value Added", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        ADD VALUES IN A NEW DOCUMENT WHERE THE DOCUEMT IS AUTO FORMED WITH A UNIQUE ID
//        Map<String, Object> data = new HashMap<>();
//        data.put("Name", "Japan");
//        data.put("Capital", "Tokyo");
//        db.collection("cities").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "Value Added", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        UPDATE / MODIFY A VALUE STORED IN A DOCUMENT
//        DocumentReference  ref = FirebaseFirestore.getInstance().collection("cities").document("SJSR");
//        ref.update("Capital", true);


//        GET A DOCUMT FROM IFRESTOR TO THE APP
//        DocumentReference  docRef = FirebaseFirestore.getInstance().collection("cities").document("Col");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot doc = task.getResult();
//                    if(doc.exists()){
////                        Log.d("Document", doc.getData().toString());
//                        Toast.makeText(MainActivity.this, doc.getData().toString(), Toast.LENGTH_SHORT).show();
//                    }else{
////                        Log.d("Document", "No Data");
//                        Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//        });

//        GET DOCUMENT WHICH HAS THE GIVEN VALUE - DEBUG CODE
//        Log.d(TAG, " --------------------------------------");
//        Log.d(TAG, " --------------------------------------");
//        Log.d(TAG, " --------------------------------------");
//        Log.d(TAG, " --------------------------------------");
//
//        CollectionReference buyersReference = db.collection("cities");
//        System.out.println("CITY REFERENCES :   "+buyersReference);
//        Log.d(TAG, " -----------------------------j---------");
//        Log.d(TAG, " ----------------------------------g----");
//        Log.d(TAG, " -------------------------h-------------");
//        Log.d(TAG, " --------------h------------------------");
//        Query usersDataQuery = buyersReference.whereEqualTo("Capital", true);
//        System.out.println("CITY with Capitals :   "+usersDataQuery);
//        Log.d(TAG, " --------------------------------------");
//        Log.d(TAG, " --------------------------------------xxx");
//        Log.d(TAG, " --------------------------------------000");
//        Log.d(TAG, " --------------------------------------__");
//
//        usersDataQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    System.out.println("TASK COMPLETE :   "+task.isSuccessful());
//                    Log.d(TAG, " --------------------------------------");
//                    Log.d(TAG, " --------------------------------------xxx");
//                    Log.d(TAG, " --------------------------------------000");
//                    Log.d(TAG, " --------------------------------------__");
//                    System.out.println("NO OF DOCUMENTS :   "+task.getResult().isEmpty());
//                    Log.d(TAG, " --------------------------------------");
//                    Log.d(TAG, " --------------------------------------xxx");
//                    Log.d(TAG, " --------------------------------------000");
//                    Log.d(TAG, " --------------------------------------__");
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        String Country = document.getString("Country");
//                        Log.d(TAG, Country);
//                        Log.d(TAG, Country);
//                        Log.d(TAG, Country);
//                        Log.d(TAG, Country);
//                        System.out.println("COUNTRY :   "+Country);
//                        Log.d(TAG, " --------------------------------------");
//                        Log.d(TAG, " --------------------------------------xxx");
//                        Log.d(TAG, " --------------------------------------000");
//                        Log.d(TAG, " --------------------------------------__");
//
//                    }
//                }
//            }
//        });



//        GET DOCUMENT WHICH HAS THE GIVEN VALUE - PROPER CODE
//        FirebaseFirestore.getInstance().collection("cities").whereEqualTo("Capital", true)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if(task.isSuccessful()){
//
//                    for(QueryDocumentSnapshot doc : task.getResult()){
//                        System.out.println(doc.getId()+" -> "+doc.getData());
//                        Log.d(TAG, doc.getId()+" -> "+doc.getData());
//                        Toast.makeText(MainActivity.this, doc.getId()+" -> "+doc.getData(), Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(MainActivity.this, "NOT SUCCESSFUL", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        ADD A REALTIME LISTENER TO A DOCUMENT
        FirebaseFirestore.getInstance().collection("cities").document("Col").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            uploadImage();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if(imageUri != null){
            StorageReference fileRefrence = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            fileRefrence.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Log.d(TAG, url);
                            Log.d(TAG, " --------------------------------------");
                            Log.d(TAG, " --------------------------------------xxx");
                            Log.d(TAG, " --------------------------------------000");
                            Log.d(TAG, " --------------------------------------__");
                            pd.dismiss();
                            Toast.makeText(MainActivity.this, "Image upload successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}