import { StyleSheet, Text, View, TouchableOpacity, FlatList } from 'react-native';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Notes from '../component/Notes';
import Ionicons from 'react-native-vector-icons/Ionicons';

export default function Home() {
  const [notes, setNotes] = useState([])
  const [accessToken, setAccessToken] = useState('');
  const [refreshing, setRefreshing] = useState(false);

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
      // Error 
      console.log('Error retrieving access token:', error);
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
        // Error 
        console.log('Error fetching notes:', error);
      });
  }

  useEffect(() => {
    getAccessToken()
    console.log('accessToken:', accessToken);
  }, [accessToken])

  useEffect(() => {
    getExistingNotes()
  }, [accessToken])

  const handleCreateNewNote = () => {
    navigation.navigate('Add', {
      notes: notes,
    });
  }

  const handleRefresh = () => {
    setRefreshing(true);
    getExistingNotes();
    setRefreshing(false);
  }

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Google Keep</Text>
      <Notes
        notes={notes}
        setNotes={setNotes}
        refreshing={refreshing}
        onRefresh={handleRefresh}
        navigation={navigation} />
      <View style={{ position: 'absolute', bottom: 0 }}>
        <TouchableOpacity style={styles.loginButton} onPress={handleCreateNewNote}>
          <Text style={styles.loginButtonText}>
            <Ionicons name="add-outline" size={20} color="#fff" />
          </Text>
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
    marginTop: '10%',
  },
  header: { fontSize: 40, fontWeight: 600 },
  loginButton: {
    backgroundColor: 'black',
    padding: 10,
    borderRadius: 10,
    marginTop: '20%',
    shadowColor: '#171717',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.5,
    shadowRadius: 3,
  },
  loginButtonText: {
    color: '#fff',
    textAlign: 'center',
    fontWeight: 'bold',
    fontSize: 16,
  },
});
