import React, { useState } from 'react'
import { View, StyleSheet, TouchableOpacity, FlatList, Text } from 'react-native'
import NoteItem from './NoteItem'
import Ionicons from 'react-native-vector-icons/Ionicons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { showMessage } from "react-native-flash-message";

function Notes({ notes, setNotes, refreshing, onRefresh, navigation }) {
    const handleDeleteItem = async (indexToDelete) => {
        try {
            const accessToken = await AsyncStorage.getItem('accessToken');

            if (accessToken !== null) {
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

                onRefresh();
            } else {
                navigation.navigate('Login');
                console.log('Access token not found');
            }
        } catch (error) {
            // Error 
            console.log('Error retrieving access token:', error);
            showMessage({
                message: error.response.data.message,
                type: "danger",
            });
        }
    }

    if (notes === null || notes.length === 0) {
        notes = [{}]
        return (
            <FlatList
                style={styles.note}
                data={notes}
                renderItem={() => {
                    return (
                        <View style={{
                            flexDirection: 'column',
                            justifyContent: 'center',
                            alignItems: 'center',
                            marginTop: '50%',
                            backgroundColor: 'white',
                            padding: '20%',
                            borderRadius: 10,
                        }}>
                            <Text style={{ fontSize: 20 }}>No notes yet!!</Text>
                        </View>
                    )
                }}
                refreshing={refreshing}
                onRefresh={onRefresh} />
        )
    }

    return (
        <FlatList
            style={styles.note}
            data={notes}
            keyExtractor={(note) => note.noteId.toString()}
            renderItem={(note) => {
                return (
                    <View style={{ flexDirection: 'row' }}>
                        <View style={{ width: '90%' }}>
                            <NoteItem id={note.item.noteId} title={note.item.title} content={note.item.content} />
                        </View>
                        <TouchableOpacity onPress={() => handleDeleteItem(note.item.noteId)} style={{ justifyContent: 'center', alignContent: 'center', width: '10%' }}>
                            <Ionicons name="trash-bin" size={20} color="red" />
                        </TouchableOpacity>
                    </View>
                )
            }}
            refreshing={refreshing}
            onRefresh={onRefresh} />
    )
}

const styles = StyleSheet.create({
    note: { marginTop: '5%', marginHorizontal: '10%', gap: '5%' }
});


export default Notes
