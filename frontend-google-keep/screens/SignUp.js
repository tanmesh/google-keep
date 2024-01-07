import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { View, Text, StyleSheet, TextInput, TouchableOpacity } from 'react-native'
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function Signup() {
    const [emailId, setEmailId] = useState('');
    const [password, setPassword] = useState('');

    const navigation = useNavigation();

    const storeAccessToken = async (accessToken) => {
        setAccessToken(accessToken)

        AsyncStorage.setItem('accessToken', accessToken)
            .then(() => {
                console.log('Access token stored: ' + accessToken);

                navigation.navigate('Home');
            })
            .catch((error) => {
                // Error 
                console.log('Error storing access token:', error);
            });

        AsyncStorage.setItem('emailId', emailId)
            .then(() => {
                console.log('emailId stored: ' + emailId);
                navigation.navigate('Home');
            })
            .catch((error) => {
                // Error 
                console.log('Error storing emailId:', emailId);
            });
    }

    const handleSignup = () => {
        console.log('emailId:', emailId);
        console.log('Password:', password);
        axios.post('http://localhost:8080/user/signin', {
            emailId: emailId,
            password: password,
        })
            .then((response) => {
                navigation.navigate('Login');
            })
            .catch((error) => {
                // Error 
                console.log('Login failed:', error);
            });
    };

    return (
        <View style={styles.container}>
            <Text style={styles.header}>SignUp</Text>

            <View style={[styles.inputContainer, { flexDirection: 'row' }]}>
                <Text style={{ fontSize: 20, width: '35%' }}>EmailId : </Text>
                <TextInput
                    style={styles.inputField}
                    value={emailId}
                    onChangeText={(text) => setEmailId(text)}
                    placeholder="Enter EmailId"
                    autoCapitalize='none'
                ></TextInput>
            </View>
            <View style={[styles.passwordContainer, { flexDirection: 'row' }]}>
                <Text style={{ fontSize: 20, width: '35%' }}>Password : </Text>
                <TextInput
                    style={styles.passwordField}
                    value={password}
                    onChangeText={(text) => setPassword(text)}
                    placeholder="Enter password"
                    secureTextEntry={true}
                    autoCapitalize='none'
                ></TextInput>
            </View>

            <TouchableOpacity style={styles.loginButton} onPress={handleSignup}>
                <Text style={styles.loginButtonText}>SignUp</Text>
            </TouchableOpacity>
        </View>
    )
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'flex-start',
        marginTop: '20%',
    },
    header: {
        fontSize: 40,
        fontWeight: 600
    },
    inputContainer: {
        marginBottom: 20,
        width: '80%',
        justifyContent: 'flex-start',
        flexDirection: 'row',
        alignItems: 'center',
        marginTop: '50%',
    },
    inputField: {
        borderWidth: 1,
        borderColor: '#d3d3d3',
        borderRadius: 10,
        padding: 10,
        fontSize: 20,
        width: '65%',
    },
    passwordContainer: {
        width: '80%',
        justifyContent: 'flex-start',
        flexDirection: 'row',
        alignItems: 'center',
    },
    passwordField: {
        borderWidth: 1,
        borderColor: '#d3d3d3',
        borderRadius: 10,
        padding: 10,
        fontSize: 20,
        width: '65%',
    },
    loginButton: {
        backgroundColor: 'green',
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