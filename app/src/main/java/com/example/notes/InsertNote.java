package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InsertNote extends AppCompatActivity {
    EditText addTitle, addDesc;
    DbClass db;
    int noteId = -1; // default no id (insert mode)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.insert), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addTitle = findViewById(R.id.addTitle);
        addDesc = findViewById(R.id.addDesc);
        db = new DbClass(this);

        MaterialToolbar toolbar = findViewById(R.id.insert_topAppBar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // ---------- CHECK IF UPDATE MODE ----------
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {

            noteId = intent.getIntExtra("id", -1);
            String title = intent.getStringExtra("title");
            String desc = intent.getStringExtra("desc");

            // Fill existing data
            addTitle.setText(title);
            addDesc.setText(desc);

            getSupportActionBar().setTitle("View/Update Note");

        }
    }
    // 1. Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.insert_menu, menu);
        return true;
    }
    // 2. Handle the click on the toolbar icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        // Check if the clicked item is the back arrow
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        if(item.getItemId() == R.id.action_save){
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveNote(){
        String title = addTitle.getText().toString().trim();
        String desc = addDesc.getText().toString().trim();
        if(title.isEmpty() && desc.isEmpty()) {
            Toast.makeText(this, "Enter title or description", Toast.LENGTH_SHORT).show();
            return;
        }
        // ---------------- INSERT MODE -----------------
        boolean success;
        if (noteId == -1) {
            success = db.insertData(title, desc);

            Toast.makeText(this, success ? "Note Added" : "Failed to Add Note", Toast.LENGTH_SHORT).show();
                finish(); // return to main activity
        } else {
            // ---------------- UPDATE MODE -----------------
            success = db.updateData(noteId, title, desc);

            Toast.makeText(this, success ? "Note Updated" : "Failed to Update Note", Toast.LENGTH_SHORT).show();
                finish();

        }
    }
}