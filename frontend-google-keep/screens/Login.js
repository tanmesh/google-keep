import React, { useState } from 'react'
import axios from 'axios';
import { View, Text, StyleSheet, TextInput, TouchableOpacity } from 'react-native'
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { showMessage } from "react-native-flash-message";

function Login({route}) {
    const [emailId, setEmailId] = useState(route.params?.emailId);
    const [password, setPassword] = useState(route.params?.password);

    const navigation = useNavigation();

    const storeAccessToken = async (accessToken) => {
        // Store access token in AsyncStorage
        AsyncStorage.setItem('accessToken', accessToken)
            .then(() => {
                console.log('Access token stored: ' + accessToken);
                navigation.navigate('Home');
            })
            .catch((error) => {
                // Error 
                console.log('Error storing access token:', error);
                showMessage({
                    message: 'Error storing access token',   
                    type: "danger",
                });
            });

        AsyncStorage.setItem('emailId', emailId)
            .then(() => {
                console.log('emailId stored: ' + emailId);
                navigation.navigate('Home');
            })
            .catch((error) => {
                // Error 
                console.log('Error storing emailId:', emailId);
                showMessage({
                    message: 'Error storing emailId',   
                    type: "danger",
                });
            });
    }

    const handleLogin = () => {
        console.log('emailId:', emailId);
        console.log('Password:', password);
        axios.post('http://localhost:8080/user/login', {
            emailId: emailId,
            password: password,
        })
            .then((response) => {
                storeAccessToken(response.data.accessToken)
                showMessage({
                    message: 'Login successful',   
                    type: "success",
                });
            })
            .catch((error) => {
                // Error 
                console.log('Login failed:', error);
                showMessage({
                    message: error.response.data.message,   
                    type: "danger",
                });
            });
    };

    const handleSignUp = () => {
        navigation.navigate('SignUp');
    }

    return (
        <>
            <View style={styles.container}>
                <Text style={styles.header}>Login</Text>

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

                <View style={{ flexDirection: 'row', gap: '20%', marginTop: '5%' }}>
                    <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
                        <Text style={styles.loginButtonText}>Login</Text>
                    </TouchableOpacity>

                    <TouchableOpacity style={[styles.loginButton, { backgroundColor: 'green' }]} onPress={handleSignUp}>
                        <Text style={styles.loginButtonText}>
                            Sign Up
                            <Ionicons name="chevron-forward-outline" size={20} color="#fff" />
                        </Text>
                    </TouchableOpacity>
                </View>
            </View>
        </>
    )
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'flex-start',
        marginTop: '30%',
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
        marginTop: '30%',
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
        backgroundColor: 'blue',
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


export default Login
