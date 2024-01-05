import React, { useEffect, useState } from 'react'
import { Text, View, StyleSheet, TextInput, TouchableOpacity } from 'react-native'
import Ionicons from 'react-native-vector-icons/Ionicons';
import { get } from 'react-native/Libraries/TurboModule/TurboModuleRegistry';

function Edit({ route }) {
    const { id, title, content } = route.params;

    const [title_, setTitle] = useState(title);
    const [content_, setContent] = useState(content);
    const [listContent, setListContent] = useState([]);
    const [type, setType] = useState('');
    const [accessToken, setAccessToken] = useState('');

    useEffect(() => {
        console.log('content:', listContent);
        console.log('title:', title);
    })

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

    const handleSave = () => {
        getAccessToken();

        console.log(id)
        console.log(title_)
        console.log(listContent)
        // axios.post('http://localhost:8080/note/edit', {
        //     noteId: emailId,
        //     title: title_,
        //     content: listContent
        // })
        //     .then((response) => {
        //         storeAccessToken(response.data.accessToken)
        //     })
        //     .catch((error) => {
        //         console.error('Login failed:', error);
        //     });
    }

    const renderListItems = () => {
        return listContent.map((item, index) => (
            <View style={styles.listItemContainer} key={index}>
                <TextInput style={styles.body} autoCapitalize='none'> {index + 1}. {item} </TextInput>
                <TouchableOpacity onPress={() => handleDeleteItem(index)} style={styles.iconContainer}>
                    {item !== ''
                        &&
                        <Ionicons name="close-outline" size={20} color="red" />}
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
            ></TextInput>
            <View style={styles.bodyContainer}>{renderListItems()}</View>
            <Ionicons
                name="add-circle-outline"
                size={60}
                color="black"
                style={{ position: 'absolute', bottom: '20%' }}
                onPress={() => { setListContent([...listContent, '']) }} />
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
        alignItems: 'flex-start',
        marginTop: '20%',
        width: '80%'
    },
    header: { fontSize: 40, fontWeight: 600 },
    body: {
        fontSize: 30,
        fontWeight: 400,
        justifyContent: 'flex-start',
    },
    listItemContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        marginBottom: 10,
    },
    loginButton: {
        backgroundColor: 'blue',
        padding: 10,
        borderRadius: 10,
        marginBottom: '10%',
    },
    loginButtonText: {
        color: '#fff',
        textAlign: 'center',
        fontWeight: 'bold',
        fontSize: 16,
    },
});

export default Edit