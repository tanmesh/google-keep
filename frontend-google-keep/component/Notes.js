import React, { useState } from 'react'
import { View, StyleSheet, TouchableOpacity, ScrollView } from 'react-native'
import NoteItem from './NoteItem'
import Ionicons from 'react-native-vector-icons/Ionicons';
import AsyncStorage from '@react-native-async-storage/async-storage';

function Notes({ notes, setNotes }) {
    const [accessToken, setAccessToken] = useState('');

    if (!notes || !Array.isArray(notes)) {
        return null;
    }

    const getAccessToken = async () => {
        try {
            const accessToken = await AsyncStorage.getItem('accessToken');
            if (accessToken !== null) {
                setAccessToken(accessToken);
            } else {
                navigation.navigate('Login');
                console.log('Access token not found');
            }
        } catch (error) {
            console.error('Error retrieving access token:', error);
        }
    };

    const handleDeleteItem = async (indexToDelete) => {
        await getAccessToken();

        console.log('accessToken:', accessToken)
        console.log('indexToDelete:', indexToDelete)
        await fetch('http://localhost:8080/note/delete', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'x-access-token': accessToken,
            },
            body: JSON.stringify({
                noteId: indexToDelete,
            })
        })

        const updatedList = notes.filter((note) => note.noteId !== indexToDelete);
        console.log('updatedList:', updatedList)
        setNotes(updatedList);
    }

    return (
        <ScrollView style={styles.note} >
            {notes.map((note) => (
                <View style={{ flexDirection: 'row' }}>
                    <View style={{ width: '90%' }}>
                        <NoteItem id={note.noteId} title={note.title} content={note.content} setNotes={setNotes} />
                    </View>
                    <TouchableOpacity onPress={() => handleDeleteItem(note.noteId)} style={{ justifyContent: 'center', alignContent: 'center', width: '10%' }}>
                        <Ionicons name="trash-bin" size={20} color="red" />
                    </TouchableOpacity>
                </View>
            ))}
        </ScrollView>
    )
}

const styles = StyleSheet.create({
    note: { marginTop: '5%', marginHorizontal: '10%', gap: '5%' }
});


export default Notes
