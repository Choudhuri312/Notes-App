package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbClass data;
    ArrayList<ModelClass> fullList;     // All notes
    ArrayList<ModelClass> currentList;  // Current displayed (search or full)
    NotesAdapter adapter;
    RecyclerView recView;
    FloatingActionButton btnFab;
    Menu menu;
    boolean isSelectionActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        data = new DbClass(this);
        recView = findViewById(R.id.recyclerV);
        btnFab = findViewById(R.id.btnFab);

        recView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        btnFab.setOnClickListener(v -> startActivity(new Intent(this, InsertNote.class)));
    }
    @Override
    protected void onResume(){
        super.onResume();
        // CALL loadData() here to refresh the list whenever the activity comes to the foreground.
        loadData();
    }
    public void loadData(){
        fullList = data.getAllData();
        currentList = new ArrayList<>(fullList);
        adapter = new NotesAdapter(this, currentList);
        recView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.selectall_delete_menu, menu);
        // Find the Search Item
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search notes...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return false;
            }
        });
        // Hide icons initially
        menu.findItem(R.id.action_del).setVisible(false);
        menu.findItem(R.id.action_secAll).setVisible(false);
        return true;
    }
    private void performSearch(String query) {
        if(query.trim().isEmpty()){
            currentList = new ArrayList<>(fullList);
        }else{
            // Fetch filtered data from DB
            currentList = data.searchData(query);
        }
        // Update the adapter with the new list
        adapter = new NotesAdapter(this, currentList);
        recView.setAdapter(adapter);
    }
    public void enterSelectionMode() {
        isSelectionActive = true;
        if (menu != null) {
            menu.findItem(R.id.action_del).setVisible(true);
            menu.findItem(R.id.action_secAll).setVisible(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void exitSelectionMode(){
        isSelectionActive = false;
        if(menu != null){
            menu.findItem(R.id.action_del).setVisible(false);
            menu.findItem(R.id.action_secAll).setVisible(false);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        adapter.clearSelection();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle the Home/Back button
        if (item.getItemId() == android.R.id.home) {
            if (isSelectionActive) {
                exitSelectionMode();
                return true;
            }
        }else if (item.getItemId() == R.id.action_secAll) {
            adapter.selectAll(true);
            return true;
        } else if (item.getItemId() == R.id.action_del) {
            deleteSelectedNotes();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteSelectedNotes() {
        ArrayList<ModelClass> selected = adapter.getSelectedItems();
        if(selected.isEmpty()){
            Toast.makeText(this, "No notes selected", Toast.LENGTH_SHORT).show();
            return;
        }
        for (ModelClass m : selected) {
            if (m.isSelected()) {
                data.deleteData(m.getId()); // Delete from DB
            }
        }
        loadData();// Fully reload to reflect changes
        exitSelectionMode();
        Toast.makeText(this, "Note's Deleted", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        if(isSelectionActive){
           exitSelectionMode();
        }else {
            super.onBackPressed();
        }
    }
}