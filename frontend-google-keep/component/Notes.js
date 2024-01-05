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
                <NoteItem title={note.title} content={note.content} />
            ))}
        </View>
    )
}

const styles = StyleSheet.create({
    note: { width: '90%', justifyContent: 'center', marginTop: '10%' }
});


export default Notes
