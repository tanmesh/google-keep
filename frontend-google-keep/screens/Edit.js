import React, { useEffect, useState } from 'react'
import { Text, View, StyleSheet, TextInput, TouchableOpacity } from 'react-native'
import Ionicons from 'react-native-vector-icons/Ionicons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';

function Edit({ route }) {
    const { id, title, content } = route.params;

    const [title_, setTitle] = useState(title);
    const [listContent, setListContent] = useState([]);
    const [type, setType] = useState('');
    const [accessToken, setAccessToken] = useState('');

    const navigation = useNavigation();

    useEffect(() => {
        try {
            const parsedContent = JSON.parse(content);
            if (Array.isArray(parsedContent.content)) {
                setListContent(parsedContent.content);
                setType(parsedContent.type);
            } else {
                setListContent([{ error: 'Invalid JSON format' }]);
            }
        } catch (error) {
            setListContent([{ error: 'Error parsing JSON' }]);
        }
    }, [content])

    const handleDeleteItem = (indexToDelete) => {
        const updatedList = listContent.filter((_, index) => index !== indexToDelete);
        setListContent(updatedList);
    };

    const handleSave = async () => {
        try {
            const accessToken = await AsyncStorage.getItem('accessToken');
            if (accessToken !== null) {
                setAccessToken(accessToken);

                console.log('NodeId - ' + id)
                console.log('Title - ' + title_)
                console.log('Content as List - ' + listContent)
                console.log('AccessToken - ' + accessToken)
                console.log('JSON.stringify(listContent) - ' + JSON.stringify({ listContent }))

                let content = listContent
                let jsonString = JSON.stringify({ content });
                const response = await fetch('http://localhost:8080/note/edit', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'x-access-token': accessToken,
                    },
                    body: JSON.stringify({
                        noteId: id,
                        title: title_,
                        content: jsonString
                    })
                })

                const data = await response.json();
                console.log(data);

                navigation.navigate('Home');
            } else {
                navigation.navigate('Login');
                console.log('Access token not found');
            }
        } catch (error) {
            // Error 
            console.log('Error retrieving access token:', error);
        }
    }

    const handleListContent = (text, index) => {
        setListContent(prevListContent => {
            return prevListContent.map((item, i) => (i === index ? text : item));
        });
    };

    const handleAddItem = () => {
        setListContent([...listContent, '']);
    };

    const renderListItems = () => {
        return listContent.map((item, index) => (
            <View style={{ flexDirection: 'row', alignItems: 'center', gap: '30', marginHorizontal: '10%' }}>
                <View style={[styles.listItemContainer, { width: '90%' }]} key={index}>
                    <Text style={[styles.body, { justifyContent: 'flex-start' }]}>{index + 1}. </Text>
                    <TextInput
                        style={[styles.body, { justifyContent: 'flex-start' }]}
                        autoCapitalize='none'
                        onChangeText={(text) => handleListContent(text, index)}
                        placeholder="Enter text here"
                        value={item}
                        multiline
                    />
                </View>
                <TouchableOpacity onPress={() => handleDeleteItem(index)} style={{ justifyContent: 'flex-end', width: '10%' }}>
                    <Ionicons name="close-outline" size={40} color="red" />
                </TouchableOpacity>
            </View>
        ))
    };

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.header}
                value={title_}
                onChangeText={setTitle}
                autoCapitalize='none'
                placeholder="Enter title here"
            ></TextInput>
            <View style={styles.bodyContainer}>{renderListItems()}</View>
            <TouchableOpacity style={[styles.loginButton, { backgroundColor: 'black' }]} onPress={handleAddItem}>
                <Text style={styles.loginButtonText}>
                    <Ionicons name="add-outline" size={20} color="#fff" style={{ position: 'absolute', bottom: '20%' }} />
                </Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.loginButton} onPress={handleSave}>
                <Text style={styles.loginButtonText}>Save</Text>
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
        marginTop: '10%',
    },
    bodyContainer: {
        flex: 1,
        justifyContent: 'flex-start',
        alignItems: 'center',
        marginTop: '20%',
    },
    header: { fontSize: 40, fontWeight: 600 },
    body: {
        fontSize: 30,
        fontWeight: 400,
    },
    listItemContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        borderColor: '#d3d3d3',
    },
    loginButton: {
        backgroundColor: 'blue',
        padding: 10,
        borderRadius: 10,
        marginBottom: '10%',
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

export default Edit