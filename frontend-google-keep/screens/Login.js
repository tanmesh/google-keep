import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { View, Text, StyleSheet, TextInput, TouchableOpacity } from 'react-native'
import { useNavigation } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';

function Login() {
    const [emailId, setEmailId] = useState('');
    const [password, setPassword] = useState('');
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

    useEffect(() => {
        getAccessToken()

        if (accessToken) {
            navigation.navigate('Home');
        }
    }, [accessToken])

    const storeAccessToken = async (accessToken) => {
        setAccessToken(accessToken)

        // Store access token in AsyncStorage
        AsyncStorage.setItem('accessToken', accessToken)
            .then(() => {
                console.log('Access token stored: ' + accessToken);
                navigation.navigate('Home');
            })
            .catch((error) => {
                console.error('Error storing access token:', error);
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
            })
            .catch((error) => {
                console.error('Login failed:', error);
            });
    };

    return (
        <View style={styles.container}>
            <Text style={styles.header}>Login</Text>

            <View style={styles.inputContainer}>
                <Text style={{ fontSize: 20, width: '25%' }}>EmailId : </Text>
                <TextInput
                    style={styles.inputField}
                    value={emailId}
                    onChangeText={(text) => setEmailId(text)}
                    placeholder="Enter EmailId"
                    autoCapitalize='none'
                ></TextInput>
            </View>
            <View style={styles.passwordContainer}>
                <Text style={{ fontSize: 20, width: '25%' }}>Password : </Text>
                <TextInput
                    style={styles.passwordField}
                    value={password}
                    onChangeText={(text) => setPassword(text)}
                    placeholder="Enter password"
                    secureTextEntry={true}
                    autoCapitalize='none'
                ></TextInput>
            </View>

            <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
                <Text style={styles.loginButtonText}>Login</Text>
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
        width: '80%',
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
        width: '80%',
    },
    loginButton: {
        backgroundColor: 'blue',
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


export default Login
