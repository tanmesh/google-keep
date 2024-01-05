import React from 'react'
import { View, StyleSheet } from 'react-native'
import NoteItem from './NoteItem'

function Notes({ notes }) {
    if (!notes || !Array.isArray(notes)) {
        return null;
    }

    return (
        <View style={styles.note} >
            {notes.map(note => (
                <NoteItem id={note.noteId} title={note.title} content={note.content} />
            ))}
        </View>
    )
}

const styles = StyleSheet.create({
    note: { width: '80%', justifyContent: 'center', marginTop: '10%', gap: '5%' }
});


export default Notes
