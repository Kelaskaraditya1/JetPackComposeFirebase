package com.starkindustries.jetpackcomposefirebase.Backend.RealTime

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import com.google.firebase.firestore.FirebaseFirestore
import com.starkindustries.jetpackcomposefirebase.Backend.Data.User

class RealTimeDatabase {

    companion object {

        fun insertNote(user: FirebaseUser, note: NotesRow) {
            val database = FirebaseDatabase.getInstance().reference
            val userId = user.uid
            val noteId = database.child(Keys.NOTES).child(userId).push().key
            note.noteId = noteId

            noteId?.let {
                database.child(Keys.NOTES)
                    .child(userId)
                    .child(noteId)
                    .setValue(note)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("Insert", "Object inserted Successfully!!")
                        }
                    }
                    .addOnFailureListener { error ->
                        Log.d("Insert", "Error: ${error.message}")
                    }
            }
        }

        fun getNotes(user: FirebaseUser, onNotesFetched: (List<NotesRow>) -> Unit) {
            val database = FirebaseDatabase.getInstance().reference
            val userId = user.uid

            database.child(Keys.NOTES)
                .child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val notes = mutableListOf<NotesRow>()
                        for (noteSnap in snapshot.children) {
                            val note = noteSnap.getValue(NotesRow::class.java)
                            note?.let {
                                notes.add(it)
                            }
                        }
                        Log.d("ALL_NOTES", "Fetched notes: $notes")
                        onNotesFetched(notes) // Return fetched notes
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("FirebaseError", "Failed to fetch notes", error.toException())
                    }
                })
        }

        fun deleteNote(noteId:String,user: FirebaseUser){
            user?.let {
                var database = FirebaseDatabase.getInstance().reference
                var userId = user.uid
                var reference = database
                    .child(Keys.NOTES)
                    .child(userId)
                    .child(noteId)
                    .removeValue()
                    .addOnSuccessListener {
                        Log.d("REMOVED_SUCCESSFULLY","Note removed successfully")
                    }
                    .addOnFailureListener{exception->
                        Log.d("Failure",""+exception.message.toString())

                    }
            }

        }

        fun updateNote(user:FirebaseUser,note:NotesRow,noteId:String){
            user?.let {
                var databasae = FirebaseDatabase.getInstance().reference
                var userId = user.uid
                var reference = databasae
                    .child(Keys.NOTES)
                    .child(userId)
                    .child(noteId)
                    .setValue(note)
                    .addOnSuccessListener {
                        Log.d("UPDATED_SUCCESSFULLY","Note updated successfully")
                    }
                    .addOnFailureListener{
                        Log.d("Failure",""+it.message.toString())
                    }
            }
        }



        // Function to fetch user data by UID
        fun fetchUserByUid(uid: String, onResult: (User?) -> Unit) {
            val firestore = FirebaseFirestore.getInstance()

            // Access the document with the specified UID in the "users" collection
            firestore.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Map Firestore document fields to the User data class
                        val user = User(
                            name = document.getString("name"),
                            userName = document.getString("userName"),
                            email = document.getString("email"),
                            password = document.getString("password"),
                            profileImageUri = document.getString("profileImageUri")
                        )
                        onResult(user) // Return the User object
                    } else {
                        Log.d("FetchUserByUid", "No user found with UID: $uid")
                        onResult(null) // No document found
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("FetchUserByUid", "Error fetching user: ${exception.message}")
                    onResult(null) // Handle error case
                }
        }


    }
}
