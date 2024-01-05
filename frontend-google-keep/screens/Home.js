import { StyleSheet, Text, View, TouchableOpacity } from 'react-native';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Notes from '../component/Notes';

export default function Home() {
  const [notes, setNotes] = useState([])
  const [accessToken, setAccessToken] = useState('');

  const navigation = useNavigation();

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

  const getExistingNotes = () => {
    axios.get('http://localhost:8080/user/getAllNotes', {
      headers: {
        'x-access-token': `${accessToken}`,
      },
    })
      .then((response) => {
        console.log('Existing notes:', response.data);
        setNotes(response.data);
      })
      .catch((error) => {
        console.error('Error fetching notes:', error);
      });
  }

  useEffect(() => {
    getAccessToken()
    console.log('accessToken:', accessToken);
  }, [])

  useEffect(() => {
    if (accessToken) {
      getExistingNotes();
    }

  }, [accessToken])

  const handleCreateNewNote = () => {
  }

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Google Keep</Text>
      <Notes notes={notes} />
      <View style={{ position: 'relative', marginVertical: '70%' }}>
        <TouchableOpacity style={styles.loginButton} onPress={handleCreateNewNote}>
          <Text style={styles.loginButtonText}>Add a note</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'flex-start',
    marginTop: '20%',
  },
  header: { fontSize: 40, fontWeight: 600 },
  loginButton: {
    backgroundColor: 'grey',
    padding: 10,
    borderRadius: 10,
    marginTop: '20%',
  },
  loginButtonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 16,
  },
});
